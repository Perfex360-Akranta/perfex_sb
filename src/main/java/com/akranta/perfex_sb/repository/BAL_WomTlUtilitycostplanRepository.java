package com.akranta.perfex_sb.repository;

import com.akranta.perfex_sb.model.BAL_WomTlUtilitycostplan;
import com.akranta.perfex_sb.model.BAL_WomTlUtilitycostplanId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BAL_WomTlUtilitycostplanRepository
        extends JpaRepository<BAL_WomTlUtilitycostplan, BAL_WomTlUtilitycostplanId> {
            @Modifying
    @Query(value = "DELETE FROM wom_tl_utilitycostplan "
            + "WHERE utcp_wokeyid = :wokeyid AND utcp_requestedby = :requestedby AND utcp_utilitymstid = :utilitymstid",
            nativeQuery = true)
    int deleteByCompositeKey(@Param("wokeyid") String wokeyid,
                              @Param("requestedby") String requestedby,
                              @Param("utilitymstid") String utilitymstid);
}