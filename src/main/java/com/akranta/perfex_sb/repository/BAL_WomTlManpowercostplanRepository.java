package com.akranta.perfex_sb.repository;

import com.akranta.perfex_sb.model.BAL_WomTlManpowercostplan;
import com.akranta.perfex_sb.model.BAL_WomTlManpowercostplanId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Map;
public interface BAL_WomTlManpowercostplanRepository
        extends JpaRepository<BAL_WomTlManpowercostplan, BAL_WomTlManpowercostplanId> {

                 @Modifying
    @Query(value = "DELETE FROM wom_tl_manpowercostplan "
            + "WHERE mpcp_woid = :woid AND mpcp_manpowerid = :manpowerid AND mpcp_skillid = :skillid",
            nativeQuery = true)
    int deleteByCompositeKey(@Param("woid") String woid,
                              @Param("manpowerid") String manpowerid,
                              @Param("skillid") String skillid);
                              @Query(value = "SELECT COUNT(*) AS cnt FROM bal_wom_tl_manpowercostactual "
            + "WHERE mpcs_maintwoid = :woId",
            nativeQuery = true)
    long countManpowerActualByWoId(@Param("woId") String woId);

    // checkManpowerExists - formType = "Estimate"
    @Query(value = "SELECT COUNT(*) AS cnt FROM wom_tl_manpowercostplan "
            + "WHERE mpcp_woid = :woId",
            nativeQuery = true)
    long countManpowerEstimateByWoId(@Param("woId") String woId);

    

@Query(value = """
    SELECT CAST(COUNT(*) AS VARCHAR) AS CNT
    FROM BAL_WOM_TL_MANPOWERCOSTACTUAL
    WHERE MPCS_MAINTWOID = :woId
    """, nativeQuery = true)
String countManpowerActual(@Param("woId") String woId);

@Query(value = """
    SELECT CAST(COUNT(*) AS VARCHAR) AS CNT
    FROM WOM_TL_MANPOWERCOSTPLAN
    WHERE MPCP_WOID = :woId
    """, nativeQuery = true)
String countManpowerEstimate(@Param("woId") String woId);
// ─── EmployeeMstRepository.java (getEmpCost pakkathula add pannunga) ───────

@Query(value = """
    SELECT UNGM_CODE, UNGM_NAME, CNCS_COSTPERHOUR, CNCS_OTCOST, CNCS_HOLIDAYCOST
    FROM PLM_TL_UNSKILLEDGRADEMST_I
    LEFT JOIN WOM_TL_CONTRACTORMST
           ON CNCS_UNSKILLEDGRADEID = UNGM_KEYID
          AND CNCS_ACTIVE = 'Y'
    WHERE UNGM_KEYID = :empId
    """, nativeQuery = true)
List<Map<String, Object>> getContractorCost(@Param("empId") String empId);

}

