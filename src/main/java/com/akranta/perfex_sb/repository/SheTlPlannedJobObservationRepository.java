package com.akranta.perfex_sb.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.akranta.perfex_sb.model.SheTlPlannedJobObservation;

import jakarta.transaction.Transactional;

@Repository
public interface SheTlPlannedJobObservationRepository extends JpaRepository<SheTlPlannedJobObservation, String> {

    @Query(value = "SELECT * FROM she_tl_plannedjobobservation WHERE PJOB_KEYID = :keyid",
           nativeQuery = true)
    SheTlPlannedJobObservation findByKeyid(@Param("keyid") String keyid);

   @Query(value = """
    SELECT 
        '' AS col1,
        pjel.PJEL_PJOB_EMPLOYEEID,
        pjel.PJEL_KEYID,
        emp.EMPM_NAME || ' - ' || emp.EMPM_CODE AS employee,
        '' AS delete_col
    FROM SHE_TL_PLANNEDJOBOBSERVATION pjob
    INNER JOIN SHE_TL_PLNJOBEMPLINK pjel ON pjob.PJOB_KEYID = pjel.PJEL_PJOB_KEYID
    INNER JOIN GEN_TL_EMPLOYEEMST emp ON pjel.PJEL_PJOB_EMPLOYEEID = emp.EMPM_KEYID
    WHERE pjob.PJOB_KEYID = :pjobKeyId
    """, nativeQuery = true)
List<Map<String, Object>> getPojEmployee(@Param("pjobKeyId") String pjobKeyId);

/* delete */
@Modifying
@Transactional
@Query(value = "DELETE FROM SHE_TL_PLANNEDJOBOBSERVATION WHERE PJOB_KEYID = :keyid", 
       nativeQuery = true)
int deleteByPjobKeyid(@Param("keyid") String keyid);


/* Delete */
  
//  Delete parent record
// Step 2: Delete parent record
// ✅ Master uses keyid (primary key) — NOT pjob_keyid


// @Modifying
// @Query(value = "DELETE FROM she_tl_plannedjobobservation WHERE pjob_keyid = :keyid", 
//        nativeQuery = true)
// int deleteByPjobKeyid(@Param("keyid") String keyid);



}