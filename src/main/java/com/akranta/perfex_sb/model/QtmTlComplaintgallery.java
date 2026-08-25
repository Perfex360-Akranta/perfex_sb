package com.akranta.perfex_sb.model;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.math.BigDecimal;
//import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "qtm_tl_complaintgallery", schema = "public")
public class QtmTlComplaintgallery {
    
    @Id
    @Column(name = "cmga_keyid", length = 15, nullable = false)
    private String keyid;

    @Column(name = "cmga_customerid", length = 8)
    private String customerid;

    @Column(name = "cmga_gradeproduct", length = 250)
    private String gradeproduct;

    @Column(name = "cmga_correctiveaction", length = 500)
    private String correctiveaction;

    @Column(name = "cmga_preventiveaction", length = 500)
    private String preventiveaction;

    @Column(name = "cmga_complaintdescription", length = 500)
    private String complaintdescription;

    @Column(name = "cmga_complaintdate")
    private LocalDateTime complaintdate;

    @Column(name = "cmga_manufacturedate")
    private LocalDateTime manufacturedate;

    @Column(name = "cmga_gradespecification", length = 25)
    private String gradespecification;

    @Column(name = "cmga_flid", length = 12)
    private String flid;

    @Column(name = "cmga_elementid", length = 500)
    private String elementid;

    @Column(name = "cmga_source", length = 1, nullable = false)
    private Character source;

    @Column(name = "cmga_defectid", length = 15, nullable = false)
    private String defectid;

    @Column(name = "cmga_defectqty", nullable = false)
    private BigDecimal defectqty;

    @Column(name = "cmga_tempfield1", length = 1, nullable = false)
    private Character tempfield1;

    @Column(name = "cmga_tempfield2", length = 1, nullable = false)
    private Character tempfield2;

    @Column(name = "cmga_tempfield3", length = 1, nullable = false)
    private Character tempfield3;

    @Column(name = "cmga_active", length = 1, nullable = false)
    private Character active = 'Y';

    @Column(name = "cmga_createdby", length = 10, nullable = false)
    private String createdby;

    @Column(name = "cmga_createdon", nullable = false)
    @CreationTimestamp
    private LocalDateTime createdon = LocalDateTime.now();

    @Column(name = "cmga_modifiedon", nullable = false)
    @UpdateTimestamp
    private LocalDateTime modifiedon = LocalDateTime.now();

    // Getters and Setters

    public String getKeyid() {
        return keyid;
    }

    public void setKeyid(String keyid) {
        this.keyid = keyid;
    }

    public String getCustomerid() {
        return customerid;
    }

    public void setCustomerid(String customerid) {
        this.customerid = customerid;
    }

    public String getGradeproduct() {
        return gradeproduct;
    }

    public void setGradeproduct(String gradeproduct) {
        this.gradeproduct = gradeproduct;
    }

    public String getCorrectiveaction() {
        return correctiveaction;
    }

    public void setCorrectiveaction(String correctiveaction) {
        this.correctiveaction = correctiveaction;
    }

    public String getPreventiveaction() {
        return preventiveaction;
    }

    public void setPreventiveaction(String preventiveaction) {
        this.preventiveaction = preventiveaction;
    }

    public String getComplaintdescription() {
        return complaintdescription;
    }

    public void setComplaintdescription(String complaintdescription) {
        this.complaintdescription = complaintdescription;
    }

    public LocalDateTime getComplaintdate() {
        return complaintdate;
    }

    public void setComplaintdate(LocalDateTime complaintdate) {
        this.complaintdate = complaintdate;
    }

    public LocalDateTime getManufacturedate() {
        return manufacturedate;
    }

    public void setManufacturedate(LocalDateTime manufacturedate) {
        this.manufacturedate = manufacturedate;
    }

    public String getGradespecification() {
        return gradespecification;
    }

    public void setGradespecification(String gradespecification) {
        this.gradespecification = gradespecification;
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

    public Character getSource() {
        return source;
    }

    public void setSource(Character source) {
        this.source = source;
    }

    public String getDefectid() {
        return defectid;
    }

    public void setDefectid(String defectid) {
        this.defectid = defectid;
    }

    public BigDecimal getDefectqty() {
        return defectqty;
    }

    public void setDefectqty(BigDecimal defectqty) {
        this.defectqty = defectqty;
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