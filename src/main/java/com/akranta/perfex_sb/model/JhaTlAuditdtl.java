package com.akranta.perfex_sb.model;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.*;

@Entity
@Table(name = "jha_tl_auditdtl", schema = "public")
public class JhaTlAuditdtl {

    
    @Id
    @Column(name = "jhad_keyid", length = 12, nullable = false)
    private String keyid;

    @Column(name = "jhad_jhauditmasterid", length = 12, nullable = false)
    private String jhauditmasterid;
    

    @Column(name = "jhad_parameterid", length = 10, nullable = false)
    private String parameterid;

    @Column(name = "jhad_maximumpoints")
    private Integer maximumpoints;

    @Column(name = "jhad_pointsscored")
    private Integer pointsscored;

    @Column(name = "jhad_remarks", length = 4000, nullable = false)
    private String remarks;

    @Column(name = "jhad_ncremarks", length = 4000, nullable = false)
    private String ncremarks;

    @Column(name = "jhad_ncactionplan", length = 12, nullable = false)
    private String ncactionplan;


    @Column(name = "jhad_ncstatus", length = 1, nullable = false)
    private Character ncstatus;

    @Column(name = "jhad_ncclosed", length = 10, nullable = false)
    private String ncclosed;

    @Column(name = "jhad_tempfield1", length = 2, nullable = false)
    private String tempfield1;

    @Column(name = "jhad_tempfield2", length = 2, nullable = false)
    private String tempfield2;

    @Column(name = "jhad_tempfield3", length = 2, nullable = false)
    private String tempfield3;

    @Column(name = "jhad_tempfield4", length = 2, nullable = false)
    private String tempfield4;

    @Column(name = "jhad_tempfield5", length = 2, nullable = false)
    private String tempfield5;

    @Column(name = "jhad_active", length = 1, nullable = false)
    private Character active;

    @Column(name = "jhad_createdby", length = 10, nullable = false)
    private String createdby;


    @Column(name = "jhad_createdon", nullable = false)
    @CreationTimestamp
    private LocalDateTime createdon;

    @Column(name = "jhad_modifiedon", nullable = false)
    @UpdateTimestamp
    private LocalDateTime modifiedon;

    public String getKeyid() {
        return keyid;
    }

    public void setKeyid(String keyid) {
        this.keyid = keyid;
    }

    public String getJhauditmasterid() {
        return jhauditmasterid;
    }

    public void setJhauditmasterid(String jhauditmasterid) {
        this.jhauditmasterid = jhauditmasterid;
    }

    public String getParameterid() {
        return parameterid;
    }

    public void setParameterid(String parameterid) {
        this.parameterid = parameterid;
    }

    public Integer getMaximumpoints() {
        return maximumpoints;
    }

    public void setMaximumpoints(Integer maximumpoints) {
        this.maximumpoints = maximumpoints;
    }

    public Integer getPointsscored() {
        return pointsscored;
    }

    public void setPointsscored(Integer pointsscored) {
        this.pointsscored = pointsscored;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getNcremarks() {
        return ncremarks;
    }

    public void setNcremarks(String ncremarks) {
        this.ncremarks = ncremarks;
    }

    public String getNcactionplan() {
        return ncactionplan;
    }

    public void setNcactionplan(String ncactionplan) {
        this.ncactionplan = ncactionplan;
    }

    public Character getNcstatus() {
        return ncstatus;
    }

    public void setNcstatus(Character ncstatus) {
        this.ncstatus = ncstatus;
    }

    public String getNcclosed() {
        return ncclosed;
    }

    public void setNcclosed(String ncclosed) {
        this.ncclosed = ncclosed;
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
