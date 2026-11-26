package com.akranta.perfex_sb.repository;

import com.akranta.perfex_sb.model.BAL_WomTlUtilitycostplan;
import com.akranta.perfex_sb.model.BAL_WomTlUtilitycostplanId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Map;


public interface BAL_WomTlUtilitycostplanRepository
        extends JpaRepository<BAL_WomTlUtilitycostplan, BAL_WomTlUtilitycostplanId> {
            @Modifying
    @Query(value = "DELETE FROM wom_tl_utilitycostplan "
            + "WHERE utcp_wokeyid = :wokeyid AND utcp_requestedby = :requestedby AND utcp_utilitymstid = :utilitymstid",
            nativeQuery = true)
    int deleteByCompositeKey(@Param("wokeyid") String wokeyid,
                              @Param("requestedby") String requestedby,
                              @Param("utilitymstid") String utilitymstid);
                              // ─── EmployeeMstRepository.java (getEmpCost / getSpareCost pakkathula add pannunga) ──

@Query(value = """
    SELECT TOLM_CODE, TOLM_NAME, SUM(RATE) AS RATE
    FROM (
        SELECT TOLM_CODE, TOLM_NAME, 0 AS RATE
        FROM GEN_TL_TOOLSMST
        WHERE TOLM_ACTIVE = 'Y'
          AND TOLM_KEYID = :utilityId

        UNION ALL

        SELECT TOLM_CODE, TOLM_NAME, EMPC_COSTPERHOUR AS RATE
        FROM GEN_TL_EMPLOYEECOST, GEN_TL_TOOLSMST
        WHERE EMPC_TYPE = 'U' AND EMPC_ACTIVE = 'Y'
          AND CAST(:effDate AS date) BETWEEN EMPC_EFFECTIVEFROMDATE AND EMPC_EFFECTIVETILLDATE
          AND TOLM_KEYID = :utilityId
          AND EMPC_EMPLOYEEID = TOLM_KEYID
    ) t
    GROUP BY TOLM_CODE, TOLM_NAME
    """, nativeQuery = true)
List<Map<String, Object>> getUtilityCost(@Param("utilityId") String utilityId,
                                         @Param("effDate") String effDate);
}