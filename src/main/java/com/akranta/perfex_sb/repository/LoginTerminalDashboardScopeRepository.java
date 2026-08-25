package com.akranta.perfex_sb.repository;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

/**
 * Resolves the dashboard root permitted for a registered browser.
 *
 * Rules:
 * - administrator user -> company root (all locations)
 * - other users         -> FLID attached to their current highest role
 */
@Repository
public class LoginTerminalDashboardScopeRepository {

    private static final Pattern SHA_256_HASH_PATTERN = Pattern.compile(
            "^[0-9a-f]{64}$");

    private static final String FIND_SCOPE_SQL = """
            WITH company_root AS
            (
                SELECT
                    node.fnln_keyid AS root_flid,
                    COALESCE(BTRIM(node.displaycode), '') AS root_level_code,
                    COALESCE(
                        NULLIF(BTRIM(hierarchy.fnln_description), ''),
                        NULLIF(BTRIM(node.functionalloc), ''),
                        'All Locations'
                    ) AS root_label,
                    COALESCE(BTRIM(hierarchy.allparents), '') AS root_path

                FROM public.gen_vw_fnln node

                LEFT JOIN public.gen_mv_flidhierarchy hierarchy
                  ON hierarchy.flid = node.fnln_keyid

                WHERE UPPER(BTRIM(node.displaycode)) = 'COMP'

                ORDER BY node.fnln_keyid
                LIMIT 1
            )
            SELECT
                breg.breg_userid AS user_key_id,
                COALESCE(BTRIM(usrm.usrm_ccno), '') AS employee_key_id,
                COALESCE(BTRIM(usrm.usrm_username), '') AS user_name,
                COALESCE(BTRIM(breg.breg_terminalname), '') AS terminal_name,

                CASE
                    WHEN COALESCE(BTRIM(usrm.usrm_isadministartor), 'N') = 'Y'
                    THEN TRUE
                    ELSE FALSE
                END AS administrator,

                COALESCE(role_data.role_id, '') AS role_id,
                COALESCE(role_data.role_code, '') AS role_code,
                COALESCE(role_data.role_name, '') AS role_name,
                role_data.role_level,

                COALESCE(role_data.role_flid, '') AS role_flid,
                COALESCE(role_data.role_level_code, '') AS role_level_code,
                COALESCE(role_data.role_scope_label, '') AS role_scope_label,
                COALESCE(role_data.role_scope_path, '') AS role_scope_path,

                COALESCE(company_root.root_flid, '') AS company_root_flid,
                COALESCE(company_root.root_level_code, '') AS company_root_level_code,
                COALESCE(company_root.root_label, 'All Locations') AS company_root_label,
                COALESCE(company_root.root_path, '') AS company_root_path

            FROM public.adm_tl_browser_registration breg

            JOIN public.adm_tl_usermst usrm
              ON usrm.usrm_keyid = breg.breg_userid
             AND COALESCE(BTRIM(usrm.usrm_isactive), 'N') = 'Y'

            LEFT JOIN LATERAL
            (
                SELECT
                    COALESCE(BTRIM(role_order.role_keyid), '') AS role_id,
                    COALESCE(BTRIM(role_order.role_code), '') AS role_code,
                    COALESCE(BTRIM(role_order.role_name), '') AS role_name,
                    role_order.role_level,

                    COALESCE(BTRIM(final_role_team.frt_fnln_keyid), '') AS role_flid,
                    COALESCE(BTRIM(scope_node.displaycode), '') AS role_level_code,
                    COALESCE(
                        NULLIF(BTRIM(hierarchy.fnln_description), ''),
                        NULLIF(BTRIM(scope_node.functionalloc), ''),
                        NULLIF(BTRIM(hierarchy.fnln_displaycode), ''),
                        ''
                    ) AS role_scope_label,
                    COALESCE(BTRIM(hierarchy.allparents), '') AS role_scope_path

                FROM public.gen_tl_fnlnroleteam final_role_team

                JOIN public.adm_tl_roleorder role_order
                  ON role_order.role_keyid = final_role_team.frt_role_keyid

                LEFT JOIN public.gen_vw_fnln scope_node
                  ON scope_node.fnln_keyid = final_role_team.frt_fnln_keyid

                LEFT JOIN public.gen_mv_flidhierarchy hierarchy
                  ON hierarchy.flid = final_role_team.frt_fnln_keyid

                WHERE final_role_team.frt_empm_keyid = usrm.usrm_ccno
                  AND COALESCE(BTRIM(final_role_team.frt_active), 'Y') = 'Y'
                  AND COALESCE(BTRIM(role_order.role_active), 'Y') = 'Y'

                ORDER BY
                    role_order.role_level ASC NULLS LAST,
                    role_order.role_name ASC,
                    COALESCE(scope_node.displaycode, '') ASC,
                    role_order.role_keyid ASC

                LIMIT 1
            ) role_data ON TRUE

            LEFT JOIN company_root ON TRUE

            WHERE breg.breg_browser_tokenhash = :browserTokenHash
              AND breg.breg_active = 'Y'

            LIMIT 1
            """;

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public LoginTerminalDashboardScopeRepository(
            NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Optional<LoginTerminalDashboardScopeRecord> findByBrowserTokenHash(
            String browserTokenHash) {

        String normalizedHash = clean(browserTokenHash).toLowerCase();

        if (!SHA_256_HASH_PATTERN.matcher(normalizedHash).matches()) {
            return Optional.empty();
        }

        MapSqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("browserTokenHash", normalizedHash);

        List<LoginTerminalDashboardScopeRecord> rows = jdbcTemplate.query(
                FIND_SCOPE_SQL,
                parameters,
                (resultSet, rowNumber) -> mapRecord(resultSet));

        return rows.stream().findFirst();
    }

    private static LoginTerminalDashboardScopeRecord mapRecord(
            ResultSet resultSet) throws SQLException {

        return new LoginTerminalDashboardScopeRecord(
                clean(resultSet.getString("user_key_id")),
                clean(resultSet.getString("employee_key_id")),
                clean(resultSet.getString("user_name")),
                clean(resultSet.getString("terminal_name")),
                resultSet.getBoolean("administrator"),
                clean(resultSet.getString("role_id")),
                clean(resultSet.getString("role_code")),
                clean(resultSet.getString("role_name")),
                nullableInteger(resultSet, "role_level"),
                clean(resultSet.getString("role_flid")),
                clean(resultSet.getString("role_level_code")),
                clean(resultSet.getString("role_scope_label")),
                clean(resultSet.getString("role_scope_path")),
                clean(resultSet.getString("company_root_flid")),
                clean(resultSet.getString("company_root_level_code")),
                clean(resultSet.getString("company_root_label")),
                clean(resultSet.getString("company_root_path")));
    }

    private static Integer nullableInteger(
            ResultSet resultSet,
            String columnName) throws SQLException {

        int value = resultSet.getInt(columnName);
        return resultSet.wasNull() ? null : value;
    }

    private static String clean(String value) {
        return value == null ? "" : value.trim();
    }

    public record LoginTerminalDashboardScopeRecord(
            String userKeyId,
            String employeeKeyId,
            String userName,
            String terminalName,
            boolean administrator,
            String roleId,
            String roleCode,
            String roleName,
            Integer roleLevel,
            String roleFlid,
            String roleLevelCode,
            String roleScopeLabel,
            String roleScopePath,
            String companyRootFlid,
            String companyRootLevelCode,
            String companyRootLabel,
            String companyRootPath) {
    }
}
