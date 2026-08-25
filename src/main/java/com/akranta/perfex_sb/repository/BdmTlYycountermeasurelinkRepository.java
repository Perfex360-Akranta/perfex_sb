package com.akranta.perfex_sb.repository;

import com.akranta.perfex_sb.model.BdmTlYycountermeasurelink;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BdmTlYycountermeasurelinkRepository extends JpaRepository<BdmTlYycountermeasurelink, String> {

    List<BdmTlYycountermeasurelink> findByYycmCountermsridAndYycmRefdoctype(String countermsrid, String refdoctype);

    void deleteByYycmCountermsridAndYycmRefdoctype(String countermsrid, String refdoctype);
}
