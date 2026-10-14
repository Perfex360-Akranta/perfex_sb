package com.akranta.perfex_sb.repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class CLTIWebScheduleRepository {

    private static final String UPDATE_SQL = """
            UPDATE BAL_CLI_TL_CALENDAR
               SET CLCA_STATUS = 'Y',
                   CLCA_ACTUALDATE = :actualDate,
                   CLCA_COMPLETEDBY = :completedBy,
                   CLCA_ACTUALSHIFTID = :shiftId,
                   CLCA_REMARKS = :remarks,
                   CLCA_OBSERVATION = :observation,
                   CLCA_ABNORMALITYTAG = :tagClass
             WHERE CLCA_CELLID = :cellId
               AND CLCA_MACHINEID = :machineId
               AND CLCA_PLANDATE::date = :planDate
               AND CLCA_PLANSHIFTID = :shiftId
               AND CLCA_CLIREFID = :cliRefId
            """;

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public CLTIWebScheduleRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    public int save(List<String> activityIds, String cellId, String machineId, String shiftId,
            String createdBy, String status, String observation, String tagClass) {

        int updated = 0;
        LocalDateTime actualDate = LocalDateTime.now();
        LocalDate planDate = LocalDate.now();

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
                    .addValue("cellId", cellId)
                    .addValue("machineId", machineId)
                    .addValue("planDate", planDate)
                    .addValue("cliRefId", activityId.trim());

            updated += namedParameterJdbcTemplate.update(UPDATE_SQL, params);
        }

        return updated;
    }
}