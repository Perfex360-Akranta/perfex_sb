package com.akranta.perfex_sb.dto;

import java.util.HashMap;
import java.util.Map;

public class ComboFilterDto {
  

    private String id;
    private String code;
    private String name;

    private String idField;
    private String codeField;
    private String nameField;

    private String condSql = " AND 1 = 1 ";

    private Map<String, Object> sqlParams = new HashMap<>();

    private String tableName;
    private String orderByField;

    private String mode;
    private String page;
    private String rows;

    // remaining existing fields...


    public String getCondSql() {
        return condSql;
    }

    public void setCondSql(String condSql) {
        this.condSql = condSql;
    }


    public Map<String, Object> getSqlParams() {
        return sqlParams;
    }

    public void setSqlParams(Map<String, Object> sqlParams) {
        this.sqlParams = sqlParams;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdField() {
        return idField;
    }

    public void setIdField(String idField) {
        this.idField = idField;
    }

    public String getCodeField() {
        return codeField;
    }

    public void setCodeField(String codeField) {
        this.codeField = codeField;
    }

    public String getNameField() {
        return nameField;
    }

    public void setNameField(String nameField) {
        this.nameField = nameField;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getOrderByField() {
        return orderByField;
    }

    public void setOrderByField(String orderByField) {
        this.orderByField = orderByField;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String getRows() {
        return rows;
    }

    public void setRows(String rows) {
        this.rows = rows;
    }

    
}

