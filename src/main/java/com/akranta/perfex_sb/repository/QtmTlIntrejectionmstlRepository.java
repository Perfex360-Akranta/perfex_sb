package com.akranta.perfex_sb.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.akranta.perfex_sb.model.QtmTlIntrejectionmst;

public interface QtmTlIntrejectionmstlRepository extends JpaRepository<QtmTlIntrejectionmst, String>

{

    @Query(value = """
            SELECT QIRM_PARENTMASTERID
            FROM QTM_TL_INTREJECTIONMST
            WHERE QIRM_KEYID = :qirmKeyId
            """, nativeQuery = true)
    String findParentMasterIdByKeyId(@Param("qirmKeyId") String qirmKeyId);

    @Query(value = """
            SELECT QIRM_LINKMASTERID
            FROM QTM_TL_INTREJECTIONMST
            WHERE QIRM_KEYID = :qirmKeyId
            """, nativeQuery = true)
    String findLinkMasterIdByKeyId(@Param("qirmKeyId") String qirmKeyId);

    @Query(value = """
                SELECT
                    '1' AS col1,
                    '2' AS col2,
                    '3' AS col3,
                    '4' AS col4,
                    '5' AS col5
            UNION ALL
                SELECT 
                    'Keyid' AS col1,
                   'Functional Location' AS col2,
                   'Grade Specification' AS col3,
                   'Inspection Date' AS col4,
                   'Inspected Shift' AS col5
            UNION ALL
            SELECT QIrm_Keyid,
                   PARENTS,
                   GSPC_NAME || '-' || GSPC_CODE,
                   TO_CHAR(qirm_inspectiondate, 'DD-MM-YYYY'),
                   SFTM_NAME
            FROM QTM_TL_INTREJECTIONMST
            CROSS JOIN GEN_MV_FLIDHIERARCHY
            LEFT JOIN PCS_TL_GRADESPECMST ON GSPC_KEYID = qirm_productid
            INNER JOIN GEN_TL_SHIFTMST ON SFTM_KEYID = qirm_inspectedshiftid
            WHERE QIRM_FLID = FLID
              AND (:flid IS NULL OR POSITION(:flid IN PARENTFLIDS || FLID) > 0)
            """,nativeQuery = true)
    List<Map<String, Object>> getInternalRejectionMstGrid(@Param("flid") String flid);

}
