package com.akranta.perfex_sb.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.akranta.perfex_sb.model.BAL_PcsLossReasonLink;

public interface BAL_PcsLossReasonLinkRepository extends JpaRepository<BAL_PcsLossReasonLink, String> {

    Optional<BAL_PcsLossReasonLink> findFirstByPldetailidAndLossidAndReasonidOrderByKeyidAsc(
            String pldetailid, String lossid, String reasonid);

}
