package com.akranta.perfex_sb.dto;
import java.util.List;
import com.akranta.perfex_sb.model.PlmtlProcessfmeaDTL;
import com.akranta.perfex_sb.model.PlmtlProcessfmeaMST;

public class PlmtlProcessfmeaDto {

    PlmtlProcessfmeaMST plmtlProcessfmeaMST;
    List<PlmtlProcessfmeaDTL> plmtlProcessfmeaDTL;

    public PlmtlProcessfmeaMST getPlmtlProcessfmeaMST() {
        return plmtlProcessfmeaMST;
    }

    public void setPlmtlProcessfmeaMST(PlmtlProcessfmeaMST plmtlProcessfmeaMST) {
        this.plmtlProcessfmeaMST = plmtlProcessfmeaMST;
    }

    public List<PlmtlProcessfmeaDTL> getPlmtlProcessfmeaDTL() {
        return plmtlProcessfmeaDTL;
    }

    public void setPlmtlProcessfmeaDTL(List<PlmtlProcessfmeaDTL> plmtlProcessfmeaDTL) {
        this.plmtlProcessfmeaDTL = plmtlProcessfmeaDTL;
    }
   

}
