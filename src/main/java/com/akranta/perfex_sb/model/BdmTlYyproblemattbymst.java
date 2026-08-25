
package com.akranta.perfex_sb.model;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
//import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "bdm_tl_yyproblemattbymst", schema = "public")
public class BdmTlYyproblemattbymst {
    
    @Id
    @Column(name = "wwpa_keyid", length = 15, nullable = false)
    private String keyid;

    @Column(name = "wwpa_wwms_keyid", length = 15, nullable = false)
    private String wwms_keyid;

    @Column(name = "wwpa_empm_keyid", length = 15, nullable = false)
    private String empm_keyid;

    @Column(name = "wwpa_tempfield1", length = 15, nullable = false)
    private String tempfield1;

    @Column(name = "wwpa_tempfield2", length = 15, nullable = false)
    private String tempfield2;

    @Column(name = "wwpa_tempfield3", length = 15, nullable = false)
    private String tempfield3;

    @Column(name = "wwpa_active", length = 1, nullable = false)
    private Character active;

    @Column(name = "wwpa_createdby", length = 8, nullable = false)
    private String createdby;

    @Column(name = "wwpa_createdon", nullable = false)
    @CreationTimestamp
    private LocalDateTime createdon;

    @Column(name = "wwpa_modifiedon", nullable = false)
    @UpdateTimestamp
    private LocalDateTime modifiedon;

    // Constructors
    public BdmTlYyproblemattbymst() {
    }

    public BdmTlYyproblemattbymst(String keyid, String wwmsKeyid, String empmKeyid) {
        this.keyid = keyid;
        this.wwms_keyid = wwmsKeyid;
        this.empm_keyid = empmKeyid;
    }

    // Getters and Setters
    public String getKeyid() {
        return keyid;
    }

    public void setKeyid(String keyid) {
        this.keyid = keyid;
    }

    public String getWwmsKeyid() {
        return wwms_keyid;
    }

    public void setWwms_keyid(String wwms_keyid) {
        this.wwms_keyid = wwms_keyid;
    }

    public String getEmpm_keyid() {
        return empm_keyid;
    }

    public void setEmpm_keyid(String empm_keyid) {
        this.empm_keyid = empm_keyid;
    }

    public String getTempfield1() {
        return tempfield1;
    }

    public void setTempfield1(String tempfield1) {
        this.tempfield1 = tempfield1;
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

    public Character getActive() {
        return active;
    }

    public void setActive(Character active) {
        this.active = active;
    }

    public String getCreatedby() {
        return createdby;
    }

    public void setCreatedby(String createdby) {
        this.createdby = createdby;
    }

    public LocalDateTime getCreatedon() {
        return createdon;
    }

    public void setCreatedon(LocalDateTime createdon) {
        this.createdon = createdon;
    }

    public LocalDateTime getModifiedon() {
        return modifiedon;
    }

    public void setModifiedon(LocalDateTime modifiedon) {
        this.modifiedon = modifiedon;
    }
}