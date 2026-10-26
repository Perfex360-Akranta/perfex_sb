package com.akranta.perfex_sb.repository;

import com.akranta.perfex_sb.model.BAL_WomTlServicecostactual;
import com.akranta.perfex_sb.model.BAL_WomTlServicecostactualId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BAL_WomTlServicecostactualRepository
        extends JpaRepository<BAL_WomTlServicecostactual, BAL_WomTlServicecostactualId> {
         
    @Modifying
    @Query(value = "DELETE FROM wom_tl_servicecostactual "
            + "WHERE svca_woid = :woid AND svca_serviceid = :serviceid",
            nativeQuery = true)
    int deleteByCompositeKey(@Param("woid") String woid,
                              @Param("serviceid") String serviceid);
}