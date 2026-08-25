package com.akranta.perfex_sb.repository;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.time.LocalDateTime;
import java.util.List;
import java.util.regex.Pattern;

/**
 * JDBC repository for the simplified one-row-per-user browser
 * registration model.
 *
 * This repository intentionally does not use:
 *
 * - a JPA entity;
 * - DBActionTemplate;
 * - terminal sequence generators;
 * - the old terminal registration tables;
 * - the terminal-role mapping table.
 */
@Repository
public class BrowserRegistrationRepository {

    private static final Pattern SHA_256_HASH_PATTERN = Pattern.compile(
            "^[0-9a-f]{64}$");

    /**
     * Resolves all information needed by GET /state in one database
     * operation:
     *
     * 1. active Perfex user mapped to the JWT employee key;
     * 2. active simplified browser registration for that user;
     * 3. whether the supplied browser-cookie hash matches;
     * 4. the employee's current highest active role.
     *
     * Direct equality is used for USRM_CCNO and FRT_EMPM_KEYID so
     * PostgreSQL can use their normal indexes. The application trims
     * the JWT employee key before this query is called.
     */
    private static final String FIND_STATE_SQL = """
            SELECT
                usrm.usrm_keyid AS user_key_id,
                usrm.usrm_ccno AS employee_key_id,
                COALESCE(BTRIM(usrm.usrm_username), '') AS user_name,
                COALESCE(BTRIM(usrm.usrm_loginid), '') AS login_id,

                CASE
                    WHEN breg.breg_userid IS NOT NULL THEN TRUE
                    ELSE FALSE
                END AS registration_exists,

                CASE
                    WHEN breg.breg_userid IS NOT NULL
                     AND :browserTokenHash IS NOT NULL
                     AND breg.breg_browser_tokenhash = :browserTokenHash
                    THEN TRUE
                    ELSE FALSE
                END AS current_browser_registered,

                COALESCE(BTRIM(breg.breg_terminalname), '') AS terminal_name,
                breg.breg_createdon AS registered_on,

                COALESCE(role_data.role_keyid, '') AS role_id,
                COALESCE(role_data.role_code, '') AS role_code,
                COALESCE(role_data.role_name, '') AS role_name,
                role_data.role_level

            FROM public.adm_tl_usermst usrm

            LEFT JOIN public.adm_tl_browser_registration breg
              ON breg.breg_userid = usrm.usrm_keyid
             AND breg.breg_active = 'Y'

            LEFT JOIN LATERAL
            (
                SELECT
                    BTRIM(role_order.role_keyid) AS role_keyid,
                    COALESCE(BTRIM(role_order.role_code), '') AS role_code,
                    COALESCE(BTRIM(role_order.role_name), '') AS role_name,
                    role_order.role_level

                FROM public.gen_tl_fnlnroleteam final_role_team

                JOIN public.adm_tl_roleorder role_order
                  ON role_order.role_keyid = final_role_team.frt_role_keyid

                WHERE final_role_team.frt_empm_keyid = usrm.usrm_ccno

                  AND COALESCE(
                        BTRIM(final_role_team.frt_active),
                        'Y'
                      ) = 'Y'

                  AND COALESCE(
                        BTRIM(role_order.role_active),
                        'Y'
                      ) = 'Y'

                ORDER BY
                    role_order.role_level ASC NULLS LAST,
                    role_order.role_name ASC,
                    role_order.role_keyid ASC

                LIMIT 1
            ) role_data ON TRUE

            WHERE usrm.usrm_ccno = :employeeKeyId

              AND COALESCE(
                    BTRIM(usrm.usrm_isactive),
                    'N'
                  ) = 'Y'

            ORDER BY usrm.usrm_keyid
            """;

    /**
     * Minimal registration lookup used by POST /register-v2.
     *
     * It deliberately does not resolve roles because role information is
     * not required to create the browser registration.
     */
    private static final String FIND_REGISTRATION_TARGET_SQL = """
            SELECT
                usrm.usrm_keyid AS user_key_id,
                usrm.usrm_ccno AS employee_key_id,

                CASE
                    WHEN breg.breg_userid IS NOT NULL THEN TRUE
                    ELSE FALSE
                END AS active_registration_exists

            FROM public.adm_tl_usermst usrm

            LEFT JOIN public.adm_tl_browser_registration breg
              ON breg.breg_userid = usrm.usrm_keyid
             AND breg.breg_active = 'Y'

            WHERE usrm.usrm_ccno = :employeeKeyId

              AND COALESCE(
                    BTRIM(usrm.usrm_isactive),
                    'N'
                  ) = 'Y'

            ORDER BY usrm.usrm_keyid
            """;

    /**
     * One-row-per-user conditional UPSERT.
     *
     * When the row exists but is inactive, the old credentials are
     * replaced and the row is reactivated. When an active row already
     * exists, the WHERE clause prevents the update and JDBC reports zero
     * affected rows.
     */
    private static final String INSERT_OR_REACTIVATE_SQL = """
            INSERT INTO public.adm_tl_browser_registration
            (
                breg_userid,
                breg_terminalname,
                breg_browser_tokenhash,
                breg_recovery_tokenhash,
                breg_active,
                breg_createdon,
                breg_modifiedon
            )
            VALUES
            (
                :userKeyId,
                :terminalName,
                :browserTokenHash,
                :recoveryTokenHash,
                'Y',
                CURRENT_TIMESTAMP,
                CURRENT_TIMESTAMP
            )
            ON CONFLICT (breg_userid)
            DO UPDATE
            SET
                breg_terminalname = EXCLUDED.breg_terminalname,
                breg_browser_tokenhash = EXCLUDED.breg_browser_tokenhash,
                breg_recovery_tokenhash = EXCLUDED.breg_recovery_tokenhash,
                breg_active = 'Y',
                breg_createdon = CURRENT_TIMESTAMP,
                breg_modifiedon = CURRENT_TIMESTAMP
            WHERE adm_tl_browser_registration.breg_active = 'N'
            """;


    /**
     * Replaces only the browser credential during recovery.
     *
     * The statement succeeds only when all of the following are true:
     *
     * 1. the uploaded file's user key matches the registration row;
     * 2. that user is the active Perfex user mapped to the JWT employee key;
     * 3. the registration is active;
     * 4. the uploaded recovery token matches the stored recovery-token hash.
     *
     * BREG_RECOVERY_TOKENHASH is intentionally not changed, so the same
     * encrypted backup file remains reusable.
     */
    private static final String ROTATE_BROWSER_TOKEN_DURING_RECOVERY_SQL = """
            UPDATE public.adm_tl_browser_registration breg
            SET
                breg_browser_tokenhash = :newBrowserTokenHash,
                breg_modifiedon = CURRENT_TIMESTAMP
            WHERE breg.breg_userid = :payloadUserKeyId
              AND breg.breg_recovery_tokenhash = :recoveryTokenHash
              AND breg.breg_active = 'Y'
              AND EXISTS
              (
                  SELECT 1
                  FROM public.adm_tl_usermst usrm
                  WHERE usrm.usrm_keyid = breg.breg_userid
                    AND usrm.usrm_ccno = :employeeKeyId
                    AND COALESCE(
                            BTRIM(usrm.usrm_isactive),
                            'N'
                        ) = 'Y'
              )
            """;

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public BrowserRegistrationRepository(
            NamedParameterJdbcTemplate jdbcTemplate) {

        this.jdbcTemplate = jdbcTemplate;
    }

    public List<BrowserRegistrationStateRecord> findStateByEmployeeKeyId(
            String employeeKeyId,
            String browserTokenHash) {

        String normalizedEmployeeKeyId = clean(employeeKeyId);

        if (normalizedEmployeeKeyId.isBlank()) {
            throw new IllegalArgumentException(
                    "Employee key ID is required.");
        }

        String normalizedBrowserTokenHash = normalizeOptionalHash(
                browserTokenHash);

        MapSqlParameterSource parameters = new MapSqlParameterSource()
                .addValue(
                        "employeeKeyId",
                        normalizedEmployeeKeyId)
                .addValue(
                        "browserTokenHash",
                        normalizedBrowserTokenHash,
                        Types.VARCHAR);

        return jdbcTemplate.query(
                FIND_STATE_SQL,
                parameters,
                (resultSet, rowNumber) -> mapState(resultSet));
    }

    public List<BrowserRegistrationTargetRecord> findRegistrationTargetByEmployeeKeyId(
            String employeeKeyId) {

        String normalizedEmployeeKeyId = clean(employeeKeyId);

        if (normalizedEmployeeKeyId.isBlank()) {
            throw new IllegalArgumentException(
                    "Employee key ID is required.");
        }

        MapSqlParameterSource parameters = new MapSqlParameterSource()
                .addValue(
                        "employeeKeyId",
                        normalizedEmployeeKeyId);

        return jdbcTemplate.query(
                FIND_REGISTRATION_TARGET_SQL,
                parameters,
                (resultSet, rowNumber) -> new BrowserRegistrationTargetRecord(
                        clean(resultSet.getString("user_key_id")),
                        clean(resultSet.getString("employee_key_id")),
                        resultSet.getBoolean("active_registration_exists")));
    }

    public int insertOrReactivateRegistration(
            String userKeyId,
            String terminalName,
            String browserTokenHash,
            String recoveryTokenHash) {

        String normalizedUserKeyId = clean(userKeyId);
        String normalizedTerminalName = clean(terminalName);
        String normalizedBrowserTokenHash = requireHash(
                browserTokenHash,
                "Browser token hash");
        String normalizedRecoveryTokenHash = requireHash(
                recoveryTokenHash,
                "Recovery token hash");

        if (normalizedUserKeyId.isBlank()) {
            throw new IllegalArgumentException(
                    "User key ID is required.");
        }

        if (normalizedTerminalName.length() < 3
                || normalizedTerminalName.length() > 150) {
            throw new IllegalArgumentException(
                    "Browser name must contain between 3 and 150 characters.");
        }

        MapSqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("userKeyId", normalizedUserKeyId)
                .addValue("terminalName", normalizedTerminalName)
                .addValue("browserTokenHash", normalizedBrowserTokenHash)
                .addValue("recoveryTokenHash", normalizedRecoveryTokenHash);

        return jdbcTemplate.update(
                INSERT_OR_REACTIVATE_SQL,
                parameters);
    }


    public int rotateBrowserTokenDuringRecovery(
            String employeeKeyId,
            String payloadUserKeyId,
            String recoveryTokenHash,
            String newBrowserTokenHash) {

        String normalizedEmployeeKeyId = clean(employeeKeyId);
        String normalizedPayloadUserKeyId = clean(payloadUserKeyId);
        String normalizedRecoveryTokenHash = requireHash(
                recoveryTokenHash,
                "Recovery token hash");
        String normalizedNewBrowserTokenHash = requireHash(
                newBrowserTokenHash,
                "New browser token hash");

        if (normalizedEmployeeKeyId.isBlank()) {
            throw new IllegalArgumentException(
                    "Employee key ID is required.");
        }

        if (normalizedPayloadUserKeyId.isBlank()) {
            throw new IllegalArgumentException(
                    "Recovery-file user key ID is required.");
        }

        MapSqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("employeeKeyId", normalizedEmployeeKeyId)
                .addValue("payloadUserKeyId", normalizedPayloadUserKeyId)
                .addValue("recoveryTokenHash", normalizedRecoveryTokenHash)
                .addValue("newBrowserTokenHash", normalizedNewBrowserTokenHash);

        return jdbcTemplate.update(
                ROTATE_BROWSER_TOKEN_DURING_RECOVERY_SQL,
                parameters);
    }

    private static BrowserRegistrationStateRecord mapState(
            ResultSet resultSet)
            throws SQLException {

        return new BrowserRegistrationStateRecord(
                clean(resultSet.getString("user_key_id")),
                clean(resultSet.getString("employee_key_id")),
                clean(resultSet.getString("user_name")),
                clean(resultSet.getString("login_id")),
                resultSet.getBoolean("registration_exists"),
                resultSet.getBoolean("current_browser_registered"),
                clean(resultSet.getString("terminal_name")),
                getNullableLocalDateTime(resultSet, "registered_on"),
                clean(resultSet.getString("role_id")),
                clean(resultSet.getString("role_code")),
                clean(resultSet.getString("role_name")),
                getNullableInteger(resultSet, "role_level"));
    }

    private static String requireHash(
            String tokenHash,
            String fieldName) {

        String normalizedHash = clean(tokenHash);

        if (!SHA_256_HASH_PATTERN
                .matcher(normalizedHash)
                .matches()) {
            throw new IllegalArgumentException(
                    fieldName
                            + " must contain exactly 64 lowercase hexadecimal characters.");
        }

        return normalizedHash;
    }

    private static String normalizeOptionalHash(
            String browserTokenHash) {

        String normalizedHash = clean(browserTokenHash);

        if (normalizedHash.isBlank()) {
            return null;
        }

        if (!SHA_256_HASH_PATTERN
                .matcher(normalizedHash)
                .matches()) {
            throw new IllegalArgumentException(
                    "Browser token hash must contain exactly "
                            + "64 lowercase hexadecimal characters.");
        }

        return normalizedHash;
    }

    private static Integer getNullableInteger(
            ResultSet resultSet,
            String columnName)
            throws SQLException {

        int value = resultSet.getInt(columnName);

        return resultSet.wasNull()
                ? null
                : value;
    }

    private static LocalDateTime getNullableLocalDateTime(
            ResultSet resultSet,
            String columnName)
            throws SQLException {

        java.sql.Timestamp timestamp = resultSet.getTimestamp(columnName);

        return timestamp == null
                ? null
                : timestamp.toLocalDateTime();
    }

    private static String clean(String value) {

        return value == null
                ? ""
                : value.trim();
    }

    public record BrowserRegistrationStateRecord(

            String userKeyId,

            String employeeKeyId,

            String userName,

            String loginId,

            boolean registrationExists,

            boolean currentBrowserRegistered,

            String terminalName,

            LocalDateTime registeredOn,

            String roleId,

            String roleCode,

            String roleName,

            Integer roleLevel) {
    }

    public record BrowserRegistrationTargetRecord(

            String userKeyId,

            String employeeKeyId,

            boolean activeRegistrationExists) {
    }
}
