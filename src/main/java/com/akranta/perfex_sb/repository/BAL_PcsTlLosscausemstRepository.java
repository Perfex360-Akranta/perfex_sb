package com.akranta.perfex_sb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.akranta.perfex_sb.model.BAL_PcsTlLosscausemst;

@Repository
public interface BAL_PcsTlLosscausemstRepository
        extends JpaRepository<BAL_PcsTlLosscausemst, String> {

}