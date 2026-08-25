package com.akranta.perfex_sb.repository;

import com.akranta.perfex_sb.model.PlmTlConditionalappraisalmstentry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface PlmTlConditionalappraisalmstentryRepository extends JpaRepository<PlmTlConditionalappraisalmstentry, String> {
    
    
    @Query(value = """
            SELECT COUNT(me.cdam_keyid) 
            FROM plm_tl_conappraisalmstentry me
            INNER JOIN plm_tl_conappraisalentry de ON de.cdap_cdam_keyid = me.cdam_keyid
            WHERE me.cdam_flid = :flid 
            AND me.cdam_date = :date 
            AND de.cdap_cdapkeyid = :cdapkeyid
            """, nativeQuery = true)
    Long checkUpdateWithJoin(@Param("flid") String flid, 
                             @Param("date") LocalDateTime date, 
                             @Param("cdapkeyid") String cdapkeyid);
    
    
    @Query(value = """
            SELECT COUNT(cdam_keyid) 
            FROM plm_tl_conappraisalmstentry 
            WHERE cdam_flid = :flid 
            AND cdam_date = :date
            """, nativeQuery = true)
    Long checkUpdateWithoutJoin(@Param("flid") String flid, 
                                 @Param("date") LocalDateTime date);
}