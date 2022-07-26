package com.kks.fintrack.controllers;

import static com.kks.fintrack.beans.ProgressDetails.ProgressStatus.COMPLETED;
import static com.kks.fintrack.beans.ProgressDetails.ProgressStatus.IN_PROGRESS;

import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;
import java.util.List;
import java.util.Map;

import com.kks.fintrack.beans.ProgressDetails;
import com.kks.fintrack.beans.Stock;
import com.kks.fintrack.datafetchers.NSEFetcher;
import com.kks.fintrack.service.StockPVHistorySerivceImpl;
import com.kks.fintrack.service.StockServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class DataMaintenanceController {

  @Autowired
  StockServiceImpl stockService;
  @Autowired
  StockPVHistorySerivceImpl stockPVHistoryService;

  @GetMapping("/updateStockTable")
  public String fetchAndSaveNSEStocks() {
    List<Stock> stocks = NSEFetcher.getListedEquitySymbols();
    stockService.saveAll(stocks);
    return "Success";
  }

  // This is not being used now because we migrted to spring batch job for
  // asynchronous calls
  @GetMapping("/updateStockPVHistoryTable")
  public String fetchAndSaveNSEStockPVHisotry() {
    List<Stock> stocks = stockService.getAllStocks();
    ProgressDetails pd = new ProgressDetails();
    pd.setStatus(IN_PROGRESS);
    pd.setTask("updateStockPVHistoryTable");
    pd.setTotal(stocks.size());
    pd.setTotalProcessed(0);
    pd.setStartedAt(Instant.now().atZone(ZoneId.of("Asia/Kolkata")).toString());
    Instant start = Instant.now();
    ProgressDetails.taskProgressHash.put("updateStockPVHistoryTable", pd);
    stockPVHistoryService.fetchAndSaveStocksPVHistory(stocks);
    pd.setStatus(COMPLETED);
    pd.setFinishedAt(Instant.now().atZone(ZoneId.of("Asia/Kolkata")).toString());
    pd.setTotalTimeTaken(Duration.between(start, Instant.now()).getSeconds());
    ProgressDetails.taskProgressHash.put("updateStockPVHistoryTable", pd);
    return "Success";
  }

  @GetMapping("/progressMap")
  public Map<String, ProgressDetails> getProgressSummary() {
    return ProgressDetails.taskProgressHash;
  }

}
