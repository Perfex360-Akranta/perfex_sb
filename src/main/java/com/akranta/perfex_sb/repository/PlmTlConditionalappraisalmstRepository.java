package com.akranta.perfex_sb.repository;

import com.akranta.perfex_sb.model.PlmTlConditionalappraisalmst;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PlmTlConditionalappraisalmstRepository extends JpaRepository<PlmTlConditionalappraisalmst, String> {
    
    // FIXED: Query by primary key 'cdam_keyid', not foreign key
    @Query(value = "SELECT * FROM plm_tl_conditionalappraisalmst WHERE cdam_keyid = :keyid", 
           nativeQuery = true)
    PlmTlConditionalappraisalmst findByKeyid(@Param("keyid") String keyid);

     @Modifying
    @Query(value = "DELETE FROM plm_tl_conditionalappraisalmst WHERE cdam_keyid = :keyid", 
           nativeQuery = true)
    int deleteByKeyid(@Param("keyid") String keyid);
}