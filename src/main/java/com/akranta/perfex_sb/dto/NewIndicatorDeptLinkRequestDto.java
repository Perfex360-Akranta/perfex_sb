package com.akranta.perfex_sb.dto;

import com.akranta.perfex_sb.model.KpiTlIndicatorDeptLink;

public class NewIndicatorDeptLinkRequestDto {

private KpiTlIndicatorDeptLink kpiTlIndicatorDeptLink;
    
private String isDelete;

public KpiTlIndicatorDeptLink getKpiTlIndicatorDeptLink() {
    return kpiTlIndicatorDeptLink;
}

public void setKpiTlIndicatorDeptLink(KpiTlIndicatorDeptLink kpiTlIndicatorDeptLink) {
    this.kpiTlIndicatorDeptLink = kpiTlIndicatorDeptLink;
}

public String getIsDelete() {
    return isDelete;
}

public void setIsDelete(String isDelete) {
    this.isDelete = isDelete;
}

}
