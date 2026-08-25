package com.akranta.perfex_sb.repository;

import com.akranta.perfex_sb.model.GenTlMomGroupmst;

import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface GenTlMomGroupmstRepository extends JpaRepository<GenTlMomGroupmst,String> 
 
{

    @Query(value = """
        SELECT *
        FROM (
            SELECT 
                ROW_NUMBER() OVER (ORDER BY (SELECT NULL))+3 AS slno,
                a.*
            FROM (
                SELECT 
                    '1' AS MGRM_KEYID,
                    '2' AS EmpgroupName,
                    '3' AS NOOFMEMBERS
                UNION ALL
                SELECT 
                    MG.MGRM_KEYID AS MGRM_KEYID,
                    MG.MGRM_NAME AS EmpgroupName,
                    COUNT(*) AS NOOFMEMBERS
                FROM GEN_TL_MOM_GROUPMST MG
                INNER JOIN GEN_TL_MOM_GROUPDTL MD
                    ON MG.MGRM_KEYID = MD.MGRD_MGRM_KEYID
                GROUP BY MG.MGRM_KEYID, MG.MGRM_NAME
            ) a
        ) b
        WHERE slno BETWEEN 1 AND 100
        """,
        nativeQuery = true)
    List<Map<String,Object>> getEmpgroupViewGridData();

    @Query(value = """
    SELECT DISTINCT
        EMPM_KEYID AS KEYID,
        EMPM_CODE AS CODE,
        EMPM_NAME AS NAME,
        string_agg(ROLE_NAME, ',' ORDER BY ROLE_NAME) AS ROLE
    FROM GEN_TL_EMPLOYEEMST,
         GEN_TL_FNLNROLETEAM
    JOIN ADM_TL_ROLEMST
        ON GEN_TL_FNLNROLETEAM.FRT_ROLE_KEYID = ADM_TL_ROLEMST.ROLE_KEYID
    WHERE EMPM_KEYID = FRT_EMPM_KEYID
      AND FRT_FNLN_KEYID = :functional
      AND EMPM_KEYID NOT IN (
            SELECT MGRD_EMPM_KEYID
            FROM GEN_TL_MOM_GROUPDTL
            WHERE MGRD_MGRM_KEYID = :mstkeyid
      )
    GROUP BY EMPM_KEYID, EMPM_CODE, EMPM_NAME
    """,
    nativeQuery = true)
List<Map<String, Object>> getEmployeesByFunction(
        @Param("functional") String functional,
        @Param("mstkeyid") String mstkeyid);


   @Query(
    value = """
        SELECT * FROM (
            SELECT 
                '1' as chk,
                '2' AS keyid,
                '3' AS code,
                '4' AS name,
                '5' AS role
            UNION ALL
            SELECT DISTINCT
                '' as chk,
                e.EMPM_KEYID AS keyid,
                e.EMPM_CODE AS code,
                e.EMPM_NAME AS name,
                STRING_AGG(r.ROLE_NAME, ',' ORDER BY r.ROLE_NAME) AS role
            FROM GEN_TL_EMPLOYEEMST e
            JOIN GEN_TL_FNLNROLETEAM frt
                ON e.EMPM_KEYID = frt.FRT_EMPM_KEYID
            JOIN ADM_TL_ROLEMST r
                ON frt.FRT_ROLE_KEYID = r.ROLE_KEYID
            WHERE frt.FRT_FNLN_KEYID = :functional
              AND e.EMPM_KEYID NOT IN (
                  SELECT MGRD_EMPM_KEYID
                  FROM GEN_TL_MOM_GROUPDTL
                  WHERE MGRD_MGRM_KEYID = :mstKeyId
              )
            GROUP BY
                e.EMPM_KEYID,
                e.EMPM_CODE,
                e.EMPM_NAME
        ) combined_results
        """,
    nativeQuery = true
)
List<Map<String,Object>> getEmployees(
    @Param("functional") String functional,
    @Param("mstKeyId") String mstKeyId
);

@Query(
    value = """
        SELECT * FROM (
            SELECT 
                '1' as chk,
                '2' AS MGRD_MGRM_KEYID,
                '3' AS MGRD_KEYID,
                '4' AS EMPM_KEYID,
                '5' AS Code,
                '6' AS Name
            UNION ALL
            SELECT 
                '' as chk,
                MGRD_MGRM_KEYID,
                MGRD_KEYID,
                EMPM_KEYID,
                EMPM_CODE AS Code,
                EMPM_NAME AS Name
            FROM GEN_TL_MOM_GROUPDTL
            JOIN GEN_TL_EMPLOYEEMST 
                ON GEN_TL_MOM_GROUPDTL.MGRD_EMPM_KEYID = GEN_TL_EMPLOYEEMST.EMPM_KEYID
            WHERE MGRD_MGRM_KEYID = :mgrmKeyId
              AND EMPM_ACTIVE = 'Y'
        ) combined_results
        ORDER BY MGRD_KEYID
        """,
    nativeQuery = true
)
List<Map<String, Object>> getGroupEmployees(@Param("mgrmKeyId") String mgrmKeyId);

@Modifying
@Transactional
@Query(
    value = "DELETE FROM GEN_TL_MOM_GROUPDTL WHERE MGRD_KEYID = :mgrdKeyId",
    nativeQuery = true
)
int deleteGroupDtl(@Param("mgrdKeyId") String mgrdKeyId);

@Query(
    value = """
        SELECT * FROM (
            SELECT
                '1' AS chk,
                '2' AS MGRM_KEYID,
                '3' AS MGRM_NAME,
                '4' AS MGRM_PILLARID,
                '5' AS MGRM_FLID,
                '6' AS MGRM_EMAILID
            UNION ALL
            SELECT
                '' AS chk,
                MGRM_KEYID,
                MGRM_NAME,
                MGRM_PILLARID,
                MGRM_FLID,
                MGRM_EMAILID
            FROM GEN_TL_MOM_GROUPMST
            WHERE MGRM_KEYID = :mgrmKeyId
        ) combined_results
        ORDER BY MGRM_KEYID
        """,
    nativeQuery = true
)
List<Map<String, Object>> getGroupMstByKeyId(@Param("mgrmKeyId") String mgrmKeyId);
}

    

