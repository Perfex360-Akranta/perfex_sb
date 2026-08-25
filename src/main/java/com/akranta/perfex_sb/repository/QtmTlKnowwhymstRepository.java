package com.akranta.perfex_sb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.akranta.perfex_sb.model.QtmTlKnowwhymst;

public interface QtmTlKnowwhymstRepository extends JpaRepository<QtmTlKnowwhymst, String> {

    @Modifying
    @Transactional
    @Query(value = """
                INSERT INTO QTM_TL_KNOWWHY_APPROVALHIST
                SELECT *
                FROM GEN_TL_WORKFLOW_INFO
                WHERE WRIN_REF_ID = :refId
            """, nativeQuery = true)
    int insertApprovalHistory(@Param("refId") String refId);

}
