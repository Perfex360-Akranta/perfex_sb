package com.akranta.perfex_sb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.akranta.perfex_sb.model.Visualsopmst;

import jakarta.transaction.Transactional;

public interface VisualsopmstRepository extends JpaRepository<Visualsopmst,String>{

    @Query(value = """
            select * from JHA_TL_VISUALSOPMST where vsom_keyid =:keyid
            """,nativeQuery = true)
    Visualsopmst findByKeyid(@Param("keyid") String keyid);       

    //deleting the record by keyid
    @Modifying
@Transactional
@Query(
    value = "DELETE FROM jha_tl_visualsopmst WHERE VSOM_KEYID = :keyId",
    nativeQuery = true
)
int deleteByKeyId(@Param("keyId") String keyId);


}
