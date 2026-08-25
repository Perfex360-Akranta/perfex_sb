package com.akranta.perfex_sb.repository;

import com.akranta.perfex_sb.model.BdmTlWwblamst;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WwblaMstRepository extends JpaRepository<BdmTlWwblamst, String> {
    
    
}