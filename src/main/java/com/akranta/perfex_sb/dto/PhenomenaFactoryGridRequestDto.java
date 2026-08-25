package com.akranta.perfex_sb.dto;

public class PhenomenaFactoryGridRequestDto {
    private String lossId;
    private String phenId;
    private Integer fromRow;
    private Integer toRow;

    public String getLossId() {
        return lossId;
    }

    public void setLossId(String lossId) {
        this.lossId = lossId;
    }

    public String getPhenId() {
        return phenId;
    }

    public void setPhenId(String phenId) {
        this.phenId = phenId;
    }

    public Integer getFromRow() {
        return fromRow;
    }

    public void setFromRow(Integer fromRow) {
        this.fromRow = fromRow;
    }

    public Integer getToRow() {
        return toRow;
    }

    public void setToRow(Integer toRow) {
        this.toRow = toRow;
    }
}
