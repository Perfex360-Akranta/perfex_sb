package com.akranta.perfex_sb.repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.akranta.perfex_sb.model.SheTlPlnjobemplink;

import jakarta.transaction.Transactional;

@Repository
public interface SheTlPlnjobemplinkRepository extends JpaRepository<SheTlPlnjobemplink, String> {

    @Query(value = "SELECT * FROM she_tl_plnjobemplink WHERE pjel_pjob_keyid = :pjobKeyid AND pjel_pjob_employeeid = :employeeId",
           nativeQuery = true)
    Optional<SheTlPlnjobemplink> findByPjobKeyidAndEmployeeId(
            @Param("pjobKeyid") String pjobKeyid,
            @Param("employeeId") String employeeId);

           @Query(value = """
    SELECT 
        '1' AS sno,
        '2' AS pjob_employeeid,
        '3' AS pjel_keyid,
        '4' AS employee,
        '5' AS delete_btn
    UNION ALL
    SELECT 
        '' AS sno,
        pjel.PJEL_PJOB_EMPLOYEEID AS pjob_employeeid,
        pjel.PJEL_KEYID AS pjel_keyid,
        COALESCE(emp.EMPM_NAME, '') || ' - ' || COALESCE(emp.EMPM_CODE, '') AS employee,
        '' AS delete_btn
    FROM SHE_TL_PLANNEDJOBOBSERVATION pjob
    JOIN SHE_TL_PLNJOBEMPLINK pjel ON pjob.PJOB_KEYID = pjel.PJEL_PJOB_KEYID
    JOIN GEN_TL_EMPLOYEEMST emp ON pjel.PJEL_PJOB_EMPLOYEEID = emp.EMPM_KEYID
    WHERE pjob.PJOB_KEYID = :pjobKeyId
    """, nativeQuery = true)
List<Map<String, Object>> getPojEmployee(@Param("pjobKeyId") String pjobKeyId);

// ✅ Detail uses pjob_keyid (foreign key) — correct
// @Modifying
// @Query(value = "DELETE FROM she_tl_plnjobemplink WHERE pjob_keyid = :keyid", 
//        nativeQuery = true)
// int deleteByPjobKeyid(@Param("keyid") String keyid);

//✅ Add this if not already present
     //List<SheTlPlnjobemplink> findByPjobKeyid(String pjobKeyid);
    
    // ✅ Keep this too@Modifying
@Modifying
@Transactional
@Query(value = "DELETE FROM SHE_TL_PLNJOBEMPLINK WHERE PJEL_PJOB_KEYID = :pjobKeyid", 
       nativeQuery = true)
int deleteByPjobKeyid(@Param("pjobKeyid") String pjobKeyid);


 // ✅ NEW — matches: delete from SHE_TL_PLNJOBEMPLINK where pjel_pjob_employeeid = ?
    @Modifying
    @Transactional
    @Query(value = "DELETE FROM SHE_TL_PLNJOBEMPLINK WHERE PJEL_PJOB_EMPLOYEEID = :pjelPjobEmployeeid",
           nativeQuery = true)
    int deleteByPjelPjobEmployeeid(@Param("pjelPjobEmployeeid") String pjelPjobEmployeeid);



           }
    // ✅ Add this to SheTlPlnjobemplinkRepository.java










// public interface SheTlPlnjobemplinkRepository 
//         extends JpaRepository<SheTlPlnjobemplink, String> {

//     // ✅ Add this if not already present
//     List<SheTlPlnjobemplink> findByPjobKeyid(String pjobKeyid);
    
//     // ✅ Keep this too
//     @Modifying
//     @Query("DELETE FROM SheTlPlnjobemplink d WHERE d.pjob_keyid = :pjobKeyid")
//     int deleteByPjobKeyid(@Param("pjobKeyid") String pjobKeyid);
// }