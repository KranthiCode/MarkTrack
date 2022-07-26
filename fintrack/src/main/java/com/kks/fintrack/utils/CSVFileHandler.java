package com.kks.fintrack.utils;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.kks.fintrack.beans.StockPVHistory;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

public class CSVFileHandler {

  public List<StockPVHistory> fetchDataFromYahoo(String symbol) throws IOException {
    URL url = new URL("https://query1.finance.yahoo.com/v7/finance/download/" + symbol
        + ".NS?period1=1468454400&period2=1626220800&interval=1d&events=history&includeAdjustedClose=true");
    CSVFormat csvFormat = CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase();
    List<StockPVHistory> data = new ArrayList<>();
    try (CSVParser csvParser = CSVParser.parse(url, StandardCharsets.UTF_8, csvFormat)) {
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
    } catch (IOException e) {
      e.printStackTrace();
    }
    return data;
  }

  // Mapping CSV headers in line with class through annotation
  public static List<StockPVHistory> fetchData(String stockName) throws IOException {
    URL url = new URL("https://query1.finance.yahoo.com/v7/finance/download/" + stockName
        + ".NS?period1=1468454400&period2=1626220800&interval=1d&events=history&includeAdjustedClose=true");
    CsvMapper m = new CsvMapper();
    CsvSchema schema = m.schemaFor(StockPVHistory.class).withHeader();
    try {
      MappingIterator<StockPVHistory> r = m.readerFor(StockPVHistory.class).with(schema).readValues(url);
      return r.readAll();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return Collections.emptyList();
  }

}
