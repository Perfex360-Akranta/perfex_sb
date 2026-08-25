package com.akranta.perfex_sb.dto;

public class JhaTlAuditTemplateGridDto {
    
    // Template fields (matching JhaTlAudittemplate entity)
    private String keyid;
    private String reviewptslno;
    private String parametername;
    private String criteriaslno;
    private String parameterdescription;
    private String evidence;
    private Integer maximumpoints;
    
    // Placeholder fields
    private String parameterId;
    private String parameterName;
    
    // Detail fields (matching JhaTlAuditdtl entity)
    private Integer pointsscored;
    private String remarks;
    private String detailKeyid;
    private String ncremarks;
    private String ncactionplan;
    private String ncactionplanKeyId;
    private String ncstatus;
    private String ncclosed;

    // Default Constructor
    public JhaTlAuditTemplateGridDto() {}

    // Constructor for query results
    public JhaTlAuditTemplateGridDto(String keyid, String reviewptslno, String parametername,
            String criteriaslno, String parameterdescription, String evidence, Integer maximumpoints,
            String parameterId, String parameterName, Integer pointsscored, String remarks, String detailKeyid,
            String ncremarks, String ncactionplan, String ncactionplanKeyId, String ncstatus, String ncclosed) {
        this.keyid = keyid;
        this.reviewptslno = reviewptslno;
        this.parametername = parametername;
        this.criteriaslno = criteriaslno;
        this.parameterdescription = parameterdescription;
        this.evidence = evidence;
        this.maximumpoints = maximumpoints;
        this.parameterId = parameterId;
        this.parameterName = parameterName;
        this.pointsscored = pointsscored;
        this.remarks = remarks;
        this.detailKeyid = detailKeyid;
        this.ncremarks = ncremarks;
        this.ncactionplan = ncactionplan;
        this.ncactionplanKeyId = ncactionplanKeyId;
        this.ncstatus = ncstatus;
        this.ncclosed = ncclosed;
    }

    // Getters and Setters
    public String getKeyid() { return keyid; }
    public void setKeyid(String keyid) { this.keyid = keyid; }

    public String getReviewptslno() { return reviewptslno; }
    public void setReviewptslno(String reviewptslno) { this.reviewptslno = reviewptslno; }

    public String getParametername() { return parametername; }
    public void setParametername(String parametername) { this.parametername = parametername; }

    public String getCriteriaslno() { return criteriaslno; }
    public void setCriteriaslno(String criteriaslno) { this.criteriaslno = criteriaslno; }

    public String getParameterdescription() { return parameterdescription; }
    public void setParameterdescription(String parameterdescription) { 
        this.parameterdescription = parameterdescription; 
    }

    public String getEvidence() { return evidence; }
    public void setEvidence(String evidence) { this.evidence = evidence; }

    public Integer getMaximumpoints() { return maximumpoints; }
    public void setMaximumpoints(Integer maximumpoints) { this.maximumpoints = maximumpoints; }

    public String getParameterId() { return parameterId; }
    public void setParameterId(String parameterId) { this.parameterId = parameterId; }

    public String getParameterName() { return parameterName; }
    public void setParameterName(String parameterName) { this.parameterName = parameterName; }

    public Integer getPointsscored() { return pointsscored; }
    public void setPointsscored(Integer pointsscored) { this.pointsscored = pointsscored; }

    public String getRemarks() { return remarks; }
    public void setRemarks(String remarks) { this.remarks = remarks; }

    public String getDetailKeyid() { return detailKeyid; }
    public void setDetailKeyid(String detailKeyid) { this.detailKeyid = detailKeyid; }

    public String getNcremarks() { return ncremarks; }
    public void setNcremarks(String ncremarks) { this.ncremarks = ncremarks; }

    public String getNcactionplan() { return ncactionplan; }
    public void setNcactionplan(String ncactionplan) { this.ncactionplan = ncactionplan; }

    public String getNcactionplanKeyId() { return ncactionplanKeyId; }
    public void setNcactionplanKeyId(String ncactionplanKeyId) { this.ncactionplanKeyId = ncactionplanKeyId; }

    public String getNcstatus() { return ncstatus; }
    public void setNcstatus(String ncstatus) { this.ncstatus = ncstatus; }

    public String getNcclosed() { return ncclosed; }
    public void setNcclosed(String ncclosed) { this.ncclosed = ncclosed; }
}
