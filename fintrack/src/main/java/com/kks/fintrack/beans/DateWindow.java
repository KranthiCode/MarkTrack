package com.kks.fintrack.beans;

public class DateWindow {
  private String fromDate;
  private String toDate;

  public DateWindow(String fromDate, String toDate) {
    this.fromDate = fromDate;
    this.toDate = toDate;
  }

  @Override
  public String toString() {
    return "DateWindow [fromDate=" + fromDate + ", toDate=" + toDate + "]\n";
  }

  /**
   * @return String return the fromDate
   */
  public String getFromDate() {
    return fromDate;
  }

  /**
   * @param fromDate the fromDate to set
   */
  public void setFromDate(String fromDate) {
    this.fromDate = fromDate;
  }

  /**
   * @return String return the toDate
   */
  public String getToDate() {
    return toDate;
  }

  /**
   * @param toDate the toDate to set
   */
  public void setToDate(String toDate) {
    this.toDate = toDate;
  }

}