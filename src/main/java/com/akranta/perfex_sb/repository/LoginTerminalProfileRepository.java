package com.akranta.perfex_sb.repository;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

/**
 * Read-only repository for the public login-page browser context.
 *
 * The unique browser-token hash index makes the initial registration lookup a
 * direct indexed read. No IP lookup and no last-seen update are performed.
 */
@Repository
public class LoginTerminalProfileRepository {

    private static final Pattern SHA_256_HASH_PATTERN = Pattern.compile(
            "^[0-9a-f]{64}$");

    private static final String FIND_ACTIVE_PROFILE_BY_BROWSER_HASH_SQL = """
            SELECT
                breg.breg_userid AS user_key_id,
                COALESCE(BTRIM(usrm.usrm_ccno), '') AS employee_key_id,
                COALESCE(BTRIM(usrm.usrm_username), '') AS user_name,
                COALESCE(BTRIM(usrm.usrm_loginid), '') AS login_id,
                COALESCE(BTRIM(breg.breg_terminalname), '') AS terminal_name,
                breg.breg_createdon AS registered_on,

                COALESCE(role_data.role_id, '') AS role_id,
                COALESCE(role_data.role_code, '') AS role_code,
                COALESCE(role_data.role_name, '') AS role_name,
                role_data.role_level,

                COALESCE(role_data.flid, '') AS flid,
                COALESCE(role_data.original_id, '') AS original_id,
                COALESCE(role_data.fnln_display_code, '') AS fnln_display_code,
                COALESCE(role_data.fnln_description, '') AS fnln_description,
                COALESCE(role_data.parent_flids, '') AS parent_flids,
                COALESCE(role_data.all_parents, '') AS all_parents,
                COALESCE(role_data.parents, '') AS parents,
                COALESCE(role_data.element_type, '') AS element_type

            FROM public.adm_tl_browser_registration breg

            JOIN public.adm_tl_usermst usrm
              ON usrm.usrm_keyid = breg.breg_userid
             AND COALESCE(BTRIM(usrm.usrm_isactive), 'N') = 'Y'

            LEFT JOIN LATERAL
            (
                SELECT
                    BTRIM(role_order.role_keyid) AS role_id,
                    COALESCE(BTRIM(role_order.role_code), '') AS role_code,
                    COALESCE(BTRIM(role_order.role_name), '') AS role_name,
                    role_order.role_level,

                    COALESCE(BTRIM(final_role_team.frt_fnln_keyid), '') AS flid,
                    COALESCE(BTRIM(hierarchy.fnln_originalid), '') AS original_id,
                    COALESCE(BTRIM(hierarchy.fnln_displaycode), '') AS fnln_display_code,
                    COALESCE(BTRIM(hierarchy.fnln_description), '') AS fnln_description,
                    COALESCE(BTRIM(hierarchy.parentflids), '') AS parent_flids,
                    COALESCE(BTRIM(hierarchy.allparents), '') AS all_parents,
                    COALESCE(BTRIM(hierarchy.parents), '') AS parents,
                    COALESCE(BTRIM(hierarchy.fnln_elementtype), '') AS element_type

                FROM public.gen_tl_fnlnroleteam final_role_team

                JOIN public.adm_tl_roleorder role_order
                  ON role_order.role_keyid = final_role_team.frt_role_keyid

                LEFT JOIN public.gen_mv_flidhierarchy hierarchy
                  ON hierarchy.flid = final_role_team.frt_fnln_keyid

                WHERE final_role_team.frt_empm_keyid = usrm.usrm_ccno
                  AND COALESCE(BTRIM(final_role_team.frt_active), 'Y') = 'Y'
                  AND COALESCE(BTRIM(role_order.role_active), 'Y') = 'Y'

                ORDER BY
                    role_order.role_level ASC NULLS LAST,
                    role_order.role_name ASC,
                    COALESCE(hierarchy.fnln_displaycode, '') ASC,
                    role_order.role_keyid ASC

                LIMIT 1
            ) role_data ON TRUE

            WHERE breg.breg_browser_tokenhash = :browserTokenHash
              AND breg.breg_active = 'Y'

            LIMIT 1
            """;

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public LoginTerminalProfileRepository(
            NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Optional<LoginTerminalProfileRecord> findActiveByBrowserTokenHash(
            String browserTokenHash) {

        String normalizedHash = clean(browserTokenHash).toLowerCase();

        if (!SHA_256_HASH_PATTERN.matcher(normalizedHash).matches()) {
            return Optional.empty();
        }

        MapSqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("browserTokenHash", normalizedHash);

        List<LoginTerminalProfileRecord> rows = jdbcTemplate.query(
                FIND_ACTIVE_PROFILE_BY_BROWSER_HASH_SQL,
                parameters,
                (resultSet, rowNumber) -> mapRecord(resultSet));

        return rows.stream().findFirst();
    }

    private static LoginTerminalProfileRecord mapRecord(ResultSet resultSet)
            throws SQLException {

        return new LoginTerminalProfileRecord(
                clean(resultSet.getString("user_key_id")),
                clean(resultSet.getString("employee_key_id")),
                clean(resultSet.getString("user_name")),
                clean(resultSet.getString("login_id")),
                clean(resultSet.getString("terminal_name")),
                resultSet.getObject("registered_on", LocalDateTime.class),
                clean(resultSet.getString("role_id")),
                clean(resultSet.getString("role_code")),
                clean(resultSet.getString("role_name")),
                getNullableInteger(resultSet, "role_level"),
                clean(resultSet.getString("flid")),
                clean(resultSet.getString("original_id")),
                clean(resultSet.getString("fnln_display_code")),
                clean(resultSet.getString("fnln_description")),
                clean(resultSet.getString("parent_flids")),
                clean(resultSet.getString("all_parents")),
                clean(resultSet.getString("parents")),
                clean(resultSet.getString("element_type")));
    }

    private static Integer getNullableInteger(
            ResultSet resultSet,
            String columnName) throws SQLException {

        int value = resultSet.getInt(columnName);
        return resultSet.wasNull() ? null : value;
    }

    private static String clean(String value) {
        return value == null ? "" : value.trim();
    }

    public record LoginTerminalProfileRecord(
            String userKeyId,
            String employeeKeyId,
            String userName,
            String loginId,
            String terminalName,
            LocalDateTime registeredOn,
            String roleId,
            String roleCode,
            String roleName,
            Integer roleLevel,
            String flid,
            String originalId,
            String fnlnDisplayCode,
            String fnlnDescription,
            String parentFlids,
            String allParents,
            String parents,
            String elementType) {
    }
}
