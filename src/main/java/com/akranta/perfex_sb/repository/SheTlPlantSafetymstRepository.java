package com.akranta.perfex_sb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.akranta.perfex_sb.model.SheTlPlantSafetydtl;
import com.akranta.perfex_sb.model.SheTlPlantSafetymst;
import java.util.List;
import java.util.Map;

@Repository
public interface SheTlPlantSafetymstRepository extends JpaRepository<SheTlPlantSafetymst, String> {

   @Query(value = "SELECT * FROM she_tl_plantsafetymst WHERE spsm_keyid = :keyid", nativeQuery = true)
SheTlPlantSafetymst findByKeyid(@Param("keyid") String keyid); 
    @Query(value = "SELECT spsd_keyid FROM she_tl_plantsafetydtl WHERE spsd_spsm_keyid = :keyid", nativeQuery = true)
String findSpsdKeyidBySpsmKeyid(@Param("keyid") String keyid);


}
