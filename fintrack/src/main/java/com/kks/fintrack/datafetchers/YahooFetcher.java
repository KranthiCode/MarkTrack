package com.kks.fintrack.datafetchers;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import com.kks.fintrack.beans.StockPVHistory;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

public class YahooFetcher {

  public static List<StockPVHistory> fetchMaxHistoricalSymbolData(String symbol, String period1, String period2) {
    String maxHistoryFetchUrl = "https://query1.finance.yahoo.com/v7/finance/download/" + symbol + ".NS?period1="
        + period1 + "&period2=" + period2 + "&interval=1d&events=history&includeAdjustedClose=true";
    try {
      URL url = new URL(maxHistoryFetchUrl);

      CSVFormat csvFormat = CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase();
      List<StockPVHistory> data = new ArrayList<>();
      CSVParser csvParser = CSVParser.parse(url, StandardCharsets.UTF_8, csvFormat);
      for (CSVRecord csvRecord : csvParser) {
        StockPVHistory stock = new StockPVHistory();
        stock.setSymbol(symbol);
        stock.setDate(csvRecord.get("Date"));
        // TODO: fix this later
        // stock.setOpen(Double.parseDouble(csvRecord.get("Open")));
        // stock.setHigh(Double.parseDouble(csvRecord.get("High")));
        // stock.setLow(Double.parseDouble(csvRecord.get("Low")));
        // stock.setClose(Double.parseDouble(csvRecord.get("Close")));
        // stock.setAdjClose(Double.parseDouble(csvRecord.get("Adj Close")));
        // stock.setVolume(Long.parseLong(csvRecord.get("Volume")));
        data.add(stock);
      }
      return data;
    } catch (IOException e) {
      e.printStackTrace();
      return null;
    }
  }

}
