package com.akranta.perfex_sb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Repository;

import com.akranta.perfex_sb.model.BAL_BdmTlMultipleResp;

@Repository
public interface BAL_BdmTlMultipleRespRepository extends JpaRepository<BAL_BdmTlMultipleResp, String> {

    @Transactional
    //@Modifying
    //void deleteByRefid(String refid);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("DELETE FROM BAL_BdmTlMultipleResp b WHERE b.refid = :refid")
    int deleteByRefid(@Param("refid") String refid);

    boolean existsByRefid(String refid);
}

    