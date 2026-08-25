package com.akranta.perfex_sb.repository;

import com.akranta.perfex_sb.model.AdmTlConfigurationmst;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdmTlConfigurationmstRepository extends JpaRepository<AdmTlConfigurationmst, String> {
   AdmTlConfigurationmst findByKeyid(String keyid);
    AdmTlConfigurationmst findByCode(String code);
}