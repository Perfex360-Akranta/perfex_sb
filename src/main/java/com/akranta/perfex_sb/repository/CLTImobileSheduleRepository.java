package com.akranta.perfex_sb.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

@Repository
public class CLTImobileSheduleRepository {

    private static final String UPDATE_SQL = """
            UPDATE BAL_CLI_TL_CALENDAR
               SET CLCA_STATUS = 'Y',
                   CLCA_ACTUALDATE = :actualDate,
                   CLCA_COMPLETEDBY = :completedBy,
                   CLCA_ACTUALSHIFTID = :shiftId,
                   CLCA_REMARKS = :remarks,
                   CLCA_OBSERVATION = :observation,
                   CLCA_ABNORMALITYTAG = :tagClass
             WHERE CLCA_KEYID = :clcaKeyId
            """;

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @PersistenceContext
    private EntityManager entityManager;

    public CLTImobileSheduleRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    public int save(List<String> activityIds, String shiftId,
            String createdBy, String status, String observation, String tagClass) {

        int updated = 0;
        LocalDateTime actualDate = LocalDateTime.now();

        for (String activityId : activityIds) {
            if (activityId == null || activityId.trim().isEmpty()) {
                continue;
            }

            MapSqlParameterSource params = new MapSqlParameterSource()
                    .addValue("actualDate", actualDate)
                    .addValue("completedBy", createdBy)
                    .addValue("shiftId", shiftId)
                    .addValue("remarks", status)
                    .addValue("observation", observation)
                    .addValue("tagClass", tagClass)
                    .addValue("clcaKeyId", activityId.trim());

            updated += namedParameterJdbcTemplate.update(UPDATE_SQL, params);
        }

        return updated;
    }

    public boolean calendarExists(String monthYear, String factoryId, String sectionId,
            String cellId, String machineId) {

        Query query = entityManager.createNativeQuery(
                "SELECT DISTINCT CLCA_MONTHYEAR FROM BAL_CLI_TL_CALENDAR "
                        + "WHERE UPPER(TO_CHAR(CLCA_MONTHYEAR, 'MON-YYYY')) = UPPER(:monthYear) "
                        + "AND CLCA_FACTORYID = :factoryId "
                        + "AND CLCA_SECTIONID = :sectionId "
                        + "AND CLCA_CELLID = :cellId "
                        + "AND CLCA_MACHINEID = :machineId");
        query.setParameter("monthYear", monthYear);
        query.setParameter("factoryId", factoryId);
        query.setParameter("sectionId", sectionId);
        query.setParameter("cellId", cellId);
        query.setParameter("machineId", machineId);

        return !query.getResultList().isEmpty();
    }

    // Same call as web genrateCalYear
    public void runCalendarProcedure(String fromDate, String factoryId, String sectionId,
            String cellId, String machineId) {

        Query query = entityManager.createNativeQuery(
                "CALL CLI_PR_RUNCLICALENDAR(:fromDate, :factoryId, :sectionId, :cellId, :machineId, :assembly, :flag)");
        query.setParameter("fromDate", fromDate);
        query.setParameter("factoryId", factoryId);
        query.setParameter("sectionId", sectionId);
        query.setParameter("cellId", cellId);
        query.setParameter("machineId", machineId);
        query.setParameter("assembly", "{}");
        query.setParameter("flag", "X");
        query.executeUpdate();
    }

}
