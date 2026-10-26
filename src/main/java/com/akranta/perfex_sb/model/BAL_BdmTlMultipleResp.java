package com.akranta.perfex_sb.model;
 
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.*;
 
@Entity
@Table(name = "bal_bdm_tl_multiple_resp", schema = "public")
public class BAL_BdmTlMultipleResp {
 
    @Id
    @Column(name = "bdrs_keyid", length = 10, nullable = false)
    private String keyid;
 
    @Column(name = "bdrs_refid", length = 15, nullable = false)
    private String refid;
 
     @JsonProperty("resp_empid")
    @Column(name = "bdrs_resp_empid", length = 10, nullable = false)
    private String resp_empid;
    @Column(name = "bdrs_tempfield", length = 1, nullable = false)
    private Character tempfield;
 
    @Column(name = "bdrs_active", length = 1, nullable = false)
    private Character active = 'Y';
 
    public BAL_BdmTlMultipleResp() {
    }
 
    public String getKeyid() {
        return keyid;
    }
 
    public void setKeyid(String keyid) {
        this.keyid = keyid;
    }
 
    public String getRefid() {
        return refid;
    }
 
    public void setRefid(String refid) {
        this.refid = refid;
    }
 
    public String getRespEmpid() {
        return resp_empid;
    }
 
    public void setRespEmpid(String respEmpid) {
        this.resp_empid = respEmpid;
    }
 
    public Character getTempfield() {
        return tempfield;
    }
 
    public void setTempfield(Character tempfield) {
        this.tempfield = tempfield;
    }
 
    public Character getActive() {
        return active;
    }
 
    public void setActive(Character active) {
        this.active = active;
    }

}