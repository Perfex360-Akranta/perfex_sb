package com.akranta.perfex_sb.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CliTlStandardsProcedures {

    private static final Logger logger = LoggerFactory.getLogger(CliTlStandardsProcedures.class);

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * CLI_PR_ADDTOCALICAL(clidetailid, tillmonth 'DD-MON-YYYY', checkforupd, OUT retval)
     * The 4th argument is the OUT retval placeholder; it is not evaluated, so NULL is used.
     */
    public String generateCalendar(String clitDetailId, String tillMonth, String checkForUpdate) {

        Query query = entityManager.createNativeQuery(
                "CALL CLI_PR_ADDTOCALICAL(:clitDetailId, :tillMonth, :checkForUpdate, NULL)");

        query.setParameter("clitDetailId", clitDetailId);
        query.setParameter("tillMonth", tillMonth);
        query.setParameter("checkForUpdate", checkForUpdate);

        // CALL with an OUT parameter returns one row (retval), so use getResultList()
        // instead of executeUpdate().
        List<?> out = query.getResultList();
        String retval = (out == null || out.isEmpty() || out.get(0) == null) ? null : String.valueOf(out.get(0));

        logger.info("CLI_PR_ADDTOCALICAL returned: {}", retval);
        return retval;
    }
}