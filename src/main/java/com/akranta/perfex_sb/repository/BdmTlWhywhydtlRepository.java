package com.akranta.perfex_sb.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.akranta.perfex_sb.model.BdmTlWhywhydtl;

public interface BdmTlWhywhydtlRepository extends JpaRepository<BdmTlWhywhydtl, String> {
    
    List<BdmTlWhywhydtl> findByWwmsKeyid(String wwmsKeyid);
    
    @Query(value = """
        SELECT
            '1' AS wwdt_keyid,
            '2' AS wwdt_why,
            '3' AS Answer,
            '4' AS Delete
        UNION ALL
        SELECT
            wwdt_keyid,
            wwdt_why,
            wwdt_answer,
            '' as delete_action
        FROM bdm_tl_whywhydtl
        WHERE wwdt_wwms_keyid = :masdetkeyid
        ORDER BY wwdt_keyid
        """, nativeQuery = true)
    List<Map<String, Object>> getAnalysisData(@Param("masdetkeyid") String masdetkeyid);
 @Modifying
    @Query(value = "DELETE FROM bdm_tl_whywhydtl WHERE wwdt_keyid = :detailId", nativeQuery = true)
    int deleteWhyWhyDetail(@Param("detailId") String detailId);
    
}