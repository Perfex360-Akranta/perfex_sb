package com.akranta.perfex_sb.repository;

import com.akranta.perfex_sb.model.AbnTlAbnormality;
import com.akranta.perfex_sb.model.BAL_GenTlAssemblymst;

import jakarta.transaction.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BAL_GenTlAssemblymstRepository extends JpaRepository<BAL_GenTlAssemblymst, String>{
    
    @Modifying
    @Query(
    value = """
        UPDATE BAL_GEN_TL_ASSEMBLYMST
        SET ASSM_ACTIVE = 'N'
        WHERE ASSM_KEYID = :keyid
        """,
    nativeQuery = true
)
void inactivateByKeyid(@Param("keyid") String keyid);
}
