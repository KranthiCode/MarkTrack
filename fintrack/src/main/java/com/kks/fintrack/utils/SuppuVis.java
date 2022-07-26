package com.kks.fintrack.utils;

import java.util.AbstractSet;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;

public class SuppuVis {

  public static int candies(int input1, int[] input2) {
    // input2 is length
    int n = input1;
    int i = n - 1;
    while (i > 0) {
      if (input2[i] < 9) {
        return i + 1;
      }
      i--;
    }
    return 0;
  }

  public static int min(int a, int b) {
    return a < b ? a : b;
  }

  public static void main(String[] args) {
    int[] a = { 9,9 };
    System.out.println(candies(2, a));
  }
}