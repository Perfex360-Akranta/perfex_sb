package com.akranta.perfex_sb.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.akranta.perfex_sb.model.KznTlMst;

@Repository
public interface KznTlMstRepository extends JpaRepository<KznTlMst, String> {

  List<KznTlMst> findByFlid(String kznmFlid);

  List<KznTlMst> findByStatus(String kznmStatus);

  List<KznTlMst> findByTpmpillarid(String kznmTpmPillarid);

  @Query(value = "SELECT KZNM_KEYID FROM KZN_TL_MST WHERE KZNM_KZBNKEYID = :keyId", nativeQuery = true)
  String findKeyid(@Param("keyId") String keyId);

  // List<GenTlWorkFlowInfo> saveAll(List<GenTlWorkFlowInfo> updatedList);

  // String findKeyid(String keyId);

  @Modifying
  @Transactional
  @Query(value = "UPDATE KZN_TL_MST " +
      "SET KZNM_THEMECATEGORYID = :themeCatId, " +
      "    KZNM_RESULTAREA = :resultArea " +
      "WHERE KZNM_KEYID = :keyId", nativeQuery = true)
  int updateThemeNative(
      @Param("themeCatId") String themeCatId,
      @Param("resultArea") String resultArea,
      @Param("keyId") String keyId);

}
