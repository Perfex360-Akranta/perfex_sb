package com.akranta.perfex_sb.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.akranta.perfex_sb.model.BAL_PcsTlCycletimemst;

public interface BAL_PcsTlCycletimemstRepository
        extends JpaRepository<BAL_PcsTlCycletimemst, String> {

      @Query(value = """
            SELECT * FROM public.bal_pcs_tl_cycletimemst
            WHERE cytm_machineid = :machineid
              AND cytm_productid = :productid
              AND cytm_cavity    = :cavity
            """, nativeQuery = true)
    List<BAL_PcsTlCycletimemst> findByMachineProductCavity(
            @Param("machineid") String machineid,
            @Param("productid") String productid,
            @Param("cavity") Integer cavity);


@Query(value = """
        SELECT CASE
                 WHEN f.retval = 1  THEN '1'
                 WHEN f.retval = -1 THEN '-1'
                 ELSE COALESCE(
                        (SELECT m.cytm_keyid
                           FROM bal_pcs_tl_cycletimemst m
                          WHERE m.cytm_machineid = :machineid
                            AND m.cytm_productid = :productid
                            AND m.cytm_cavity    = CAST(:cavity AS numeric)
                          ORDER BY m.cytm_keyid
                          LIMIT 1),
                        CAST(f.retval AS varchar))
               END
        FROM bal_pcs_fn_checkcycletimeinpcs(
                 :machineid, :productid, CAST(:cavity AS numeric)) f
        """, nativeQuery = true)
String checkCycleTimeInPcs(
        @Param("machineid") String machineid,
        @Param("productid") String productid,
        @Param("cavity") Integer cavity);

  
    @Query(value = """
            SELECT * FROM (
                SELECT m.cytm_keyid                              AS keyid,
                       ''                                        AS blank,
                       p.prdm_code || '-' || p.prdm_name         AS product,
                       pm.prmm_name                              AS model,
                       m.cytm_cycletime                          AS cycletime,
                       m.cytm_manpower                           AS manpower,
                       TO_CHAR(m.cytm_fromdate, 'DD-Mon-YYYY')   AS effdate,
                       m.cytm_factoryid                          AS factoryid,
                       m.cytm_sectionid                          AS sectionid,
                       c.cell_keyid                              AS cellid,
                       m.cytm_productid                          AS productid,
                       m.cytm_machineid                          AS machineid
                FROM public.bal_pcs_tl_cycletimemst m,
                     public.gen_tl_cellmst c,
                     public.pcs_tl_productmst p,
                     public.pcs_tl_productmodelmst pm
                WHERE p.prdm_model = pm.prmm_keyid
                  AND m.cytm_productid = p.prdm_keyid
                  AND m.cytm_cellid = c.cell_keyid
                  AND m.cytm_active = 'Y'
                  AND p.prdm_active = 'Y'
                  AND (CAST(:cellid AS text)    IS NULL OR m.cytm_cellid    = :cellid)
                  AND (CAST(:machineid AS text) IS NULL OR m.cytm_machineid = :machineid)
                  AND (CAST(:productid AS text) IS NULL OR m.cytm_productid = :productid)
                UNION
                SELECT '', '', '', '', 0, 0,
                       TO_CHAR(e.pelc_effectivefrom, 'DD-Mon-YYYY'),
                       e.pelc_factoryid,
                       e.pelc_lineid,
                       c.cell_keyid,
                       CAST(:productid AS text),
                       e.pelc_machineid
                FROM public.gen_tl_cellmst c
                LEFT JOIN public.bal_pcs_tl_enablelosscapture e
                       ON e.pelc_cellid = c.cell_keyid AND e.pelc_active = 'Y'
                WHERE e.pelc_ispcsenabled = 'Y' AND e.pelc_type = 'M'
            ) t
            ORDER BY t.product, t.machineid
            """,
            countQuery = """
            SELECT count(*) FROM (
                SELECT m.cytm_keyid
                FROM public.bal_pcs_tl_cycletimemst m,
                     public.gen_tl_cellmst c,
                     public.pcs_tl_productmst p,
                     public.pcs_tl_productmodelmst pm
                WHERE p.prdm_model = pm.prmm_keyid
                  AND m.cytm_productid = p.prdm_keyid
                  AND m.cytm_cellid = c.cell_keyid
                  AND m.cytm_active = 'Y'
                  AND p.prdm_active = 'Y'
                  AND (CAST(:cellid AS text)    IS NULL OR m.cytm_cellid    = :cellid)
                  AND (CAST(:machineid AS text) IS NULL OR m.cytm_machineid = :machineid)
                  AND (CAST(:productid AS text) IS NULL OR m.cytm_productid = :productid)
                UNION
                SELECT e.pelc_machineid
                FROM public.gen_tl_cellmst c
                LEFT JOIN public.bal_pcs_tl_enablelosscapture e
                       ON e.pelc_cellid = c.cell_keyid AND e.pelc_active = 'Y'
                WHERE e.pelc_ispcsenabled = 'Y' AND e.pelc_type = 'M'
            ) t
            """,
            nativeQuery = true)
    Page<Map<String, Object>> findGridData(
            @Param("cellid") String cellid,
            @Param("machineid") String machineid,
            @Param("productid") String productid,
            Pageable pageable);
 

        
    
            
            @Modifying
            @Query(
        value = "DELETE FROM BAL_PCS_TL_CYCLETIMEMST WHERE CYTM_KEYID = :keyid",
        nativeQuery = true
    )
    int deleteByKeyid(@Param("keyid") String keyid);
}