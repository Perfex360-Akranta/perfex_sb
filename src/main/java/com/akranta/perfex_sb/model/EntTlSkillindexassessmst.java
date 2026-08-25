package com.akranta.perfex_sb.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "ENT_TL_SKILLINDEXASSESSMST", schema = "public")
public class EntTlSkillindexassessmst 
{
    
    
    @Column(name = "siam_keyid", length = 15, nullable = false)
    @Id
    private String keyid;

    @Column(name = "siam_flid", length = 12, nullable = false)
    private String flid;

    @Column(name = "siam_uniqueposid", length = 15, nullable = false)
    private String uniqueposid;

    @Column(name = "siam_tempfiled1", length = 10, nullable = false)
    private String tempfiled1;

    @Column(name = "siam_reviewdate", length = 15, nullable = false)
    private LocalDateTime reviewdate;

    @Column(name = "siam_tempfiled2", length = 1,columnDefinition = "CHAR(1)", nullable = false)
    private Character tempfiled2;

    @Column(name = "siam_tempfiled3", length = 1,columnDefinition = "CHAR(1)", nullable = false)
    private Character tempfiled3;

    @Column(name = "siam_tempfiled4", length = 1,columnDefinition = "CHAR(1)", nullable = false)
    private Character tempfiled4;

    @Column(name = "siam_tempfiled5", length = 1, columnDefinition = "CHAR(1)", nullable = false)
    private Character tempfiled5;

    @Column(name = "siam_active", length = 1,columnDefinition = "CHAR(1)", nullable = false)
    private Character active;

    @Column(name = "siam_createdby", length = 8, nullable = false)
    private String createdby;

    @Column(name = "siam_createdon", nullable = false)
    private LocalDateTime createdon;

    @Column(name = "siam_modifiedon", nullable = false)
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

    public String getUniqueposid() {
        return uniqueposid;
    }

    public void setUniqueposid(String uniqueposid) {
        this.uniqueposid = uniqueposid;
    }

    public String getTempfiled1() {
        return tempfiled1;
    }

    public void setTempfiled1(String tempfiled1) {
        this.tempfiled1 = tempfiled1;
    }

    public LocalDateTime getReviewdate() {
        return reviewdate;
    }

    public void setReviewdate(LocalDateTime reviewdate) {
        this.reviewdate = reviewdate;
    }

    public Character getTempfiled2() {
        return tempfiled2;
    }

    public void setTempfiled2(Character tempfiled2) {
        this.tempfiled2 = tempfiled2;
    }

    public Character getTempfiled3() {
        return tempfiled3;
    }

    public void setTempfiled3(Character tempfiled3) {
        this.tempfiled3 = tempfiled3;
    }

    public Character getTempfiled4() {
        return tempfiled4;
    }

    public void setTempfiled4(Character tempfiled4) {
        this.tempfiled4 = tempfiled4;
    }

    public Character getTempfiled5() {
        return tempfiled5;
    }

    public void setTempfiled5(Character tempfiled5) {
        this.tempfiled5 = tempfiled5;
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
