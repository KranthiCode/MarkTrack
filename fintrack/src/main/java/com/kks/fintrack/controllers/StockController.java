package com.kks.fintrack.controllers;

import java.util.List;

import com.kks.fintrack.beans.Stock;
import com.kks.fintrack.service.StockServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class StockController {

  @Autowired
  StockServiceImpl stockService;

  @GetMapping("/getNSEStocksList")
  public List<Stock> getNSEStocksList() {
    return stockService.getAllStocks();
  }

}
