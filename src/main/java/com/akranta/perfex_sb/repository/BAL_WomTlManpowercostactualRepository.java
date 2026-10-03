package com.akranta.perfex_sb.repository;

import com.akranta.perfex_sb.model.BAL_WomTlManpowercostactual;
import com.akranta.perfex_sb.model.BAL_WomTlManpowercostactualId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BAL_WomTlManpowercostactualRepository
        extends JpaRepository<BAL_WomTlManpowercostactual, BAL_WomTlManpowercostactualId> {
                 @Modifying
    @Query(value = "DELETE FROM bal_wom_tl_manpowercostactual "
            + "WHERE mpcs_maintwoid = :maintwoid AND mpcs_manpowerid = :manpowerid AND mpcs_skillid = :skillid",
            nativeQuery = true)
    int deleteByCompositeKey(@Param("maintwoid") String maintwoid,
                              @Param("manpowerid") String manpowerid,
                              @Param("skillid") String skillid);
}