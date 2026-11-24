package com.akranta.perfex_sb.repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.akranta.perfex_sb.model.BAL_PcsEntryDtl;

public interface BAL_PcsEntryDtlRepository extends JpaRepository<BAL_PcsEntryDtl,String>{

      Optional<BAL_PcsEntryDtl> findFirstByMachineidAndProductidAndPlmasteridAndActualcycletimeOrderByPldetailsidAsc(
            String machineid, String productid, String plmasterid, BigDecimal actualcycletime);
 
    /** Legacy: MACHINEID + PLMASTERID */
    Optional<BAL_PcsEntryDtl> findFirstByMachineidAndPlmasteridOrderByPldetailsidAsc(
            String machineid, String plmasterid);

            @Query(value = "SELECT CNFM_SETTINGVALUE FROM ADM_TL_CONFIGURATIONMST WHERE CNFM_CODE = :code",
       nativeQuery = true)
String findConfigValue(@Param("code") String code);

@Query(value = "SELECT bal_pcs_fn_updatepldtl(:tbl, :id, :cycle, 'Y', 'N', :flid)", nativeQuery = true)
Integer updatePlDtl(@Param("tbl") String tbl, @Param("id") String id,
                    @Param("cycle") String cycle, @Param("flid") String flid);

 @Query(value = "SELECT bal_pcs_fn_getpldetailstbl(:cellId, 'N')", nativeQuery = true)
String findDetailTable(@Param("cellId") String cellId);

@Query(value = "SELECT bal_pcs_fn_updatepldtl(:tbl, :id, :avail, 'N', 'N', :flid)", nativeQuery = true)
Integer recalculate(@Param("tbl") String tbl, @Param("id") String id,
                    @Param("avail") String avail, @Param("flid") String flid);


@Query(value = "SELECT pprd_tempfield1 FROM bal_pcs_tl_productiondata "
                 + "WHERE pprd_cellid = :cell AND pprd_shiftid = :shift "
                 + "AND pprd_date::date = :date AND pprd_hour = :hour LIMIT 1",
           nativeQuery = true)
    String findDetailIdByHour(@Param("cell") String cell, @Param("shift") String shift,
                              @Param("date") LocalDate date, @Param("hour") int hour);


}


