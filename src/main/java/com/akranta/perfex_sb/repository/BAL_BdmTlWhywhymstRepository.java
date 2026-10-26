package com.akranta.perfex_sb.repository;

import com.akranta.perfex_sb.model.BAL_BdmTlWhywhymst;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface BAL_BdmTlWhywhymstRepository extends JpaRepository<BAL_BdmTlWhywhymst, String> {

    @Query(value = """
            SELECT WWMS_KEYID
            FROM BAL_BDM_TL_WHYWHYMST
            WHERE WWMS_REFDOCNO = :refDocId
            """, nativeQuery = true)
    List<String> getYYRefKeyid(@Param("refDocId") String refDocId);
     @Query(value = """
            SELECT *
            FROM BAL_BDM_TL_WHYWHYMST
            WHERE WWMS_KEYID = :keyid
            """, nativeQuery = true)
    BAL_BdmTlWhywhymst selectMasKeyid(@Param("keyid") String keyid);
  @Query(value = """
    SELECT wrcm_keyid, '', wrcm_name 
    FROM bdm_tl_rootcausemst 
    WHERE wrcm_type = CASE 
        WHEN :openMode IS NOT NULL AND :openMode != '' 
        THEN :openMode 
        ELSE 'BDM' 
    END
    """, nativeQuery = true)
List<Map<String, Object>> getRootCauseBySql(@Param("openMode") String openMode);
}