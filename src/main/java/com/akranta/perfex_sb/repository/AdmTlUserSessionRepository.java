package com.akranta.perfex_sb.repository;

import com.akranta.perfex_sb.model.AdmTlUsersessions;

import java.time.LocalDate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface AdmTlUserSessionRepository  extends JpaRepository<AdmTlUsersessions, AdmTlUsersessions.UserSessionId> {
    // AdmTlUsersessions findByKeyid(String keyid);
    // AdmTlUsersessions findByCode(String code);

    @Query(value = """
        SELECT COALESCE(MAX(USSE_SESSIONNO), 0)
        FROM ADM_TL_USERSESSIONS 
        WHERE USSE_USERID = :userId 
          AND DATE_TRUNC('day', USSE_SESSIONDATE) = :sessionDate
        """, nativeQuery = true)
    int findMaxSessionNo(@Param("userId") String userId,@Param("sessionDate") LocalDate sessionDate);
}


