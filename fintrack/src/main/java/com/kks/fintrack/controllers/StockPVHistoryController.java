package com.kks.fintrack.controllers;

import com.kks.fintrack.beans.StockRecentDataUpdate;
import com.kks.fintrack.service.StockPVHistoryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping("/stock")
public class StockPVHistoryController {

  @Autowired
  StockPVHistoryService stockPVHistoryService;

  @GetMapping("/latesttime")
  public Page<StockRecentDataUpdate> latesttime(Pageable pageable) {
    return stockPVHistoryService.findLastUpdatedTime(pageable);
  }

}
