package com.akranta.perfex_sb.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import com.akranta.perfex_sb.model.KpiTlKpiRemarks;

public interface KpiTlKpiRemarksRepository extends JpaRepository<KpiTlKpiRemarks,String> {



    @Query(value = """
            select * from kpi_tl_kpiremarks where kprm_keyid =:keyid
            """, nativeQuery = true)
    KpiTlKpiRemarks getbykprmkeyid(@Param("keyid") String keyid);



}