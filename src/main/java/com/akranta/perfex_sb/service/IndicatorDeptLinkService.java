package com.akranta.perfex_sb.service;

import com.akranta.perfex_sb.dto.IndicatorDeptLinkRequest;
import com.akranta.perfex_sb.model.KpiTlIndicatorDeptLink;

public interface IndicatorDeptLinkService {
    
    //KpiTlIndicatorDeptLink createIndicatorDeptLink(IndicatorDeptLinkRequest request) throws Exception;
    // KpiTlIndicatorDeptLink createIndicatorDeptLink(
    //         KpiTlIndicatorDeptLink kpiTlIndicatorDeptLink,
    //         IndicatorDeptLinkRequest request) throws Exception;

    public KpiTlIndicatorDeptLink createIndicatorDeptLink(IndicatorDeptLinkRequest request) throws Exception;

    public String validateKeyIndLink(KpiTlIndicatorDeptLink kpiTlIndicatorDeptLink) throws Exception;
}