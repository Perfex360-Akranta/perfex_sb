// 1. DTO Class
package com.akranta.perfex_sb.dto;



public class JhaTlAuditParameterDto {
    private String jautKeyid;
    private String jautReviewptslno;
    private String paramname;
    private String jautCriteriaslno;
    private String paramdesc;
    private String evidence;
    private Integer maxpoint;
    private String delet;

    // Constructors
    public JhaTlAuditParameterDto() {
        this.delet = "";
    }

    public JhaTlAuditParameterDto(String jautKeyid, String jautReviewptslno, String paramname, 
                           String jautCriteriaslno, String paramdesc, String evidence, 
                           Integer maxpoint) {
        this.jautKeyid = jautKeyid;
        this.jautReviewptslno = jautReviewptslno;
        this.paramname = paramname;
        this.jautCriteriaslno = jautCriteriaslno;
        this.paramdesc = paramdesc;
        this.evidence = evidence;
        this.maxpoint = maxpoint;
        this.delet = "";
    }

    // Getters and Setters
    public String getJautKeyid() {
        return jautKeyid;
    }

    public void setJautKeyid(String jautKeyid) {
        this.jautKeyid = jautKeyid;
    }

    public String getJautReviewptslno() {
        return jautReviewptslno;
    }

    public void setJautReviewptslno(String jautReviewptslno) {
        this.jautReviewptslno = jautReviewptslno;
    }

    public String getParamname() {
        return paramname;
    }

    public void setParamname(String paramname) {
        this.paramname = paramname;
    }

    public String getJautCriteriaslno() {
        return jautCriteriaslno;
    }

    public void setJautCriteriaslno(String jautCriteriaslno) {
        this.jautCriteriaslno = jautCriteriaslno;
    }

    public String getParamdesc() {
        return paramdesc;
    }

    public void setParamdesc(String paramdesc) {
        this.paramdesc = paramdesc;
    }

    public String getEvidence() {
        return evidence;
    }

    public void setEvidence(String evidence) {
        this.evidence = evidence;
    }

    public Integer getMaxpoint() {
        return maxpoint;
    }

    public void setMaxpoint(Integer maxpoint) {
        this.maxpoint = maxpoint;
    }

    public String getDelet() {
        return delet;
    }

    public void setDelet(String delet) {
        this.delet = delet;
    }

    // public void setJhaTlAuditparameter(JhaTlAudittemplate jhaTlAuditmst) {
    //     // TODO Auto-generated method stub
    //     throw new UnsupportedOperationException("Unimplemented method 'setJhaTlAuditparameter'");
    // }
}
