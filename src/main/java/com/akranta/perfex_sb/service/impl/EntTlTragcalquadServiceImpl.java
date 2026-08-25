package com.akranta.perfex_sb.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.akranta.perfex_sb.model.EntTlTrgCalQuad;
import com.akranta.perfex_sb.repository.EntTlTrgCalQuadRepository;
import com.akranta.perfex_sb.service.EntTlTragcalquadService;

import io.jsonwebtoken.lang.Arrays;

@Service
public class EntTlTragcalquadServiceImpl implements EntTlTragcalquadService {

    @Autowired
    private EntTlTrgCalQuadRepository repository;

    @Override
    @Transactional
    public EntTlTrgCalQuad createAssmLevel(String updateList, String userid) throws Exception {
        EntTlTrgCalQuad entTlTragcalquad = new EntTlTrgCalQuad();

        // Convert comma-separated string to List
        // Remove quotes and spaces, then split
        String cleaned = updateList.replaceAll("['\"]", "").trim();
        // List<String> keyIds = Arrays.stream(cleaned.split(","))
        // .map(String::trim)
        // .collect(Collectors.toList());

        List<String> keyIds = new ArrayList<>();
        for (String id : cleaned.split(",")) {
            keyIds.add(id.trim());
        }

        System.out.println("Key IDs List: " + keyIds);

        // Get current level from repository
        Integer currentLevel = repository.getCurrentLevel(keyIds);

        System.out.println("Current Level: " + currentLevel);

        // Validate current level
        if (currentLevel == null) {
            throw new Exception("No records found for the given IDs: " + updateList);
        }

        // Business logic: Handle level progression based on current level
        if (currentLevel == 2) {
            int rowsUpdated = repository.updateToLevel3(keyIds, userid);
            System.out.println("Rows updated to level 3: " + rowsUpdated);
            if (rowsUpdated == 0) {
                throw new Exception("Failed to update records to Level 3");
            }
        } else if (currentLevel == 3) {
            int rowsUpdated = repository.updateToLevel4(keyIds, userid);
            System.out.println("Rows updated to level 4: " + rowsUpdated);
            if (rowsUpdated == 0) {
                throw new Exception("Failed to update records to Level 4");
            }
        } else {
            throw new Exception("Invalid current level: " + currentLevel + ". Expected 2 or 3.");
        }

        return entTlTragcalquad;
    }

    // @Override
    // @Transactional
    // public EntTlTrgCalQuad createAssmLevel(String updateList, String userid)
    // throws Exception {
    // EntTlTrgCalQuad entTlTragcalquad = new EntTlTrgCalQuad();

    // // Clean the input and format for SQL IN clause
    // String modifiedId = updateList.replaceAll("\"", "'");

    // // If the input doesn't have quotes, add them
    // if (!modifiedId.contains("'")) {
    // // Split by comma and add quotes to each ID
    // String[] ids = modifiedId.split(",");
    // StringBuilder formattedIds = new StringBuilder();
    // for (int i = 0; i < ids.length; i++) {
    // formattedIds.append("'").append(ids[i].trim()).append("'");
    // if (i < ids.length - 1) {
    // formattedIds.append(",");
    // }
    // }
    // modifiedId = formattedIds.toString();
    // }

    // System.out.println("Modified ID list: " + modifiedId);

    // // Get current level from repository
    // Integer currentLevel = repository.getCurrentLevel(modifiedId);

    // System.out.println("Current Level: " + currentLevel);

    // // Validate current level
    // if (currentLevel == null) {
    // throw new Exception("No records found for the given IDs: " + updateList);
    // }

    // // Business logic: Handle level progression based on current level
    // if (currentLevel == 2) {
    // int rowsUpdated = repository.updateToLevel3(modifiedId, userid);
    // System.out.println("Rows updated to level 3: " + rowsUpdated);
    // if (rowsUpdated == 0) {
    // throw new Exception("Failed to update records to Level 3");
    // }
    // } else if (currentLevel == 3) {
    // int rowsUpdated = repository.updateToLevel4(modifiedId, userid);
    // System.out.println("Rows updated to level 4: " + rowsUpdated);
    // if (rowsUpdated == 0) {
    // throw new Exception("Failed to update records to Level 4");
    // }
    // } else {
    // throw new Exception("Invalid current level: " + currentLevel + ". Expected 2
    // or 3.");
    // }

    // return entTlTragcalquad;
    // }

}