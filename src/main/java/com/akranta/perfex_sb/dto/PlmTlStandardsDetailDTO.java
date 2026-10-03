package com.akranta.perfex_sb.dto;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.akranta.perfex_sb.model.BAL_PlmTlStandards;
import com.akranta.perfex_sb.model.BalPlmTlCbmstdcadtl;

import java.util.ArrayList;
import java.util.List;


public class PlmTlStandardsDetailDTO {

    @JsonUnwrapped
    private BAL_PlmTlStandards standard;

    private List<BalPlmTlCbmstdcadtl> cbmData = new ArrayList<>();

    public PlmTlStandardsDetailDTO() {
    }

    public BAL_PlmTlStandards getStandard() {
        return standard;
    }

    public void setStandard(BAL_PlmTlStandards standard) {
        this.standard = standard;
    }

    public List<BalPlmTlCbmstdcadtl> getCbmData() {
        return cbmData;
    }

    public void setCbmData(List<BalPlmTlCbmstdcadtl> cbmData) {
        this.cbmData = cbmData;
    }
}