package com.akranta.perfex_sb.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.akranta.perfex_sb.model.BAL_PcsLossReasonLink;

public interface BAL_PcsLossReasonLinkRepository extends JpaRepository<BAL_PcsLossReasonLink, String> {

    Optional<BAL_PcsLossReasonLink> findFirstByPldetailidAndLossidAndReasonidOrderByKeyidAsc(
            String pldetailid, String lossid, String reasonid);

            @Query(value = """
    SELECT COUNT(*)
    FROM pcs_tl_lossreasonlink
    WHERE plrk_lossid = :lossId
      AND plrk_reasonid = :reasonId
      AND plrk_causeid = :causeId
      AND plrk_rootcauseid = :rootCauseId
      AND plrk_pldetailid = :detailId
      AND plrk_keyid <> :keyId
    """, nativeQuery = true)
long countExistingLossLink(
        @Param("lossId") String lossId,
        @Param("reasonId") String reasonId,
        @Param("causeId") String causeId,
        @Param("rootCauseId") String rootCauseId,
        @Param("detailId") String detailId,
        @Param("keyId") String keyId
);
}
