package com.akranta.perfex_sb.repository;

import com.akranta.perfex_sb.model.BAL_WomTlSparecostactual;
import com.akranta.perfex_sb.model.BAL_WomTlSparecostactualId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BAL_WomTlSparecostactualRepository
        extends JpaRepository<BAL_WomTlSparecostactual, BAL_WomTlSparecostactualId> {

    @Modifying
    @Query(value = "DELETE FROM wom_tl_sparecostactual "
            + "WHERE wsca_woid = :woid AND wsca_requestedby = :requestedby AND wsca_sparesid = :sparesid",
            nativeQuery = true)
    int deleteByCompositeKey(@Param("woid") String woid,
                              @Param("requestedby") String requestedby,
                              @Param("sparesid") String sparesid);
}