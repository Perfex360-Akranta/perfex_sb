package com.akranta.perfex_sb.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.akranta.perfex_sb.model.KznTlProjectKaizenLink;

public interface KznTlProjectKaizenLinkRepository extends JpaRepository<KznTlProjectKaizenLink, String>  {
    
    


    @Query(value = """
       SELECT '0' as keyid,'1' as date,'2' as doneby,'3' as link  	 	
       union all
       SELECT 'Kaizen No.','Date','Done By','Link'  UNION ALL
       select KZNM_KEYID,to_char(KZNM_DATE,'dd-Mon-YYYY'),EMPM_NAME,'' FROM KZN_TL_MST JOIN gen_tl_employeemst ON empm_keyid = kznm_createdby  
       JOIN KZN_TL_PROJECT_KAIZEN_LINK ON kznm_keyid = kplk_kznm_keyid WHERE    kznm_flid = :flid and kplk_kzpm_keyid =:masterId
    """, nativeQuery = true)
    List<Map<String, Object>> getProjectKaizen(@Param("flid") String flid,@Param("masterId") String masterId);

     @Modifying
    @Query(value = " DELETE from KZN_TL_PROJECT_KAIZEN_LINK where KPLK_KEYID  = :keyid ", nativeQuery = true)
    int DeleteProjectKaizen(@Param("keyid") String keyid);
}
