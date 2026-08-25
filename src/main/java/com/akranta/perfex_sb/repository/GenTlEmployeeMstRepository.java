package com.akranta.perfex_sb.repository;

import com.akranta.perfex_sb.model.GenTlEmployeemst;
import org.springframework.data.jpa.repository.JpaRepository;


public interface GenTlEmployeeMstRepository  extends JpaRepository<GenTlEmployeemst, String> {
    GenTlEmployeemst findByKeyid(String keyid);
    GenTlEmployeemst findByCode(String code);
}

