package com.akranta.perfex_sb.repository;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.akranta.perfex_sb.model.SheTlIncidentmstnew;

import jakarta.transaction.Transactional;
@Repository
public interface SheTlIncidentmstnewRepository 
        extends JpaRepository<SheTlIncidentmstnew, String> {


    @Query(value = "SELECT APLM_KEYID FROM GEN_TL_ACTIONPLANMST " +
               "WHERE APLM_MASTERREFID = :masterRefId",
        nativeQuery = true)
        String findAplmKeyId(@Param("masterRefId") String masterRefId);

    @Query(value = "SELECT APLM_KEYID FROM GEN_TL_ACTIONPLANMST WHERE APLM_MASTERREFID = :masterRefId",
       nativeQuery = true)
       String getAplmKeyId(@Param("masterRefId") String masterRefId);

    @Query(value = "SELECT APLD_KEYID FROM GEN_TL_ACTIONPLANDTL WHERE APLD_APLM_KEYID = :aplmKeyId",
       nativeQuery = true)
       String getApldKeyId(@Param("aplmKeyId") String aplmKeyId);

    @Query(value = """
            SELECT STRING_AGG(WWMS_KEYID, ',' ORDER BY WWMS_KEYID)
            FROM BDM_TL_WHYWHYMST
            WHERE WWMS_REFDOCNO = :refDocNo
            """,
            nativeQuery = true)
    String findAggregatedKeyIds(@Param("refDocNo") String refDocNo);



@Query(value = """
            SELECT *
            FROM SHE_TL_INCIDENTMSTNEW
            WHERE SINM_KEYID = :keyid
            """,
            nativeQuery = true)
    SheTlIncidentmstnew findBySinmKeyIdNative(
            @Param("keyid") String keyid);

@Modifying
@Transactional
@Query(value = "UPDATE SHE_TL_INCIDENTMSTNEW SET " +
        "SINM_ACCIDENT_DESC = :accidentDesc, " +
        "SINM_EXACT_TYPE = :exactType, " +
        "SINM_ANALYSED_BY = :analysedBy, " +
        "SINM_ANALYSED_DATE = :analysedDate, " +
        "SINM_ANALYSED_SHIFT = :analysedShift, " +
        "SINM_RPTRECOMMENDATIONS = :recommendations, " +
        "SINM_INVESTIGATION = :investigation, " +
        "SINM_INVESTRMRKS = :investigationRemarks, " +
        "SINM_ROOTCAUSEDUETO = :rootCauseDueTo, " +
        "SINM_STATUS = 'P' " +
        "WHERE SINM_KEYID = :keyid",
        nativeQuery = true)
        int updateIncident(
        @Param("accidentDesc") String accidentDesc,
        @Param("exactType") String exactType,
        @Param("analysedBy") String analysedBy,
        @Param("analysedDate") LocalDateTime analysedDate,
        @Param("analysedShift") String analysedShift,
        @Param("recommendations") String recommendations,
        @Param("investigation") String investigation,
        @Param("investigationRemarks") String investigationRemarks,
        @Param("rootCauseDueTo") String rootCauseDueTo,
        @Param("keyid") String keyid
);    

@Modifying
    @Transactional
    @Query(value = "UPDATE SHE_TL_INCIDENTMSTNEW " +
                   "SET SINM_RESPONSIBILITY = :responsibility, " +
                   "SINM_RESPONSERMRKS = :remarks, " +
                   "SINM_TARGETDATE = :targetDate " +
                   "WHERE SINM_KEYID = :keyId", nativeQuery = true)
                   int assignResponsibility
                   (@Param("responsibility") String responsibility,
                   @Param("remarks") String remarks,
                   @Param("targetDate") LocalDateTime targetDate,
                   @Param("keyId") String keyId);

        
@Modifying
@Transactional
@Query(value = "UPDATE SHE_TL_INCIDENTMSTNEW SET " +
        "SINM_COMPLETEDBY = :completedBy, " +
        "SINM_DATECOMPLETED = :dateCompleted, " +
        "SINM_REVISEDDATE = :revisedDate, " +
        "SINM_STATUS = :status, " +
        "SINM_ACTIONTAKEN = :actionTaken, " +
        "SINM_COMPLETERMRKS = :completedRemarks, " +
        "SINM_VERIFIEDSTATUS = 'N' " +
        "WHERE SINM_KEYID = :keyId",
        nativeQuery = true)
int updateIncidentClosure(
        @Param("completedBy") String completedBy,
        @Param("dateCompleted") LocalDateTime dateCompleted,
        @Param("revisedDate") LocalDateTime revisedDate,
        @Param("status") String status,
        @Param("actionTaken") String actionTaken,
        @Param("completedRemarks") String completedRemarks,
        @Param("keyId") String keyId
);   

@Query(value = """
            SELECT APLM_KEYID, APLD_KEYID
            FROM SHE_TL_INCIDENTMSTNEW, GEN_TL_ACTIONPLANMST, GEN_TL_ACTIONPLANDTL
            WHERE APLM_MASTERREFID = SINM_KEYID
            AND APLM_KEYID = APLD_APLM_KEYID
            AND APLD_RESPONSIBILITY = SINM_RESPONSIBILITY
            AND APLD_TARGETDATE = SINM_TARGETDATE
            AND SINM_KEYID = :keyid
            """, nativeQuery = true)
    List<Object[]> findActionPlanIds(@Param("keyid") String keyid);


     @Query(value = "SELECT APLM_KEYID FROM GEN_TL_ACTIONPLANMST WHERE APLM_MASTERREFID = :masterRefId", nativeQuery = true)
    String findAplmKeyidByMasterRefId(@Param("masterRefId") String masterRefId);


    @Modifying
    @Transactional
    @Query(
        value = "DELETE FROM SHE_TL_INCIDENTMSTNEW WHERE sinm_keyid = :keyId",
        nativeQuery = true
    )
    int deleteByKeyId(String keyId);

    @Modifying
    @Transactional
    @Query(
        value = "DELETE FROM GEN_TL_ACTIONPLANDTL WHERE APLD_KEYID = :apldKeyId",
        nativeQuery = true
    )
    int deleteActionPlanDtl(String apldKeyId);

    @Modifying
    @Transactional
    @Query(
        value = "DELETE FROM GEN_TL_ACTIONPLANMST WHERE APLM_KEYID = :aplmKeyId",
        nativeQuery = true
    )
    int deleteActionPlanMst(String aplmKeyId);

    @Query(
        value = "SELECT APLM_KEYID FROM GEN_TL_ACTIONPLANMST WHERE APLM_MASTERREFID = :masterRefId",
        nativeQuery = true
    )
    String findKeyIdByMasterRefId(String masterRefId);

    @Query(
        value = "SELECT APLD_KEYID FROM GEN_TL_ACTIONPLANDTL WHERE APLD_APLM_KEYID = :aplmKeyId",
        nativeQuery = true
    )
    String findKeyIdByAplmKeyId(String aplmKeyId);


    @Modifying
    @Transactional
    @Query(value = "UPDATE SHE_TL_INCIDENTMSTNEW SET " +
            "SINM_VERIFIEDBY = :verifiedBy, " +
            "SINM_DATEVERIFIED = :dateVerified, " +
            "SINM_VERIFIEDSTATUS = :verifyStatus, " +
            "SINM_VERIFYRMRKS = :verifyRemarks, " +
            "SINM_APPROVALSTATUS = 'N' " +
            "WHERE SINM_KEYID = :keyId",
            nativeQuery = true)
    int updateIncidentVerification(
            @Param("verifiedBy") String verifiedBy,
            @Param("dateVerified") LocalDateTime dateVerified,
            @Param("verifyStatus") String verifyStatus,
            @Param("verifyRemarks") String verifyRemarks,
            @Param("keyId") String keyId);


    @Modifying
@Transactional
@Query(value = "UPDATE SHE_TL_INCIDENTMSTNEW " +
               "SET SINM_APPROVALBY = :approvalBy, " +
               "SINM_APPROVALDATE = :approvalDate, " +
               "SINM_APPROVALSTATUS = :approvalStatus, " +
               "SINM_APPROVALRMRKS = :approvalRemarks " +
               "WHERE SINM_KEYID = :keyId",
       nativeQuery = true)
int updateIncidentApproval(
        @Param("approvalBy") String approvalBy,
        @Param("approvalDate") LocalDateTime approvalDate,
        @Param("approvalStatus") String approvalStatus,
        @Param("approvalRemarks") String approvalRemarks,
        @Param("keyId") String keyId
);
}








    

