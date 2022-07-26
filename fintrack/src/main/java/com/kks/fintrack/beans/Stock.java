package com.kks.fintrack.beans;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "STOCK")
public class Stock {

  @Id
  @Column(name = "SYMBOL", nullable = false)
  private String symbol;

  @Column(name = "NAME_OF_COMPANY")
  private String nameOfCompany;

  @Column(name = "SERIES")
  private String series;

  @Column(name = "DATE_OF_LISTING")
  private String dateOfListing;

  @Column(name = "PAID_UP_VALUE")
  private long paidUpValue;

  @Column(name = "MARKET_LOT")
  private int marketLot;

  @Column(name = "ISIN_NUMBER")
  private String isinNumber;

  @Column(name = "FACE_VALUE")
  private long faceValue;

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
   * @return String return the nameOfCompany
   */
  public String getNameOfCompany() {
    return nameOfCompany;
  }

  /**
   * @param nameOfCompany the nameOfCompany to set
   */
  public void setNameOfCompany(String nameOfCompany) {
    this.nameOfCompany = nameOfCompany;
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
   * @return String return the dateOfListing
   */
  public String getDateOfListing() {
    return dateOfListing;
  }

  /**
   * @param dateOfListing the dateOfListing to set
   */
  public void setDateOfListing(String dateOfListing) {
    this.dateOfListing = dateOfListing;
  }

  /**
   * @return long return the paidUpValue
   */
  public long getPaidUpValue() {
    return paidUpValue;
  }

  /**
   * @param paidUpValue the paidUpValue to set
   */
  public void setPaidUpValue(long paidUpValue) {
    this.paidUpValue = paidUpValue;
  }

  /**
   * @return int return the marketLot
   */
  public int getMarketLot() {
    return marketLot;
  }

  /**
   * @param marketLot the marketLot to set
   */
  public void setMarketLot(int marketLot) {
    this.marketLot = marketLot;
  }

  /**
   * @return String return the isinNumber
   */
  public String getIsinNumber() {
    return isinNumber;
  }

  /**
   * @param isinNumber the isinNumber to set
   */
  public void setIsinNumber(String isinNumber) {
    this.isinNumber = isinNumber;
  }

  /**
   * @return long return the faceValue
   */
  public long getFaceValue() {
    return faceValue;
  }

  /**
   * @param faceValue the faceValue to set
   */
  public void setFaceValue(long faceValue) {
    this.faceValue = faceValue;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((dateOfListing == null) ? 0 : dateOfListing.hashCode());
    result = prime * result + (int) (faceValue ^ (faceValue >>> 32));
    result = prime * result + ((isinNumber == null) ? 0 : isinNumber.hashCode());
    result = prime * result + marketLot;
    result = prime * result + ((nameOfCompany == null) ? 0 : nameOfCompany.hashCode());
    result = prime * result + (int) (paidUpValue ^ (paidUpValue >>> 32));
    result = prime * result + ((series == null) ? 0 : series.hashCode());
    result = prime * result + ((symbol == null) ? 0 : symbol.hashCode());
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
    Stock other = (Stock) obj;
    if (dateOfListing == null) {
      if (other.dateOfListing != null)
        return false;
    } else if (!dateOfListing.equals(other.dateOfListing))
      return false;
    if (faceValue != other.faceValue)
      return false;
    if (isinNumber == null) {
      if (other.isinNumber != null)
        return false;
    } else if (!isinNumber.equals(other.isinNumber))
      return false;
    if (marketLot != other.marketLot)
      return false;
    if (nameOfCompany == null) {
      if (other.nameOfCompany != null)
        return false;
    } else if (!nameOfCompany.equals(other.nameOfCompany))
      return false;
    if (paidUpValue != other.paidUpValue)
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
    return true;
  }

  @Override
  public String toString() {
    return "Stock [dateOfListing=" + dateOfListing + ", faceValue=" + faceValue + ", isinNumber=" + isinNumber
        + ", marketLot=" + marketLot + ", nameOfCompany=" + nameOfCompany + ", paidUpValue=" + paidUpValue + ", series="
        + series + ", symbol=" + symbol + "]";
  }

}
