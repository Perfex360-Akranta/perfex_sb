
// ─── BAL_BdmTlMstRepository.java ───────────────────────────────────────────
package com.akranta.perfex_sb.repository;

import com.akranta.perfex_sb.model.BAL_BdmTlMst;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BAL_BdmTlMstRepository extends JpaRepository<BAL_BdmTlMst, String> {

    BAL_BdmTlMst findByKeyid(String keyid);

    // Native query: get ISYYMANDATRY config value
    @Query(value = """
        SELECT cnfm_settingvalue
        FROM adm_tl_configurationmst
        WHERE cnfm_code = :code
        LIMIT 1
        """, nativeQuery = true)
    String getConfigValue(@Param("code") String code);

    @Query(value = """
        SELECT * FROM BAL_BDM_tl_mst
        WHERE bdms_keyid = :keyid
        """, nativeQuery = true)
    BAL_BdmTlMst findByKeyidNative(@Param("keyid") String keyid);
}



// ─── BAL_BdmTlDtlRepository.java ───────────────────────────────────────────



