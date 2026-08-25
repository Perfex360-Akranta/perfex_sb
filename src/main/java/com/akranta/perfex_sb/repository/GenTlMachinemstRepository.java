package com.akranta.perfex_sb.repository;

import com.akranta.perfex_sb.model.GenTlMachinemst;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;


@Repository
public interface GenTlMachinemstRepository extends JpaRepository<GenTlMachinemst, String> {
    GenTlMachinemst findBykeyid(String machineId);


     @Query(value = """
            SELECT 
                '' AS selection,
                EMPM_KEYID,
                EMPM_CODE,
                EMPM_NAME 
            FROM GEN_TL_EMPLOYEEMST 
            WHERE 1 = 1
            AND (:factId IS NULL OR :factId = '' OR EMPM_FACTORYID = :factId)
            ORDER BY EMPM_NAME
            """, nativeQuery = true)
    List<Map<String, Object>> getOperatorData(@Param("factId") String factId);

    @Query(value = """
            SELECT 
                '' AS selection,
                ROW_NUMBER() OVER (ORDER BY MSKM_SKILLDESCRIPTION) as rownum,
                MSKM_SKILLDESCRIPTION
            FROM (
                SELECT DISTINCT MSKM_SKILLDESCRIPTION 
                FROM GEN_TL_MACHINESKILLMST
                WHERE MSKM_SKILLFORDEPARTMENT = 'O'
            ) subquery
            ORDER BY MSKM_SKILLDESCRIPTION
            """, nativeQuery = true)
    List<Map<String, Object>> getOperatorSkillData();

    @Query(value = """
            SELECT DISTINCT 
                CASE WHEN mtmm_keyid = mcmt_maintenanceteamid THEN 1 ELSE 0 END AS tick,
                mtmm_code,
                mtmm_name,
                mtmm_keyid
            FROM GEN_TL_MAINTENANCETEAMMST
            LEFT JOIN GEN_TL_MCHMAINTTEAMLINK 
                ON mtmm_keyid = mcmt_maintenanceteamid 
                AND mcmt_machineid = :machineId
            ORDER BY tick DESC, mtmm_code
            """, nativeQuery = true)
    List<Map<String, Object>> getMaintenanceTeamDataForMachine(@Param("machineId") String machineId);
    @Query(value = """
            SELECT 
                '' AS selection,
                ROW_NUMBER() OVER (ORDER BY MSKM_SKILLDESCRIPTION) as rownum,
                MSKM_SKILLDESCRIPTION
            FROM (
                SELECT DISTINCT MSKM_SKILLDESCRIPTION 
                FROM GEN_TL_MACHINESKILLMST
                WHERE MSKM_SKILLFORDEPARTMENT = 'M'
            ) subquery
            ORDER BY MSKM_SKILLDESCRIPTION
            """, nativeQuery = true)
    List<Map<String, Object>> getMaintenanceSkillData();
    @Query(value = """
            SELECT 
                sccv_keyid, 
                sccv_objectkey, 
                sccv_equipmentnum, 
                sccv_classtype,
                sccv_classnum, 
                sccv_intclassnum, 
                sccv_intcharnum, 
                sccv_charname, 
                sccv_charvalue
            FROM sap_classchar_val
            WHERE sccv_equipmentnum = :equipmentNum
            ORDER BY sccv_intcharnum
            """, nativeQuery = true)
    List<Map<String, Object>> getEquipmentData(@Param("equipmentNum") String equipmentNum);
     @Query(value = """
            SELECT DISTINCT
                CASE 
                    WHEN :eqpId IS NOT NULL AND :eqpId != '' 
                    THEN CASE WHEN mchm_keyid = scml_childmchid THEN 1 ELSE 0 END 
                    ELSE 0 
                END AS tick,
                mchm_keyid,
                '' AS empty_col,
                mchm_machineno,
                mchm_machinename
            FROM gen_tl_machinemst
            LEFT JOIN gen_tl_mchsubmchlink 
                ON mchm_keyid = scml_childmchid
                AND (:eqpId IS NULL OR :eqpId = '' OR scml_parentmchid = :eqpId)
            INNER JOIN gen_tl_cellmst 
                ON mchm_cellid = cell_keyid
            WHERE mchm_active = 'Y' 
                AND mchm_machineno <> '-'
                AND (:sectId IS NULL OR :sectId = '' OR cell_sectionid = :sectId)
                AND (:eqpId IS NULL OR :eqpId = '' OR mchm_keyid <> :eqpId)
            ORDER BY mchm_machineno
            """, nativeQuery = true)
    List<Map<String, Object>> getSubEquipmentData(
            @Param("sectId") String sectId, 
            @Param("eqpId") String eqpId);

             @Query(value = """
            SELECT DISTINCT 
                MCLK_CIRCLEID AS KEYID,
                CRCM_NAME AS CIRCLE
            FROM GEN_TL_MCHCIRCLELINK, GEN_TL_CIRCLEMST
            WHERE 1=1 
                AND CRCM_KEYID = MCLK_CIRCLEID 
                AND CRCM_ACTIVE = 'Y' 
                AND MCLK_MACHINEID = :mchId
            """, nativeQuery = true)
    List<Map<String, Object>> getFormCircle(@Param("mchId") String mchId);

    

@Query(value = """
        SELECT 
            EMPM_KEYID, 
            EMPM_NAME, 
            EMPM_CODE 
        FROM GEN_TL_MCHEMPLINK
        LEFT JOIN GEN_TL_EMPLOYEEMST 
            ON MCEM_EMPLOYEEID = EMPM_KEYID
        WHERE MCEM_MACHINEID = :machineId
        ORDER BY EMPM_NAME
        """, nativeQuery = true)
List<Map<String, Object>> recallOperatorData(@Param("machineId") String machineId);


@Query(value = """
        SELECT 
            MSKM_MACHINEID, 
            MSKM_SKILLDESCRIPTION, 
            MSKM_SKILLFORDEPARTMENT 
        FROM GEN_TL_MACHINESKILLMST 
        WHERE MSKM_MACHINEID = :machineId 
            AND MSKM_SKILLFORDEPARTMENT = 'O'
        """, nativeQuery = true)
List<Map<String, Object>> recallOperatorSkillData(@Param("machineId") String machineId);

@Query(value = """
        SELECT DISTINCT 
            MTMM_KEYID, 
            MTMM_CODE, 
            MTMM_NAME,
            CASE 
                WHEN MTMM_KEYID = MCMT_MAINTENANCETEAMID THEN 1 
                ELSE 0 
            END AS TICK 
        FROM GEN_TL_MAINTENANCETEAMMST, GEN_TL_MCHMAINTTEAMLINK 
        WHERE MTMM_KEYID = MCMT_MAINTENANCETEAMID 
            AND MCMT_MACHINEID = :machineId 
        ORDER BY TICK DESC, MTMM_CODE
        """, nativeQuery = true)
List<Map<String, Object>> recallMaintenanceData(@Param("machineId") String machineId);
@Query(value = """
        SELECT 
            MSKM_MACHINEID, 
            MSKM_SKILLDESCRIPTION, 
            MSKM_SKILLFORDEPARTMENT 
        FROM GEN_TL_MACHINESKILLMST 
        WHERE MSKM_MACHINEID = :machineId 
            AND MSKM_SKILLFORDEPARTMENT = 'M'
        """, nativeQuery = true)
List<Map<String, Object>> recallMaintenanceSkillData(@Param("machineId") String machineId);

@Query(value = """
        SELECT 
            MCPM_KEYID, 
            MCPM_PARAMETER, 
            MPLK_DESCRIPTION, 
            '' AS empty_col,
            CASE MCPM_ENTRYOPTION 
                WHEN 'T' THEN 'ListText' 
                WHEN 'N' THEN 'ListNumber' 
                WHEN 'S' THEN 'ListSelection' 
                WHEN 'D' THEN 'ListDate' 
                WHEN 'W' THEN 'ListDropdown' 
            END AS entry_option,
            REPLACE(MCPM_LISTTEXT, '{}', '') AS list_text,
            REPLACE(MCPM_LISTVALUE, 'X', '') AS list_value,
            CASE MCPM_ISMANDATORY 
                WHEN 'Y' THEN 'YES' 
                WHEN 'N' THEN 'NO' 
            END AS is_mandatory,
            TO_CHAR(MPLK_DATE, 'DD-MON-YYYY') AS parameter_date
        FROM GEN_TL_MCHPARAMETERCONFIG 
        LEFT JOIN GEN_TL_MCHPARAMETERLINK 
            ON MPLK_PARAMETERID = MCPM_KEYID 
            AND MPLK_KEYID = :machineId
            AND MPLK_ACTIVE = 'Y' 
        WHERE MCPM_ACTIVE = 'Y' 
        ORDER BY MCPM_SLNO
        """, nativeQuery = true)
List<Map<String, Object>> recallEquipmentParameterData(@Param("machineId") String machineId);
@Modifying
@Query(value = "DELETE FROM GEN_TL_MACHINESKILLMST WHERE MSKM_MACHINEID = :machineId AND MSKM_SKILLDESCRIPTION = :skillDescription AND MSKM_SKILLFORDEPARTMENT = 'O'", nativeQuery = true)
int deleteOperatorSkill(@Param("machineId") String machineId, @Param("skillDescription") String skillDescription);

@Modifying
@Query(value = "DELETE FROM GEN_TL_MACHINESKILLMST WHERE MSKM_MACHINEID = :machineId AND MSKM_SKILLDESCRIPTION = :skillDescription AND MSKM_SKILLFORDEPARTMENT = 'M'", nativeQuery = true)
int deleteMaintenanceSkill(@Param("machineId") String machineId, @Param("skillDescription") String skillDescription);

    @Modifying
@Query(value = "DELETE FROM GEN_TL_MCHEMPLINK WHERE MCEM_MACHINEID = :machineId AND MCEM_EMPLOYEEID = :employeeId", nativeQuery = true)
int deleteOperatorMachineLink(@Param("machineId") String machineId, @Param("employeeId") String employeeId);

@Modifying
@Query(value = "DELETE FROM GEN_TL_MCHMAINTTEAMLINK WHERE MCMT_MACHINEID = :machineId AND MCMT_MAINTENANCETEAMID = :maintenanceTeamId", nativeQuery = true)
int deleteMaintenanceTeamMachineLink(@Param("machineId") String machineId, @Param("maintenanceTeamId") String maintenanceTeamId);
}

