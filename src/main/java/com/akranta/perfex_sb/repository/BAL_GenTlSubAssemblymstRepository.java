package com.akranta.perfex_sb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.akranta.perfex_sb.model.BAL_GenTlSubAssemblymst;

public interface BAL_GenTlSubAssemblymstRepository extends JpaRepository<BAL_GenTlSubAssemblymst, String>
{

    @Modifying
    @Query(
        value = """
            UPDATE GEN_TL_SUBASSEMBLYMST
            SET SBAM_ACTIVE = 'N'
            WHERE SBAM_KEYID = :keyid
            """,
        nativeQuery = true
    )
    //void inactivateByKeyid(@Param("keyid") String keyid);
    int inactivateByKeyid(@Param("keyid") String keyid);

    @Query(
        value = """
            SELECT vw.machineid
            FROM gen_tl_subassemblymst sa
            JOIN gen_vw_mchasmlink vw ON sa.sbam_assemblyid = vw.assemblyid
            WHERE sa.sbam_keyid = :sbamKeyid
            """,
        nativeQuery = true
    )
    String getMachineIdByAssembly(@Param("sbamKeyid") String sbamKeyid);
}