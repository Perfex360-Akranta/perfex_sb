package com.akranta.perfex_sb.security;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

/**
 * Additional identity information obtained from the already validated JWT.
 *
 * employeeKeyId corresponds to:
 * GEN_TL_EMPLOYEEMST.EMPM_KEYID
 */
public class PerfexAuthenticationDetails
        extends WebAuthenticationDetails {

    private final String employeeKeyId;

    public PerfexAuthenticationDetails(
            HttpServletRequest request,
            String employeeKeyId) {

        super(request);

        this.employeeKeyId = employeeKeyId == null
                ? ""
                : employeeKeyId.trim();
    }

    public String getEmployeeKeyId() {
        return employeeKeyId;
    }
}