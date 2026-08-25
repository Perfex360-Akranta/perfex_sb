package com.akranta.perfex_sb.repository;

import com.akranta.perfex_sb.model.StdTlStdworksheetdtl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface StdTlStdworksheetdtlRepository extends JpaRepository<StdTlStdworksheetdtl, String> {
    
    // Using @Query with JPQL
    @Query("SELECT d FROM StdTlStdworksheetdtl d WHERE d.stws_keyid = :stwsKeyid")
    List<StdTlStdworksheetdtl> findByStwsKeyid(@Param("stwsKeyid") String stwsKeyid);
    
    // Alternative using native query
    // @Query(value = "SELECT * FROM std_tl_stdworksheetdtl WHERE stwd_stws_keyid = :stwsKeyid", nativeQuery = true)
    // List<StdTlStdworksheetdtl> findByStwsKeyid(@Param("stwsKeyid") String stwsKeyid);
}