package com.kks.fintrack.batch;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

import com.kks.fintrack.beans.DateWindow;
import com.kks.fintrack.beans.Stock;
import com.kks.fintrack.beans.StockPVHistory;
import com.kks.fintrack.datafetchers.NSEFetcher;
import com.kks.fintrack.repository.StockRepository;
import com.kks.fintrack.service.StockPVHistorySerivceImpl;
import com.kks.fintrack.utils.TimeUtils;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.integration.async.AsyncItemProcessor;
import org.springframework.batch.integration.async.AsyncItemWriter;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.adapter.ItemWriterAdapter;
import org.springframework.batch.item.data.RepositoryItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
@EnableBatchProcessing
public class HistoricalDataFetcher {

  @Autowired
  StockPVHistorySerivceImpl stockPVHistorySerivceImpl;
  @Autowired
  StockRepository stockRepository;

  @Bean
  public Job historicalDataFetcherAsyncJob(JobBuilderFactory jobBuilderFactory) {
    return jobBuilderFactory.get("historicalDataFetcherAsyncProcess").incrementer(new RunIdIncrementer())
        .flow(historicalDataFetcherAsyncManagerStep(null)).end().build();
  }

  @Bean
  public Step historicalDataFetcherAsyncManagerStep(StepBuilderFactory stepBuilderFactory) {
    return stepBuilderFactory.get("step1").<Stock, Future<List<StockPVHistory>>>chunk(10)
        .reader(historicalDataFetcherAsyncReader()).processor(historicalDataFetcherAsyncProcessor())
        .writer(historicalDataFetcherAsyncWriter()).taskExecutor(historicalDataFetcherTaskExecutor()).build();
  }

  @Bean
  public RepositoryItemReader<Stock> historicalDataFetcherAsyncReader() {
    RepositoryItemReader reader = new RepositoryItemReader<>();
    reader.setRepository(stockRepository);
    reader.setMethodName("findAll");
    reader.setPageSize(50);
    Map<String, Direction> sorts = new HashMap<>();
    sorts.put("symbol", Direction.ASC);
    reader.setSort(sorts);
    return reader;
  }

  @Bean
  public AsyncItemProcessor<Stock, List<StockPVHistory>> historicalDataFetcherAsyncProcessor() {
    AsyncItemProcessor<Stock, List<StockPVHistory>> asyncItemProcessor = new AsyncItemProcessor<>();
    asyncItemProcessor.setDelegate(historicalDataFetcherItemProcessor());
    asyncItemProcessor.setTaskExecutor(historicalDataFetcherTaskExecutor());
    return asyncItemProcessor;
  }

  @Bean
  public ItemProcessor<Stock, List<StockPVHistory>> historicalDataFetcherItemProcessor() {
    return new ItemProcessor<Stock, List<StockPVHistory>>() {
      @Override
      public List<StockPVHistory> process(Stock stock) throws Exception {
        System.out.println("Processing " + stock.getSymbol());
        String symbolCount = NSEFetcher.fetchSymbolCount((stock).getSymbol());
        List<DateWindow> dateWindows = TimeUtils.splitDateByNdaysRange(stock.getDateOfListing(),
            TimeUtils.nowInDDMMMYYYY(), 90);
        List<StockPVHistory> stockPVHistories = new ArrayList<>();
        dateWindows.forEach(dateWindow -> {
          List<StockPVHistory> stockPVHistoriesTemp = NSEFetcher.fetchStockHistoryForPeriod(dateWindow.getFromDate(),
              dateWindow.getToDate(), stock.getSymbol(), symbolCount);
          stockPVHistories.addAll(stockPVHistoriesTemp);
        });
        return stockPVHistories;
      }
    };
  }

  @Bean
  public TaskExecutor historicalDataFetcherTaskExecutor() {
    ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
    executor.setCorePoolSize(64);
    executor.setMaxPoolSize(64);
    executor.setQueueCapacity(64);
    executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
    executor.setThreadNamePrefix("MultiThreaded-");
    return executor;
  }

  @Bean
  public AsyncItemWriter<List<StockPVHistory>> historicalDataFetcherAsyncWriter() {
    AsyncItemWriter<List<StockPVHistory>> asyncItemWriter = new AsyncItemWriter<>();
    asyncItemWriter.setDelegate(historicalDataFetcherItemWriter());
    return asyncItemWriter;
  }

  @Bean
  public ItemWriterAdapter<List<StockPVHistory>> historicalDataFetcherItemWriter() {
    ItemWriterAdapter<List<StockPVHistory>> wr = new ItemWriterAdapter<>();
    wr.setTargetObject(stockPVHistorySerivceImpl);
    wr.setTargetMethod("saveAllStockPHistory");
    return wr;
  }

}
