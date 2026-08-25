package com.akranta.perfex_sb.repository;

import com.akranta.perfex_sb.model.BdmTlYyproblemattbymst;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;

public interface BdmTlYyproblemattbymstRepository extends JpaRepository<BdmTlYyproblemattbymst, String> {
    BdmTlYyproblemattbymst findByKeyid(String keyid);
    
    @Query("SELECT p FROM BdmTlYyproblemattbymst p WHERE p.wwms_keyid = :wwmsKeyid")
    List<BdmTlYyproblemattbymst> findByWwmsKeyid(@Param("wwmsKeyid") String wwmsKeyid);
    
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
        pa.WWPA_KEYID as keyid,
        pa.WWPA_WWMS_KEYID as wwmsKeyid,
        COALESCE(emp.EMPM_NAME, '') || ' - ' || COALESCE(emp.EMPM_CODE, '') AS employee,
        '' as btn_delete
    FROM BDM_TL_YYPROBLEMATTBYMST pa
    JOIN GEN_TL_EMPLOYEEMST emp ON pa.WWPA_EMPM_KEYID = emp.EMPM_KEYID
    WHERE pa.WWPA_WWMS_KEYID = :masterKeyid
    """, nativeQuery = true)
List<Map<String, Object>> getProbAttbyByMasterKeyid(@Param("masterKeyid") String masterKeyid);
@Modifying
    @Query(value = "DELETE FROM BDM_TL_YYPROBLEMATTBYMST WHERE WWPA_KEYID = :keyId", nativeQuery = true)
    int deleteProblemAttBy(@Param("keyId") String keyId);
}