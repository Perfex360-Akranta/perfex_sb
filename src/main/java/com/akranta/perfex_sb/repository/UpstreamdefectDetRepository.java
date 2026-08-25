package com.akranta.perfex_sb.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.akranta.perfex_sb.model.UpstreamdefectDet;
import com.akranta.perfex_sb.model.Upstreamdefectmst;

import jakarta.transaction.Transactional;

public interface UpstreamdefectDetRepository extends JpaRepository<UpstreamdefectDet, String> {

        @Query(value = """
                SELECT
                 '1'AS  UPSD_RAWMATERIAL,
                  '2' AS   UPSD_DEFECT,
                  '3'     AS UPSD_INFORMTO,
                '4'     AS UPSD_CORRECTIONACTION,
                     '5'     AS UPSD_PREVENTIVEACTION,
                     '6'        AS UPSM_DATE,
                     '7'        AS UPSD_KEYID
                UNION ALL
                SELECT
                    d.UPSD_RAWMATERIAL AS  UPSD_RAWMATERIAL,
                    d.UPSD_DEFECT AS UPSD_DEFECT,
                    d.UPSD_INFORMTO AS UPSD_INFORMTO,
                    d.UPSD_CORRECTIONACTION AS UPSD_CORRECTIONACTION,
                    d.UPSD_PREVENTIVEACTION AS UPSD_PREVENTIVEACTION,
                    TO_CHAR(m.UPSM_DATE,'DD-Mon-YYYY') AS UPSM_DATE,
                    d.UPSD_KEYID AS UPSD_KEYID
                FROM GEN_TL_UPSTREAMDEFECT_DET d
                JOIN GEN_TL_UPSTREAMDEFECT_MST m
                    ON d.UPSD_UPSM_KEYID = m.UPSM_KEYID
                WHERE d.UPSD_KEYID = :keyid
                """, nativeQuery = true)
        List<Map<String,Object>> recall(@Param("keyid") String keyid);



        //delete the detail table record

    @Modifying
    @Transactional
    @Query(
        value = "DELETE FROM GEN_TL_UPSTREAMDEFECT_DET " +
                "WHERE UPSD_UPSM_KEYID = :upsmKeyId",
        nativeQuery = true
    )
    void deleteByMasterKey(@Param("upsmKeyId") String upsmKeyId);
    
    //delete the detail table record by using detail table keyid
    @Modifying
    @Transactional
    @Query(
        value = "DELETE FROM GEN_TL_UPSTREAMDEFECT_DET WHERE UPSD_KEYID = :upsdKeyId",
        nativeQuery = true
    )
    int deleteByUpsdKeyId(@Param("upsdKeyId") String upsdKeyId);

    //delete setail table by detlete keyid
    @Modifying
    @Transactional
    @Query(
        value = "DELETE FROM GEN_TL_UPSTREAMDEFECT_DET WHERE UPSD_KEYID = :upsdKeyid",
        nativeQuery = true
    )
    void deleteByUpsdKeyid(@Param("upsdKeyid") String upsdKeyid);


}
