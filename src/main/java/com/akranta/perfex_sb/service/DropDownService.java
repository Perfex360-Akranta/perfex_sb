package com.akranta.perfex_sb.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import com.akranta.perfex_sb.repository.CommonFilterRepository;
import com.akranta.perfex_sb.dto.ComboFilterDto;
import com.akranta.perfex_sb.dto.CommonFilterDto;
import com.akranta.perfex_sb.dto.DropDownDto;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.akranta.perfex_sb.dto.FunctionalLocationHierarchyDto;
import com.akranta.perfex_sb.dto.FunctionalLocationOptionDto;
import com.akranta.perfex_sb.util.ValidationUtil;

import jakarta.persistence.Query;

@Service
public class DropDownService {

        @PersistenceContext
        private EntityManager entityManager;

        @Autowired
        private CommonFilterRepository repository;

        public List<DropDownDto> getDropdown(
                        String tableName,
                        String valueColumn,
                        String labelColumn,
                        String condSql) {

                String sql = """
                                SELECT %s, %s
                                FROM %s
                                %s
                                ORDER BY %s LIMIT 100
                                """.formatted(
                                valueColumn,
                                labelColumn,
                                tableName,
                                (condSql != null && !condSql.isBlank())
                                                ? "WHERE " + condSql
                                                : "",
                                labelColumn);

                List<Object[]> rows = entityManager
                                .createNativeQuery(sql)
                                .getResultList();

                return rows.stream()
                                .map(r -> new DropDownDto(
                                                String.valueOf(r[0]),
                                                String.valueOf(r[1])))
                                .toList();
        }

        public List<FunctionalLocationOptionDto> getFunctionalCompanies() {

                String sql = """
                                SELECT value, label, flid, elementid
                                FROM (
                                    SELECT DISTINCT ON (v.COMP_KEYID)
                                           v.COMP_KEYID AS value,
                                           TRIM(v.COMP_NAME) || ' - ' || TRIM(v.COMP_CODE) AS label,
                                           COALESCE(f.FNLN_KEYID, 'FNL000000001') AS flid,
                                           COALESCE(f.FNLN_ELEMENTID, v.COMP_KEYID) AS elementid
                                    FROM GEN_VW_FACTORYLAYOUT v
                                    LEFT JOIN GEN_TL_FUNCTIONALLOCN f
                                           ON f.FNLN_ORIGINALID = v.COMP_KEYID
                                    WHERE v.COMP_ACTIVE = 'Y'
                                    ORDER BY v.COMP_KEYID, label
                                ) x
                                ORDER BY label
                                LIMIT 100
                                """;

                return executeFunctionalLocationOptionQuery(sql, Map.of());
        }

        /*
         * public List<FunctionalLocationOptionDto> getFunctionalCompanies() {
         * 
         * String sql = """
         * SELECT DISTINCT
         * COMP_KEYID AS value,
         * TRIM(COMP_NAME) || ' - ' || TRIM(COMP_CODE) AS label,
         * (
         * SELECT FNLN_KEYID
         * FROM GEN_TL_FUNCTIONALLOCN
         * WHERE FNLN_ORIGINALID = COMP_KEYID
         * LIMIT 1
         * ) AS flid,
         * (
         * SELECT FNLN_ELEMENTID
         * FROM GEN_TL_FUNCTIONALLOCN
         * WHERE FNLN_ORIGINALID = COMP_KEYID
         * LIMIT 1
         * ) AS elementid
         * FROM GEN_VW_FACTORYLAYOUT
         * WHERE COMP_ACTIVE = 'Y'
         * ORDER BY label
         * LIMIT 100
         * """;
         * 
         * return executeFunctionalLocationOptionQuery(sql, Map.of());
         * }
         */

        public List<FunctionalLocationOptionDto> getFunctionalLocations(String companyId) {

                StringBuilder sql = new StringBuilder("""
                                SELECT DISTINCT
                                       LOCN_KEYID AS value,
                                       TRIM(LOCN_NAME) || ' - ' || TRIM(LOCN_CODE) AS label,
                                       (
                                         SELECT FNLN_KEYID
                                         FROM GEN_TL_FUNCTIONALLOCN
                                         WHERE FNLN_ORIGINALID = LOCN_KEYID
                                         LIMIT 1
                                       ) AS flid,
                                       (
                                         SELECT FNLN_ELEMENTID
                                         FROM GEN_TL_FUNCTIONALLOCN
                                         WHERE FNLN_ORIGINALID = LOCN_KEYID
                                         LIMIT 1
                                       ) AS elementid
                                FROM GEN_TL_LOCATIONMST
                                WHERE 1 = 1
                                """);

                Map<String, Object> params = new HashMap<>();

                if (isValid(companyId)) {
                        sql.append(" AND LOCN_COMPANYID = :companyId ");
                        params.put("companyId", companyId);
                }

                sql.append(" ORDER BY label LIMIT 100 ");

                return executeFunctionalLocationOptionQuery(sql.toString(), params);
        }

        public List<FunctionalLocationOptionDto> getFunctionalSbus(
                        String companyId,
                        String locationId) {

                StringBuilder sql = new StringBuilder("""
                                WITH sbu_base AS (
                                    SELECT DISTINCT
                                           SBUT_KEYID AS value,
                                           TRIM(SBUT_NAME) || ' - ' || TRIM(SBUT_CODE) AS label
                                    FROM GEN_VW_FACTORYLAYOUT
                                    WHERE SBUT_KEYID IS NOT NULL
                                """);

                Map<String, Object> params = new HashMap<>();

                if (isValid(companyId)) {
                        sql.append(" AND COMP_KEYID = :companyId ");
                        params.put("companyId", companyId);
                }

                if (isValid(locationId)) {
                        sql.append(" AND LOCN_KEYID = :locationId ");
                        params.put("locationId", locationId);
                }

                sql.append("""
                                )
                                SELECT
                                       b.value,
                                       b.label,
                                       COALESCE(f.FNLN_KEYID, '') AS flid,
                                       COALESCE(f.FNLN_ELEMENTID, '') AS elementid
                                FROM sbu_base b
                                LEFT JOIN LATERAL (
                                    SELECT FNLN_KEYID, FNLN_ELEMENTID
                                    FROM GEN_TL_FUNCTIONALLOCN
                                    WHERE FNLN_ORIGINALID = b.value
                                    LIMIT 1
                                ) f ON TRUE
                                ORDER BY b.label
                                LIMIT 100
                                """);

                return executeFunctionalLocationOptionQuery(sql.toString(), params);
        }

        public List<FunctionalLocationOptionDto> getFunctionalPbus(
                        String companyId,
                        String locationId,
                        String sbuId) {

                StringBuilder sql = new StringBuilder("""
                                WITH pbu_base AS (
                                    SELECT DISTINCT
                                           PBUT_KEYID AS value,
                                           TRIM(PBUT_NAME) || ' - ' || TRIM(PBUT_CODE) AS label
                                    FROM GEN_VW_FACTORYLAYOUT
                                    WHERE PBUT_KEYID IS NOT NULL
                                """);

                Map<String, Object> params = new HashMap<>();

                if (isValid(companyId)) {
                        sql.append(" AND COMP_KEYID = :companyId ");
                        params.put("companyId", companyId);
                }

                if (isValid(locationId)) {
                        sql.append(" AND LOCN_KEYID = :locationId ");
                        params.put("locationId", locationId);
                }

                if (isValid(sbuId)) {
                        sql.append(" AND SBUT_KEYID = :sbuId ");
                        params.put("sbuId", sbuId);
                }

                sql.append("""
                                )
                                SELECT
                                       b.value,
                                       b.label,
                                       COALESCE(f.FNLN_KEYID, '') AS flid,
                                       COALESCE(f.FNLN_ELEMENTID, '') AS elementid
                                FROM pbu_base b
                                LEFT JOIN LATERAL (
                                    SELECT FNLN_KEYID, FNLN_ELEMENTID
                                    FROM GEN_TL_FUNCTIONALLOCN
                                    WHERE FNLN_ORIGINALID = b.value
                                    LIMIT 1
                                ) f ON TRUE
                                ORDER BY b.label
                                LIMIT 100
                                """);

                return executeFunctionalLocationOptionQuery(sql.toString(), params);
        }

        public List<FunctionalLocationOptionDto> getFunctionalSections(
                        String companyId,
                        String locationId,
                        String sbuId,
                        String pbuId) {

                StringBuilder sql = new StringBuilder("""
                                WITH section_base AS (
                                    SELECT DISTINCT
                                           SECT_KEYID AS value,
                                           TRIM(SECT_NAME) || ' - ' || TRIM(SECT_CODE) AS label
                                    FROM GEN_VW_FACTORYLAYOUT
                                    WHERE SECT_ACTIVE = 'Y'
                                """);

                Map<String, Object> params = new HashMap<>();

                if (isValid(companyId)) {
                        sql.append(" AND COMP_KEYID = :companyId ");
                        params.put("companyId", companyId);
                }

                if (isValid(locationId)) {
                        sql.append(" AND LOCN_KEYID = :locationId ");
                        params.put("locationId", locationId);
                }

                if (isValid(sbuId)) {
                        sql.append(" AND SBUT_KEYID = :sbuId ");
                        params.put("sbuId", sbuId);
                }

                if (isValid(pbuId)) {
                        sql.append(" AND PBUT_KEYID = :pbuId ");
                        params.put("pbuId", pbuId);
                }

                sql.append("""
                                )
                                SELECT
                                       b.value,
                                       b.label,
                                       COALESCE(f.FNLN_KEYID, '') AS flid,
                                       COALESCE(f.FNLN_ELEMENTID, '') AS elementid
                                FROM section_base b
                                LEFT JOIN LATERAL (
                                    SELECT FNLN_KEYID, FNLN_ELEMENTID
                                    FROM GEN_TL_FUNCTIONALLOCN
                                    WHERE FNLN_ORIGINALID = b.value
                                    LIMIT 1
                                ) f ON TRUE
                                ORDER BY b.label
                                LIMIT 100
                                """);

                return executeFunctionalLocationOptionQuery(sql.toString(), params);
        }

        public List<FunctionalLocationOptionDto> getFunctionalCells(
                        String companyId,
                        String locationId,
                        String sbuId,
                        String pbuId,
                        String sectionId) {

                StringBuilder sql = new StringBuilder("""
                                WITH cell_base AS (
                                    SELECT DISTINCT
                                           CELL_KEYID AS value,
                                           TRIM(CELL_CODE) || ' - ' || TRIM(CELL_NAME) AS label
                                    FROM GEN_VW_FACTORYLAYOUT
                                    WHERE CELL_ACTIVE = 'Y'
                                """);

                Map<String, Object> params = new HashMap<>();

                if (isValid(companyId)) {
                        sql.append(" AND COMP_KEYID = :companyId ");
                        params.put("companyId", companyId);
                }

                if (isValid(locationId)) {
                        sql.append(" AND LOCN_KEYID = :locationId ");
                        params.put("locationId", locationId);
                }

                if (isValid(sbuId)) {
                        sql.append(" AND SBUT_KEYID = :sbuId ");
                        params.put("sbuId", sbuId);
                }

                if (isValid(pbuId)) {
                        sql.append(" AND PBUT_KEYID = :pbuId ");
                        params.put("pbuId", pbuId);
                }

                if (isValid(sectionId)) {
                        sql.append(" AND SECT_KEYID = :sectionId ");
                        params.put("sectionId", sectionId);
                }

                sql.append("""
                                )
                                SELECT
                                       b.value,
                                       b.label,
                                       COALESCE(f.FNLN_KEYID, '') AS flid,
                                       COALESCE(f.FNLN_ELEMENTID, '') AS elementid
                                FROM cell_base b
                                LEFT JOIN LATERAL (
                                    SELECT FNLN_KEYID, FNLN_ELEMENTID
                                    FROM GEN_TL_FUNCTIONALLOCN
                                    WHERE FNLN_ORIGINALID = b.value
                                    LIMIT 1
                                ) f ON TRUE
                                ORDER BY b.label
                                LIMIT 150
                                """);

                return executeFunctionalLocationOptionQuery(sql.toString(), params);
        }

        public List<FunctionalLocationOptionDto> getFunctionalMachines(
                        String companyId,
                        String locationId,
                        String sbuId,
                        String pbuId,
                        String sectionId,
                        String cellId,
                        String loginFlid) {

                StringBuilder sql = new StringBuilder("""
                                SELECT DISTINCT
                                       MCHM_KEYID AS value,
                                       TRIM(MCHM_MACHINENAME) || '-' || TRIM(MCHM_MACHINENO) AS label,
                                       MCHM_FLID AS flid,
                                       (
                                          SELECT FNLN_ELEMENTID
                                          FROM GEN_TL_FUNCTIONALLOCN
                                          WHERE FNLN_KEYID = MCHM_FLID
                                          LIMIT 1
                                       ) AS elementid
                                FROM GEN_VW_FACTORYLAYOUT
                                WHERE MCHM_ACTIVE = 'Y'
                                  AND MCHM_TYPE = 'MCH'
                                """);

                Map<String, Object> params = new HashMap<>();

                if (isValid(companyId)) {
                        sql.append(" AND COMP_KEYID = :companyId ");
                        params.put("companyId", companyId);
                }

                if (isValid(locationId)) {
                        sql.append(" AND LOCN_KEYID = :locationId ");
                        params.put("locationId", locationId);
                }

                if (isValid(sbuId)) {
                        sql.append(" AND SBUT_KEYID = :sbuId ");
                        params.put("sbuId", sbuId);
                }

                if (isValid(pbuId)) {
                        sql.append(" AND PBUT_KEYID = :pbuId ");
                        params.put("pbuId", pbuId);
                }

                if (isValid(sectionId)) {
                        sql.append(" AND SECT_KEYID = :sectionId ");
                        params.put("sectionId", sectionId);
                }

                if (isValid(cellId)) {
                        sql.append(" AND CELL_KEYID = :cellId ");
                        params.put("cellId", cellId);
                }

                if (isValid(loginFlid)) {
                        sql.append("""
                                        AND MCHM_FLID IN (
                                            SELECT FLID
                                            FROM GEN_MV_FLIDHIERARCHY
                                            WHERE POSITION(:loginFlid IN (COALESCE(PARENTFLIDS, '') || FLID)) > 0
                                        )
                                        """);
                        params.put("loginFlid", loginFlid);
                }

                sql.append(" ORDER BY label LIMIT 100 ");

                return executeFunctionalLocationOptionQuery(sql.toString(), params);
        }

        public FunctionalLocationHierarchyDto getFunctionalLocationHierarchy(
                        String originalId,
                        String flid) {

                StringBuilder sql = new StringBuilder("""
                                SELECT
                                       COMP_KEYID,
                                       COMP_CODE,

                                       LOCN_KEYID,
                                       LOCN_CODE,

                                       SBUT_KEYID,
                                       SBUT_CODE,

                                       PBUT_KEYID,
                                       PBUT_CODE,

                                       SECT_KEYID,
                                       SECT_CODE,

                                       CELL_KEYID,
                                       CELL_NAME,

                                       MCHM_KEYID,
                                       MCHM_MACHINENO,

                                       FNLN_KEYID,
                                       FNLN_ELEMENTID,
                                       DISPLAYCODE
                                FROM GEN_VW_FNLN
                                WHERE 1 = 1
                                """);

                Map<String, Object> params = new HashMap<>();

                if (isValid(flid)) {
                        sql.append(" AND FNLN_KEYID = :flid ");
                        params.put("flid", flid);
                } else if (isValid(originalId)) {
                        // sql.append("""
                        //                 AND (
                        //                        COMP_KEYID = :originalId
                        //                     OR LOCN_KEYID = :originalId
                        //                     OR SBUT_KEYID = :originalId
                        //                     OR PBUT_KEYID = :originalId
                        //                     OR SECT_KEYID = :originalId
                        //                     OR CELL_KEYID = :originalId
                        //                     OR MCHM_KEYID = :originalId
                        //                 )
                        //                 """);
                        sql.append(" AND fnln_originalid = :originalId  ");
                        params.put("originalId", originalId);
                        
                } else {
                        return null;
                }

                sql.append(" LIMIT 1 ");

                Query query = entityManager.createNativeQuery(sql.toString());
                params.forEach(query::setParameter);

                List<Object[]> rows = query.getResultList();

                if (rows == null || rows.isEmpty()) {
                        return null;
                }

                Object[] r = rows.get(0);

                String functionalLocationText = buildFunctionalLocationText(
                                toStringValue(r[1]),
                                toStringValue(r[3]),
                                toStringValue(r[5]),
                                toStringValue(r[7]),
                                toStringValue(r[9]),
                                toStringValue(r[11]),
                                toStringValue(r[13]));

                return new FunctionalLocationHierarchyDto(
                                toStringValue(r[0]),
                                toStringValue(r[1]),

                                toStringValue(r[2]),
                                toStringValue(r[3]),

                                toStringValue(r[4]),
                                toStringValue(r[5]),

                                toStringValue(r[6]),
                                toStringValue(r[7]),

                                toStringValue(r[8]),
                                toStringValue(r[9]),

                                toStringValue(r[10]),
                                toStringValue(r[11]),

                                toStringValue(r[12]),
                                toStringValue(r[13]),

                                toStringValue(r[14]),
                                toStringValue(r[15]),
                                toStringValue(r[16]),
                                functionalLocationText);
        }

        public FunctionalLocationHierarchyDto getLoginDefaultFunctionalLocation(String employeeId) {

                if (!isValid(employeeId)) {
                        return getFunctionalLocationHierarchy(null, "FNL000000001");
                }

                String sql = """
                                SELECT DISTINCT
                                       ROLE_KEYID,
                                       FLID,
                                       Role_Name || ' - ' || Fnln_Displaycode AS text,
                                       ROLE_LEVEL
                                FROM Gen_Tl_Fnlnroleteam,
                                     Adm_Tl_Roleorder,
                                     GEN_MV_FLIDHIERARCHY
                                WHERE Role_Keyid = Frt_Role_Keyid
                                  AND Flid = Frt_Fnln_Keyid
                                  AND FRT_EMPM_KEYID = :employeeId
                                ORDER BY ROLE_LEVEL ASC, text ASC
                                LIMIT 1
                                """;

                Query query = entityManager.createNativeQuery(sql);
                query.setParameter("employeeId", employeeId);

                List<Object[]> rows = query.getResultList();

                if (rows == null || rows.isEmpty()) {
                        return getFunctionalLocationHierarchy(null, "FNL000000001");
                }

                Object[] row = rows.get(0);

                String roleId = toStringValue(row[0]);
                String flid = toStringValue(row[1]);

                System.out.println("LOGIN DEFAULT FUNCTIONAL LOCATION");
                System.out.println("Employee ID : " + employeeId);
                System.out.println("Role ID     : " + roleId);
                System.out.println("FLID        : " + flid);

                if (!isValid(flid)) {
                        return getFunctionalLocationHierarchy(null, "FNL000000001");
                }

                return getFunctionalLocationHierarchy(null, flid);
        }

        public FunctionalLocationHierarchyDto getDefaultFunctionalLocation(
                        String employeeId,
                        String roleId,
                        String flid) {

                if (!isValid(employeeId)) {
                        return null;
                }

                StringBuilder sql = new StringBuilder("""
                                SELECT DISTINCT
                                       COMP_KEYID,
                                       TRIM(COMP_NAME) || ' - ' || TRIM(COMP_CODE),

                                       LOCN_KEYID,
                                       LOCN_NAME || '-' || LOCN_CODE,

                                       SBUT_KEYID,
                                       SBUT_NAME || '-' || SBUT_CODE,

                                       PBUT_KEYID,
                                       PBUT_NAME || '-' || PBUT_CODE,

                                       SECT_KEYID,
                                       TRIM(SECT_NAME) || ' - ' || TRIM(SECT_CODE),

                                       CELL_KEYID,
                                       TRIM(CELL_CODE) || ' - ' || TRIM(CELL_NAME),

                                       MCHM_KEYID,
                                       TRIM(MCHM_MACHINENAME) || '-' || TRIM(MCHM_MACHINENO),

                                       FNLN_KEYID,
                                       FNLN_ELEMENTID,
                                       DISPLAYCODE
                                FROM GEN_VW_FNLN
                                JOIN GEN_TL_FNLNROLETEAM
                                  ON FNLN_KEYID = FRT_FNLN_KEYID
                                WHERE FRT_EMPM_KEYID = :employeeId
                                """);

                Map<String, Object> params = new HashMap<>();
                params.put("employeeId", employeeId);

                if (isValid(roleId)) {
                        sql.append(" AND FRT_ROLE_KEYID = :roleId ");
                        params.put("roleId", roleId);
                }

                if (isValid(flid)) {
                        sql.append(" AND FRT_FNLN_KEYID = :flid ");
                        params.put("flid", flid);
                }

                sql.append(" ORDER BY DISPLAYCODE LIMIT 1 ");

                Query query = entityManager.createNativeQuery(sql.toString());
                params.forEach(query::setParameter);

                List<Object[]> rows = query.getResultList();

                if (rows == null || rows.isEmpty()) {
                        return null;
                }

                Object[] r = rows.get(0);

                String functionalLocationText = buildFunctionalLocationText(
                                toStringValue(r[1]),
                                toStringValue(r[3]),
                                toStringValue(r[5]),
                                toStringValue(r[7]),
                                toStringValue(r[9]),
                                toStringValue(r[11]),
                                toStringValue(r[13]));

                return new FunctionalLocationHierarchyDto(
                                toStringValue(r[0]),
                                toStringValue(r[1]),

                                toStringValue(r[2]),
                                toStringValue(r[3]),

                                toStringValue(r[4]),
                                toStringValue(r[5]),

                                toStringValue(r[6]),
                                toStringValue(r[7]),

                                toStringValue(r[8]),
                                toStringValue(r[9]),

                                toStringValue(r[10]),
                                toStringValue(r[11]),

                                toStringValue(r[12]),
                                toStringValue(r[13]),

                                toStringValue(r[14]),
                                toStringValue(r[15]),
                                toStringValue(r[16]),
                                functionalLocationText);
        }

        private List<FunctionalLocationOptionDto> executeFunctionalLocationOptionQuery(
                        String sql,
                        Map<String, Object> params) {

                Query query = entityManager.createNativeQuery(sql);
                params.forEach(query::setParameter);

                List<Object[]> rows = query.getResultList();

                List<FunctionalLocationOptionDto> result = new ArrayList<>();

                for (Object[] r : rows) {
                        result.add(new FunctionalLocationOptionDto(
                                        toStringValue(r[0]),
                                        toStringValue(r[1]),
                                        toStringValue(r[2]),
                                        toStringValue(r[3])));
                }

                return result;
        }

        private String toStringValue(Object value) {
                return value == null ? "" : String.valueOf(value);
        }

        private boolean isValid(String value) {
                return value != null
                                && !value.trim().isEmpty()
                                && !"null".equalsIgnoreCase(value.trim())
                                && !"undefined".equalsIgnoreCase(value.trim())
                                && !"{}".equals(value.trim())
                                && !"-".equals(value.trim());
        }

        private String buildFunctionalLocationText(String... labels) {
                StringBuilder builder = new StringBuilder();

                for (String label : labels) {
                        if (isValid(label)) {
                                if (builder.length() > 0) {
                                        builder.append(" / ");
                                }

                                builder.append(label.trim());
                        }
                }

                if (builder.length() == 0) {
                        return "Company /";
                }

                return builder.append(" /").toString();
        }

        public List<DropDownDto> getEmployeeComboList(CommonFilterDto commonFilterDto) 
        
        {

                ComboFilterDto comboFilter = new ComboFilterDto();
                List<Object> params = new ArrayList<>();

                /*
                 * Employee dropdown configuration
                 */

                comboFilter.setTableName("GEN_TL_EMPLOYEEMST E");

                comboFilter.setIdField("E.EMPM_KEYID");

                comboFilter.setNameField("TRIM(E.EMPM_NAME)");

                comboFilter.setCodeField("TRIM(E.EMPM_CODE)");

                comboFilter.setOrderByField("LABEL");

                /*
                 * Base employee condition
                 */

                comboFilter.setCondSql("""
                                        AND E.EMPM_ACTIVE = 'Y'
                                """);

                if ((ValidationUtil.isValidKeyId(commonFilterDto.getCellId())) && "N".equals(commonFilterDto.getAbnmOthers()) ) 
                {

                        comboFilter.setCondSql("""
                                        AND E.EMPM_KEYID IN (
                                                SELECT DISTINCT EMPM_KEYID
                                                FROM
                                                   GEN_TL_EMPLOYEEMST,
                                                   GEN_TL_FNLNROLETEAM
                                                   WHERE FRT_EMPM_KEYID = EMPM_KEYID
                                                AND
                                                FRT_FNLN_KEYID IN (
                                                                SELECT FLID
                                                                FROM GEN_MV_FLIDHIERARCHY
                                                                WHERE FNLN_ORIGINALID = ?
                                                        )
                                                )
                                        """);
                        params.add(commonFilterDto.getCellId());
                }
                        
                else if ((ValidationUtil.isValidKeyId(commonFilterDto.getCellId()))&&  "Y".equals(commonFilterDto.getAbnmOthers()))
                {
                        comboFilter.setCondSql("""
                                        AND E.EMPM_KEYID IN (
                                                SELECT DISTINCT EMPM_KEYID
                                                FROM
                                                   GEN_TL_EMPLOYEEMST,
                                                   GEN_TL_FNLNROLETEAM
                                                   WHERE FRT_EMPM_KEYID = EMPM_KEYID
                                                AND
                                                FRT_FNLN_KEYID IN (
                                                                SELECT FLID
                                                                FROM GEN_MV_FLIDHIERARCHY
                                                                WHERE FNLN_ORIGINALID <> ?
                                                        )
                                                )
                                        """);
                        // comboFilter.getSqlParams().put("cellId",commonFilterDto.getCellId()
                        params.add(commonFilterDto.getCellId());
                                

                }       

                

        return repository.fillComboValues(comboFilter,params.toArray());

        }

        public List<DropDownDto> getAbnSubTypeComboList(CommonFilterDto commonFilterDto) {

                ComboFilterDto comboFilter = new ComboFilterDto();
                List<Object> params = new ArrayList<>();

                /*
                 * Employee dropdown configuration
                 */

                comboFilter.setTableName("ABN_TL_HTASOCMST");

                comboFilter.setIdField("AHSM_KEYID");

                comboFilter.setNameField("TRIM(AHSM_NAME)");

                comboFilter.setCodeField("TRIM(AHSM_CODE)");

                comboFilter.setOrderByField("LABEL");

                /*
                 * Base employee condition
                 */

                

                if (ValidationUtil.isValidKeyId(commonFilterDto.getAbnormalityTypeId())) {

                        comboFilter.setCondSql("""
                                        AND AHSM_ABNORMALITYTYPE = ?
                                        """);

                        // comboFilter.getSqlParams().put("cellId",commonFilterDto.getCellId()
                        params.add(commonFilterDto.getAbnormalityTypeId());

                }

                return repository.fillComboValues(comboFilter, params.toArray());
        }
}
