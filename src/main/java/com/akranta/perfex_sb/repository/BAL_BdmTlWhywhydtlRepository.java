package com.akranta.perfex_sb.repository;

import com.akranta.perfex_sb.model.BAL_BdmTlWhywhydtl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Map;

@Repository
public interface BAL_BdmTlWhywhydtlRepository extends JpaRepository<BAL_BdmTlWhywhydtl, String> {
    @Modifying
    @Query(value = "DELETE FROM bal_bdm_tl_whywhydtl WHERE wwdt_keyid = :detailId", nativeQuery = true)
    int deleteWhyWhyDetail(@Param("detailId") String detailId);

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
        FROM bal_bdm_tl_whywhydtl
        WHERE wwdt_wwms_keyid = :masdetkeyid
        ORDER BY wwdt_keyid
        """, nativeQuery = true)
    List<Map<String, Object>> getAnalysisData(@Param("masdetkeyid") String masdetkeyid);
}
