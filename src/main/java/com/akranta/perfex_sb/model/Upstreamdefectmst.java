package com.akranta.perfex_sb.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "gen_tl_upstreamdefect_mst",schema="public")
public class Upstreamdefectmst {

    //upsm_keyid
    @Id
    @Column(name = "upsm_keyid", length = 15)
    private String keyid;

    //upsm_flid
    @Column(name = "upsm_flid", length = 12, nullable = false)
    private String flid;

    //upsm_elementid
    @Column(name = "upsm_elementid", length = 12, nullable = false)
    private String elementid;

    //upsm_date
    @Column(name = "upsm_date", nullable = false)
    private LocalDateTime date;

    //upsm_area
    @Column(name = "upsm_area", length = 200, nullable = false)
    private String area;

    //upsm_title
    @Column(name = "upsm_title", length = 500, nullable = false)
    private String title;

    //upsm_remakrs
    @Column(name = "upsm_remakrs", length = 500, nullable = false)
    private String remakrs;

    //upsm_inspectionlotno
    @Column(name = "upsm_inspectionlotno", length = 200, nullable = false)
    private String inspectionlotno;

    //upsm_tempfield1
    @Column(name = "upsm_tempfield1", length = 1)
    private Character tempfield1;

    //upsm_tempfield2
    @Column(name = "upsm_tempfield2", length = 1)
    private Character tempfield2;

    //upsm_tempfield3
    @Column(name = "upsm_tempfield3", length = 1)
    private Character tempfield3;

    //upsm_tempfield4
    @Column(name = "upsm_tempfield4", length = 1)
    private Character tempfield4;

    //upsm_tempfield5
    @Column(name = "upsm_tempfield5", length = 1)
    private Character tempfield5;

    //upsm_active
    @Column(name = "upsm_active", length = 1)
    private Character active;

    //upsm_createdby
    @Column(name = "upsm_createdby", length = 8)
    private String createdby;

    //upsm_createdon
    @Column(name = "upsm_createdon")
    private LocalDateTime createdon;

    //upsm_modifiedon
    @Column(name = "upsm_modifiedon")
    private LocalDateTime modifiedon;

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

    public String getElementid() {
        return elementid;
    }

    public void setElementid(String elementid) {
        this.elementid = elementid;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRemakrs() {
        return remakrs;
    }

    public void setRemakrs(String remakrs) {
        this.remakrs = remakrs;
    }

    public String getInspectionlotno() {
        return inspectionlotno;
    }

    public void setInspectionlotno(String inspectionlotno) {
        this.inspectionlotno = inspectionlotno;
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
