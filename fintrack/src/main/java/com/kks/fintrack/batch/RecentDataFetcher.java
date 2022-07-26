package com.kks.fintrack.batch;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

import com.kks.fintrack.beans.DateWindow;
import com.kks.fintrack.beans.StockPVHistory;
import com.kks.fintrack.beans.StockRecentDataUpdate;
import com.kks.fintrack.datafetchers.NSEFetcher;
import com.kks.fintrack.repository.StockPVHistoryRepository;
import com.kks.fintrack.repository.StockRepository;
import com.kks.fintrack.service.StockPVHistorySerivceImpl;
import com.kks.fintrack.utils.TimeUtils;
import static com.kks.fintrack.utils.TimeUtils.addNdays;

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
public class RecentDataFetcher {

  @Autowired
  StockPVHistorySerivceImpl stockPVHistorySerivceImpl;
  @Autowired
  StockRepository stockRepository;
  @Autowired
  StockPVHistoryRepository stockPVHistoryRepository;

  @Bean
  public Job recentDataFetcherAsyncJob(JobBuilderFactory jobBuilderFactory) {
    return jobBuilderFactory.get("recentDataFetcherAsyncProcess").incrementer(new RunIdIncrementer())
        .flow(recentDataFetcherAsyncManagerStep(null)).end().build();
  }

  @Bean
  public Step recentDataFetcherAsyncManagerStep(StepBuilderFactory stepBuilderFactory) {
    return stepBuilderFactory.get("step1").<StockRecentDataUpdate, Future<List<StockPVHistory>>>chunk(10)
        .reader(recentDataFetcherAsyncReader()).processor(recentDataFetcherAsyncProcessor())
        .writer(recentDataFetcherAsyncWriter()).taskExecutor(recentDataFetcherTaskExecutor()).build();
  }

  @Bean
  public RepositoryItemReader<StockRecentDataUpdate> recentDataFetcherAsyncReader() {
    RepositoryItemReader reader = new RepositoryItemReader<>();
    reader.setRepository(stockPVHistoryRepository);
    reader.setMethodName("findLastUpdatedTime");
    reader.setPageSize(50);
    Map<String, Direction> sorts = new HashMap<>();
    sorts.put("symbol", Direction.ASC);
    reader.setSort(sorts);
    return reader;
  }

  @Bean
  public AsyncItemProcessor<StockRecentDataUpdate, List<StockPVHistory>> recentDataFetcherAsyncProcessor() {
    AsyncItemProcessor<StockRecentDataUpdate, List<StockPVHistory>> asyncItemProcessor = new AsyncItemProcessor<>();
    asyncItemProcessor.setDelegate(recentDataFetcherItemProcessor());
    asyncItemProcessor.setTaskExecutor(recentDataFetcherTaskExecutor());
    return asyncItemProcessor;
  }

  @Bean
  public ItemProcessor<StockRecentDataUpdate, List<StockPVHistory>> recentDataFetcherItemProcessor() {
    return new ItemProcessor<StockRecentDataUpdate, List<StockPVHistory>>() {
      @Override
      public List<StockPVHistory> process(StockRecentDataUpdate stock) throws Exception {
        System.out.println("Processing " + stock.getSymbol());
        String symbolCount = NSEFetcher.fetchSymbolCount(stock.getSymbol());
        String fromDate = addNdays(stock.getLastUpdatedDate(), 1, "dd-MMM-yyyy", "dd-MMM-yyyy");
        List<DateWindow> dateWindows = TimeUtils.splitDateByNdaysRange(fromDate, TimeUtils.nowInDDMMMYYYY(), 90);
        List<StockPVHistory> stockPVHistories = new ArrayList<>();
        dateWindows.forEach(dateWindow -> {
          List<StockPVHistory> stockPVHistoriesTemp = NSEFetcher.fetchStockHistoryForPeriod(dateWindow.getFromDate(),
              dateWindow.getToDate(), stock.getSymbol(), symbolCount);
          stockPVHistories.addAll(stockPVHistoriesTemp);
        });
        // dateWindows.forEach(System.out::println);
        return stockPVHistories;
      }
    };
  }

  @Bean
  public TaskExecutor recentDataFetcherTaskExecutor() {
    ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
    executor.setCorePoolSize(64);
    executor.setMaxPoolSize(64);
    executor.setQueueCapacity(64);
    executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
    executor.setThreadNamePrefix("MultiThreaded-");
    return executor;
  }

  @Bean
  public AsyncItemWriter<List<StockPVHistory>> recentDataFetcherAsyncWriter() {
    AsyncItemWriter<List<StockPVHistory>> asyncItemWriter = new AsyncItemWriter<>();
    asyncItemWriter.setDelegate(recentDataFetcherItemWriter());
    return asyncItemWriter;
  }

  @Bean
  public ItemWriterAdapter<List<StockPVHistory>> recentDataFetcherItemWriter() {
    ItemWriterAdapter<List<StockPVHistory>> wr = new ItemWriterAdapter<>();
    wr.setTargetObject(stockPVHistorySerivceImpl);
    wr.setTargetMethod("saveAllStockPHistory");
    return wr;
  }

}
