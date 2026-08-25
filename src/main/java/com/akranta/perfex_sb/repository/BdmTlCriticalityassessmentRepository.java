package com.akranta.perfex_sb.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.akranta.perfex_sb.model.BdmTlCriticalityassessment;

@Repository
public interface BdmTlCriticalityassessmentRepository extends JpaRepository<BdmTlCriticalityassessment, String> {

    @Query(value = """
        SELECT C.cria_keyid
        FROM plm_tl_criteriamst C
        INNER JOIN gen_tl_machinemst M ON M.mchm_keyid = :equipmentId
        WHERE C.cria_flid = :flId
        AND CAST(:totalPoints AS NUMERIC) BETWEEN C.cria_minimumpoints AND C.cria_maximumpoints
        AND (
            CASE 
                WHEN :tradeId IS NOT NULL AND :tradeId != '-' 
                THEN (C.cria_tradeid = :tradeId OR C.cria_tradeid = '-')
                ELSE (C.cria_tradeid = '-' OR C.cria_tradeid = M.mchm_tradeid)
            END
        )
        ORDER BY CASE WHEN C.cria_tradeid = '-' THEN 2 ELSE 1 END
        LIMIT 1
        """, nativeQuery = true)
    String getCriteriaKeyId(
        @Param("flId") String flId,
        @Param("totalPoints") BigDecimal totalPoints,
        @Param("equipmentId") String equipmentId,
        @Param("tradeId") String tradeId
    );
    @Modifying
     @Query(value = "SELECT FLID FROM GEN_MV_FLIDHIERARCHY WHERE PARENTS = :parentFlid", 
           nativeQuery = true)
          String getFlidByParent(@Param("parentFlid") String parentFlid);
    @Query(value = "SELECT DISTINCT CASM_REMARKS FROM BDM_TL_CRITICALITYASSESSMENT " +
                   "WHERE CASM_FLID = :flid AND CASM_EQUIPMENTID = :equipmentId", 
           nativeQuery = true)
    String getCriticalityAssessmentRemarks(
            @Param("flid") String flid, 
            @Param("equipmentId") String equipmentId);

@Transactional
@Modifying
@Query(
  value = "DELETE FROM BDM_TL_CRITICALITYASSESSMENT WHERE CASM_KEYID IN (:keyIds)",
  nativeQuery = true
)
int deleteCriteriaByKeyIds(@Param("keyIds") List<String> keyIds);

}
