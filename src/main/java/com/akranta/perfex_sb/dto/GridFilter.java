package com.akranta.perfex_sb.dto;

/**
 * Minimal representation of a jqGrid-style filter condition.
 * Only the fields needed for the unique position grid are captured.
 */
public class GridFilter {
    private String field;
    private String data;

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
