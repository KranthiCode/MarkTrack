package com.kks.fintrack.controllers;

import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.kks.fintrack.beans.ProgressDetails;
import com.kks.fintrack.beans.ProgressDetails.ProgressStatus;
import com.kks.fintrack.beans.Stock;
import com.kks.fintrack.beans.StockPVHistory;
import com.kks.fintrack.beans.StockRecentDataUpdate;
import com.kks.fintrack.repository.StockPVHistoryRepository;
import com.kks.fintrack.service.StockServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class PracticeController {
  @Autowired
  StockServiceImpl stockService;

  @Autowired
  StockPVHistoryRepository stockPVHistoryRepository;

  @GetMapping("/streams")
  public Map<String, List<Stock>> twentyPlusYearCompanies() {
    List<Stock> stocks = stockService.getAllStocks();
    return stocks.stream().filter(stock -> Integer.parseInt(stock.getDateOfListing().substring(7)) < 2000)
        .sorted((stock1, stock2) -> stock1.getNameOfCompany().compareTo(stock2.getNameOfCompany()))
        .sorted((stock1, stock2) -> stock1.getDateOfListing().substring(7)
            .compareTo(stock2.getDateOfListing().substring(7)))
        // .map(stock -> stock.getNameOfCompany() + " " + stock.getDateOfListing())
        .map(stock -> {
          stock.setDateOfListing(stock.getDateOfListing().substring(7));
          return stock;
        }).collect(Collectors.groupingBy(Stock::getDateOfListing));
  }

  @GetMapping("/test")
  public Map<String, List<Stock>> test() {
    List<Stock> stocks = stockService.getAllStocks();
    return stocks.stream().filter(stock -> Integer.parseInt(stock.getDateOfListing().substring(7)) < 2000)
        .sorted((stock1, stock2) -> stock1.getNameOfCompany().compareTo(stock2.getNameOfCompany()))
        .sorted((stock1, stock2) -> stock1.getDateOfListing().substring(7)
            .compareTo(stock2.getDateOfListing().substring(7)))
        // .map(stock -> stock.getNameOfCompany() + " " + stock.getDateOfListing())
        .map(stock -> {
          stock.setDateOfListing(stock.getDateOfListing().substring(7));
          return stock;
        }).collect(Collectors.groupingBy(Stock::getDateOfListing));

  }

  @GetMapping(value = "/cagr")
  public void getCAGROfAllCompanies() {
    List<StockRecentDataUpdate> list = stockPVHistoryRepository.lastUpdatedTime();
    ProgressDetails pd = new ProgressDetails();
    pd.setStatus(ProgressStatus.IN_PROGRESS);
    pd.setTask("updateStockPVHistoryTable");
    pd.setTotal(list.size());
    pd.setTotalProcessed(0);
    pd.setStartedAt(Instant.now().atZone(ZoneId.of("Asia/Kolkata")).toString());
    Instant start = Instant.now();
    ProgressDetails.taskProgressHash.put("updateStockPVHistoryTable", pd);
    List<StockPVHistory> pvList = new ArrayList<>();

    list.stream().forEach(stockRecentUpdate -> {
      System.out.println("Processing " + stockRecentUpdate.getSymbol());
      pvList.add(stockPVHistoryRepository.getLatestRecord(stockRecentUpdate.getSymbol(),
          stockRecentUpdate.getLastUpdatedDate()));
      pd.setTotalProcessed(pd.getTotalProcessed() + 1);
      pd.setLastUpdatedAt(Instant.now().atZone(ZoneId.of("Asia/Kolkata")).toString());
    });

    pd.setStatus(ProgressStatus.COMPLETED);
    pd.setFinishedAt(Instant.now().atZone(ZoneId.of("Asia/Kolkata")).toString());
    pd.setTotalTimeTaken(Duration.between(start, Instant.now()).getSeconds());
    ProgressDetails.taskProgressHash.put("updateStockPVHistoryTable", pd);

    // latestUpdates.stream().forEach(stock -> {
    // System.out.println(stock.getSymbol() + " : " + stock.getLastUpdatedDate());
    // });
    // // List<Stock> stocks = stockService.getAllStocks();
  }

}
