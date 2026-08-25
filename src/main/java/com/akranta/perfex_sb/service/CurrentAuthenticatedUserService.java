package com.akranta.perfex_sb.service;

import com.akranta.perfex_sb.model.AdmTlUsermst;
import com.akranta.perfex_sb.repository.AdmTlUsermstRepository;
import com.akranta.perfex_sb.security.PerfexAuthenticationDetails;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

/**
 * Resolves the current Perfex user from EMPM_KEYID contained
 * in the existing JWT "id" claim.
 */
@Service
public class CurrentAuthenticatedUserService {

    private final AdmTlUsermstRepository admTlUsermstRepository;

    public CurrentAuthenticatedUserService(
            AdmTlUsermstRepository admTlUsermstRepository) {

        this.admTlUsermstRepository = admTlUsermstRepository;
    }

    public AdmTlUsermst getRequiredCurrentUser() {

        String employeeKeyId = getRequiredEmployeeKeyId();

        List<AdmTlUsermst> users = admTlUsermstRepository
                .findActiveUsersByEmployeeKeyId(
                        employeeKeyId);

        if (users == null || users.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED,
                    "No active Perfex user is mapped to the authenticated employee.");
        }

        if (users.size() > 1) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Multiple active Perfex users are mapped to the authenticated employee.");
        }

        AdmTlUsermst user = users.get(0);

        if (user == null ||
                clean(user.getKeyid()).isBlank()) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED,
                    "Authenticated Perfex user key is unavailable.");
        }

        return user;
    }

    public String getRequiredUserKeyId() {

        return clean(
                getRequiredCurrentUser()
                        .getKeyid());
    }

    public String getRequiredEmployeeKeyId() {

        Authentication authentication = getRequiredAuthentication();

        Object details = authentication.getDetails();

        if (!(details instanceof PerfexAuthenticationDetails perfexDetails)) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED,
                    "Employee identity is unavailable in the authenticated request.");
        }

        String employeeKeyId = clean(
                perfexDetails.getEmployeeKeyId());

        if (employeeKeyId.isBlank()) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED,
                    "Authenticated employee key ID is unavailable.");
        }

        return employeeKeyId;
    }

    private Authentication getRequiredAuthentication() {

        Authentication authentication = SecurityContextHolder
                .getContext()
                .getAuthentication();

        if (authentication == null ||
                !authentication.isAuthenticated() ||
                authentication instanceof AnonymousAuthenticationToken) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED,
                    "Authentication is required.");
        }

        return authentication;
    }

    private static String clean(String value) {

        return value == null
                ? ""
                : value.trim();
    }
}