package com.akranta.perfex_sb.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity 
@Table(name="bal_pcs_tl_rejectionbreakupdata", schema = "public")
public class Bal_pcs_tl_rejectionbreakupdata {

    @Id 
    @Column(name="prbd_keyid", length=15, nullable= false)
    private String keyid;

    @Column(name="prbd_pprd_keyid", length=15, nullable=false)
    private String pprd_keyid;

    @Column (name="prbd_type", length = 1, nullable = false)
    private Character type;

    @Column (name="prbd_phenomena", length = 50, nullable = false)
    private String phenomena;

    @Column (name="prbd_quantity", nullable = false)
    private Integer quantity;

    @Column (name="prbd_rejectionreason", length = 500, nullable = false)
    private String rejectionreason;

    @Column (name="prbd_defect", length=100, nullable = false)
    private String defect;

    @Column (name="prbd_defectsource", length = 1, nullable = false)
    private Character defectsource;

    @Column (name="prbd_equipment", length = 150, nullable = false)
    private String equipment;

    @Column (name="prbd_tempfield1", length=1, nullable = false)
    private String tempfield1;

    @Column (name="prbd_tempfield2", length=1, nullable = false)
    private String tempfield2;

    @Column (name="prbd_tempfield3", length=1, nullable = false)
    private String tempfield3;

    @Column (name="prbd_tempfield4", length=1, nullable = false)
    private String tempfield4;

    @Column (name="prbd_active", length=1, nullable = false)
    private Character active;

    @Column (name="prbd_createdby", length=8, nullable = false)
    private String createdby;

    @Column (name = "prbd_createdon", nullable = false)
    private LocalDateTime createdon;

    @Column (name="prbd_modifiedon", nullable = false)
    private LocalDateTime modifiedon;

    public String getKeyid() {
        return keyid;
    }

    public void setKeyid(String keyid) {
        this.keyid = keyid;
    }

    public String getPprd_keyid() {
        return pprd_keyid;
    }

    public void setPprd_keyid(String pprd_keyid) {
        this.pprd_keyid = pprd_keyid;
    }

    public Character getType() {
        return type;
    }

    public void setType(Character type) {
        this.type = type;
    }

    public String getPhenomena() {
        return phenomena;
    }

    public void setPhenomena(String phenomena) {
        this.phenomena = phenomena;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getRejectionreason() {
        return rejectionreason;
    }

    public void setRejectionreason(String rejectionreason) {
        this.rejectionreason = rejectionreason;
    }

    public String getDefect() {
        return defect;
    }

    public void setDefect(String defect) {
        this.defect = defect;
    }

    public Character getDefectsource() {
        return defectsource;
    }

    public void setDefectsource(Character defectsource) {
        this.defectsource = defectsource;
    }

    public String getEquipment() {
        return equipment;
    }

    public void setEquipment(String equipment) {
        this.equipment = equipment;
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

    public String getTempfield4() {
        return tempfield4;
    }

    public void setTempfield4(String tempfield4) {
        this.tempfield4 = tempfield4;
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
