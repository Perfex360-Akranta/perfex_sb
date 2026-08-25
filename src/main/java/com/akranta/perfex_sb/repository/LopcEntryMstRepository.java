package com.akranta.perfex_sb.repository;

import com.akranta.perfex_sb.model.LopcEntryMst;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface LopcEntryMstRepository extends JpaRepository<LopcEntryMst, String> {

@Query(value = "SELECT * FROM GEN_TL_LOPCENTRYMST WHERE LOEM_KEYID = :keyid", 
           nativeQuery = true)
    LopcEntryMst findByKeyid(@Param("keyid") String keyid);

    @Query(value = "SELECT WWBD_WWBL_KEYID as wwbl_keyid FROM BDM_TL_WWBLADTL WHERE WWBD_KEYID = :keyid", 
           nativeQuery = true)
    List<Map<String, Object>> getWwblKeyidByDetailKeyid(@Param("keyid") String keyid);

    /**
     * Update all detail records with the same foreign key
     */
    @Modifying
    @Query(value = """
            UPDATE BDM_TL_WWBLADTL 
            SET WWBD_ACTIONTAKEN = :correctiveAction,
                WWBD_COMPLETEDBY = :completedBy,
                WWBD_COMPLETEDDATE = TO_TIMESTAMP(:completedDate, 'DD-MON-YYYY HH24:MI:SS'),
                WWBD_STATUS = :status,
                WWBD_REMARKS = :remarks,
                WWBD_MODIFIEDON = CURRENT_TIMESTAMP
            WHERE WWBD_WWBL_KEYID = :wwblKeyid
            """, nativeQuery = true)
    int updateLopcActionClosure(
            @Param("correctiveAction") String correctiveAction,
            @Param("completedBy") String completedBy,
            @Param("completedDate") String completedDate,
            @Param("status") String status,
            @Param("remarks") String remarks,
            @Param("wwblKeyid") String wwblKeyid
    );

    /**
     * Count pending (non-completed) records for a given master key
     * Returns List<Map<String, Object>> with single row containing count
     */
    @Query(value = """
            SELECT COUNT(*) as pending_count
            FROM BDM_TL_WWBLADTL 
            WHERE WWBD_WWBL_KEYID = :wwblKeyid 
                AND WWBD_STATUS != 'C' 
                AND WWBD_ACTIVE = 'Y'
            """, nativeQuery = true)
    List<Map<String, Object>> countPendingRecords(@Param("wwblKeyid") String wwblKeyid);

    /**
     * Update master investigation status to completed
     */
    @Modifying
    @Query(value = """
            UPDATE BDM_TL_WWBLAMST 
            SET WWBL_INVESTIGATION = 'C',
                WWBL_MODIFIEDON = CURRENT_TIMESTAMP
            WHERE WWBL_KEYID = :wwblKeyid
            """, nativeQuery = true)
    int updateMasterInvestigationStatus(@Param("wwblKeyid") String wwblKeyid);

    /**
     * Get all detail records for a given master key
     */
    @Query(value = """
            SELECT 
                WWBD_KEYID as keyid,
                WWBD_WWBL_KEYID as wwbl_keyid,
                COALESCE(WWBD_PHENOMENA_FACTOR, '') as phenomena_factor,
                COALESCE(WWBD_VERIFICATION, '') as verification,
                COALESCE(WWBD_PARENTID, '') as parentid,
                COALESCE(CAST(WWBD_ORDERNO AS VARCHAR), '') as orderno,
                COALESCE(CAST(WWBD_LEVELNO AS VARCHAR), '') as levelno,
                COALESCE(WWBD_ISLASTFACTOR, '') as islastfactor,
                COALESCE(WWBD_COUNTERMEASURE, '') as countermeasure,
                COALESCE(WWBD_SKILLTYPE, '') as skilltype,
                COALESCE(WWBD_RESPONSIBILITY, '') as responsibility,
                CASE 
                    WHEN TO_CHAR(WWBD_TARGETDATE, 'DD-Mon-YYYY') = '01-Jan-1801' 
                    THEN '' 
                    ELSE TO_CHAR(WWBD_TARGETDATE, 'DD-Mon-YYYY') 
                END as targetdate,
                COALESCE(WWBD_STATUS, '') as status,
                COALESCE(WWBD_ACTIONTAKEN, '') as actiontaken,
                COALESCE(WWBD_COMPLETEDBY, '') as completedby,
                CASE 
                    WHEN TO_CHAR(WWBD_COMPLETEDDATE, 'DD-Mon-YYYY') = '01-Jan-1801' 
                    THEN '' 
                    ELSE TO_CHAR(WWBD_COMPLETEDDATE, 'DD-Mon-YYYY HH24:MI:SS') 
                END as completeddate,
                COALESCE(WWBD_REMARKS, '') as remarks,
                COALESCE(WWBD_REOCCUR, '') as reoccur
            FROM BDM_TL_WWBLADTL
            WHERE WWBD_WWBL_KEYID = :wwblKeyid
                AND WWBD_ACTIVE = 'Y'
            ORDER BY WWBD_ORDERNO, WWBD_LEVELNO
            """, nativeQuery = true)
    List<Map<String, Object>> getDetailRecordsByMasterKeyid(@Param("wwblKeyid") String wwblKeyid);

}