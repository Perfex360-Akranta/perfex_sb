package com.akranta.perfex_sb.repository;

import com.akranta.perfex_sb.model.BdmTlYyeffectivedtl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BdmTlYyeffectivedtlRepository extends JpaRepository<BdmTlYyeffectivedtl, String> {
    // This method finds ALL detail records that belong to a specific master record
    //List<BdmTlYyeffectivedtl> findByYyef_keyid(String yyefKeyid);
    

    @Query("SELECT y FROM BdmTlYyeffectivedtl y WHERE y.yyef_keyid = :yyefKeyid")
    List<BdmTlYyeffectivedtl> findByYyef_keyid(@Param("yyefKeyid") String yyef_keyid);
}
