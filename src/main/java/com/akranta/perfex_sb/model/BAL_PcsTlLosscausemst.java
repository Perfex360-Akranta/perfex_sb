package com.akranta.perfex_sb.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "pcs_tl_losscausemst", schema = "public")
public class BAL_PcsTlLosscausemst {

    @Id
    @Column(name = "plcs_keyid", length = 15, nullable = false)
    private String keyid;

    @Column(name = "plcs_phenomenaid", length = 15, nullable = false)
    private String phenomenaid;

    @Column(name = "plcs_description", length = 500, nullable = false)
    private String description;

    @Column(name = "plcs_tempfield2", length = 2, nullable = false)
    private String tempfield2;

    @Column(name = "plcs_tempfield3", length = 2, nullable = false)
    private String tempfield3;

    // ---------- Getters & Setters ----------

    public String getKeyid() {
        return keyid;
    }

    public void setKeyid(String keyid) {
        this.keyid = keyid;
    }

    public String getPhenomenaid() {
        return phenomenaid;
    }

    public void setPhenomenaid(String phenomenaid) {
        this.phenomenaid = phenomenaid;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTempfield2() {
        return tempfield2;
    }

    public void setTempfield2(String tempfield2) {
        this.tempfield2 = tempfield2;
    }

    public String getTempfield3() {
        return tempfield3;
    }

    public void setTempfield3(String tempfield3) {
        this.tempfield3 = tempfield3;
    }
}