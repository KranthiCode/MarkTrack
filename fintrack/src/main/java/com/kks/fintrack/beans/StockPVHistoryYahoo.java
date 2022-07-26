package com.kks.fintrack.beans;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({ "date", "open", "high", "low", "close", "adjClose", "volume" })
@Entity
@Table(name = "STOCK_PV_HISTORY_YAHOO")
public class StockPVHistoryYahoo {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long id;

  @Column(name = "SYMBOL", nullable = false)
  private String symbol;

  @Column(name = "SERIES")
  private String series;

  @Column(name = "DATE", nullable = false)
  private String date;

  @Column(name = "PREV_CLOSE")
  private double prevClose;

  @Column(name = "OPEN_PRICE", nullable = false)
  private double openPrice;

  @Column(name = "HIGH_PRICE", nullable = false)
  private double highPrice;

  @Column(name = "LOW_PRICE", nullable = false)
  private double lowPrice;

  @Column(name = "LAST_PRICE", nullable = false)
  private double lastPrice;

  @Column(name = "CLOSE_PRICE", nullable = false)
  private double closePrice;

  @Column(name = "VWAP")
  private double vwap;

  @Column(name = "TOTAL_TRADED_QUANTITY")
  private long totalTradedQuantity;

  @Column(name = "TURNOVER")
  private double turnover;

  @Column(name = "TOTAL_NO_OF_TRADES")
  private long totalNoOfTrades;

  @Column(name = "DELIVERY_QTY")
  private long deliveryQty;

  @Column(name = "DELIVERY_PERC")
  private double deliveryPerc;

  @Column(name = "CHANGE_PERC")
  private double changePerc;

  /**
   * @return long return the id
   */
  public long getId() {
    return id;
  }

  /**
   * @param id the id to set
   */
  public void setId(long id) {
    this.id = id;
  }

  /**
   * @return String return the symbol
   */
  public String getSymbol() {
    return symbol;
  }

  /**
   * @param symbol the symbol to set
   */
  public void setSymbol(String symbol) {
    this.symbol = symbol;
  }

  /**
   * @return String return the series
   */
  public String getSeries() {
    return series;
  }

  /**
   * @param series the series to set
   */
  public void setSeries(String series) {
    this.series = series;
  }

  /**
   * @return String return the date
   */
  public String getDate() {
    return date;
  }

  /**
   * @param date the date to set
   */
  public void setDate(String date) {
    this.date = date;
  }

  /**
   * @return double return the prevClose
   */
  public double getPrevClose() {
    return prevClose;
  }

  /**
   * @param prevClose the prevClose to set
   */
  public void setPrevClose(double prevClose) {
    this.prevClose = prevClose;
  }

  /**
   * @return double return the openPrice
   */
  public double getOpenPrice() {
    return openPrice;
  }

  /**
   * @param openPrice the openPrice to set
   */
  public void setOpenPrice(double openPrice) {
    this.openPrice = openPrice;
  }

  /**
   * @return double return the highPrice
   */
  public double getHighPrice() {
    return highPrice;
  }

  /**
   * @param highPrice the highPrice to set
   */
  public void setHighPrice(double highPrice) {
    this.highPrice = highPrice;
  }

  /**
   * @return double return the lowPrice
   */
  public double getLowPrice() {
    return lowPrice;
  }

  /**
   * @param lowPrice the lowPrice to set
   */
  public void setLowPrice(double lowPrice) {
    this.lowPrice = lowPrice;
  }

  /**
   * @return double return the lastPrice
   */
  public double getLastPrice() {
    return lastPrice;
  }

  /**
   * @param lastPrice the lastPrice to set
   */
  public void setLastPrice(double lastPrice) {
    this.lastPrice = lastPrice;
  }

  /**
   * @return double return the closePrice
   */
  public double getClosePrice() {
    return closePrice;
  }

  /**
   * @param closePrice the closePrice to set
   */
  public void setClosePrice(double closePrice) {
    this.closePrice = closePrice;
  }

  /**
   * @return double return the vwap
   */
  public double getVwap() {
    return vwap;
  }

  /**
   * @param vwap the vwap to set
   */
  public void setVwap(double vwap) {
    this.vwap = vwap;
  }

  /**
   * @return long return the totalTradedQuantity
   */
  public long getTotalTradedQuantity() {
    return totalTradedQuantity;
  }

  /**
   * @param totalTradedQuantity the totalTradedQuantity to set
   */
  public void setTotalTradedQuantity(long totalTradedQuantity) {
    this.totalTradedQuantity = totalTradedQuantity;
  }

  /**
   * @return double return the turnover
   */
  public double getTurnover() {
    return turnover;
  }

  /**
   * @param turnover the turnover to set
   */
  public void setTurnover(double turnover) {
    this.turnover = turnover;
  }

  /**
   * @return long return the totalNoOfTrades
   */
  public long getTotalNoOfTrades() {
    return totalNoOfTrades;
  }

  /**
   * @param totalNoOfTrades the totalNoOfTrades to set
   */
  public void setTotalNoOfTrades(long totalNoOfTrades) {
    this.totalNoOfTrades = totalNoOfTrades;
  }

  /**
   * @return long return the deliveryQty
   */
  public long getDeliveryQty() {
    return deliveryQty;
  }

  /**
   * @param deliveryQty the deliveryQty to set
   */
  public void setDeliveryQty(long deliveryQty) {
    this.deliveryQty = deliveryQty;
  }

  /**
   * @return double return the deliveryPerc
   */
  public double getDeliveryPerc() {
    return deliveryPerc;
  }

  /**
   * @param deliveryPerc the deliveryPerc to set
   */
  public void setDeliveryPerc(double deliveryPerc) {
    this.deliveryPerc = deliveryPerc;
  }

  /**
   * @return double return the changePerc
   */
  public double getChangePerc() {
    return changePerc;
  }

  /**
   * @param changePerc the changePerc to set
   */
  public void setChangePerc(double changePerc) {
    this.changePerc = changePerc;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    long temp;
    temp = Double.doubleToLongBits(changePerc);
    result = prime * result + (int) (temp ^ (temp >>> 32));
    temp = Double.doubleToLongBits(closePrice);
    result = prime * result + (int) (temp ^ (temp >>> 32));
    result = prime * result + ((date == null) ? 0 : date.hashCode());
    temp = Double.doubleToLongBits(deliveryPerc);
    result = prime * result + (int) (temp ^ (temp >>> 32));
    result = prime * result + (int) (deliveryQty ^ (deliveryQty >>> 32));
    temp = Double.doubleToLongBits(highPrice);
    result = prime * result + (int) (temp ^ (temp >>> 32));
    result = prime * result + (int) (id ^ (id >>> 32));
    temp = Double.doubleToLongBits(lastPrice);
    result = prime * result + (int) (temp ^ (temp >>> 32));
    temp = Double.doubleToLongBits(lowPrice);
    result = prime * result + (int) (temp ^ (temp >>> 32));
    temp = Double.doubleToLongBits(openPrice);
    result = prime * result + (int) (temp ^ (temp >>> 32));
    temp = Double.doubleToLongBits(prevClose);
    result = prime * result + (int) (temp ^ (temp >>> 32));
    result = prime * result + ((series == null) ? 0 : series.hashCode());
    result = prime * result + ((symbol == null) ? 0 : symbol.hashCode());
    result = prime * result + (int) (totalNoOfTrades ^ (totalNoOfTrades >>> 32));
    result = prime * result + (int) (totalTradedQuantity ^ (totalTradedQuantity >>> 32));
    temp = Double.doubleToLongBits(turnover);
    result = prime * result + (int) (temp ^ (temp >>> 32));
    temp = Double.doubleToLongBits(vwap);
    result = prime * result + (int) (temp ^ (temp >>> 32));
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    StockPVHistoryYahoo other = (StockPVHistoryYahoo) obj;
    if (Double.doubleToLongBits(changePerc) != Double.doubleToLongBits(other.changePerc))
      return false;
    if (Double.doubleToLongBits(closePrice) != Double.doubleToLongBits(other.closePrice))
      return false;
    if (date == null) {
      if (other.date != null)
        return false;
    } else if (!date.equals(other.date))
      return false;
    if (Double.doubleToLongBits(deliveryPerc) != Double.doubleToLongBits(other.deliveryPerc))
      return false;
    if (deliveryQty != other.deliveryQty)
      return false;
    if (Double.doubleToLongBits(highPrice) != Double.doubleToLongBits(other.highPrice))
      return false;
    if (id != other.id)
      return false;
    if (Double.doubleToLongBits(lastPrice) != Double.doubleToLongBits(other.lastPrice))
      return false;
    if (Double.doubleToLongBits(lowPrice) != Double.doubleToLongBits(other.lowPrice))
      return false;
    if (Double.doubleToLongBits(openPrice) != Double.doubleToLongBits(other.openPrice))
      return false;
    if (Double.doubleToLongBits(prevClose) != Double.doubleToLongBits(other.prevClose))
      return false;
    if (series == null) {
      if (other.series != null)
        return false;
    } else if (!series.equals(other.series))
      return false;
    if (symbol == null) {
      if (other.symbol != null)
        return false;
    } else if (!symbol.equals(other.symbol))
      return false;
    if (totalNoOfTrades != other.totalNoOfTrades)
      return false;
    if (totalTradedQuantity != other.totalTradedQuantity)
      return false;
    if (Double.doubleToLongBits(turnover) != Double.doubleToLongBits(other.turnover))
      return false;
    if (Double.doubleToLongBits(vwap) != Double.doubleToLongBits(other.vwap))
      return false;
    return true;
  }

  @Override
  public String toString() {
    return "Stock [changePerc=" + changePerc + ", closePrice=" + closePrice + ", date=" + date + ", deliveryPerc="
        + deliveryPerc + ", deliveryQty=" + deliveryQty + ", highPrice=" + highPrice + ", id=" + id + ", lastPrice="
        + lastPrice + ", lowPrice=" + lowPrice + ", openPrice=" + openPrice + ", prevClose=" + prevClose + ", series="
        + series + ", symbol=" + symbol + ", totalNoOfTrades=" + totalNoOfTrades + ", totalTradedQuantity="
        + totalTradedQuantity + ", turnover=" + turnover + ", vwap=" + vwap + "]";
  }

}
