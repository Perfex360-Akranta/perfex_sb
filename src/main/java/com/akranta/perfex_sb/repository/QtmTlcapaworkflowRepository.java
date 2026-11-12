package com.akranta.perfex_sb.repository;

import java.time.LocalDateTime;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.akranta.perfex_sb.model.QtmTlCapaWorkflow;

import jakarta.transaction.Transactional;

@Repository
public interface QtmTlcapaworkflowRepository extends JpaRepository<QtmTlCapaWorkflow, String> {
        @Modifying
        @Transactional
        @Query(value = "UPDATE QTM_TL_CAPAWORKFLOW " +
                        "SET QCWF_APPROVEDBY = :empId, " +
                        "QCWF_REMARKS = :remarks, " +
                        "QCWF_STATUS = :status, " +

                        "QCWF_DATE = CAST(:date AS TIMESTAMP)" +
                        "WHERE QCWF_CAPAREFID = :capaId", nativeQuery = true)

        int updateCapaWorkflow(@Param("empId") String empId,
                        @Param("remarks") String remarks,
                        @Param("status") String status,
                        @Param("date") String date,
                        @Param("capaId") String capaId);

}
