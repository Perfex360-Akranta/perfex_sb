package com.akranta.perfex_sb.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.akranta.perfex_sb.model.BAL_PcsTlPcsBd;

public interface BAL_PcsTlPcsBdRepository extends JpaRepository<BAL_PcsTlPcsBd, String> {

        Optional<BAL_PcsTlPcsBd> findFirstByBdmKeyid(String bdmsKeyid);
    
}
