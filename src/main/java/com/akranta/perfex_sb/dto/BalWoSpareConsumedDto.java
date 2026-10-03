package com.akranta.perfex_sb.dto;
public class BalWoSpareConsumedDto {

    private String spareId;
    private String quantity;
    private String docType; // legacy pscaDoctype - jobType the spare cost is booked against

    public String getSpareId() { return spareId; }
    public void setSpareId(String spareId) { this.spareId = spareId; }

    public String getQuantity() { return quantity; }
    public void setQuantity(String quantity) { this.quantity = quantity; }

    public String getDocType() { return docType; }
    public void setDocType(String docType) { this.docType = docType; }
}
