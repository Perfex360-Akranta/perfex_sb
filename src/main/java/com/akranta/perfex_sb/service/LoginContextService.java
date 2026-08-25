package com.akranta.perfex_sb.service;

import com.akranta.perfex_sb.dto.LoginHeaderContextDto;
import com.akranta.perfex_sb.dto.LoginHeaderContextDto.LoginHeaderRoleDto;
import com.akranta.perfex_sb.dto.LoginHeaderContextDto.LoginHeaderUserDto;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LoginContextService {

    @PersistenceContext
    private EntityManager entityManager;

    public LoginHeaderContextDto getLoginHeaderContext(String userKeyId) {

        LoginHeaderUserDto user = getUserInfo(userKeyId);

        if (user == null) {
            return new LoginHeaderContextDto(
                    null,
                    null,
                    List.of());
        }

        List<LoginHeaderRoleDto> roles = getUserRoles(user.employeeId());

        LoginHeaderRoleDto activeRole = roles.isEmpty()
                ? null
                : roles.get(0);

        return new LoginHeaderContextDto(
                user,
                activeRole,
                roles);
    }

    private LoginHeaderUserDto getUserInfo(String userKeyId) {

        String sql = """
                SELECT
                       USRM_KEYID,
                       USRM_USERNAME,
                       USRM_LOGINID,
                       USRM_CCNO
                FROM ADM_TL_USERMST
                WHERE USRM_KEYID = :userKeyId
                LIMIT 1
                """;

        Query query = entityManager.createNativeQuery(sql);
        query.setParameter("userKeyId", userKeyId);

        List<Object[]> rows = query.getResultList();

        if (rows == null || rows.isEmpty()) {
            return null;
        }

        Object[] r = rows.get(0);

        return new LoginHeaderUserDto(
                toStringValue(r[0]),
                toStringValue(r[1]),
                toStringValue(r[2]),
                toStringValue(r[3]));
    }

    private List<LoginHeaderRoleDto> getUserRoles(String employeeId) {

        if (!isValid(employeeId)) {
            return List.of();
        }

        String sql = """
                SELECT DISTINCT
                       ro.ROLE_KEYID,
                       ro.ROLE_CODE,
                       ro.ROLE_NAME,
                       ro.ROLE_LEVEL,

                       frt.FRT_FNLN_KEYID,

                       COALESCE(h.FNLN_ORIGINALID, ''),
                       COALESCE(h.FNLN_DISPLAYCODE, ''),
                       COALESCE(h.FNLN_DESCRIPTION, ''),
                       COALESCE(h.PARENTFLIDS, ''),
                       COALESCE(h.ALLPARENTS, ''),
                       COALESCE(h.PARENTS, ''),
                       COALESCE(h.FNLN_ELEMENTTYPE, '')

                FROM GEN_TL_FNLNROLETEAM frt
                JOIN ADM_TL_ROLEORDER ro
                  ON ro.ROLE_KEYID = frt.FRT_ROLE_KEYID

                LEFT JOIN GEN_MV_FLIDHIERARCHY h
                  ON h.FLID = frt.FRT_FNLN_KEYID

                WHERE frt.FRT_EMPM_KEYID = :employeeId
                  AND COALESCE(TRIM(frt.FRT_ACTIVE), 'Y') = 'Y'
                  AND COALESCE(TRIM(ro.ROLE_ACTIVE), 'Y') = 'Y'

                ORDER BY
                       ro.ROLE_LEVEL ASC,
                       ro.ROLE_NAME ASC,
                       COALESCE(h.FNLN_DISPLAYCODE, '') ASC
                """;

        Query query = entityManager.createNativeQuery(sql);
        query.setParameter("employeeId", employeeId);

        List<Object[]> rows = query.getResultList();

        List<LoginHeaderRoleDto> result = new ArrayList<>();

        for (Object[] r : rows) {
            result.add(new LoginHeaderRoleDto(
                    toStringValue(r[0]),
                    toStringValue(r[1]),
                    toStringValue(r[2]),
                    toIntegerValue(r[3]),
                    toStringValue(r[4]),
                    toStringValue(r[5]),
                    toStringValue(r[6]),
                    toStringValue(r[7]),
                    toStringValue(r[8]),
                    toStringValue(r[9]),
                    toStringValue(r[10]),
                    toStringValue(r[11])));
        }

        return result;
    }

    private String toStringValue(Object value) {
        return value == null ? "" : String.valueOf(value).trim();
    }

    private Integer toIntegerValue(Object value) {
        if (value == null) {
            return null;
        }

        try {
            return Integer.parseInt(String.valueOf(value).trim());
        } catch (Exception e) {
            return null;
        }
    }

    private boolean isValid(String value) {
        return value != null
                && !value.trim().isEmpty()
                && !"null".equalsIgnoreCase(value.trim())
                && !"undefined".equalsIgnoreCase(value.trim())
                && !"-".equals(value.trim());
    }
}