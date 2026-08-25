package com.akranta.perfex_sb.repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.akranta.perfex_sb.model.GenTlEmployeemst;

@Repository
public interface ApplicationMaintananceRepo extends JpaRepository<GenTlEmployeemst, String> {

        // ------------------------------------------------EMPLOYEE USER
        // INACTIVE----------------------------------------------------------------//
        @Query(value = """
                            SELECT
                                '1' AS EMPM_KEYID,
                                '2' AS EMPM_CODE,
                                '3' AS EMPM_NAME,
                                '4' AS EMPM_EMPLOYEETYPE,
                                '5' AS LOCN_NAME,
                                '6' AS USRM_VALIDTILL,
                                '7' AS USRM_REMARKS,
                                '9' AS BTN_INACTIVE
                            UNION
                            SELECT
                                E.EMPM_KEYID AS EMPM_KEYID,
                                E.EMPM_CODE AS EMPM_CODE,
                                E.EMPM_NAME AS EMPM_NAME,
                                E.EMPM_EMPLOYEETYPE AS EMPM_EMPLOYEETYPE,
                                L.LOCN_NAME AS LOCN_NAME,
                                TO_CHAR(U.USRM_VALIDTILL,'DD-MM-YYYY') AS USRM_VALIDTILL,
                                U.USRM_REMARKS AS USRM_REMARKS,
                                '' AS BTN_INACTIVE
                            FROM GEN_TL_EMPLOYEEMST E
                            JOIN GEN_TL_LOCATIONMST L ON E.EMPM_LOCATION = L.LOCN_KEYID
                            JOIN ADM_TL_USERMST U ON E.EMPM_KEYID = U.USRM_CCNO
                            WHERE E.EMPM_ACTIVE = 'Y'
                            AND (:empKey IS NULL OR E.EMPM_KEYID = :empKey)
                            ORDER BY EMPM_NAME ASC
                        """, nativeQuery = true)
        List<Map<String, Object>> getEmployeeList(@Param("empKey") String keyId);

        @Modifying
        @Transactional
        @Query(value = """
                        UPDATE ADM_TL_USERMST
                        SET USRM_ISVALIDITYREQ = 'Y',
                            USRM_ISACTIVE = 'N',
                            USRM_REMARKS = :remarks,
                            USRM_VALIDTILL = CURRENT_DATE
                        WHERE USRM_CCNO = :empKeyId
                        """, nativeQuery = true)
        int deactivateUser(
                        @Param("empKeyId") String empKeyId,
                        @Param("remarks") String remarks);

        @Modifying
        @Transactional
        @Query(value = """
                        UPDATE GEN_TL_EMPLOYEEMST
                        SET EMPM_ACTIVE = 'N'
                        WHERE EMPM_KEYID = :empKeyId
                        """, nativeQuery = true)
        int deactivateEmployee(
                        @Param("empKeyId") String empKeyId);

        // ------------------------------------------------LOCATION
        // TRANSFER----------------------------------------------------------------//

        @Query(value = """
                        SELECT
                            '1'        AS EMPM_KEYID,
                            '2'         AS EMPM_CODE,
                            '3'         AS EMPM_NAME,
                            '4' AS EMPM_EMPLOYEETYPE,
                            '5'     AS EMPM_LOCATION,
                            '6'         AS LOCN_NAME,
                            '7' AS COL1,
                            '8' AS COL2,
                            '9' AS COL3
                        UNION
                        SELECT
                            E.EMPM_KEYID        AS EMPM_KEYID,
                            E.EMPM_CODE         AS EMPM_CODE,
                            E.EMPM_NAME         AS EMPM_NAME,
                            E.EMPM_EMPLOYEETYPE AS EMPM_EMPLOYEETYPE,
                            E.EMPM_LOCATION     AS EMPM_LOCATION,
                            L.LOCN_NAME         AS LOCN_NAME,
                            '' AS COL1,
                            '' AS COL2,
                            '' AS COL3
                        FROM GEN_TL_EMPLOYEEMST E
                        JOIN GEN_TL_LOCATIONMST L
                             ON E.EMPM_LOCATION = L.LOCN_KEYID
                        WHERE E.EMPM_ACTIVE = 'Y'
                        AND (:empKey IS NULL OR E.EMPM_KEYID = :empKey)
                        AND (:location IS NULL OR E.EMPM_LOCATION = :location)
                        ORDER BY EMPM_NAME ASC
                        """, nativeQuery = true)
        List<Map<String, Object>> getEmployeeLocation(
                        @Param("empKey") String empKey,
                        @Param("location") String location);

        // 1️⃣ Delete from GEN_TL_TEAMTRADELINK
        @Modifying
        @Transactional
        @Query(value = """
                        DELETE FROM GEN_TL_TEAMTRADELINK
                        WHERE FRP_FRT_KEYID IN (
                            SELECT FRT_KEYID
                            FROM GEN_TL_FNLNROLETEAM
                            WHERE FRT_EMPM_KEYID = :empKeyId
                        )
                        """, nativeQuery = true)
        int deleteTeamTradeLink(@Param("empKeyId") String empKeyId);

        // 2️⃣ Delete from GEN_TL_FNLNROLETEAM
        @Modifying
        @Transactional
        @Query(value = """
                        DELETE FROM GEN_TL_FNLNROLETEAM
                        WHERE FRT_EMPM_KEYID = :empKeyId
                        """, nativeQuery = true)
        int deleteRoleTeam(@Param("empKeyId") String empKeyId);

        // 3️⃣ Update employee location
        @Modifying
        @Transactional
        @Query(value = """
                        UPDATE GEN_TL_EMPLOYEEMST
                        SET EMPM_LOCATION = :location
                        WHERE EMPM_KEYID = :empKeyId
                        """, nativeQuery = true)
        int updateEmployeeLocation(
                        @Param("empKeyId") String empKeyId,
                        @Param("location") String location);
        // -----------------------------------EMPLOYEE AND USER
        // ACTIVE--------------------------------------------------//

        // 1️⃣ Update user master
        @Modifying
        @Transactional
        @Query(value = """
                        UPDATE ADM_TL_USERMST
                        SET USRM_ISACTIVE = 'Y',
                            USRM_ISVALIDITYREQ = 'Y',
                            USRM_REMARKS = :remarks,
                            USRM_VALIDTILL = CAST(:validTill AS DATE)
                        WHERE USRM_CCNO = :empKeyId
                        """, nativeQuery = true)
        int activateUser(
                        @Param("empKeyId") String empKeyId,
                        @Param("remarks") String remarks,
                        @Param("validTill") String validTill);

        // 2️⃣ Update employee master
        @Modifying
        @Transactional
        @Query(value = """
                        UPDATE GEN_TL_EMPLOYEEMST
                        SET EMPM_ACTIVE = 'Y'
                        WHERE EMPM_KEYID = :empKeyId
                        """, nativeQuery = true)
        int activateEmployee(
                        @Param("empKeyId") String empKeyId);

        // ------------------------------------------------ABNORMALITY
        // DELETE-----------------------------------------------

        @Query(value = "SELECT FLID FROM GEN_MV_FLIDHIERARCHY WHERE FNLN_ORIGINALID = :flid", nativeQuery = true)
        String findFlidByOriginalId(@Param("flid") String flid);

        @Modifying
        @Transactional
        @Query(value = """
                        DELETE FROM GEN_TL_ACTIONPLANDTL
                        WHERE APLD_APLM_KEYID = (
                            SELECT APLM_KEYID
                            FROM GEN_TL_ACTIONPLANMST
                            WHERE APLM_DETAILREFID = :abnKeyid
                        )
                        """, nativeQuery = true)
        void deleteAbnActionPlanDtl(@Param("abnKeyid") String abnKeyid);

        @Modifying
        @Transactional
        @Query(value = """
                        DELETE FROM GEN_TL_ACTIONPLANMST
                        WHERE APLM_DETAILREFID = :abnKeyid
                        """, nativeQuery = true)
        void deleteAbnActionPlanMst(@Param("abnKeyid") String abnKeyid);

        @Modifying
        @Transactional
        @Query(value = """
                        DELETE FROM BDM_TL_WHYWHYDTL
                        WHERE WWDT_WWMS_KEYID = (
                            SELECT WWMS_KEYID
                            FROM BDM_TL_WHYWHYMST
                            WHERE WWMS_REFDOCNO = :abnKeyid
                        )
                        """, nativeQuery = true)
        void deleteWhyWhyDtl(@Param("abnKeyid") String abnKeyid);

        @Modifying
        @Transactional
        @Query(value = """
                        DELETE FROM BDM_TL_YYDONEBYMST
                        WHERE WWDB_WWMS_KEYID = (
                            SELECT WWMS_KEYID
                            FROM BDM_TL_WHYWHYMST
                            WHERE WWMS_REFDOCNO = :abnKeyid
                        )
                        """, nativeQuery = true)
        void deleteDoneBy(@Param("abnKeyid") String abnKeyid);

        @Modifying
        @Transactional
        @Query(value = """
                        DELETE FROM BDM_TL_YYPROBLEMATTBYMST
                        WHERE WWPA_WWMS_KEYID = (
                            SELECT WWMS_KEYID
                            FROM BDM_TL_WHYWHYMST
                            WHERE WWMS_REFDOCNO = :abnKeyid
                        )
                        """, nativeQuery = true)
        void deleteProblemAttBy(@Param("abnKeyid") String abnKeyid);

        @Modifying
        @Transactional
        @Query(value = """
                        DELETE FROM BDM_TL_YYEFFECTIVEDTL
                        WHERE YYED_YYEF_KEYID IN (
                            SELECT YYEF_KEYID
                            FROM BDM_TL_YYEFFECTIVEMST
                            WHERE YYEF_WWMS_KEYID = (
                                SELECT WWMS_KEYID
                                FROM BDM_TL_WHYWHYMST
                                WHERE WWMS_REFDOCNO = :abnKeyid
                            )
                        )
                        """, nativeQuery = true)
        void deleteEffectiveDtl(@Param("abnKeyid") String abnKeyid);

        @Modifying
        @Transactional
        @Query(value = """
                        DELETE FROM BDM_TL_YYEFFECTIVEMST
                        WHERE YYEF_WWMS_KEYID = (
                            SELECT WWMS_KEYID
                            FROM BDM_TL_WHYWHYMST
                            WHERE WWMS_REFDOCNO = :abnKeyid
                        )
                        """, nativeQuery = true)
        void deleteEffectiveMst(@Param("abnKeyid") String abnKeyid);

        @Modifying
        @Transactional
        @Query(value = """
                        DELETE FROM BDM_TL_WHYWHYMST
                        WHERE WWMS_REFDOCNO = :abnKeyid
                        """, nativeQuery = true)
        void deleteWhyWhyMst(@Param("abnKeyid") String abnKeyid);

        @Modifying
        @Transactional
        @Query(value = """
                        DELETE FROM DCM_TL_DOCUMENTMANAGER
                        WHERE DMDM_REFDOCNO = :abnKeyid
                        """, nativeQuery = true)
        void deleteDocuments(@Param("abnKeyid") String abnKeyid);

        @Modifying
        @Transactional
        @Query(value = """
                        DELETE FROM ABN_TL_DTL
                        WHERE ABND_ABNORMALITYID = :abnKeyid
                        """, nativeQuery = true)
        void deleteAbnormalityDtl(@Param("abnKeyid") String abnKeyid);

        @Modifying
        @Transactional
        @Query(value = """
                        DELETE FROM ABN_TL_ABNORMALITY
                        WHERE ABNM_KEYID = :abnKeyid
                        """, nativeQuery = true)
        void deleteAbnormality(@Param("abnKeyid") String abnKeyid);

        // --------------------------------------------------DELETE
        // SUGGESSTION----------------------------------------------------------------------//

        @Modifying
        @Transactional
        @Query(value = "DELETE FROM KZN_TL_KAIZENBANKMST WHERE KZBN_KEYID = :suggKeyid", nativeQuery = true)
        void deleteSuggestion(@Param("suggKeyid") String suggKeyid);

        // -----------------------------------DELETE KAIZEN
        // --------------------------------------------------------------//

        /* ---------- Document Manager ---------- */

        @Modifying
        @Query(value = """
                        DELETE FROM DCM_TL_DOCUMENTLAYOUT
                        WHERE DMLY_PARENTID = (
                            SELECT DMDM_KEYID
                            FROM DCM_TL_DOCUMENTMANAGER
                            WHERE DMDM_REFDOCNO = :kaizenKeyid
                        )
                        """, nativeQuery = true)
        void deleteKznDocumentLayout(@Param("kaizenKeyid") String kaizenKeyid);

        @Modifying
        @Query(value = """
                        DELETE FROM DCM_TL_DOCUMENTMANAGER
                        WHERE DMDM_REFDOCNO = :kaizenKeyid
                        """, nativeQuery = true)
        void deleteKznDocumentManager(@Param("kaizenKeyid") String kaizenKeyid);

        /* ---------- Action Plan ---------- */

        @Modifying
        @Query(value = """
                        DELETE FROM GEN_TL_ACTIONPLANDTL
                        WHERE APLD_APLM_KEYID = (
                            SELECT APLM_KEYID
                            FROM GEN_TL_ACTIONPLANMST
                            WHERE APLM_DETAILREFID = :kaizenKeyid
                        )
                        """, nativeQuery = true)
        void deleteKznActionPlanDtl(@Param("kaizenKeyid") String kaizenKeyid);

        @Modifying
        @Query(value = """
                        DELETE FROM GEN_TL_ACTIONPLANMST
                        WHERE APLM_DETAILREFID = :kaizenKeyid
                        """, nativeQuery = true)
        void deleteKznActionPlanMst(@Param("kaizenKeyid") String kaizenKeyid);

        /* ---------- Workflow ---------- */

        @Modifying
        @Query(value = """
                        DELETE FROM GEN_TL_WORKFLOW_INFO
                        WHERE WRIN_REF_ID = :kaizenKeyid
                        """, nativeQuery = true)
        void deleteKznWorkflow(@Param("kaizenKeyid") String kaizenKeyid);

        /* ---------- Kaizen Related ---------- */

        @Modifying
        @Query(value = """
                        DELETE FROM KZN_TL_HDMST
                        WHERE KHDM_KAIZENID = :kaizenKeyid
                        """, nativeQuery = true)
        void deleteKaizenHeader(@Param("kaizenKeyid") String kaizenKeyid);

        @Modifying
        @Query(value = """
                        DELETE FROM KZN_TL_GRAPHDATA
                        WHERE KZGD_KAIZENID = :kaizenKeyid
                        """, nativeQuery = true)
        void deleteGraphData(@Param("kaizenKeyid") String kaizenKeyid);

        @Modifying
        @Query(value = """
                        DELETE FROM KZN_TL_MST
                        WHERE KZNM_KEYID = :kaizenKeyid
                        """, nativeQuery = true)
        void deleteKaizenMaster(@Param("kaizenKeyid") String kaizenKeyid);

        // ---------------------------------------ACTION PLAN DELETE
        // -----------------------------------------------------//

        /* Delete Action Plan Details */
        @Modifying
        @Query(value = "DELETE FROM GEN_TL_ACTIONPLANDTL WHERE APLD_APLM_KEYID = :actionPlanKeyid", nativeQuery = true)
        void deleteActionPlanDtl(@Param("actionPlanKeyid") String actionPlanKeyid);

        /* Delete Action Plan Master */
        @Modifying
        @Query(value = "DELETE FROM GEN_TL_ACTIONPLANMST WHERE APLM_KEYID = :actionPlanKeyid", nativeQuery = true)
        void deleteActionPlanMst(@Param("actionPlanKeyid") String actionPlanKeyid);

        // ----------------------------------------------DELETE WHY WHY
        // -------------------------------------------------------------------------------//

        /* ---------- WHY WHY ---------- */

        @Modifying
        @Query(value = "DELETE FROM BDM_TL_WHYWHYDTL WHERE WWDT_WWMS_KEYID = :whyWhyKeyid", nativeQuery = true)
        void deleteWhyWhyWhyDtl(@Param("whyWhyKeyid") String whyWhyKeyid);

        @Modifying
        @Query(value = "DELETE FROM BDM_TL_YYPROBLEMATTBYMST WHERE WWPA_WWMS_KEYID = :whyWhyKeyid", nativeQuery = true)
        void deleteWhyProblemAttBy(@Param("whyWhyKeyid") String whyWhyKeyid);

        @Modifying
        @Query(value = "DELETE FROM BDM_TL_YYDONEBYMST WHERE WWDB_WWMS_KEYID = :whyWhyKeyid", nativeQuery = true)
        void deleteWhyDoneBy(@Param("whyWhyKeyid") String whyWhyKeyid);

        @Modifying
        @Query(value = "DELETE FROM BDM_TL_WHYWHYMST WHERE WWMS_KEYID = :whyWhyKeyid", nativeQuery = true)
        void deleteBdmWhyWhyMst(@Param("whyWhyKeyid") String whyWhyKeyid);

        /* ---------- DOCUMENT ---------- */

        @Modifying
        @Query(value = """
                        DELETE FROM DCM_TL_DOCUMENTLAYOUT
                        WHERE DMLY_PARENTID = (
                            SELECT DMDM_KEYID
                            FROM DCM_TL_DOCUMENTMANAGER
                            WHERE DMDM_REFDOCNO = :whyWhyKeyid
                        )
                        """, nativeQuery = true)
        void deleteWhyDocumentLayout(@Param("whyWhyKeyid") String whyWhyKeyid);

        @Modifying
        @Query(value = "DELETE FROM DCM_TL_DOCUMENTMANAGER WHERE DMDM_REFDOCNO = :whyWhyKeyid", nativeQuery = true)
        void deleteWhyDocumentManager(@Param("whyWhyKeyid") String whyWhyKeyid);

        /* ---------- ACTION PLAN ---------- */

        @Modifying
        @Query(value = """
                        DELETE FROM GEN_TL_ACTIONPLANDTL
                        WHERE APLD_APLM_KEYID = (
                            SELECT APLM_KEYID
                            FROM GEN_TL_ACTIONPLANMST
                            WHERE APLM_MASTERREFID = :whyWhyKeyid
                        )
                        """, nativeQuery = true)
        void deleteWhyWhyActionPlanDtl(@Param("whyWhyKeyid") String whyWhyKeyid);

        @Modifying
        @Query(value = "DELETE FROM GEN_TL_ACTIONPLANMST WHERE APLM_MASTERREFID = :whyWhyKeyid", nativeQuery = true)
        void deleteWhyWhyActionPlanMst(@Param("whyWhyKeyid") String whyWhyKeyid);

        /* ---------- OPL ---------- */

        @Modifying
        @Query(value = "DELETE FROM OPL_TL_MST WHERE OPLM_REFDOCNO = :whyWhyKeyid", nativeQuery = true)
        void deleteOpl(@Param("whyWhyKeyid") String whyWhyKeyid);

        /* ---------- KAIZEN BANK ---------- */

        @Modifying
        @Query(value = "DELETE FROM KZN_TL_KAIZENBANKMST WHERE KZBN_REFDOCNO = :whyWhyKeyid", nativeQuery = true)
        void deleteKaizenBank(@Param("whyWhyKeyid") String whyWhyKeyid);

        // -------------------------------------TRAINING CALENDAR
        // DELETE--------------------------------------------------------------------------//

        /* ---------- TRAINING CALENDAR CHILD TABLES ---------- */

        @Modifying
        @Query(value = "DELETE FROM ENT_TL_TRGCALSESSION WHERE ETCS_ETCM_KEYID = :trainingKeyid", nativeQuery = true)
        void ETDeleteSession(@Param("trainingKeyid") String trainingKeyid);

        @Modifying
        @Query(value = "DELETE FROM ENT_TL_TRGCALUNQP WHERE ETCU_ETCM_KEYID = :trainingKeyid", nativeQuery = true)
        void ETDeleteUnqp(@Param("trainingKeyid") String trainingKeyid);

        @Modifying
        @Query(value = "DELETE FROM ENT_TL_TRGCALEMP WHERE ETCE_ETCM_KEYID = :trainingKeyid", nativeQuery = true)
        void ETDeleteEmployee(@Param("trainingKeyid") String trainingKeyid);

        @Modifying
        @Query(value = "DELETE FROM ENT_TL_TRGCALEMPATSCORE WHERE ETCA_ETCM_KEYID = :trainingKeyid", nativeQuery = true)
        void ETDeleteEmpAtScore(@Param("trainingKeyid") String trainingKeyid);

        @Modifying
        @Query(value = "DELETE FROM ENT_TL_TRGCALQUAD WHERE ETCQ_L1_TRGCALID = :trainingKeyid", nativeQuery = true)
        void ETDeleteQuad(@Param("trainingKeyid") String trainingKeyid);

        @Modifying
        @Query(value = "DELETE FROM ENT_TL_TRGFACULTY WHERE ETCF_ETCM_KEYID = :trainingKeyid", nativeQuery = true)
        void ETDeleteFaculty(@Param("trainingKeyid") String trainingKeyid);

        /* ---------- DOCUMENT TABLES ---------- */

        @Modifying
        @Query(value = """
                        DELETE FROM DCM_TL_DOCUMENTLAYOUT
                        WHERE DMLY_PARENTID = (
                            SELECT DMDM_KEYID
                            FROM DCM_TL_DOCUMENTMANAGER
                            WHERE DMDM_REFDOCNO = :trainingKeyid
                        )
                        """, nativeQuery = true)
        void ETDeleteDocumentLayout(@Param("trainingKeyid") String trainingKeyid);

        @Modifying
        @Query(value = "DELETE FROM DCM_TL_DOCUMENTMANAGER WHERE DMDM_REFDOCNO = :trainingKeyid", nativeQuery = true)
        void ETDeleteDocumentManager(@Param("trainingKeyid") String trainingKeyid);

        /* ---------- TRAINING CALENDAR MASTER ---------- */

        @Modifying
        @Query(value = "DELETE FROM ENT_TL_TRGCALMST WHERE ETCM_KEYID = :trainingKeyid", nativeQuery = true)
        void ETDeleteTrainingMaster(@Param("trainingKeyid") String trainingKeyid);

        // -----------------------------------------DELETE
        // LOSS-----------------------------------------------------------//

        /* ---------- ACTION PLAN DETAIL ---------- */
        @Modifying
        @Query(value = """
                        DELETE FROM GEN_TL_ACTIONPLANDTL
                        WHERE APLD_APLM_KEYID IN (
                            SELECT APLM_KEYID
                            FROM GEN_TL_ACTIONPLANMST
                            WHERE APLM_MASTERREFID = :lossKeyid
                        )
                        """, nativeQuery = true)
        void deleteActionPlanDtlByLoss(@Param("lossKeyid") String lossKeyid);

        /* ---------- ACTION PLAN MASTER ---------- */
        @Modifying
        @Query(value = """
                        DELETE FROM GEN_TL_ACTIONPLANMST
                        WHERE APLM_MASTERREFID = :lossKeyid
                        """, nativeQuery = true)
        void deleteActionPlanMstByLoss(@Param("lossKeyid") String lossKeyid);

        /* ---------- LOSS CAPTURE ---------- */
        @Modifying
        @Query(value = """
                        DELETE FROM PCS_TL_LOSSCAPTURE
                        WHERE PLOS_KEYID = :lossKeyid
                        """, nativeQuery = true)
        void deleteLoss(@Param("lossKeyid") String lossKeyid);

        // --------------------------------------------------ABNORMALITY
        // CLOSURE-------------------------------------------//

        /* ---------- ACTION PLAN DETAIL ---------- */
        @Modifying
        @Query(value = """
                        UPDATE GEN_TL_ACTIONPLANDTL
                        SET
                            APLD_STATUS = :status,
                            APLD_COMPLETEDBY = APLD_RESPONSIBILITY,
                            APLD_COMPLEATEDON = APLD_TARGETDATE,
                            APLD_COUNTERMEASURE = :counterMeasure
                        WHERE APLD_APLM_KEYID = (
                            SELECT APLM_KEYID
                            FROM GEN_TL_ACTIONPLANMST
                            WHERE APLM_DETAILREFID = :abnmKeyid
                        )
                        """, nativeQuery = true)
        void updateAbnActionPlanDtl(@Param("status") String status,
                        @Param("counterMeasure") String counterMeasure,
                        @Param("abnmKeyid") String abnmKeyid);

        /* ---------- ACTION PLAN MASTER ---------- */
        @Modifying
        @Query(value = """
                        UPDATE GEN_TL_ACTIONPLANMST
                        SET APLM_STATUS = :status
                        WHERE APLM_DETAILREFID = :abnmKeyid
                        """, nativeQuery = true)
        void updateAbnActionPlanMst(@Param("status") String status,
                        @Param("abnmKeyid") String abnmKeyid);

        /* ---------- ABNORMALITY ---------- */
        @Modifying
        @Query(value = """
                        UPDATE ABN_TL_ABNORMALITY
                        SET
                            ABNM_STATUS = :status,
                            ABNM_COUNTERMEASURE = :counterMeasure,
                            ABNM_WOENDTIME = :completedDate,
                            ABNM_COMPLETEDBY = :completedBy
                        WHERE ABNM_KEYID = :abnmKeyid
                        """, nativeQuery = true)
        void updateAbnormality(@Param("status") String status,
                        @Param("counterMeasure") String counterMeasure,
                        @Param("completedDate") LocalDateTime completedDate,
                        @Param("completedBy") String completedBy,
                        @Param("abnmKeyid") String abnmKeyid);

        // -----------------------------------------ACTION PLAN CLOSURE
        // ---------------------------------------------------//

        /* ---------- ACTION PLAN DETAIL ---------- */
        @Modifying
        @Query(value = """
                        UPDATE GEN_TL_ACTIONPLANDTL
                        SET
                            APLD_STATUS = :status,
                            APLD_COMPLEATEDON = :completedOn,
                            APLD_COMPLETEDBY = :completedBy,
                            APLD_COUNTERMEASURE = :counterMeasure
                        WHERE APLD_KEYID = :detailId
                        """, nativeQuery = true)
        void updateActionPlanDtl(@Param("status") String status,
                        @Param("completedOn") LocalDateTime completedOn,
                        @Param("completedBy") String completedBy,
                        @Param("counterMeasure") String counterMeasure,
                        @Param("detailId") String detailId);

        /* ---------- ACTION PLAN MASTER ---------- */
        @Modifying
        @Query(value = """
                        UPDATE GEN_TL_ACTIONPLANMST
                        SET APLM_STATUS = COALESCE(
                            (
                                SELECT 'P'
                                FROM GEN_TL_ACTIONPLANDTL
                                WHERE APLD_APLM_KEYID = :actionPlanId
                                  AND APLD_STATUS = 'P'
                                LIMIT 1
                            ),
                            'C'
                        )
                        WHERE APLM_KEYID = :actionPlanId
                        """, nativeQuery = true)
        void updateActionPlanMst(@Param("actionPlanId") String actionPlanId);

        // ----------------------------------------KAIZEN DATE CHANGE
        // ---------------------------------------------------//

        @Modifying
        @Query(value = """
                        UPDATE KZN_TL_MST
                        SET KZNM_DATE = :kaizenDate
                        WHERE KZNM_KEYID = :kaizenKeyid
                        """, nativeQuery = true)
        void updateKaizenDate(@Param("kaizenDate") LocalDateTime kaizenDate,
                        @Param("kaizenKeyid") String kaizenKeyid);

        /* ---------- UPDATE KAIZEN BANK DATE ---------- */
        @Modifying
        @Query(value = """
                        UPDATE KZN_TL_KAIZENBANKMST
                        SET KZBN_DATE = :kaizenDate
                        WHERE KZBN_KEYID = (
                            SELECT KZNM_KZBNKEYID
                            FROM KZN_TL_MST
                            WHERE KZNM_KEYID = :kaizenKeyid
                        )
                        """, nativeQuery = true)
        void updateKaizenBankDate(@Param("kaizenDate") LocalDateTime kaizenDate,
                        @Param("kaizenKeyid") String kaizenKeyid);

        // ------------------------------------FIP PROJECT
        // DATE-------------------------------------------------------------//

        @Modifying
        @Query(value = """
                        UPDATE KZN_TL_PROJECTCREATIONMST
                        SET KZPM_ENDDATE = :endDate
                        WHERE KZPM_KEYID = :fiProjectId
                        """, nativeQuery = true)
        void updateFIProjectEndDate(@Param("fiProjectId") String fiProjectId,
                        @Param("endDate") LocalDateTime endDate);

        // -----------------------------------------JH DMT TRANSFER
        // -------------------------------------------------------//

        @Query(value = """
                         SELECT
                                '1'   AS keyid,
                                '2' AS rolekeyid,
                                '3' AS rolename,
                                '4' AS rolecode
                        UNION ALL
                            SELECT
                                FRL_KEYID   AS keyid,
                                ROLE_KEYID AS rolekeyid,
                                ROLE_NAME AS rolename,
                                ROLE_CODE AS rolecode
                            FROM adm_tl_rolemst r
                            JOIN gen_tl_fnlnrolemap f
                              ON f.FRL_ROLE_KEYID = r.ROLE_KEYID
                            WHERE r.ROLE_ACTIVE = 'Y'
                              AND f.FRL_LEVEL <= :level
                              AND f.FRL_LEVEL > (:level - 1)
                            ORDER BY rolename
                        """, nativeQuery = true)
        List<Map<String, Object>> findRolesByLevel(@Param("level") Integer level);

}
