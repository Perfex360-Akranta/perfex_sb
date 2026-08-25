package com.akranta.perfex_sb.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "gen_tl_upstreamdefect_det",schema="public")
public class UpstreamdefectDet {

    //upsd_keyid
    @Id
    @Column(name = "upsd_keyid", length = 15)
    private String keyid;

    //upsd_upsm_keyid
    @Column(name = "upsd_upsm_keyid", length = 15, nullable = false)
    private String upsm_keyid;   // FK reference to MST table

    //upsd_informto
    @Column(name = "upsd_informto", length = 500, nullable = false)
    private String informto;

    //upsd_rawmaterial
    @Column(name = "upsd_rawmaterial", length = 500, nullable = false)
    private String rawmaterial;

    //upsd_defect
    @Column(name = "upsd_defect", length = 10, nullable = false)
    private String defect;

    //upsd_correctionaction
    @Column(name = "upsd_correctionaction", length = 500, nullable = false)
    private String correctionaction;

    //upsd_preventiveaction
    @Column(name = "upsd_preventiveaction", length = 500, nullable = false)
    private String preventiveaction;

    //upsd_tempfield1
    @Column(name = "upsd_tempfield1", length = 1)
    private Character tempfield1;

    //upsd_tempfield2
    @Column(name = "upsd_tempfield2", length = 1)
    private Character tempfield2;

    //upsd_tempfield3
    @Column(name = "upsd_tempfield3", length = 1)
    private Character tempfield3;

    //upsd_tempfield4
    @Column(name = "upsd_tempfield4", length = 1)
    private Character tempfield4;

    //upsd_tempfield5
    @Column(name = "upsd_tempfield5", length = 1)
    private Character tempfield5;

    //upsd_active
    @Column(name = "upsd_active", length = 1)
    private Character active;

    //upsd_createdby
    @Column(name = "upsd_createdby", length = 8)
    private String createdby;

    //upsd_createdon
    @Column(name = "upsd_createdon")
    private LocalDateTime createdon;

    //upsd_modifiedon
    @Column(name = "upsd_modifiedon")
    private LocalDateTime modifiedon;

    public String getKeyid() {
        return keyid;
    }

    public void setKeyid(String keyid) {
        this.keyid = keyid;
    }

    public String getUpsm_keyid() {
        return upsm_keyid;
    }

    public void setUpsm_keyid(String upsm_keyid) {
        this.upsm_keyid = upsm_keyid;
    }

    public String getInformto() {
        return informto;
    }

    public void setInformto(String informto) {
        this.informto = informto;
    }

    public String getRawmaterial() {
        return rawmaterial;
    }

    public void setRawmaterial(String rawmaterial) {
        this.rawmaterial = rawmaterial;
    }

    public String getDefect() {
        return defect;
    }

    public void setDefect(String defect) {
        this.defect = defect;
    }

    public String getCorrectionaction() {
        return correctionaction;
    }

    public void setCorrectionaction(String correctionaction) {
        this.correctionaction = correctionaction;
    }

    public String getPreventiveaction() {
        return preventiveaction;
    }

    public void setPreventiveaction(String preventiveaction) {
        this.preventiveaction = preventiveaction;
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
