package com.akranta.perfex_sb.dto;

public class EquipmentInfoDto {
    private String machineid;
    private String equipmentNo;
    private String equipmentName;
    private String flid;

    public String getMachineid() { return machineid; }
    public void setMachineid(String machineid) { this.machineid = machineid; }

    public String getEquipmentNo() { return equipmentNo; }
    public void setEquipmentNo(String equipmentNo) { this.equipmentNo = equipmentNo; }

    public String getEquipmentName() { return equipmentName; }
    public void setEquipmentName(String equipmentName) { this.equipmentName = equipmentName; }

    public String getFlid() { return flid; }
    public void setFlid(String flid) { this.flid = flid; }
}