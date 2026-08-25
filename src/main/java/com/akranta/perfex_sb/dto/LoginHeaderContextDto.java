package com.akranta.perfex_sb.dto;

import java.util.List;

public record LoginHeaderContextDto(
        LoginHeaderUserDto user,
        LoginHeaderRoleDto activeRole,
        List<LoginHeaderRoleDto> roles) {

    public record LoginHeaderUserDto(
            String userKeyId,
            String username,
            String loginId,
            String employeeId) {
    }

    public record LoginHeaderRoleDto(
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