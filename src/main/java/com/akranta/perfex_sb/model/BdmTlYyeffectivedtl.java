package com.akranta.perfex_sb.model;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

//import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "bdm_tl_yyeffectivedtl", schema = "public")
public class BdmTlYyeffectivedtl {
    
    @Id
    @Column(name = "yyed_keyid", length = 12, nullable = false)
    private String keyid;

    @Column(name = "yyed_yyef_keyid", length = 12, nullable = false)
    private String yyef_keyid;

    @Column(name = "yyed_countermesid", length = 12, nullable = false)
    private String countermesid;

    @Column(name = "yyed_countermestype", length = 50, nullable = false)
    private String countermestype;

    @Column(name = "yyed_empm_keyid", length = 12, nullable = false)
    private String empm_keyid;

    @Column(name = "yyed_countermesdate")
    private LocalDateTime countermesdate = LocalDateTime.now();

    @Column(name = "yyed_effectiveid", length = 12, nullable = false)
    private String effectiveid;

    @Column(name = "yyed_tempfield1", length = 1)
    private Character tempfield1 = 'N';

    @Column(name = "yyed_tempfield2", length = 1)
    private Character tempfield2 = 'N';

    @Column(name = "yyed_tempfield3", length = 1)
    private Character tempfield3 = 'N';

    @Column(name = "yyed_tempfield4", length = 1)
    private Character tempfield4 = 'N';

    @Column(name = "yyed_tempfield5", length = 1)
    private Character tempfield5 = 'N';

    @Column(name = "yyed_tempfield6", length = 1)
    private Character tempfield6 = 'N';

    @Column(name = "yyed_tempfield7", length = 1)
    private Character tempfield7 = 'N';

    @Column(name = "yyed_active", length = 1, nullable = false)
    private Character active = 'Y';

    @Column(name = "yyed_createdby", length = 8)
    private String createdby;

    @Column(name = "yyed_createdon", nullable = false)
    @CreationTimestamp
    private LocalDateTime createdon = LocalDateTime.now();

    @Column(name = "yyed_modifiedon", nullable = false)
    @UpdateTimestamp
    private LocalDateTime modifiedon = LocalDateTime.now();

    // Getters and Setters
    public String getKeyid() {
        return keyid;
    }

    public void setKeyid(String keyid) {
        this.keyid = keyid;
    }

    public String getYyefKeyid() {
        return yyef_keyid;
    }

    public void setYyefKeyid(String yyefKeyid) {
        this.yyef_keyid = yyefKeyid;
    }

    public String getCountermesid() {
        return countermesid;
    }

    public void setCountermesid(String countermesid) {
        this.countermesid = countermesid;
    }

    public String getCountermestype() {
        return countermestype;
    }

    public void setCountermestype(String countermestype) {
        this.countermestype = countermestype;
    }

    public String getEmpm_Keyid() {
        return empm_keyid;
    }

    public void setEmpm_keyid(String empmKeyid) {
        this.empm_keyid = empmKeyid;
    }

    public LocalDateTime getCountermesdate() {
        return countermesdate;
    }

    public void setCountermesdate(LocalDateTime countermesdate) {
        this.countermesdate = countermesdate;
    }

    public String getEffectiveid() {
        return effectiveid;
    }

    public void setEffectiveid(String effectiveid) {
        this.effectiveid = effectiveid;
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