package com.kks.fintrack.service;

import java.util.List;

import com.kks.fintrack.beans.Stock;
import com.kks.fintrack.beans.StockPVHistory;
import com.kks.fintrack.beans.StockRecentDataUpdate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface StockPVHistoryService {

  StockPVHistory saveStockPHistory(StockPVHistory stockPVHistory);

  void fetchAndSaveStocksPVHistory(List<Stock> stocks);

  List<StockPVHistory> saveAllStockPHistory(List<StockPVHistory> stockPVHistory);

  Page<StockRecentDataUpdate> findLastUpdatedTime(Pageable pageable);

}
