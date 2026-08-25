package com.akranta.perfex_sb.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.akranta.perfex_sb.model.KznTlBestmst;

public interface KznTlBestmstRepository extends JpaRepository<KznTlBestmst, String> {

    @Query(value = """
                    SELECT
                        '1' AS KZBM_KEYID,
                        '2' AS KZBM_EMPLOYEEID,
                        '3' AS KZBM_DATE,
                        '4' AS KZBM_MONTH,
                        '5' AS KZBM_LEVEL,
                        '6' AS KZBM_CREATEDON
                    UNION ALL
                    SELECT
                        COALESCE(KZBM_KEYID, '') AS KZBM_KEYID,
                        COALESCE(KZBM_EMPLOYEEID, '') AS KZBM_EMPLOYEEID,
                        COALESCE(TO_CHAR(KZBM_DATE, 'DD-MON-YYYY'), '') AS KZBM_DATE,
                        COALESCE(KZBM_MONTH, '') AS KZBM_MONTH,
                        COALESCE(KZBM_LEVEL, '') AS KZBM_LEVEL,
                        COALESCE(TO_CHAR(KZBM_CREATEDON, 'DD-MON-YYYY'), '') AS KZBM_CREATEDON
            FROM KZN_TL_BESTMST
            WHERE KZBM_FLID = :flid
                AND TO_DATE(KZBM_MONTH, 'MON-YYYY') = TO_DATE(:fromMonth, 'MON-YYYY')
                AND KZBM_LEVEL = :kznBankType
                        """, nativeQuery = true)
    List<Map<String, Object>> selectData(@Param("flid") String flid,
            @Param("fromMonth") String fromMonth, @Param("kznBankType") String kznBankType);

}
