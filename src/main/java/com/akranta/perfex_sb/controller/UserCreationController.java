package com.akranta.perfex_sb.controller;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import com.akranta.perfex_sb.model.AdmTlUserRoleLink;
import com.akranta.perfex_sb.model.AdmTlUsermst;
import com.akranta.perfex_sb.service.UserCreationService;

@RestController
@RequestMapping("/api/saveuser")
public class UserCreationController

{

    @Autowired
    private UserCreationService service;

    private static final Logger logger = LoggerFactory.getLogger(UserCreationController.class);

    @PostMapping
    public ResponseEntity<AdmTlUsermst> saveUser(@RequestBody AdmTlUsermst usermst) throws Exception {
        logger.info("Entered into controller");

        AdmTlUsermst userResult = service.saveUser(usermst);
        return ResponseEntity.ok(userResult);

    }

    @GetMapping("/singleRole")
    public ResponseEntity<List<Map<String, Object>>> findSingleRoleById(
            @RequestParam(value = "keyId", required = false) String userId) {
        List<Map<String, Object>> result = service.findRolesByUserId(userId);
        return ResponseEntity.ok(result);

    }

    @GetMapping("/multipleRole")
    public ResponseEntity<List<Map<String, Object>>> findMultipleRoles(
            @RequestParam(value = "keyId", required = false) String userId) {
        List<Map<String, Object>> result = service.findMultipleRolesByUserId(userId);
        return ResponseEntity.ok(result);

    }

    @PostMapping("/saveRole")
    public ResponseEntity<AdmTlUserRoleLink> saveRole(@RequestBody AdmTlUserRoleLink userRoleLink) throws Exception {
        logger.info("Entered into controller");

        AdmTlUserRoleLink userResult = service.saveUserRoll(userRoleLink);
        return ResponseEntity.ok(userResult);

    }

    @GetMapping("/userRecall")
    public ResponseEntity<AdmTlUsermst> userRecall(@RequestParam("keyId") String userKeyId) throws Exception {
        logger.info("Entered into controller");

        AdmTlUsermst userResult = service.userRecall(userKeyId);
        return ResponseEntity.ok(userResult);

    }

    @PostMapping("/updateRole")
    public ResponseEntity<AdmTlUserRoleLink> updateRole(@RequestBody AdmTlUserRoleLink userRoleLink) throws Exception {
        logger.info("Entered into controller");

        AdmTlUserRoleLink userResult = service.updateUserRole(userRoleLink);
        return ResponseEntity.ok(userResult);

    }

    @GetMapping("/deleteRole")
    public void deleteRole(@RequestParam("userId") String userKeyId,
            @RequestParam("roleId") String userRoleId) throws Exception {
        logger.info("Entered into controller");

        service.deleteRole(userKeyId, userRoleId);

    }

}
