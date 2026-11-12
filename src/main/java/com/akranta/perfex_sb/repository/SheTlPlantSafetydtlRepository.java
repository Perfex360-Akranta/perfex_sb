package com.akranta.perfex_sb.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.akranta.perfex_sb.model.SheTlPlantSafetydtl;
import java.util.List;
import java.util.Map;


@Repository
public interface SheTlPlantSafetydtlRepository extends JpaRepository<SheTlPlantSafetydtl, String> {

   @Query(value = "SELECT * FROM she_tl_plantsafetydtl WHERE spsd_spsm_keyid = :keyid", nativeQuery = true)
List<Map<String, Object>> findBySpsmKeyid(@Param("keyid") String keyid);

@Query(value = "SELECT spsd_responsibility FROM she_tl_plantsafetydtl WHERE spsd_keyid = :keyid", nativeQuery = true)
    String findResponsibilityByKeyid(@Param("keyid") String keyid);

    @Query(value = "SELECT spsd_observations FROM she_tl_plantsafetydtl WHERE spsd_keyid = :keyid", nativeQuery = true)
    String findObservationsByKeyid(@Param("keyid") String keyid);

    @Query(value = "SELECT spsd_spsm_keyid FROM she_tl_plantsafetydtl WHERE spsd_keyid = :keyid", nativeQuery = true)
    String findMasterKeyidByKeyid(@Param("keyid") String keyid);

    @Query(value = "SELECT spsd_respremarks FROM she_tl_plantsafetydtl WHERE spsd_keyid = :keyid", nativeQuery = true)
    String findRespRemarksByKeyid(@Param("keyid") String keyid);
 
 @Modifying
@Query(value = "UPDATE she_tl_plantsafetydtl SET spsd_responsibility = :responsevalue, " +
               "spsd_targetdate = :targetdate, " +
               "spsd_respremarks = :responRemarks " +
               "WHERE spsd_keyid = :detailkeyid", nativeQuery = true)
void updateResponsibility(@Param("detailkeyid") String detailkeyid,
                          @Param("responsevalue") String responsevalue,
                          @Param("targetdate") LocalDateTime targetdate,  // ✅ changed to LocalDateTime
                          @Param("responRemarks") String responRemarks);


                          @Modifying
@Query(value = "UPDATE she_tl_plantsafetydtl " +
               "SET spsd_responsibility = :verid, " +
               "spsd_targetdate = :verifydate " +
               "WHERE spsd_keyid = :dtlid", nativeQuery = true)
void updateVerify(@Param("dtlid") String dtlid,
                  @Param("verid") String verid,
                  @Param("verifydate") LocalDateTime verifydate);

               @Modifying
@Query(value = "UPDATE she_tl_plantsafetydtl " +
               "SET spsd_responsibility = :resid, " +
               "spsd_targetdate = :targetdate " +
               "WHERE spsd_keyid = :dtlid", nativeQuery = true)
void resUpdate(@Param("dtlid") String dtlid,
               @Param("resid") String resid,
               @Param("targetdate") LocalDateTime targetdate);
               
   @Modifying
@Query(value = "DELETE FROM she_tl_plantsafetydtl WHERE spsd_keyid = :detailkeyid", nativeQuery = true)
void deleteByDetailKeyid(@Param("detailkeyid") String detailkeyid);

@Modifying
@Query(value = "DELETE FROM gen_tl_actionplandtl WHERE apld_keyid = :apmdelete", nativeQuery = true)
void deleteByApldKeyid(@Param("apmdelete") String apmdelete);

@Modifying
@Query(value = "DELETE FROM gen_tl_actionplanmst WHERE aplm_detailrefid = :detailkeyid", nativeQuery = true)
void deleteByDetailRefid(@Param("detailkeyid") String detailkeyid);    

@Modifying
@Query(value = "UPDATE she_tl_plantsafetydtl " +
               "SET spsd_verified = :verifystatus, " +
               "spsd_verifieddate = :targetdate, " +
               "spsd_verifyremarks = :remarks, " +
               "spsd_verifiedby = :responsevalue " +
               "WHERE spsd_keyid = :detailkeyid", nativeQuery = true)
void updateVerification(@Param("detailkeyid") String detailkeyid,
                        @Param("responsevalue") String responsevalue,
                        @Param("targetdate") LocalDateTime targetdate,
                        @Param("verifystatus") String verifystatus,
                        @Param("remarks") String remarks);

                        // SheTlPlantSafetydtlRepository.java

@Modifying
@Query(value = "DELETE FROM gen_tl_actionplandtl WHERE apld_aplm_keyid = " +
               "(SELECT aplm_keyid FROM gen_tl_actionplanmst WHERE aplm_detailrefid = :detailkeyid)",
               nativeQuery = true)
void deleteActionPlanDtlByDetailRefid(@Param("detailkeyid") String detailkeyid);

@Modifying
@Query(value = "UPDATE she_tl_plantsafetydtl SET " +
               "spsd_completedby = :responsevalue, " +
               "spsd_completeddate = :targetdate, " +
               "spsd_status = :status, " +
               "spsd_completeremarks = :remarks, " +
               "spsd_correcaction = :correctiveaction " +
               "WHERE spsd_keyid = :detailkeyid", nativeQuery = true)
void updateActionClosure(@Param("detailkeyid") String detailkeyid,
                         @Param("responsevalue") String responsevalue,
                         @Param("targetdate") LocalDateTime targetdate,
                         @Param("status") String status,
                         @Param("remarks") String remarks,
                         @Param("correctiveaction") String correctiveaction);

@Modifying
@Query(value = "UPDATE she_tl_plantsafetydtl SET " +
               "spsd_status = :status, " +
               "spsd_reviseddate = :revisedate, " +
               "spsd_completeremarks = :remarks " +
               "WHERE spsd_keyid = :detailkeyid", nativeQuery = true)
void updateActionClosureStatus(@Param("detailkeyid") String detailkeyid,
                               @Param("status") String status,
                               @Param("revisedate") LocalDateTime revisedate,
                               @Param("remarks") String remarks);

@Modifying
@Query(value = "UPDATE abn_tl_abnormality SET " +
               "abnm_status = 'C', " +
               "abnm_completedby = :responsevalue, " +
               "abnm_wostarttime = :targetdate, " +
               "abnm_woendtime = :targetdate, " +
               "abnm_remarks = :remarks " +
               "WHERE abnm_keyid = :abnKeyid", nativeQuery = true)
void updateAbnormalityClosure(@Param("abnKeyid") String abnKeyid,
                              @Param("responsevalue") String responsevalue,
                              @Param("targetdate") LocalDateTime targetdate,
                              @Param("remarks") String remarks);
}