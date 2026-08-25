package com.akranta.perfex_sb.model;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.time.LocalDateTime;

@Entity
@Table(name = "plm_tl_conappraisalmstentry", schema = "public")
public class PlmTlConditionalappraisalmstentry {
    
    @Id
    @Column(name = "cdam_keyid", length = 10, nullable = false)
    private String keyid;

    @Column(name = "cdam_flid", length = 15)
    private String flid;

    @Column(name = "cdam_elementid", length = 500)
    private String elementid;

    @Column(name = "cdam_date")
    private LocalDateTime date;

    @Column(name = "cdam_tempfield1", length = 1)
    private String tempfield1;

    @Column(name = "cdam_tempfield2", length = 1)
    private String tempfield2;

    @Column(name = "cdam_tempfield3", length = 1)
    private String tempfield3;

    @Column(name = "cdam_tempfield4", length = 1)
    private String tempfield4;

    @Column(name = "cdam_tempfield5", length = 1)
    private String tempfield5;

    @Column(name = "cdam_active", length = 1)
    private Character active ;

    @Column(name = "cdam_createdby", length = 10)
    private String createdby;

    @Column(name = "cdam_createdon")
    @CreationTimestamp
    private LocalDateTime createdon = LocalDateTime.now();

    @Column(name = "cdam_modifiedon")
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

    public String getTempfield5() {
        return tempfield5;
    }

    public void setTempfield5(String tempfield5) {
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