package com.akranta.perfex_sb.repository;

import com.akranta.perfex_sb.model.BAL_WomTlSparecostplan;
import com.akranta.perfex_sb.model.BAL_WomTlSparecostplanId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Map;

public interface BAL_WomTlSparecostplanRepository
        extends JpaRepository<BAL_WomTlSparecostplan, BAL_WomTlSparecostplanId> {
                @Modifying
    @Query(value = "DELETE FROM wom_tl_sparecostplan "
            + "WHERE wscp_woid = :woid AND wscp_requestedby = :requestedby AND wscp_sparesid = :sparesid",
            nativeQuery = true)
    int deleteByCompositeKey(@Param("woid") String woid,
                              @Param("requestedby") String requestedby,
                              @Param("sparesid") String sparesid);
                              // ─── EmployeeMstRepository.java (getEmpCost / getContractorCost pakkathula add pannunga) ──

@Query(value = """
    SELECT SPRM_PARTNO, SPRM_PARTNAME, COALESCE(ITCS_UNITRATE, 0) AS RATE
    FROM GEN_TL_SPARESMST
    LEFT JOIN MTM_TL_ITEMCURRENTSTOCK ON ITCS_SPAREID = SPRM_KEYID
    WHERE SPRM_ACTIVE = 'Y' AND SPRM_KEYID = :spareId
    """, nativeQuery = true)
List<Map<String, Object>> getSpareCost(@Param("spareId") String spareId);
}