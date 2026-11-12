package com.akranta.perfex_sb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import jakarta.transaction.Transactional;
import com.akranta.perfex_sb.model.QtmTlCapaActionmst;

@Repository
public interface QtmTlCapaActionmstRepository extends JpaRepository<QtmTlCapaActionmst, String> {

    @Query(value = "SELECT FNLN_KEYID FROM GEN_TL_FUNCTIONALLOCN WHERE FNLN_ORIGINALID = :targetJH", nativeQuery = true)
    String getFnlnKeyIdByOriginalId(@Param("targetJH") String targetJH);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM QTM_TL_CAPACTIONMST WHERE QCPA_KEYID = :qcpaKeyid", nativeQuery = true)
    void deleteByQcpaKeyid(@Param("qcpaKeyid") String qcpaKeyid);

}