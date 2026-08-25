package com.akranta.perfex_sb.repository;

import com.akranta.perfex_sb.model.EntTlTrgCalEmp;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EntTlTrgCalEmpRepository extends JpaRepository<EntTlTrgCalEmp, String>, EntTlTrgCalEmpRepositoryCustom {
}
