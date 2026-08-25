package com.akranta.perfex_sb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.akranta.perfex_sb.model.KznTlProjectResourceLink;

public interface KznTlProjectResourceLinkRepository extends JpaRepository<KznTlProjectResourceLink, String> {
    
    @Modifying
    @Query(value = " DELETE from KZN_TL_PROJECT_RESOURCE_LINK where KPRL_KEYID  = :keyid ", nativeQuery = true)
    int DeleteProjectResource(@Param("keyid") String keyid);
}
