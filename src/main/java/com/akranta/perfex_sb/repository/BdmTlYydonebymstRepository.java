package com.akranta.perfex_sb.repository;

import com.akranta.perfex_sb.model.BdmTlYydonebymst;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;

public interface BdmTlYydonebymstRepository extends JpaRepository<BdmTlYydonebymst, String> {
   
    @Query("SELECT b FROM BdmTlYydonebymst b WHERE b.wwms_keyid = :wwmsKeyid")
    List<BdmTlYydonebymst> findByWwmsKeyid(@Param("wwmsKeyid") String wwmsKeyid);
    
    @Query(value = """
        SELECT 
            '1' as sno,
            '2' as keyid,
            '3' as wwmsKeyid,
            '4' as Employee,
            '5' as Delete
        UNION ALL
            SELECT 
                '' as sno,
                db.WWDB_KEYID as keyid,
                db.WWDB_WWMS_KEYID as wwmsKeyid,
                COALESCE(emp.EMPM_NAME, '') || ' - ' || COALESCE(emp.EMPM_CODE, '') AS employee,
                '' as btn_delete
        FROM BDM_TL_YYDONEBYMST db
        JOIN GEN_TL_EMPLOYEEMST emp ON db.WWDB_EMPM_KEYID = emp.EMPM_KEYID
       WHERE (
         db.WWDB_WWMS_KEYID = :masterKeyid
    )
        """, nativeQuery = true)
    List<Map<String, Object>> getYyDonebyByMasterKeyid(@Param("masterKeyid") String masterKeyid);
    @Modifying
@Query(value = "DELETE FROM BDM_TL_YYDONEBYMST WHERE WWDB_KEYID = :keyId", nativeQuery = true)
int deleteYyDoneby(@Param("keyId") String keyId);
}