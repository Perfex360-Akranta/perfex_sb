package com.akranta.perfex_sb.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.akranta.perfex_sb.model.GenTlHolidaymst;

public interface GenTlHolidaymstRepository extends JpaRepository<GenTlHolidaymst, String> {

    List<GenTlHolidaymst> findByFactoryAndHolidayflag(String factory, String holidayflag);

    @Query("SELECT h FROM GenTlHolidaymst h WHERE h.date >= :startDate AND h.date < :endDate AND h.holidayflag = :flag")  
  List<GenTlHolidaymst> findByYearAndFlag(@Param("startDate") java.time.LocalDateTime startDate, @Param("endDate") java.time.LocalDateTime endDate, @Param("flag") String flag);

  @Query("SELECT h FROM GenTlHolidaymst h WHERE h.date >= :startDate AND h.date < :endDate AND h.holidayflag = :flag AND h.factory = :factory")  
    List<GenTlHolidaymst> findByYearFlagAndFactory(@Param("startDate") java.time.LocalDateTime startDate, @Param("endDate") java.time.LocalDateTime endDate, @Param("flag") String flag, @Param("factory") String factory);
}