package com.kks.fintrack.service;

import java.util.List;

import com.kks.fintrack.beans.Stock;
import com.kks.fintrack.repository.StockRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StockServiceImpl implements StockService {

  @Autowired
  private StockRepository repository;

  @Override
  public Stock saveSTock(Stock stock) {
    return repository.save(stock);
  }

  @Override
  public List<Stock> getAllStocks() {
    return repository.findAll();
  }

  @Override
  public List<Stock> saveAll(List<Stock> stocks) {
    return repository.saveAll(stocks);
  }

}
