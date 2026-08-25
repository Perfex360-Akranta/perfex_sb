package com.akranta.perfex_sb.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.akranta.perfex_sb.dto.MenuTreeRecordDto;
import com.akranta.perfex_sb.service.MenuTreeService;

@RestController
@RequestMapping("/api")
public class MenuTreeController {

    private static final Logger logger = LoggerFactory.getLogger(MenuTreeController.class);

    private final MenuTreeService menuTreeService;

    public MenuTreeController(MenuTreeService menuTreeService) {
        this.menuTreeService = menuTreeService;
    }

    @GetMapping("/getAllMenuTree")
    public ResponseEntity<List<MenuTreeRecordDto>> getMenuTree(
            @RequestParam(value = "id", defaultValue = "0") String id,
            @RequestParam(value = "parentNumber", required = false) String parentNumber,
            @RequestParam(value = "userId", required = false) String userId) {

        try {
            String pNum = (parentNumber != null && !parentNumber.trim().isEmpty()) ? parentNumber : id;

            if (userId == null || userId.trim().isEmpty()) {
                Authentication auth = SecurityContextHolder.getContext().getAuthentication();
                if (auth != null && auth.isAuthenticated() && !"anonymousUser".equals(auth.getName())) {
                    userId = auth.getName();
                }
            }

            logger.info("Loading menu tree for parentId: {}, userId: {}", pNum, userId);

            List<MenuTreeRecordDto> result = menuTreeService.getMenuTree(pNum, userId);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            logger.error("Error loading menu tree", e);
            return ResponseEntity.internalServerError().build();
        }
    }
}