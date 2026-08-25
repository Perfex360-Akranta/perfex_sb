package com.akranta.perfex_sb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import com.akranta.perfex_sb.model.KkTlFipapprovalslist;


public interface KkTlFipapprovalslistRepository extends JpaRepository<KkTlFipapprovalslist, KkTlFipapprovalslist.FipApprovalsListId> {
    

    @Modifying
    @Query(value = "Update KK_TL_FIPAPPROVALSLIST set CURRENTSTATUS='C' where PROJECTNO= :kzpmKeyid AND STAGE= :trnsCode AND CURRENTSTATUS='N'", nativeQuery = true)
    int UpdateFipApprovalCurrentStatus(@Param("kzpmKeyid") String kzpmKeyid,@Param("trnsCode") String trnsCode);

    @Modifying
    @Query(value = "Update KK_TL_FIPAPPROVALSLIST set MENULEVELSTATUS='C' where PROJECTNO= :kzpmKeyid AND STAGE= :trnsCode AND MENULEVELSTATUS='N'", nativeQuery = true)
    int UpdateFipApprovalMenuLevelStatus(@Param("kzpmKeyid") String kzpmKeyid,@Param("trnsCode") String trnsCode);

    @Query(value = " Select count(*) from KK_TL_FIPAPPROVALSLIST where PROJECTNO= :kzpmKeyid AND STAGE= :trnsCode ", nativeQuery = true)
    int getFipApprovalCount(@Param("kzpmKeyid") String kzpmKeyid,@Param("trnsCode") String trnsCode);
}
