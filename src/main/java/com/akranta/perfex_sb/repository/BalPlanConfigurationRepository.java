package com.akranta.perfex_sb.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.akranta.perfex_sb.model.BalPlanConfiguration;

@Repository
public interface BalPlanConfigurationRepository extends JpaRepository<BalPlanConfiguration,LocalDateTime> {

    @Query(value =
        "SELECT FREQ, TO_CHAR(MIN(NEXTDUEDATE),'DD-MON-YYYY') AS NEXTDUEDATE " +
        "FROM ( " +
        "   SELECT FREQ, " +
        "       CASE FREQ " +
        "           WHEN 'Y' THEN EDATE + ((12 * (ANTB_WEEKNAME + 1)) * INTERVAL '1 month') " +
        "           WHEN 'H' THEN EDATE + ((6 * ANTB_WEEKNAME) * INTERVAL '1 month') " +
        "           WHEN 'Q' THEN EDATE + ((3 * ANTB_WEEKNAME) * INTERVAL '1 month') " +
        "           ELSE EDATE + ((1 * ANTB_WEEKNAME) * INTERVAL '1 month') " +
        "       END AS NEXTDUEDATE " +
        "   FROM BAL_GEN_TL_ANNUALTABLE, " +
        "       (SELECT 'Q' AS FREQ UNION SELECT 'Y' AS FREQ UNION SELECT 'M' AS FREQ UNION SELECT 'H' AS FREQ) f, " +
        "       (SELECT (TO_DATE('01-01-2026','DD-MM-YYYY') - INTERVAL '24 months') AS EDATE) e " +
        "   WHERE ANTB_WEEKNAME >= 0 " +
        ") sub " +
        "WHERE NEXTDUEDATE >= DATE_TRUNC('month', CURRENT_DATE) " +
        "GROUP BY FREQ ORDER BY FREQ",
        nativeQuery = true)
    List<Object[]> findNextDueDatesRaw();

}
