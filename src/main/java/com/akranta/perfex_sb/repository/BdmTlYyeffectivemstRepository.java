package com.akranta.perfex_sb.repository;

import com.akranta.perfex_sb.model.BdmTlYyeffectivemst;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface BdmTlYyeffectivemstRepository extends JpaRepository<BdmTlYyeffectivemst, String> {
    
    @Query("SELECT e FROM BdmTlYyeffectivemst e WHERE e.wwms_keyid = :wwms_keyid")
    List<BdmTlYyeffectivemst> findByWwms_keyid(@Param("wwmsKeyid") String wwms_keyid);
}