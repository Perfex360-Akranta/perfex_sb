package com.akranta.perfex_sb.dto;

public class PhenomenaLossGridRequestDto {
    private String phenId;
    private String lossId;
    private Integer fromRow; // optional
    private Integer toRow;   // optional

    public String getPhenId() {
        return phenId;
    }

    public void setPhenId(String phenId) {
        this.phenId = phenId;
    }

    public String getLossId() {
        return lossId;
    }

    public void setLossId(String lossId) {
        this.lossId = lossId;
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
