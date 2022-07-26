package com.kks.fintrack.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

import com.kks.fintrack.beans.DateWindow;

public class TimeUtils {

  private static final String DD_MMM_YYYY = "dd-MMM-yyyy";
  private static final String DD_MM_YYYY = "dd-MM-yyyy";

  public static Long currentEpochSeconds() {
    return Instant.now().getEpochSecond();
  }

  public static String nowInDDMMMYYYY() {
    DateTimeFormatter dtf_DDMMYYY = new DateTimeFormatterBuilder().parseCaseInsensitive().appendPattern(DD_MM_YYYY)
        .toFormatter();
    return LocalDate.now().format(dtf_DDMMYYY);
  }

  public static Long toEpochSeconds(String dateStr) {
    try {
      SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
      return sdf.parse(dateStr).getTime() / 1000;
    } catch (ParseException e) {
      e.printStackTrace();
      return null;
    }
  }

  public static String toDD_MM_YYYY(String dateStr) {
    DateTimeFormatter dtf_DDMMMYYYY = new DateTimeFormatterBuilder().parseCaseInsensitive().appendPattern(DD_MMM_YYYY)
        .toFormatter();
    DateTimeFormatter dtf_DDMMYYYY = new DateTimeFormatterBuilder().parseCaseInsensitive().appendPattern(DD_MM_YYYY)
        .toFormatter();
    LocalDate ld = LocalDate.parse(dateStr, dtf_DDMMMYYYY);
    return ld.format(dtf_DDMMYYYY);
  }

  // Splits a date range into smaller date ranges
  public static List<DateWindow> splitDateByNdaysRange(String from, String to, int nDays) {

    try {
      List<DateWindow> dateWindowsList = new ArrayList<>();
      DateTimeFormatter dtf_DDMMMYYYY = new DateTimeFormatterBuilder().parseCaseInsensitive().appendPattern(DD_MMM_YYYY)
          .toFormatter();
      DateTimeFormatter dtf_DDMMYYYY = new DateTimeFormatterBuilder().parseCaseInsensitive().appendPattern(DD_MM_YYYY)
          .toFormatter();
      LocalDate fromDate = LocalDate.parse(from, dtf_DDMMMYYYY);
      LocalDate toDate = LocalDate.parse(to, dtf_DDMMYYYY);

      LocalDate tempDate;
      while ((tempDate = fromDate.plusDays(nDays)).isBefore(toDate)) {
        dateWindowsList.add(new DateWindow(fromDate.format(dtf_DDMMYYYY), tempDate.format(dtf_DDMMYYYY)));
        fromDate = tempDate.plusDays(1);
      }
      dateWindowsList.add(new DateWindow(fromDate.format(dtf_DDMMYYYY), toDate.format(dtf_DDMMYYYY)));
      return dateWindowsList;

    } catch (DateTimeParseException e) {
      e.printStackTrace();
    }

    return null;
  }

  public static String addNdays(String date, int noOfDays, String currentDatePattern, String resultDatePattern) {
    DateTimeFormatter currentDateTimeFormat = new DateTimeFormatterBuilder().parseCaseInsensitive()
        .appendPattern(currentDatePattern).toFormatter();
    DateTimeFormatter resultDateTimeFormat = new DateTimeFormatterBuilder().parseCaseInsensitive()
        .appendPattern(resultDatePattern).toFormatter();
    LocalDate fromDate = LocalDate.parse(date, currentDateTimeFormat);
    return fromDate.plusDays(noOfDays).format(resultDateTimeFormat);
  }

  public static void main(String[] args) {
    System.out.println(splitDateByNdaysRange("29-NOV-1995", "03-11-2021", 90));
    // System.out.println(toDD_MM_YYYY("31-Oct-2019"));
    // System.out.println(addNdays("22-Oct-2021", 1, "dd-MMM-yyyy",
    // "dd-MMM-yyyy"));
  }
}
