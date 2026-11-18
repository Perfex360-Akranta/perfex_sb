package com.akranta.perfex_sb.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.akranta.perfex_sb.model.Bal_pcs_tl_rejectionbreakupdata;

public interface Bal_pcs_tl_rejectionbreakupdataRepository extends JpaRepository<Bal_pcs_tl_rejectionbreakupdata,String>{

    Optional<Bal_pcs_tl_rejectionbreakupdata> findByKeyid(String keyid);

    boolean existsByKeyid(String keyid);

    @Modifying
@Query("DELETE FROM Bal_pcs_tl_rejectionbreakupdata r WHERE r.keyid = :keyid")
int deleteByKeyId(@Param("keyid") String keyid);

}
