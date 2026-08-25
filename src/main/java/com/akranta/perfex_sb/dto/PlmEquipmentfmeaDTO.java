package com.akranta.perfex_sb.dto;
import com.akranta.perfex_sb.model.PlmtlEquipmentfmeaDTL;
import com.akranta.perfex_sb.model.PlmtlEquipmentfmeaMST;
import java.util.List;

public class PlmEquipmentfmeaDTO {
    PlmtlEquipmentfmeaMST plmtlequipmentfmeaMST;
    List<PlmtlEquipmentfmeaDTL> plmtlequipmentfmeaDTL;

    public PlmtlEquipmentfmeaMST getPlmtlequipmentfmeaMST() {
        return plmtlequipmentfmeaMST;
    }

    public void setPlmtlequipmentfmeaMST(PlmtlEquipmentfmeaMST plmtlequipmentfmeaMST) {
        this.plmtlequipmentfmeaMST = plmtlequipmentfmeaMST;
    }

    public List<PlmtlEquipmentfmeaDTL> getPlmtlequipmentfmeaDTL() {
        return plmtlequipmentfmeaDTL;
    }

    public void setPlmtlequipmentfmeaDTL(List<PlmtlEquipmentfmeaDTL> plmtlequipmentfmeaDTL) {
        this.plmtlequipmentfmeaDTL = plmtlequipmentfmeaDTL;
    }

}
