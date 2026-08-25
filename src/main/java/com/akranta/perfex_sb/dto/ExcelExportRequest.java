package com.akranta.perfex_sb.dto;

import jakarta.servlet.http.HttpServletResponse;
//import net.sf.json.JSONObject;

public class ExcelExportRequest {
    private String functionName; // e.g. GEN_FN_MOMMAINGRID_SB
    private String vconditionparam; // condParms string
    private String vcommonparam; // commonParams string
    // private JSONObject colModel; // JSON string from frontend (fetched from DB by
    // caller)
    private String title; // Report title e.g. "JH Minutes Of Meeting Report"
    private String format; // "xlsx" or "xls", default xlsx
    private String fileName; // e.g. "MinutesOfMeetingReport"
    private MetaConfigDto metaConfig;

    public String getFunctionName() {
        return functionName;
    }

    public void setFunctionName(String functionName) {
        this.functionName = functionName;
    }

    public String getVconditionparam() {
        return vconditionparam;
    }

    public void setVconditionparam(String vconditionparam) {
        this.vconditionparam = vconditionparam;
    }

    public String getVcommonparam() {
        return vcommonparam;
    }

    public void setVcommonparam(String vcommonparam) {
        this.vcommonparam = vcommonparam;
    }

    // public JSONObject getColModel() {
    // return colModel;
    // }

    // public void setColModel(JSONObject colModel) {
    // this.colModel = colModel;
    // }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public MetaConfigDto getMetaConfig() {
        return metaConfig;
    }

    public void setMetaConfig(MetaConfigDto metaConfig) {
        this.metaConfig = metaConfig;
    }




}