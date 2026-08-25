package com.akranta.perfex_sb.model;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

//import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "bdm_tl_yyeffectivemst", schema = "public")
public class BdmTlYyeffectivemst {
    
    @Id
    @Column(name = "yyef_keyid", length = 12, nullable = false)
    private String keyid;

    @Column(name = "yyef_flid", length = 12, nullable = false)
    private String flid;

    @Column(name = "yyef_refdocid", length = 12, nullable = false)
    private String refdocid;

    @Column(name = "yyef_refdoctype", length = 50, nullable = false)
    private String refdoctype;

    @Column(name = "yyef_wwms_keyid", length = 12, nullable = false)
    private String wwms_keyid;

    @Column(name = "yyef_effectivedate")
    private LocalDateTime effectivedate = LocalDateTime.now();

    @Column(name = "yyef_tempfield1", length = 1)
    private Character tempfield1 = 'N';

    @Column(name = "yyef_tempfield2", length = 1)
    private Character tempfield2 = 'N';

    @Column(name = "yyef_tempfield3", length = 1)
    private Character tempfield3 = 'N';

    @Column(name = "yyef_tempfield4", length = 1)
    private Character tempfield4 = 'N';

    @Column(name = "yyef_tempfield5", length = 1)
    private Character tempfield5 = 'N';

    @Column(name = "yyef_tempfield6", length = 1)
    private Character tempfield6 = 'N';

    @Column(name = "yyef_tempfield7", length = 1)
    private Character tempfield7 = 'N';

    @Column(name = "yyef_active", length = 1, nullable = false)
    private Character active = 'Y';

    @Column(name = "yyef_createdby", length = 8)
    private String createdby;

    @Column(name = "yyef_createdon", nullable = false)
    @CreationTimestamp
    private LocalDateTime createdon = LocalDateTime.now();

    @Column(name = "yyef_modifiedon", nullable = false)
    @UpdateTimestamp
    private LocalDateTime modifiedon = LocalDateTime.now();

    // Getters and Setters
    public String getKeyid() {
        return keyid;
    }

    public void setKeyid(String keyid) {
        this.keyid = keyid;
    }

    public String getFlid() {
        return flid;
    }

    public void setFlid(String flid) {
        this.flid = flid;
    }

    public String getRefdocid() {
        return refdocid;
    }

    public void setRefdocid(String refdocid) {
        this.refdocid = refdocid;
    }

    public String getRefdoctype() {
        return refdoctype;
    }

    public void setRefdoctype(String refdoctype) {
        this.refdoctype = refdoctype;
    }

    public String getWwms_keyid() {
        return wwms_keyid;
    }

    public void setWwms_keyid(String wwmsKeyid) {
        this.wwms_keyid = wwmsKeyid;
    }

    public LocalDateTime getEffectivedate() {
        return effectivedate;
    }

    public void setEffectivedate(LocalDateTime effectivedate) {
        this.effectivedate = effectivedate;
    }

    public Character getTempfield1() {
        return tempfield1;
    }

    public void setTempfield1(Character tempfield1) {
        this.tempfield1 = tempfield1;
    }

    public Character getTempfield2() {
        return tempfield2;
    }

    public void setTempfield2(Character tempfield2) {
        this.tempfield2 = tempfield2;
    }

    public Character getTempfield3() {
        return tempfield3;
    }

    public void setTempfield3(Character tempfield3) {
        this.tempfield3 = tempfield3;
    }

    public Character getTempfield4() {
        return tempfield4;
    }

    public void setTempfield4(Character tempfield4) {
        this.tempfield4 = tempfield4;
    }

    public Character getTempfield5() {
        return tempfield5;
    }

    public void setTempfield5(Character tempfield5) {
        this.tempfield5 = tempfield5;
    }

    public Character getTempfield6() {
        return tempfield6;
    }

    public void setTempfield6(Character tempfield6) {
        this.tempfield6 = tempfield6;
    }

    public Character getTempfield7() {
        return tempfield7;
    }

    public void setTempfield7(Character tempfield7) {
        this.tempfield7 = tempfield7;
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