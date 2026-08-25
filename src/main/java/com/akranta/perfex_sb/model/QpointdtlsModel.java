package com.akranta.perfex_sb.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigDecimal;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "qtm_tl_qpointdtls", schema = "public")
public class QpointdtlsModel implements Serializable {

    @Id
    @NotNull
    @Size(max = 15)
    @Column(name = "qptd_keyid", length = 15, nullable = false)
    private String keyid;

    @NotNull
    @Size(max = 15)
    @Column(name = "qptd_qptm_keyid", length = 15, nullable = false)
    private String qptm_keyid;

    @NotNull
    @Size(max = 500)
    @Column(name = "qptd_qpoint", length = 500, nullable = false)
    private String qpoint;

    @NotNull
    @Column(name = "qptd_nooflocations", nullable = false)
    private BigDecimal nooflocations;

    @NotNull
    @Column(name = "qptd_tempfield1", length = 1, nullable = false)
    private Character tempfield1;

    @NotNull
    @Column(name = "qptd_tempfield2", length = 1, nullable = false)
    private Character tempfield2;

    @NotNull
    @Column(name = "qptd_tempfield3", length = 1, nullable = false)
    private Character tempfield3;

    @NotNull
    @Column(name = "qptd_tempfield4", length = 1, nullable = false)
    private Character tempfield4;

    @NotNull
    @Column(name = "qptd_tempfield5", length = 1, nullable = false)
    private Character tempfield5;

    @NotNull
    @Size(max = 8)
    @Column(name = "qptd_createdby", length = 8, nullable = false)
    private String createdby;

    @NotNull
    @Column(name = "qptd_active", length = 1, nullable = false)
    private Character active;

    @NotNull
    @Column(name = "qptd_createdon", nullable = false)
    private LocalDateTime createdon;

    @NotNull
    @Column(name = "qptd_modifiedon", nullable = false)
    private LocalDateTime modifiedon;

    public QpointdtlsModel() {
    }

    public QpointdtlsModel(String keyid, String qptmKeyid, String qpoint, BigDecimal nooflocations,
            Character tempfield1, Character tempfield2, Character tempfield3, Character tempfield4, Character tempfield5,
            String createdby, Character active, @NotNull LocalDateTime createdon, @NotNull LocalDateTime modifiedon) {
        this.keyid = keyid;
        this.qptm_keyid = qptmKeyid;
        this.qpoint = qpoint;
        this.nooflocations = nooflocations;
        this.tempfield1 = tempfield1;
        this.tempfield2 = tempfield2;
        this.tempfield3 = tempfield3;
        this.tempfield4 = tempfield4;
        this.tempfield5 = tempfield5;
        this.createdby = createdby;
        this.active = active;
        this.createdon = createdon;
        this.modifiedon = modifiedon;
    }

    public String getKeyid() {
        return keyid;
    }

    public void setKeyid(String keyid) {
        this.keyid = keyid;
    }

    public String getQptm_keyid() {
        return qptm_keyid;
    }

    public void setQptm_keyid(String qptm_keyid) {
        this.qptm_keyid = qptm_keyid;
    }

    public String getQpoint() {
        return qpoint;
    }

    public void setQpoint(String qpoint) {
        this.qpoint = qpoint;
    }

    public BigDecimal getNooflocations() {
        return nooflocations;
    }

    public void setNooflocations(BigDecimal nooflocations) {
        this.nooflocations = nooflocations;
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

    @Override
    public int hashCode() {
        return Objects.hash(keyid);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        QpointdtlsModel other = (QpointdtlsModel) obj;
        return Objects.equals(keyid, other.keyid);
    }

    @Override
    public String toString() {
        return "qtm_tl_qpointdtlsModel [keyid=" + keyid + ", qptmKeyid=" + qptm_keyid + ", qpoint=" + qpoint
                + ", nooflocations=" + nooflocations + ", tempfield1=" + tempfield1 + ", tempfield2=" + tempfield2
                + ", tempfield3=" + tempfield3 + ", tempfield4=" + tempfield4 + ", tempfield5=" + tempfield5
                + ", createdby=" + createdby + ", active=" + active + ", createdon=" + createdon + ", modifiedon="
                + modifiedon + "]";
    }

}
