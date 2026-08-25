package com.akranta.perfex_sb.service;

import java.util.List;
import com.akranta.perfex_sb.dto.MenuTreeRecordDto;


public interface MenuTreeService {
    List<MenuTreeRecordDto> getMenuTree(String parentNumber, String userId) throws Exception;
}