package com.akranta.perfex_sb.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.akranta.perfex_sb.dto.MenuTreeRecordDto;
import com.akranta.perfex_sb.repository.MenuTreeRepository;
import com.akranta.perfex_sb.service.MenuTreeService;

@Service
public class MenuTreeServiceImpl implements MenuTreeService {

    private static final Logger logger = LoggerFactory.getLogger(MenuTreeServiceImpl.class);

    private final MenuTreeRepository menuTreeRepository;

    public MenuTreeServiceImpl(MenuTreeRepository menuTreeRepository) {
        this.menuTreeRepository = menuTreeRepository;
    }

    @Override
    public List<MenuTreeRecordDto> getMenuTree(String parentNumber, String userId) throws Exception {
        String pNum = (parentNumber == null || parentNumber.trim().isEmpty()) ? "0" : parentNumber.trim();
        String uId = (userId != null) ? userId.trim() : "";

        logger.info("Fetching menu tree for parentNumber: {}, userId: {}", pNum, uId);

        List<Map<String, Object>> rows;

        if ("0".equals(pNum)) {
            rows = menuTreeRepository.getRootMenus(uId);
        } else if (!"-1".equals(pNum)) {
            rows = menuTreeRepository.getChildMenus(pNum, uId);
        } else {
            rows = menuTreeRepository.getAllUserMenus(uId);
        }

        List<MenuTreeRecordDto> menuList = new ArrayList<>();
        if (rows != null) {
            for (Map<String, Object> row : rows) {
                MenuTreeRecordDto record = mapRowToRecord(row);
                menuList.add(record);
            }
        }

        return menuList;
    }

    private MenuTreeRecordDto mapRowToRecord(Map<String, Object> row) {
        MenuTreeRecordDto record = new MenuTreeRecordDto();

        record.setMenuNumber(getMapString(row, "mnum_menunumber"));
        record.setMenuName(getMapString(row, "mnum_loadformargument"));
        record.setMenuCaption(getMapString(row, "mnum_mastintegsql"));
        record.setReportFileName(getMapString(row, "mnum_formname"));
        record.setFormName(getMapString(row, "mnum_mastintegorderbysql"));

        String isParentStr = getMapString(row, "mnum_isparent");
        record.setParent("Y".equalsIgnoreCase(isParentStr) || "true".equalsIgnoreCase(isParentStr));

        String isMasterStr = getMapString(row, "mnum_ismaster");
        record.setMaster("Y".equalsIgnoreCase(isMasterStr) || "true".equalsIgnoreCase(isMasterStr));

        String relatedFilter = stripBraces(getMapString(row, "mnum_similarcolumn"));
        record.setRelatedFilter(relatedFilter);

        String tableName = stripBraces(getMapString(row, "mnum_tablename"));
        record.setFilterNeed(tableName.isEmpty() ? 'N' : tableName.charAt(0));

        return record;
    }

    private String stripBraces(String value) {
        return value == null ? "" : value.replace("{", "").replace("}", "");
    }

    private String getMapString(Map<String, Object> map, String key) {
        if (map == null) return "";
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            if (entry.getKey() != null && entry.getKey().equalsIgnoreCase(key)) {
                return entry.getValue() != null ? entry.getValue().toString() : "";
            }
        }
        return "";
    }
}