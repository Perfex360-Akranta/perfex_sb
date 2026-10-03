package com.akranta.perfex_sb.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "bal_plm_tl_multiple_resp")
public class BalPlmTlMultipleResp {

    @Id
    @Column(name = "pmrs_keyid", length = 15)
    private String keyid;

    @Column(name = "pmrs_refid", length = 15, nullable = false)
    private String refid;

    @Column(name = "pmrs_alloted_empid", length = 10, nullable = false)
    private String allotedEmpid;

    @Column(name = "pmrs_completed_empid", length = 10, nullable = false)
    private String completedEmpid;

    @Column(name = "pmrs_active", length = 1, nullable = false)
    private String active;

    public BalPlmTlMultipleResp() {
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

    public String getAllotedEmpid() {
        return allotedEmpid;
    }

    public void setAllotedEmpid(String allotedEmpid) {
        this.allotedEmpid = allotedEmpid;
    }

    public String getCompletedEmpid() {
        return completedEmpid;
    }

    public void setCompletedEmpid(String completedEmpid) {
        this.completedEmpid = completedEmpid;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }
}