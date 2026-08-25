package com.akranta.perfex_sb.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.akranta.perfex_sb.dto.KpiIndicatorDto;
import com.akranta.perfex_sb.model.KpiTlActual;
import com.akranta.perfex_sb.model.KpiTlIndicator;
import com.akranta.perfex_sb.model.KpiTlKpiRemarks;

public interface KPIService {

    public String getByPillCode(String pillcode);

    public String getflId(String flId);

    // indicator creation 1111
    public KpiTlIndicator createIndicator(KpiTlIndicator kpiTlIndicator);

    public KpiTlIndicator updateIndicator(KpiTlIndicator kpiTlIndicator);

    public List<Map<String, Object>> getAllKeyIndicators(KpiIndicatorDto kpiIndicatorDto);

    public List<Map<String, Object>> getAllKeyIndicatorsKkValue(KpiIndicatorDto kpiIndicatorDto);

    public int deleteByKeyId(KpiTlIndicator kpiTlIndicator);

    String getSortNo2(KpiTlIndicator kpiTlIndicator);

    // public String validateDelkeyIndLevel(String parentId, String pillarId, String
    // keyId, String location)
    // throws Exception;

    // target setting 222222

    // public List<KpiTlActual> getByKeyId(String keyid, String indicatorid);
    // public KpiTlActual getByModel(KpiTlActual kpiTlActual);

    // public String getByPillCode(String pillcode);

    public List<KpiTlActual> createordelete(List<KpiTlActual> list);

    public List<KpiTlKpiRemarks> createordeleteremarks(List<KpiTlKpiRemarks> list);

    // List<Map<String, Object>> getDeviationListif(String flid, String year, String
    // frequency, String currDate);

    public List<Map<String, Object>> getKPIDeviationCount(String flid, BigDecimal year, String frequency,
            String currDate, String currMonthYear);

    // public List<KpiTlKpiRemarks> getbykeyid(String keyid);

    int getkeyIndLevel(String Keyid) throws Exception;

    int getConfigkeyIndLevel() throws Exception;

    String getEntProgStartMonth();

    KpiTlIndicator indiactor(String Keyid, String indicatorName, String parentId);

    // List<String[]> getElementId(String loginflid, String empId, String loginElementid, Integer loginlevel)
    //         throws Exception;

   List<Map<String, Object>> getElementIdAsMap(String loginflid, Integer loginlevel, 
                                                 String loginElementid, String empId) throws Exception;

}
