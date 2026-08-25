package com.akranta.perfex_sb.model;

import java.time.LocalDateTime;

import jakarta.persistence.*;

@Entity
@Table(name = "GEN_TL_VISUALCNTCHECKLISTDTL", schema = "public")
public class GenTlVisualcntchecklistdtl {


    @Id
    @Column(name = "vcdt_keyid", length = 15, nullable = false)
    private String keyid;


     @Column(name = "vcdt_vccl_keyid", length = 15, nullable = false)
    private String vccl_keyid;
    

    @Column(name = "vcdt_vccd_keyid", length = 12, nullable = false)
    private String vccd_keyid;

    @Column(name = "vcdt_criteriaval", length = 1, nullable = false)
    private Character criteriaval;

    @Column(name = "vcdt_tempfield1",  length = 1, nullable = false)
    private Character tempfield1;

    @Column(name = "vcdt_tempfield2", length = 1, nullable = false)
    private Character tempfield2;

    @Column(name = "vcdt_tempfield3", length = 1, nullable = false)
    private Character tempfield3;

    @Column(name = "vcdt_tempfield4", length = 1, nullable = false)
    private Character tempfield4;


    @Column(name = "vcdt_tempfield5", length = 1, nullable = false)
    private Character tempfield5;

    @Column(name = "vcdt_tempfield6", length = 1, nullable = false)
    private Character tempfield6;

    @Column(name = "vcdt_active", length = 1, nullable = false)
    private Character active;

    @Column(name = "vcdt_createdby", length = 10, nullable = false)
    private String createdby;

    @Column(name = "vcdt_createdon", updatable = false)
    private LocalDateTime createdon;

    @Column(name = "vcdt_modifiedon")
    private LocalDateTime modifiedon;

    public String getKeyid() {
        return keyid;
    }

    public void setKeyid(String keyid) {
        this.keyid = keyid;
    }

    public String getVccl_keyid() {
        return vccl_keyid;
    }

    public void setVccl_keyid(String vccl_keyid) {
        this.vccl_keyid = vccl_keyid;
    }

    public String getVccd_keyid() {
        return vccd_keyid;
    }

    public void setVccd_keyid(String vccd_keyid) {
        this.vccd_keyid = vccd_keyid;
    }

    public Character getCriteriaval() {
        return criteriaval;
    }

    public void setCriteriaval(Character criteriaval) {
        this.criteriaval = criteriaval;
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
