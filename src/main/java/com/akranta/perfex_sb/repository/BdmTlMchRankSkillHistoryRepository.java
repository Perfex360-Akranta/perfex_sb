package com.akranta.perfex_sb.repository;

import java.time.LocalDateTime;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.akranta.perfex_sb.model.BdmTlMchRankSkillHistory;


@Repository
public interface BdmTlMchRankSkillHistoryRepository extends JpaRepository<BdmTlMchRankSkillHistory, String> {
@Modifying
@Query(value = "DELETE FROM bdm_tl_mchrankskillhistory WHERE mrsh_equipmentid = :equipmentId AND DATE(mrsh_date) = CAST(:date AS DATE)", nativeQuery = true)
int deleteByEquipmentIdAndDate(@Param("equipmentId") String equipmentId, @Param("date") LocalDateTime date);
}


