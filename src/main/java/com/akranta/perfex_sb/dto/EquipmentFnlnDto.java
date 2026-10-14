package com.akranta.perfex_sb.dto;

public class EquipmentFnlnDto {
    private String fnlnKeyid;
    private String machineid;
    private String equipmentName;
    private String equipmentNo;
    private String compKeyid;
    private String locnKeyid;
    private String sbutKeyid;   // factory
    private String pbutKeyid;
    private String sectKeyid;
    private String cellKeyid;

    public String getFnlnKeyid() { return fnlnKeyid; }
    public void setFnlnKeyid(String fnlnKeyid) { this.fnlnKeyid = fnlnKeyid; }

    public String getMachineid() { return machineid; }
    public void setMachineid(String machineid) { this.machineid = machineid; }

    public String getEquipmentName() { return equipmentName; }
    public void setEquipmentName(String equipmentName) { this.equipmentName = equipmentName; }

    public String getEquipmentNo() { return equipmentNo; }
    public void setEquipmentNo(String equipmentNo) { this.equipmentNo = equipmentNo; }

    public String getCompKeyid() { return compKeyid; }
    public void setCompKeyid(String compKeyid) { this.compKeyid = compKeyid; }

    public String getLocnKeyid() { return locnKeyid; }
    public void setLocnKeyid(String locnKeyid) { this.locnKeyid = locnKeyid; }

    public String getSbutKeyid() { return sbutKeyid; }
    public void setSbutKeyid(String sbutKeyid) { this.sbutKeyid = sbutKeyid; }

    public String getPbutKeyid() { return pbutKeyid; }
    public void setPbutKeyid(String pbutKeyid) { this.pbutKeyid = pbutKeyid; }

    public String getSectKeyid() { return sectKeyid; }
    public void setSectKeyid(String sectKeyid) { this.sectKeyid = sectKeyid; }

    public String getCellKeyid() { return cellKeyid; }
    public void setCellKeyid(String cellKeyid) { this.cellKeyid = cellKeyid; }
}