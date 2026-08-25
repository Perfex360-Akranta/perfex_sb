package com.akranta.perfex_sb.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "kzn_tl_bestdtl", schema = "public")
public class KznTlBestdtl {
    @Column(name = "kzbd_keyid", length = 15, nullable = false)
    @Id
    private String keyid;

    @Column(name = "kzbd_kzbm_keyid", length = 15, nullable = false)
    private String kzbm_keyid;

    @Column(name = "kzbd_kaizenid", length = 20, nullable = false)
    private String kaizenid;

    @Column(name = "kzbd_tempfield1", columnDefinition = "CHAR(1)",nullable = false)
    private Character tempfield1;

    @Column(name = "kzbd_tempfield2", columnDefinition = "CHAR(1)", nullable = false)
    private Character tempfield2;

    @Column(name = "kzbd_tempfield3", columnDefinition = "CHAR(1)", nullable = false)
    private Character tempfield3;

    @Column(name = "kzbd_tempfield4", columnDefinition = "CHAR(1)", nullable = false)
    private Character tempfield4;

    @Column(name = "kzbd_tempfield5", columnDefinition = "CHAR(1)", nullable = false)
    private Character tempfield5;

    @Column(name = "kzbd_active", columnDefinition = "CHAR(1)",nullable = false)
    private Character active;

    @Column(name = "kzbd_createdby", length = 10, nullable = false)
    private String createdby;

    @Column(name = "kzbd_createdon", nullable = false)
    private LocalDateTime createdon;

    @Column(name = "kzbd_modifiedon", nullable = false)
    private LocalDateTime modifiedon;

    public String getKeyid() {
        return keyid;
    }

    public void setKeyid(String keyid) {
        this.keyid = keyid;
    }

    public String getKzbm_keyid() {
        return kzbm_keyid;
    }

    public void setKzbm_keyid(String kzbm_keyid) {
        this.kzbm_keyid = kzbm_keyid;
    }

    public String getKaizenid() {
        return kaizenid;
    }

    public void setKaizenid(String kaizenid) {
        this.kaizenid = kaizenid;
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
