package com.akranta.perfex_sb.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.akranta.perfex_sb.model.PlmTlGenmaintenance;

@Repository
public interface PlmTlGenmaintenanceRepository extends JpaRepository<PlmTlGenmaintenance, String> {

    // FIX: Added schema prefix "public." to be safe in PostgreSQL environments
    // where search_path may not include public by default
    @Query(value = "SELECT * FROM public.plm_tl_genmaintenance WHERE gmnt_keyid = :keyid",
           nativeQuery = true)
    PlmTlGenmaintenance findByKeyid(@Param("keyid") String keyid);

    // FIX: Added schema prefix
    @Query(value = "SELECT * FROM public.plm_tl_genmaintenance WHERE gmnt_active = 'Y' ORDER BY gmnt_createdon DESC",
           nativeQuery = true)
    List<PlmTlGenmaintenance> findAllActive();

    // FIX: Added soft-delete query — sets active = 'N' instead of hard delete
    // Called from deleteById override in service
    @Modifying
    @Query(value = "UPDATE public.plm_tl_genmaintenance SET gmnt_active = 'N', gmnt_modifiedon = NOW() WHERE gmnt_keyid = :keyid",
           nativeQuery = true)
    int softDeleteByKeyid(@Param("keyid") String keyid);

}