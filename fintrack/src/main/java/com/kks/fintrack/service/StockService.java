package com.kks.fintrack.service;

import java.util.List;

import com.kks.fintrack.beans.Stock;

public interface StockService {
  public Stock saveSTock(Stock stock);

  public List<Stock> getAllStocks();

  public List<Stock> saveAll(List<Stock> stocks);
}
