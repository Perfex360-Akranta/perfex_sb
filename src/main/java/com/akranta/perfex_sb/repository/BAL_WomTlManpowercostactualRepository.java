package com.akranta.perfex_sb.repository;

import com.akranta.perfex_sb.model.BAL_WomTlManpowercostactual;
import com.akranta.perfex_sb.model.BAL_WomTlManpowercostactualId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Map;

public interface BAL_WomTlManpowercostactualRepository
        extends JpaRepository<BAL_WomTlManpowercostactual, BAL_WomTlManpowercostactualId> {
                 @Modifying
    @Query(value = "DELETE FROM bal_wom_tl_manpowercostactual "
            + "WHERE mpcs_maintwoid = :maintwoid AND mpcs_manpowerid = :manpowerid AND mpcs_skillid = :skillid",
            nativeQuery = true)
    int deleteByCompositeKey(@Param("maintwoid") String maintwoid,
                              @Param("manpowerid") String manpowerid,
                              @Param("skillid") String skillid);

                                @Query(value = """
        SELECT EMPM_EMPLOYEENUMBER, EMPM_NAME, EMPC_COSTPERHOUR, EMPC_OTCOSTPERHOUR, EMPC_CALLOUTCOSTPERHOUR
        FROM GEN_TL_EMPLOYEEMST
        LEFT JOIN GEN_TL_EMPLOYEECOST ON EMPC_EMPLOYEEID = EMPM_KEYID AND EMPC_ACTIVE = 'Y'
        WHERE EMPM_KEYID = :empId
        """, nativeQuery = true)
    List<Map<String, Object>> getEmpCost(@Param("empId") String empId);

}