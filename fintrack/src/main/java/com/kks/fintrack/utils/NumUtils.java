package com.kks.fintrack.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class NumUtils {
  public static long toLong(String s) {
    s = s.replace(",", "").trim();
    s = s.contains(".") ? s.split("\\.")[0] : s;
    return Pattern.matches("[\\+\\-]{0,1}[0-9]+", s) ? Long.parseLong(s) : 0;
  }

  public static double toDouble(String s) {
    s = s.replace(",", "");
    s = s.contains(".") ? s : (s + ".0");
    return Pattern.matches("^[\\+\\-]{0,1}[0-9]+[\\.\\,]{0,1}[0-9]+$", s) ? Double.parseDouble(s) : 0.0;
  }

  // Only number [0-9]+
  // For number pattern - Pattern.matches("[\\+\\-]{0,1}[0-9]+", s)
  // For Double pattern -
  // Pattern.matches("^[\\+\\-]{0,1}[0-9]+[\\.\\,]{0,1}[0-9]+$", s)

  public static void main(String[] args) {
    Map<String, Integer> map = new HashMap<>();
  }

}
