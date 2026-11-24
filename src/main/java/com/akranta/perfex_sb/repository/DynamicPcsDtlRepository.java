package com.akranta.perfex_sb.repository;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.akranta.perfex_sb.model.BAL_PcsEntryDtl;

import jakarta.persistence.Column;
import jakarta.persistence.Id;

@Repository
public class DynamicPcsDtlRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public DynamicPcsDtlRepository(
            NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public int insert(String tableName, BAL_PcsEntryDtl entity) {

        // Validate dynamic table name
        if (tableName == null ||
            !tableName.matches(
                "[a-zA-Z_][a-zA-Z0-9_]*(\\.[a-zA-Z_][a-zA-Z0-9_]*)?")) {
            throw new IllegalArgumentException("Invalid table name");
        }

        List<String> columns = new ArrayList<>();
        List<String> parameters = new ArrayList<>();
        MapSqlParameterSource params = new MapSqlParameterSource();

        try {
            for (Field field : BAL_PcsEntryDtl.class.getDeclaredFields()) {

                Column column = field.getAnnotation(Column.class);
                Id id = field.getAnnotation(Id.class);

                // Include only persistent entity fields
                if (column == null && id == null) {
                    continue;
                }

                String columnName = column != null &&
                        !column.name().isBlank()
                        ? column.name()
                        : field.getName();

                field.setAccessible(true);

                Object value = field.get(entity);

                columns.add(columnName);
                parameters.add(":" + field.getName());

                params.addValue(field.getName(), value);
            }

            String sql = """
                INSERT INTO %s (%s)
                VALUES (%s)
                """.formatted(
                    tableName,
                    String.join(", ", columns),
                    String.join(", ", parameters)
                );

            return jdbcTemplate.update(sql, params);

        } catch (IllegalAccessException e) {
            throw new RuntimeException("Unable to insert detail", e);
        }
    }

//     public int update(String tableName, BAL_PcsEntryDtl entity) {

//     // validateTableName(tableName);

//     try {
//         Class<?> clazz = BAL_PcsEntryDtl.class;

//         List<String> setColumns = new ArrayList<>();
//         MapSqlParameterSource params = new MapSqlParameterSource();

//         String idColumn = null;
//         Object idValue = null;

//         for (Field field : clazz.getDeclaredFields()) {

//             Column column = field.getAnnotation(Column.class);
//             Id id = field.getAnnotation(Id.class);

//             if (column == null && id == null) {
//                 continue;
//             }

//             field.setAccessible(true);

//             String columnName = column != null && !column.name().isBlank()
//                     ? column.name()
//                     : field.getName();

//             Object value = field.get(entity);

//             if (id != null) {
//                 idColumn = columnName;
//                 idValue = value;
//                 continue;
//             }

//             String paramName = field.getName();

//             setColumns.add(columnName + " = :" + paramName);
//             params.addValue(paramName, value);
//         }

//         if (idColumn == null || idValue == null) {
//             throw new IllegalArgumentException("Primary key value is missing");
//         }

//         params.addValue("whereId", idValue);

//         String sql = """
//                 UPDATE %s
//                 SET %s
//                 WHERE %s = :whereId
//                 """.formatted(
//                 tableName,
//                 String.join(", ", setColumns),
//                 idColumn
//         );

//         return jdbcTemplate.update(sql, params);

//     } catch (Exception e) {
//         throw new RuntimeException("Unable to update dynamic PCS detail table", e);
//     }
// }

private static final Set<String> BAL_PCS_UPDATE_FIELDS = Set.of(
    "CALENDARTIME",
    "LOADINGTIME",
    "REMARKS",
    "CAVITYAVAILABLE",
    "BACKLOGQTY",
    "WNO",
    "RAWMATERIALTYPE",
    "WEIGHT",
    "TRIMMINGQTY",
    "EXPANSIONQTY"
);

public int update(String tableName, BAL_PcsEntryDtl entity) {

    try {
        Class<?> clazz = BAL_PcsEntryDtl.class;

        List<String> setColumns = new ArrayList<>();
        MapSqlParameterSource params = new MapSqlParameterSource();

        String idColumn = null;
        Object idValue = null;

        for (Field field : clazz.getDeclaredFields()) {

            Column column = field.getAnnotation(Column.class);
            Id id = field.getAnnotation(Id.class);

            if (column == null && id == null) {
                continue;
            }

            field.setAccessible(true);

            String columnName = column != null && !column.name().isBlank()
                    ? column.name()
                    : field.getName();

            Object value = field.get(entity);

            // Primary key
            if (id != null) {
                idColumn = columnName;
                idValue = value;
                continue;
            }

            // Update only fields permitted by legacy logic
            if (!BAL_PCS_UPDATE_FIELDS.contains(columnName.toUpperCase())) {
                continue;
            }

            String paramName = field.getName();

            setColumns.add(columnName + " = :" + paramName);
            params.addValue(paramName, value);
        }

        if (idColumn == null || idValue == null) {
            throw new IllegalArgumentException(
                "Primary key value is missing"
            );
        }

        if (setColumns.isEmpty()) {
            return 0;
        }

        params.addValue("whereId", idValue);

        String sql = """
                UPDATE %s
                SET %s
                WHERE %s = :whereId
                """.formatted(
                tableName,
                String.join(", ", setColumns),
                idColumn
        );

        return jdbcTemplate.update(sql, params);

    } catch (Exception e) {
        throw new RuntimeException(
            "Unable to update dynamic PCS detail table", e
        );
    }
}


public int updateLossValue(
        String detailTableName,
        String detailId,
        String lossId,
        String lossMapId,
        String lossColName) {

// validateTableName(detailTableName);

    String columnName;
System.err.println("lossMapId:"+lossMapId+"-lossColName:"+lossColName+"-lossId:"+lossId+"-detailId:"+detailId);
    if (lossMapId != null && !lossMapId.equals("{}")// {} TO Y
            && !lossMapId.isBlank()) {
        columnName = lossMapId;
    } else {
        columnName = lossColName;
    }

    if (columnName == null ||
            !columnName.matches("[a-zA-Z_][a-zA-Z0-9_]*")) {
        throw new IllegalArgumentException(
                "Invalid loss mapping column: " + columnName);
    }

    String sql = """
        UPDATE %s
        SET %s = (
            SELECT NULLIF(SUM(plrk_minutes), 0)
            FROM pcs_tl_lossreasonlink
            WHERE plrk_pldetailid = :detailId
              AND plrk_lossid = :lossId
        )
        WHERE pldetailsid = :detailId
        """.formatted(detailTableName, columnName);

    MapSqlParameterSource params = new MapSqlParameterSource()
            .addValue("detailId", detailId)
            .addValue("lossId", lossId);

    return jdbcTemplate.update(sql, params);
}

}
