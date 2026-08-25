package com.akranta.perfex_sb.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "plm_tl_equipmentfmeamst", schema = "public")
public class PlmtlEquipmentfmeaMST {

    @Id
    @Column(name = "fmeq_keyid", length = 15, nullable = false)
    private String keyid;

    @Column(name = "fmeq_flid", length = 15, nullable = false)
    private String flid;

    @Column(name = "fmeq_date", nullable = false)
    private LocalDateTime date;

    @Column(name = "fmeq_no", length = 50, nullable = false)
    private String no;

    @Column(name = "fmeq_equipid", length = 15, nullable = false)
    private String equipid;

    @Column(name = "fmeq_supequipid", length = 15, nullable = false)
    private String supequipid;

    @Column(name = "fmeq_preparedby", length = 15, nullable = false)
    private String preparedby;

    @Column(name = "fmeq_coreteam", length = 250, nullable = false)
    private String coreteam;

    @Column(name = "fmeq_doctype", length = 5, nullable = false)
    private String doctype;

    @Column(name = "fmeq_docmstid", length = 15, nullable = false)
    private String docmstid;

    @Column(name = "fmdm_docdtlsid", length = 15, nullable = false)
    private String docdtlsid;

    @Column(name = "fmeq_tempfield1", columnDefinition = "char(1)", nullable = false)
    private Character tempfield1;

    @Column(name = "fmeq_tempfield2", columnDefinition = "char(1)", nullable = false)
    private Character tempfield2;

    @Column(name = "fmeq_tempfield3", columnDefinition = "char(1)", nullable = false)
    private Character tempfield3;

    @Column(name = "fmeq_tempfield4", columnDefinition = "char(1)", nullable = false)
    private Character tempfield4;

    @Column(name = "fmeq_tempfield5", columnDefinition = "char(1)", nullable = false)
    private Character tempfield5;

    @Column(name = "fmeq_active", columnDefinition = "char(1)", nullable = false)
    private Character active;

    @Column(name = "fmeq_createdby", length = 15, nullable = false)
    private String createdby;

    @Column(name = "fmeq_createdon", nullable = false)
    @CreationTimestamp
    private LocalDateTime createdon;

    @Column(name = "fmeq_modifiedon", nullable = false)
    @UpdateTimestamp
    private LocalDateTime modifiedon;

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

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getEquipid() {
        return equipid;
    }

    public void setEquipid(String equipid) {
        this.equipid = equipid;
    }

    public String getSupequipid() {
        return supequipid;
    }

    public void setSupequipid(String supequipid) {
        this.supequipid = supequipid;
    }

    public String getPreparedby() {
        return preparedby;
    }

    public void setPreparedby(String preparedby) {
        this.preparedby = preparedby;
    }

    public String getCoreteam() {
        return coreteam;
    }

    public void setCoreteam(String coreteam) {
        this.coreteam = coreteam;
    }

    public String getDoctype() {
        return doctype;
    }

    public void setDoctype(String doctype) {
        this.doctype = doctype;
    }

    public String getDocmstid() {
        return docmstid;
    }

    public void setDocmstid(String docmstid) {
        this.docmstid = docmstid;
    }

    public String getDocdtlsid() {
        return docdtlsid;
    }

    public void setDocdtlsid(String docdtlsid) {
        this.docdtlsid = docdtlsid;
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
