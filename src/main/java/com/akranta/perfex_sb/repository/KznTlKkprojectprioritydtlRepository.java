package com.akranta.perfex_sb.repository;

import java.math.BigDecimal;

import org.hibernate.sql.Update;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.akranta.perfex_sb.model.KznTlKkprojectprioritydtl;


public interface KznTlKkprojectprioritydtlRepository extends JpaRepository<KznTlKkprojectprioritydtl, String> {
    @Query(value = "select kppd_score from KZN_TL_KKPROJECTPRIORITYDTL where kppd_kppm_keyid = :kppmKeyid and kppd_kkpm_keyid= :KkpmKeyid", nativeQuery = true)
    String getDetailScore(@Param("kppmKeyid") String kppmKeyid,@Param("KkpmKeyid") String KkpmKeyid);

    @Modifying
    @Query(value = "Update KZN_TL_KKPROJECTPRIORITYDTL set KPPD_SCORE = :score where kppd_kppm_keyid = :kppmKeyid and kppd_kkpm_keyid= :KkpmKeyid ", nativeQuery = true)
    int UpdateDetailScore(@Param("score") BigDecimal score,@Param("kppmKeyid") String kppmKeyid,@Param("KkpmKeyid") String KkpmKeyid);
}
