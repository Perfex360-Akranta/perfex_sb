package com.akranta.perfex_sb.repository;


import java.math.BigDecimal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import com.akranta.perfex_sb.model.KznTlKkprojectprioritymst;

public interface KznTlKkprojectprioritymstRepository extends JpaRepository<KznTlKkprojectprioritymst, String> {

    @Query(value = "select count(*) from KZN_TL_PRIORITY_PARAMAETER_MST WHERE KKPM_ACTIVE = 'Y' ", nativeQuery = true)
    int getParmeterCount();

    @Modifying
    @Query(value = " Update KZN_TL_KKPROJECTPRIORITYMST set KPPM_PROJECTSCORE = :projectscore ,KPPM_APPROVEDBY= :approvedby ,KPPM_RANK= :rank  where kppm_keyid= :masterkeyid ", nativeQuery = true)
    int updateProjectPriorityMst(@Param("projectscore") BigDecimal projectscore,@Param("approvedby") String approvedby,@Param("rank") String rank,@Param("masterkeyid") String masterkeyid);

}
