package com.akranta.perfex_sb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.akranta.perfex_sb.model.GenTlVisualcontrolchecklist;

import jakarta.transaction.Transactional;


public interface GenTlVisualcontrolchecklistRepository extends JpaRepository<GenTlVisualcontrolchecklist, String>  {

     
    @Query(value = "SELECT G.VCCL_KEYID, G.VCCL_FLID, G.VCCL_EMPLOYEEID, " +
               "to_char(G.VCCL_DATE,'dd-MON-yyyy') as VCCL_DATE, G.VCCL_TITLE, G.VCCL_APPROVEDBY, " +
               "G.VCCL_TEMPFIELD3, G.VCCL_TEMPFIELD4, G.VCCL_TEMPFIELD5, G.VCCL_TEMPFIELD6, " +
               "G.VCCL_ACTIVE, G.VCCL_CREATEDBY, G.VCCL_CREATEDON, G.VCCL_MODIFIEDON " +
               "FROM GEN_TL_VISUALCONTROLCHECKLIST G WHERE G.VCCL_KEYID = :keyid", 
       nativeQuery = true)
Object[] findByKeyidAsArray(@Param("keyid") String keyid);

//deleting master table
@Modifying
@Transactional
@Query(
    value = "DELETE FROM gen_tl_visualcontrolchecklist WHERE vccl_keyid = :keyId",
    nativeQuery = true
)
int deleteChecklistMaster(@Param("keyId") String keyId);
    
} 