package com.kks.fintrack.service;

import java.time.Instant;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import com.kks.fintrack.beans.DateWindow;
import com.kks.fintrack.beans.ProgressDetails;
import com.kks.fintrack.beans.Stock;
import com.kks.fintrack.beans.StockPVHistory;
import com.kks.fintrack.beans.StockRecentDataUpdate;
import com.kks.fintrack.datafetchers.NSEFetcher;
import com.kks.fintrack.repository.StockPVHistoryRepository;
import com.kks.fintrack.utils.TimeUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class StockPVHistorySerivceImpl implements StockPVHistoryService {

  @Autowired
  private StockPVHistoryRepository repository;

  @Override
  public StockPVHistory saveStockPHistory(StockPVHistory stockPVHistory) {
    return repository.save(stockPVHistory);
  }

  @Override
  public void fetchAndSaveStocksPVHistory(List<Stock> stocks) {
    ProgressDetails pd = ProgressDetails.taskProgressHash.get("updateStockPVHistoryTable");
    int totalNoOfStocks = stocks.size();
    for (int i = 0; i < totalNoOfStocks; i++) {
      Stock stock = stocks.get(i);
      System.out.println("Processing " + stock.getSymbol());
      String symbolCount = NSEFetcher.fetchSymbolCount(stock.getSymbol());
      List<DateWindow> dateWindows = TimeUtils.splitDateByNdaysRange(stock.getDateOfListing(),
          TimeUtils.nowInDDMMMYYYY(), 90);
      List<StockPVHistory> stockPVHistories = new ArrayList<>();
      dateWindows.forEach(dateWindow -> {
        List<StockPVHistory> stockPVHistoriesTemp = NSEFetcher.fetchStockHistoryForPeriod(dateWindow.getFromDate(),
            dateWindow.getToDate(), stock.getSymbol(), symbolCount);
        // Save these to data base;
        stockPVHistories.addAll(stockPVHistoriesTemp);
      });
      if (!stockPVHistories.isEmpty()) {
        repository.saveAllAndFlush(stockPVHistories);
      }
      pd.setTotalProcessed(pd.getTotalProcessed() + 1);
      pd.setLastUpdatedAt(Instant.now().atZone(ZoneId.of("Asia/Kolkata")).toString());
      ProgressDetails.taskProgressHash.put("updateStockPVHistoryTable", pd);
    }
  }

  @Override
  public List<StockPVHistory> saveAllStockPHistory(List<StockPVHistory> stockPVHistory) {
    return repository.saveAllAndFlush(stockPVHistory);
  }

  @Override
  public Page<StockRecentDataUpdate> findLastUpdatedTime(Pageable pageable) {
    return repository.findLastUpdatedTime(pageable);
  }

}
