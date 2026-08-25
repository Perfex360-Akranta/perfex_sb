package com.akranta.perfex_sb.service;

import java.util.List;
import java.util.Map;


import com.akranta.perfex_sb.model.AdmTlUserRoleLink;
import com.akranta.perfex_sb.model.AdmTlUsermst;

public interface UserCreationService {
    AdmTlUsermst saveUser(AdmTlUsermst usermst) throws Exception;

    public List<Map<String, Object>> findMultipleRolesByUserId(String userId);

    public List<Map<String, Object>> findRolesByUserId(String userId);

    AdmTlUserRoleLink saveUserRoll(AdmTlUserRoleLink userRoleLink);

    AdmTlUsermst userRecall(String userKeyId) throws Exception;

    public void deleteRole(String userId, String roleId);

    AdmTlUserRoleLink updateUserRole(AdmTlUserRoleLink userRoleLink);

}
