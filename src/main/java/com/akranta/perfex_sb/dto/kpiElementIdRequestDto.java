package com.akranta.perfex_sb.dto;

public class kpiElementIdRequestDto {

    private String loginflid;
    private Integer loginlevel;
    private String loginElementid;
    private String empId;
    public String getLoginflid() {
        return loginflid;
    }
    public void setLoginflid(String loginflid) {
        this.loginflid = loginflid;
    }
    public Integer getLoginlevel() {
        return loginlevel;
    }
    public void setLoginlevel(Integer loginlevel) {
        this.loginlevel = loginlevel;
    }
    public String getLoginElementid() {
        return loginElementid;
    }
    public void setLoginElementid(String loginElementid) {
        this.loginElementid = loginElementid;
    }
    public String getEmpId() {
        return empId;
    }
    public void setEmpId(String empId) {
        this.empId = empId;
    }
    
}
