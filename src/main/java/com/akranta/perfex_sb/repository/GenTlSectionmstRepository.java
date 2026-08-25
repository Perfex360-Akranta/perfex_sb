package com.akranta.perfex_sb.repository;

import com.akranta.perfex_sb.model.GenTlSectionmst;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GenTlSectionmstRepository extends JpaRepository<GenTlSectionmst, String> {
    GenTlSectionmst findByKeyid(String keyid);
    GenTlSectionmst findByCode(String code);
}