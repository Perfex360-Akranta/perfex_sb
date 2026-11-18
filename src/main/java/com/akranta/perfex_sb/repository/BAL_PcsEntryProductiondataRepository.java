package com.akranta.perfex_sb.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.akranta.perfex_sb.model.BAL_PcsEntryProductiondata;

public interface BAL_PcsEntryProductiondataRepository extends JpaRepository<BAL_PcsEntryProductiondata,String>{

    // Native SQL (uses table and column names)
@Query(value = "SELECT * FROM bal_pcs_tl_productiondata WHERE pprd_tempfield1 = :dtlId ORDER BY pprd_keyid LIMIT 1",
       nativeQuery = true)
Optional<BAL_PcsEntryProductiondata> findByDetailIdNative(@Param("dtlId") String dtlId);

}
