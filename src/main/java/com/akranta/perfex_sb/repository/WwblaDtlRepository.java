package com.akranta.perfex_sb.repository;

import com.akranta.perfex_sb.model.BdmTlWwbladtl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;


@Repository
public interface WwblaDtlRepository extends JpaRepository<BdmTlWwbladtl, String> {
     @Query(value = """
            SELECT
                
                '0' AS wwbd_verification,
                '1' AS wwbd_phenomena_factor,
                '2' AS wwbd_islastfactor,
                '3' AS wwbd_countermeasure,
                '4' AS wwbd_responsibility,
                '5' AS wwbd_reoccur,
                '6' AS wwbd_status,
                '7' AS wwbd_targetdate
            UNION ALL
            SELECT
               
                COALESCE(wwbd_verification, '') AS wwbd_verification,
                COALESCE(wwbd_phenomena_factor, '') AS wwbd_phenomena_factor,
                COALESCE(wwbd_islastfactor, '') AS wwbd_islastfactor,
                COALESCE(wwbd_countermeasure, '') AS wwbd_countermeasure,
                COALESCE(wwbd_responsibility, '') AS wwbd_responsibility,
                COALESCE(wwbd_reoccur, '') AS wwbd_reoccur,
                COALESCE(wwbd_status, '') AS wwbd_status,
                CASE 
                    WHEN TO_CHAR(wwbd_targetdate, 'DD-Mon-YYYY') = '01-Jan-1801' 
                    THEN '' 
                    ELSE TO_CHAR(wwbd_targetdate, 'DD-Mon-YYYY') 
                END AS wwbd_targetdate
            FROM bdm_tl_wwbladtl
            WHERE wwbd_keyid = :keyid
            """, nativeQuery = true)
    List<Map<String, Object>> recallWwblaDetail(@Param("keyid") String keyid);

    //List<Map<String, Object>> recallWwblaDetail(@Param("keyid") String keyid);
    
     @Query(value = """
            SELECT
                COALESCE(wwbd_phenomena_factor, '') AS wwbd_phenomena_factor,
                COALESCE(wwbd_parentid, '') AS wwbd_parentid,
                COALESCE(CAST(wwbd_orderno AS VARCHAR), '') AS wwbd_orderno,
                COALESCE(CAST(wwbd_levelno AS VARCHAR), '') AS wwbd_levelno,
                COALESCE(wwbd_wwbl_keyid, '') AS wwbd_wwbl_keyid,
                COALESCE(wwbd_keyid, '') AS wwbd_keyid
            FROM bdm_tl_wwbladtl
            LEFT JOIN bdm_tl_wwblamst ON wwbl_keyid = wwbd_wwbl_keyid
            WHERE 1=1
                AND (:masterKeyid IS NULL OR wwbl_keyid = :masterKeyid)
                AND (:parentId IS NULL OR wwbd_parentid = :parentId)
                AND (:excludeSelfReference = false OR wwbd_keyid <> wwbd_parentid)
            """, nativeQuery = true)
    List<Map<String, Object>> getWwblaValues(
            @Param("masterKeyid") String masterKeyid,
            @Param("parentId") String parentId,
            @Param("excludeSelfReference") boolean excludeSelfReference
    );
    @Modifying
    @Query(value = "DELETE FROM bdm_tl_wwbladtl WHERE wwbd_keyid = :keyid", nativeQuery = true)
    int deleteByKeyid(@Param("keyid") String keyid);
    
    /**
     * Delete all WWBLA detail entries where parent ID matches the given keyid
     */
    @Modifying
    @Query(value = "DELETE FROM bdm_tl_wwbladtl WHERE wwbd_parentid = :keyid", nativeQuery = true)
    int deleteByParentId(@Param("keyid") String keyid);
}