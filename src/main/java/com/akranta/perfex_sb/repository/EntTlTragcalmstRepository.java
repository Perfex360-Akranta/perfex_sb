package com.akranta.perfex_sb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.akranta.perfex_sb.model.EntTlTragcalmst;

public interface EntTlTragcalmstRepository extends JpaRepository<EntTlTragcalmst, String>, EntTlTragcalmstRepositoryCustom {

    /**
     * Delete grid-based training calendar and all its dependent rows in one native call.
     */
    @Modifying
    @Transactional
    @Query(value = """
        WITH del_att AS (
            DELETE FROM ent_tl_trgcalempatscore WHERE etca_etcm_keyid = :keyid
        ), del_quad AS (
            DELETE FROM ent_tl_trgcalquad WHERE etcq_l1_trgcalid = :keyid
        ), del_emp AS (
            DELETE FROM ent_tl_trgcalemp WHERE etce_etcm_keyid = :keyid
        ), del_session AS (
            DELETE FROM ent_tl_trgcalsession WHERE etcs_etcm_keyid = :keyid
        ), del_unqp AS (
            DELETE FROM ent_tl_trgcalunqp WHERE etcu_etcm_keyid = :keyid
        ), del_fac AS (
            DELETE FROM ent_tl_trgfaculty WHERE etcf_etcm_keyid = :keyid
        )
        DELETE FROM ent_tl_trgcalmst WHERE etcm_keyid = :keyid
        """, nativeQuery = true)
    int deleteCascadeByEtcmKeyid(@Param("keyid") String etcmKeyid);
}
