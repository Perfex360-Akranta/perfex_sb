package com.akranta.perfex_sb.repository;



import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.akranta.perfex_sb.model.KznTlKaizenBankMst;

import jakarta.transaction.Transactional;

public interface KaizenBankMstRepository extends JpaRepository<KznTlKaizenBankMst, String>{


    @Query(
    value = """
            SELECT 
                kzbn_kaizen AS kzbnKaizen
            FROM kzn_tl_kaizenbankmst
            WHERE kzbn_keyid = :keyId
            """,
    nativeQuery = true
)
  String selectKznData(@Param("keyId") String keyId);

  

    @Modifying
    @Transactional
    @Query(
        value = "UPDATE kzn_Tl_Kaizenbankmst SET " +
                "Kzbn_Status = :status, " +
                "KZBN_KAIZEN = :kaizen, " +
                "Kzbn_Acrejby = :acrejby, " +
                "Kzbn_Implementcost = :implementCost, " +
                "Kzbn_Targetdate = :targetDate, " +
                "KZBN_MOCREQUIRED = :mocRequired, " +
                "KZBN_RESPONSIBILITY = :responsibility, " +
                "Kzbn_Verifyremarks = :verifyRemarks , " +
                "Kzbn_mocitem = :mocitem "+
                "WHERE kzbn_keyid = :keyId",
        nativeQuery = true
    )
    int updateKaizenNative(
            @Param("status") String status,
            @Param("kaizen") String kaizen,
            @Param("acrejby") String acrejby,
            @Param("implementCost") BigDecimal implementCost,
            @Param("targetDate") LocalDateTime targetDate,
            @Param("mocRequired") String mocRequired,
            @Param("responsibility") String responsibility,
            @Param("verifyRemarks") String verifyRemarks,
            @Param("mocitem") String mocitem,
            @Param("keyId") String keyId
    );

    @Modifying
    @Transactional
    @Query(
        value = "DELETE FROM ADM_APPROVALS_LIST WHERE DOCUMENTNO = :keyId",
        nativeQuery = true
    )
    int deleteApprovalByDocNo(@Param("keyId") String keyId);

    @Query(value = "SELECT KZCT_KEYID AS keyid, KZCT_CODE AS code, KZCT_NAME AS name " +
                   "FROM KZN_TL_CATEGORYTHMMST WHERE KZCT_KEYID = :keyid", nativeQuery = true)
    List<Map<String, Object>> findCategoryRecall(@Param("keyid") String keyid);
}




    // @Query(
    //     value = "SELECT * FROM TBL_KZN_TL_KAIZENBANKMST WHERE KZBN_KEYID = :keyId",
    //     nativeQuery = true
    // )
    // KznTlKaizenBankMst findByKeyId(@Param("keyId") String keyId);



