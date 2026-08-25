package com.akranta.perfex_sb.model;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDateTime;

@Entity
@Table(name = "gen_tl_mchsubmchlink", schema = "public")
public class GenTlMchsubmchlink {
    
    @Id
    @Column(name = "scml_parentmchid", length = 9, nullable = false)
    private String parentmchid;

    @Column(name = "scml_sectionid", length = 10, nullable = false)
    private String sectionid;

    @Column(name = "scml_cellid", length = 10, nullable = false)
    private String cellid;

    @Column(name = "scml_childmchid", length = 9, nullable = false)
    private String childmchid;

    @Column(name = "scml_createdon", nullable = false)
    @CreationTimestamp
    private LocalDateTime createdon = LocalDateTime.now();

    // Getters and Setters

    public String getParentmchid() {
        return parentmchid;
    }

    public void setParentmchid(String parentmchid) {
        this.parentmchid = parentmchid;
    }

    public String getSectionid() {
        return sectionid;
    }

    public void setSectionid(String sectionid) {
        this.sectionid = sectionid;
    }

    public String getCellid() {
        return cellid;
    }

    public void setCellid(String cellid) {
        this.cellid = cellid;
    }

    public String getChildmchid() {
        return childmchid;
    }

    public void setChildmchid(String childmchid) {
        this.childmchid = childmchid;
    }

    public LocalDateTime getCreatedon() {
        return createdon;
    }

    public void setCreatedon(LocalDateTime createdon) {
        this.createdon = createdon;
    }
}