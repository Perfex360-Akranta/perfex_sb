package com.akranta.perfex_sb.dto;

public class PhenomenaComboRequestDto {
    private String keyId;
    private String type; // PHENOMENA or LOSS

    public String getKeyId() {
        return keyId;
    }

    public void setKeyId(String keyId) {
        this.keyId = keyId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
