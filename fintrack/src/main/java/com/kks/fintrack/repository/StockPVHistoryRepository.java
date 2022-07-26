package com.kks.fintrack.repository;

import java.util.List;

import com.kks.fintrack.beans.StockPVHistory;
import com.kks.fintrack.beans.StockRecentDataUpdate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface StockPVHistoryRepository extends JpaRepository<StockPVHistory, Long> {
  // select symbol, date_format(max(str_to_date(date, '%d-%M-%Y')), '%d-%b-%Y')
  // from testdb.stock_pv_history group by symbol;
  @Query(value = "select symbol, date_format(max(str_to_date(date, '%d-%M-%Y')), '%d-%b-%Y') as lastUpdatedDate from  testdb.stock_pv_history group by symbol", countQuery = "select count(distinct symbol) from testdb.stock_pv_history", nativeQuery = true)
  Page<StockRecentDataUpdate> findLastUpdatedTime(Pageable pageable);

  @Query(value = "select symbol, date_format(max(str_to_date(date, '%d-%M-%Y')), '%d-%b-%Y') as lastUpdatedDate from  testdb.stock_pv_history group by symbol limit 100", nativeQuery = true)
  List<StockRecentDataUpdate> lastUpdatedTime();

  @Query(value = "select * from testdb.stock_pv_history where symbol = ?1 and date = ?2 limit 1", nativeQuery = true)
  StockPVHistory getLatestRecord(String symbol, String date);

}
