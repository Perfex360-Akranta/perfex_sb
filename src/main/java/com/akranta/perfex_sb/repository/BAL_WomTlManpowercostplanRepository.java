package com.akranta.perfex_sb.repository;

import com.akranta.perfex_sb.model.BAL_WomTlManpowercostplan;
import com.akranta.perfex_sb.model.BAL_WomTlManpowercostplanId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BAL_WomTlManpowercostplanRepository
        extends JpaRepository<BAL_WomTlManpowercostplan, BAL_WomTlManpowercostplanId> {

                 @Modifying
    @Query(value = "DELETE FROM wom_tl_manpowercostplan "
            + "WHERE mpcp_woid = :woid AND mpcp_manpowerid = :manpowerid AND mpcp_skillid = :skillid",
            nativeQuery = true)
    int deleteByCompositeKey(@Param("woid") String woid,
                              @Param("manpowerid") String manpowerid,
                              @Param("skillid") String skillid);
}