package com.akranta.perfex_sb.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.akranta.perfex_sb.model.KznTlProjectKpiLink;


public interface KznTlProjectKpiLinkRepository extends JpaRepository<KznTlProjectKpiLink, String> {
    
    @Query(value = """
       SELECT '0' as keyid,'1' as indicatorname,'2' as BASEVAL,'3' as TARGETVAL  	 	
       union all
       SELECT '1' as keyid,'Key Performance Indicator','Base Value','Target Value' 		
       union all select kpkl_keyid as keyid,kink_indicatorname,KPKL_BASEVAL :: TEXT,KPKL_TARGETVAL :: TEXT from kpi_tl_indicator JOIN gen_tl_tpmpillarmst ON KINK_PILLARID = TPMP_KEYID LEFT JOIN kzn_tl_project_kpi_link ON  kpkl_kink_keyid = kink_keyid WHERE  KINK_ISCHILD = 'Y' and kpkl_kzpm_keyid= :keyid order by keyid
        """, nativeQuery = true)
    List<Map<String, Object>> getProjectKpi(@Param("keyid") String keyid);

   
    @Modifying
    @Query(value = " DELETE from KZN_TL_PROJECT_KPI_LINK where kpkl_keyid  = :keyid ", nativeQuery = true)
    int DeleteProjectKpi(@Param("keyid") String keyid);

    // @Modifying
    // @Query(value = " DELETE from KZN_TL_PROJECT_KPI_LINK where kpkl_keyid  = :keyid ", nativeQuery = true)
    // int UpdateProjectKpi(@Param("keyid") String keyid);

}
