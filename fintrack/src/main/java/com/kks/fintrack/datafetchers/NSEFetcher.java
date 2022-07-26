package com.kks.fintrack.datafetchers;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.kks.fintrack.beans.Stock;
import com.kks.fintrack.beans.StockPVHistory;
import com.kks.fintrack.utils.NumUtils;
import com.kks.fintrack.utils.TimeUtils;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

public class NSEFetcher {

  public static List<Stock> getListedEquitySymbols() {
    try {
      URL url = new URL("https://www1.nseindia.com/content/equities/EQUITY_L.csv");
      CSVFormat csvFormat = CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase();
      CSVParser csvParser = CSVParser.parse(url, StandardCharsets.UTF_8, csvFormat);
      List<Stock> stocksList = new ArrayList<>();
      for (CSVRecord csvRecord : csvParser) {
        Stock stock = new Stock();
        stock.setSymbol(csvRecord.get("SYMBOL"));
        stock.setNameOfCompany(csvRecord.get("NAME OF COMPANY"));
        stock.setSeries(csvRecord.get(" SERIES"));
        stock.setDateOfListing(csvRecord.get(" DATE OF LISTING"));
        stock.setPaidUpValue(Long.parseLong(csvRecord.get(" PAID UP VALUE")));
        stock.setMarketLot(Integer.parseInt(csvRecord.get(" MARKET LOT")));
        stock.setIsinNumber(csvRecord.get(" ISIN NUMBER"));
        stock.setFaceValue(Long.parseLong(csvRecord.get(" FACE VALUE")));
        stocksList.add(stock);
      }
      return stocksList;
    } catch (IOException e) {
      e.printStackTrace();
      return null;
    }
    // List<Stock> stocks = new ArrayList();
    // Stock stock = new Stock();
    // stock.setSymbol("RELIANCE");
    // stock.setDateOfListing("29-NOV-1995");
    // stocks.add(stock);
    // return stocks;
  }

  public static String fetchSymbolCount(String symbol) {
    String symbolCountUrl = "https://www1.nseindia.com/marketinfo/sym_map/symbolCount.jsp";
    UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(symbolCountUrl).queryParam("symbol", symbol);
    HttpHeaders headers = new HttpHeaders();
    headers.set("Connection", "keep-alive");
    headers.set("sec-ch-ua", "\"Chromium\";v=\"94\", \"Google Chrome\";v=\"94\", \";Not A Brand\";v=\"99\"");
    headers.set("Accept", "*/*");
    headers.set("X-Requested-With", "XMLHttpRequest");
    headers.set("sec-ch-ua-mobile", "?0");
    headers.set("User-Agent",
        "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/94.0.4606.81 Safari/537.36");
    headers.set("Sec-Fetch-Site", "same-origin");
    headers.set("Sec-Fetch-Mode", "cors");
    headers.set("sec-ch-ua-platform", "\"Linux\"");
    headers.set("Sec-Fetch-Dest", "empty");
    headers.set("Referer", "https://www1.nseindia.com/products/content/equities/equities/eq_security.htm");
    headers.set("Accept-Language", "en-US,en;q=0.9");
    // headers.set("Cookie", "ga=GA1.2.1120804121.1626263775; pointer=1; sym1=" +
    // symbol
    // + "; _gid=GA1.2.633081320.1633882397; AKA_A2=A;
    // bm_mi=3B837B5590AEDC8DD46C2A6509829F24~BVxlqTPRWXIOol31YUJcGmjq8Auoh/zGeP4R+h20kZm0gehi2l4MNOryHc7sk/MRVLZglOqgU80TEeLb3ZWQXxO+dxdiKV9tXeC6djQzE3njIxwGT0om2hJ0rAimd3hiENGcjCTnd6sb+c5s5Z9oB2XjIzf61iJ6RDD9WAuGVRWJ4cgcumJPbl8hS1HJ8IqdgKOHZkdU0l7BRtiTvuusnjsw+4XQsqa4xmAm3OTUlsLqXJlwwtufpj4xY7LbR8E2o4IMK+VyB3kJuM2OnRM/Ag==;
    // ak_bmsc=CB0FC1F96F52217225106C315AE081A6~000000000000000000000000000000~YAAQQ3xBF+0Jv2h8AQAAk8CTcA3mNHe5/o22cnSnq95xF72B2ieCT11reISIXIiMQhv89YanZRCimNFfLgu9nqx1wkSBV+7fJgAwrnVnA1A3HvyYbxE9zYYf/CSsKkDyV5NtJgLIjs2FnfEvmxJ5QVHfk0cXKEbAYf9UjyFv3dEoi+knErmBiywPCEowxSjTYe7zl/VJE8QuKcvv2pLcOIbqRbc/f040Kwjawjv1sBb/tnawqSu42nS3HV6KtgxC8RHhWDIWMx+V596vA9PmJlN2yu5yRGztAfy18Nu31UessRNYoFnSp2Qd3dhwELLQ932tFQkMaXXaG97dyJ4Eu9GzekJxGutlcRMFRg639P4RJsYsBWn4Qv72jBVU1Gr12LymzV2Dm4/T/QdHZ7C8fFYDvm/aYtB1DRMzZJs=;
    // NSE-TEST-1=1944068106.20480.0000;
    // JSESSIONID=36A3D7674A0DAA298EA72F317FF03B73.tomcat2;
    // RT=\"z=1&dm=nseindia.com&si=f9d2a91a-394c-4aaf-92c6-1fd09431d0bd&ss=kumzav2s&sl=d&tt=7xy&bcn=%2F%2F684fc536.akstat.io%2F&ld=l919&nu=bc83bc72f32b478e63220cea705aa17a&cl=lyww\";
    // bm_sv=0C7B2E230E25E7EFB414273861BB330B~jio1nPaw1M9C8dnyzkXjF22Sd5LM77mgX1fa23J6oR/sadNpkiBdhrOkjYg8PW0/fma+qUKNwiNNSy2VaKV0Qws7UCNfs2BjOhdE2flsOBuCvBN8s8JQfmzLjhO5ZVDcg3jrQk6CncGeJ1OZgm6CdNneSYv4x2EirzS/hemes0A='");
    HttpEntity<?> entity = new HttpEntity<>(headers);
    RestTemplate restTemplate = new RestTemplate();

    HttpEntity<String> response = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, entity, String.class);

    return Optional.ofNullable(response.getBody()).map(String::trim).orElse("");
  }

  public static List<StockPVHistory> fetchStockHistoryForPeriod(String from, String to, String symbol,
      String symbolCount) {
    String url = "https://www1.nseindia.com/products/dynaContent/common/productsSymbolMapping.jsp";
    UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url).queryParam("segmentLink", "3")
        .queryParam("symbolCount", symbolCount).queryParam("series", "ALL").queryParam("dateRange", "")
        .queryParam("fromDate", from).queryParam("toDate", to).queryParam("dataType", "PRICEVOLUMEDELIVERABLE")
        .queryParam("symbol", symbol);
    HttpHeaders headers = new HttpHeaders();
    headers.set("Connection", "keep-alive");
    headers.set("sec-ch-ua", "\"Chromium\";v=\"88\", \"Google Chrome\";v=\"88\", \";Not A Brand\";v=\"99\"");
    headers.set("Accept", "*/*");
    headers.set("X-Requested-With", "XMLHttpRequest");
    headers.set("sec-ch-ua-mobile", "?0");
    headers.set("User-Agent",
        "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/88.0.4324.96 Safari/537.36");
    headers.set("Sec-Fetch-Site", "same-origin");
    headers.set("Sec-Fetch-Mode", "cors");
    headers.set("Sec-Fetch-Dest", "empty");
    headers.set("Referer", "https://www1.nseindia.com/products/content/equities/equities/eq_security.htm");
    headers.set("Accept-Language", "en-US,en;q=0.9");
    // System.out.println("fetching stock history for the period: from: " + from + "
    // to: " + to);
    HttpEntity<?> entity = new HttpEntity<>(headers);
    RestTemplate restTemplate = new RestTemplate();
    HttpEntity<String> response = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, entity, String.class);

    // System.out.println(response.getBody());
    List<StockPVHistory> stockPVHistories = new ArrayList<>();
    Document doc = Jsoup.parse(response.getBody());
    // System.out.println(doc.toString());
    if (doc.select("table").isEmpty()) {
      return stockPVHistories;
    }
    Element table = doc.select("table").get(0);
    Elements rows = table.select("tr");

    for (int i = 1; i < rows.size(); i++) { // first row is the col names so skip it.
      Element row = rows.get(i);
      Elements cols = row.select("td");
      // System.out.println(
      // cols.get(0).text() + " " + cols.get(1).text() + " " + cols.get(2).text() + "
      // " + cols.get(3).text() + " "
      // + cols.get(4).text() + " " + cols.get(5).text() + " " + cols.get(6).text() +
      // " " + cols.get(7).text());
      if (cols.size() > 14) {
        StockPVHistory stockPVHistory = new StockPVHistory();
        stockPVHistory.setSymbol(cols.get(0).text());
        stockPVHistory.setSeries(cols.get(1).text());
        stockPVHistory.setDate(cols.get(2).text());
        stockPVHistory.setPrevClose(NumUtils.toDouble(cols.get(3).text()));
        stockPVHistory.setOpenPrice(NumUtils.toDouble(cols.get(4).text()));
        stockPVHistory.setHighPrice(NumUtils.toDouble(cols.get(5).text()));
        stockPVHistory.setLowPrice(NumUtils.toDouble(cols.get(6).text()));
        stockPVHistory.setLastPrice(NumUtils.toDouble(cols.get(7).text()));
        stockPVHistory.setClosePrice(NumUtils.toDouble(cols.get(8).text()));
        stockPVHistory.setVwap(NumUtils.toDouble(cols.get(9).text()));
        stockPVHistory.setTotalTradedQuantity(NumUtils.toLong(cols.get(10).text()));
        stockPVHistory.setTurnover(NumUtils.toDouble(cols.get(11).text()));
        stockPVHistory.setTotalNoOfTrades(NumUtils.toLong(cols.get(12).text()));
        stockPVHistory.setDeliveryQty(NumUtils.toLong(cols.get(13).text()));
        stockPVHistory.setDeliveryPerc(NumUtils.toDouble(cols.get(14).text()));
        if (stockPVHistory.getPrevClose() == 0.0) {
          stockPVHistory.setPrevClose(stockPVHistory.getClosePrice());
        }
        stockPVHistory.setChangePerc(
            ((stockPVHistory.getClosePrice() - stockPVHistory.getPrevClose()) / stockPVHistory.getPrevClose()) * 100);
        stockPVHistories.add(stockPVHistory);
      }
    }
    return stockPVHistories;
  }

  public static List<StockPVHistory> fetchStockHistoryForPeriodOriginal(String from, String to, String symbol,
      String symbolCount) {
    String url = "https://www1.nseindia.com/products/dynaContent/common/productsSymbolMapping.jsp";
    UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url).queryParam("segmentLink", "3")
        .queryParam("symbolCount", symbolCount).queryParam("series", "ALL").queryParam("dateRange", "")
        .queryParam("fromDate", from).queryParam("toDate", to).queryParam("dataType", "PRICEVOLUMEDELIVERABLE")
        .queryParam("symbol", symbol);
    HttpHeaders headers = new HttpHeaders();
    headers.set("Connection", "keep-alive");
    headers.set("sec-ch-ua", "\"Chromium\";v=\"88\", \"Google Chrome\";v=\"88\", \";Not A Brand\";v=\"99\"");
    headers.set("Accept", "*/*");
    headers.set("X-Requested-With", "XMLHttpRequest");
    headers.set("sec-ch-ua-mobile", "?0");
    headers.set("User-Agent",
        "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/88.0.4324.96 Safari/537.36");
    headers.set("Sec-Fetch-Site", "same-origin");
    headers.set("Sec-Fetch-Mode", "cors");
    headers.set("Sec-Fetch-Dest", "empty");
    headers.set("Referer", "https://www1.nseindia.com/products/content/equities/equities/eq_security.htm");
    headers.set("Accept-Language", "en-US,en;q=0.9");

    HttpEntity<?> entity = new HttpEntity<>(headers);
    RestTemplate restTemplate = new RestTemplate();
    HttpEntity<String> response = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, entity, String.class);

    // System.out.println(response.getBody());

    Document doc = Jsoup.parse(response.getBody());
    Element table = doc.select("table").get(0);
    Elements rows = table.select("tr");
    List<StockPVHistory> stockPVHistories = new ArrayList<>();
    for (int i = 1; i < rows.size(); i++) { // first row is the col names so skip it.
      Element row = rows.get(i);
      Elements cols = row.select("td");
      // System.out.println(
      // cols.get(0).text() + " " + cols.get(1).text() + " " + cols.get(2).text() + "
      // " + cols.get(3).text() + " "
      // + cols.get(4).text() + " " + cols.get(5).text() + " " + cols.get(6).text() +
      // " " + cols.get(7).text());
      if (cols.size() > 14) {
        StockPVHistory stockPVHistory = new StockPVHistory();
        stockPVHistory.setSymbol(cols.get(0).text());
        stockPVHistory.setSeries(cols.get(1).text());
        stockPVHistory.setDate(TimeUtils.toDD_MM_YYYY(cols.get(2).text()));
        stockPVHistory.setPrevClose(NumUtils.toDouble(cols.get(3).text()));
        stockPVHistory.setOpenPrice(NumUtils.toDouble(cols.get(4).text()));
        stockPVHistory.setHighPrice(NumUtils.toDouble(cols.get(5).text()));
        stockPVHistory.setLowPrice(NumUtils.toDouble(cols.get(6).text()));
        stockPVHistory.setLastPrice(NumUtils.toDouble(cols.get(7).text()));
        stockPVHistory.setClosePrice(NumUtils.toDouble(cols.get(8).text()));
        stockPVHistory.setVwap(NumUtils.toDouble(cols.get(9).text()));
        stockPVHistory.setTotalTradedQuantity(NumUtils.toLong(cols.get(10).text()));
        stockPVHistory.setTurnover(NumUtils.toDouble(cols.get(11).text()));
        stockPVHistory.setTotalNoOfTrades(NumUtils.toLong(cols.get(12).text()));
        stockPVHistory.setDeliveryQty(NumUtils.toLong(cols.get(13).text()));
        stockPVHistory.setDeliveryPerc(NumUtils.toDouble(cols.get(14).text()));
        stockPVHistories.add(stockPVHistory);
      }

    }
    return stockPVHistories;
  }

  // public static void main(String[] args) {
  // // NSEFetcher.fetchStockHistoryForPeriod("01-09-2021", "29-09-2021",
  // "reliance",
  // // "2");
  // // NSEFetcher.fetchSymbolCount("reliance");
  // Object testObject = "10";
  // System.out.println(Pattern.matches("^[\\+\\-]{0,1}[0-9]+[\\.\\,]{0,1}[0-9]+$",
  // (CharSequence) testObject));
  // }
}