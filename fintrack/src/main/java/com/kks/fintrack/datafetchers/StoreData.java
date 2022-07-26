package com.kks.fintrack.datafetchers;

import java.util.List;

import com.kks.fintrack.beans.Stock;
import com.kks.fintrack.utils.TimeUtils;

public class StoreData {

  public void fetchStocksData() {
    String now = Long.toString(TimeUtils.currentEpochSeconds());

    // 1. Fetch All equity symbols and their date of listing
    List<Stock> symbols = NSEFetcher.getListedEquitySymbols();

    symbols.forEach((symbol) -> {
      String period1 = Long.toString(TimeUtils.toEpochSeconds(symbol.getDateOfListing()));

      // 2. Fetch daily OHLC, volume data from the date of listing for each stock
      // List<Stock> stocks = YahooFetcher.fetchMaxHistoricalSymbolData(symbol,
      // period1, now); // From Yahoo

    });
  }

}
