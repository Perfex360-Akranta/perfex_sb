package com.akranta.perfex_sb.repository;

import com.akranta.perfex_sb.model.BAL_WomTlServicecostplan;
import com.akranta.perfex_sb.model.BAL_WomTlServicecostplanId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Map;


public interface BAL_WomTlServicecostplanRepository
        extends JpaRepository<BAL_WomTlServicecostplan, BAL_WomTlServicecostplanId> {
                  
    @Modifying
    @Query(value = "DELETE FROM wom_tl_servicecostplan "
            + "WHERE svcp_woid = :woid AND svcp_serviceid = :serviceid",
            nativeQuery = true)
    int deleteByCompositeKey(@Param("woid") String woid,
                              @Param("serviceid") String serviceid);
                              // ─── EmployeeMstRepository.java (getEmpCost / getContractorCost / getSpareCost pakkathula add pannunga) ──

@Query(value = """
    SELECT AMVM_CODE, AMVM_NAME, 0 AS RATE
    FROM GEN_TL_AMCVENDORMST
    WHERE AMVM_ACTIVE = 'Y' AND AMVM_KEYID = :serviceId
    """, nativeQuery = true)
List<Map<String, Object>> getServiceCost(@Param("serviceId") String serviceId);

}