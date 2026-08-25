package com.akranta.perfex_sb.dto;

public class UserLoginDetailsDto {
    private String loginId;
	private String userName;
	private String employeeName;
	private String deptName;
	private String designation;
	private String lastLoggedOn;
	private String loginTime;
	private String password;
	private String defaultpwd;

	public UserLoginDetailsDto(String loginId, String userName, String employeeName,
                               String deptName, String designation,
                               String password, String defaultpwd) {
        this.loginId = loginId;
        this.userName = userName;
        this.employeeName = employeeName;
        this.deptName = deptName;
        this.designation = designation;
        this.password = password;
        this.defaultpwd = defaultpwd;
    }
	
	public String getLoginId() {
		return loginId;
	}
	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getEmployeeName() {
		return employeeName;
	}
	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	public String getDesignation() {
		return designation;
	}
	public void setDesignation(String designation) {
		this.designation = designation;
	}
	public String getLastLoggedOn() {
		return lastLoggedOn;
	}
	public void setLastLoggedOn(String lastLoggedOn) {
		this.lastLoggedOn = lastLoggedOn;
	}
	public String getLoginTime() {
		return loginTime;
	}
	public void setLoginTime(String loginTime) {
		this.loginTime = loginTime;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPassword() {
		return password;
	}
	public void setDefaultpwd(String defaultpwd) {
		this.defaultpwd = defaultpwd;
	}
	public String getDefaultpwd() {
		return defaultpwd;
	}
}
