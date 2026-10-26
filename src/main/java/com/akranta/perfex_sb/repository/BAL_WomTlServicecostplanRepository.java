package com.akranta.perfex_sb.repository;

import com.akranta.perfex_sb.model.BAL_WomTlServicecostplan;
import com.akranta.perfex_sb.model.BAL_WomTlServicecostplanId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BAL_WomTlServicecostplanRepository
        extends JpaRepository<BAL_WomTlServicecostplan, BAL_WomTlServicecostplanId> {
                  
    @Modifying
    @Query(value = "DELETE FROM wom_tl_servicecostplan "
            + "WHERE svcp_woid = :woid AND svcp_serviceid = :serviceid",
            nativeQuery = true)
    int deleteByCompositeKey(@Param("woid") String woid,
                              @Param("serviceid") String serviceid);
}