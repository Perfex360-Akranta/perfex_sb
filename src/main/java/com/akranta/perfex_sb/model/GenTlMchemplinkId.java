package com.akranta.perfex_sb.model;

import java.io.Serializable;
import java.util.Objects;

public class GenTlMchemplinkId implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    
    private String machineid;  
    private String employeeid;
    
    
    public GenTlMchemplinkId() {}
    
    public GenTlMchemplinkId(String machineid, String employeeid) {
        this.machineid = machineid;
        this.employeeid = employeeid;
    }
    
    // Getters and setters
    public String getMachineid() {
        return machineid;
    }
    
    public void setMachineid(String machineid) {
        this.machineid = machineid;
    }
    
    public String getEmployeeid() {
        return employeeid;
    }
    
    public void setEmployeeid(String employeeid) {
        this.employeeid = employeeid;
    }
    
    // Override equals and hashCode (REQUIRED)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GenTlMchemplinkId that = (GenTlMchemplinkId) o;
        return Objects.equals(machineid, that.machineid) &&
               Objects.equals(employeeid, that.employeeid);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(machineid, employeeid);
    }
}