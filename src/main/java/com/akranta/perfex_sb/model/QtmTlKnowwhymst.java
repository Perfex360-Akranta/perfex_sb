package com.akranta.perfex_sb.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "qtm_tl_knowwhymst", schema = "public")
public class QtmTlKnowwhymst {
    @Column(name = "knwm_keyid", length = 15, nullable = false)
    @Id
    private String keyid;

    @Column(name = "knwm_flid", length = 15, nullable = false)
    private String flid;

    @Column(name = "knwm_elementid", length = 250, nullable = false)
    private String elementid;

    @Column(name = "knwm_pillarid", length = 6, nullable = false)
    private String pillarid;

    @Column(name = "knwm_type", length = 50, nullable = false)
    private String type;

    @Column(name = "knwm_developedby", length = 8, nullable = false)
    private String developedby;

    @Column(name = "knwm_approvedby", length = 8, nullable = false)
    private String approvedby;

    @Column(name = "knwm_quality", length = 500, nullable = false)
    private String quality;

    @Column(name = "knwm_description", length = 500, nullable = false)
    private String description;

    @Column(name = "knwm_image", length = 200, nullable = false)
    private String image;

    @Column(name = "knwm_phenomena", length = 10, nullable = false)
    private String phenomena;

    @Column(name = "knwm_prepareddate", nullable = false)
    private LocalDateTime prepareddate;

    @Column(name = "knwm_versionno", nullable = false)
    private BigDecimal versionno;

    @Column(name = "knwm_versiondate", nullable = false)
    private LocalDateTime versiondate;

    @Column(name = "knwm_tempfield5", columnDefinition = "CHAR(1)", nullable = false)
    private Character tempfield5;

    @Column(name = "knwm_createdby", length = 8, nullable = false)
    private String createdby;

    @Column(name = "knwm_active", columnDefinition = "CHAR(1)", nullable = false)
    private Character active;

    @Column(name = "knwm_createdon", length = 15, nullable = false)
    private LocalDateTime createdon;

    @Column(name = "knwm_modifiedon", length = 15, nullable = false)
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

    public String getPillarid() {
        return pillarid;
    }

    public void setPillarid(String pillarid) {
        this.pillarid = pillarid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDevelopedby() {
        return developedby;
    }

    public void setDevelopedby(String developedby) {
        this.developedby = developedby;
    }

    public String getApprovedby() {
        return approvedby;
    }

    public void setApprovedby(String approvedby) {
        this.approvedby = approvedby;
    }

    public String getQuality() {
        return quality;
    }

    public void setQuality(String quality) {
        this.quality = quality;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPhenomena() {
        return phenomena;
    }

    public void setPhenomena(String phenomena) {
        this.phenomena = phenomena;
    }

    public LocalDateTime getPrepareddate() {
        return prepareddate;
    }

    public void setPrepareddate(LocalDateTime prepareddate) {
        this.prepareddate = prepareddate;
    }

    public BigDecimal getVersionno() {
        return versionno;
    }

    public void setVersionno(BigDecimal versionno) {
        this.versionno = versionno;
    }

    public LocalDateTime getVersiondate() {
        return versiondate;
    }

    public void setVersiondate(LocalDateTime versiondate) {
        this.versiondate = versiondate;
    }

    public Character getTempfield5() {
        return tempfield5;
    }

    public void setTempfield5(Character tempfield5) {
        this.tempfield5 = tempfield5;
    }

    public String getCreatedby() {
        return createdby;
    }

    public void setCreatedby(String createdby) {
        this.createdby = createdby;
    }

    public Character getActive() {
        return active;
    }

    public void setActive(Character active) {
        this.active = active;
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
