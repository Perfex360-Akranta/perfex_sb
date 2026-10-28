package com.akranta.perfex_sb.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "gen_tl_otgrouter", schema = "public")
public class GenTlOtgRouter {

    @Id
    @Column(name = "otgr_keyid", length = 15, nullable = false)
    private String keyid;

    // Unique in DB (gen_tl_otgrouter_otgr_otgc_keyid_key)
    @Column(name = "otgr_otgc_keyid", length = 15, nullable = false, unique = true)
    private String otgcKeyid;

    @Column(name = "otgr_reftype", length = 15, nullable = false)
    private String reftype;

    @Column(name = "otgr_refkeyid", length = 20, nullable = false)
    private String refkeyid;

    @Column(name = "otgr_status", length = 15, nullable = false)
    private String status;

    @Column(name = "otgr_remarks", nullable = false, columnDefinition = "text")
    private String remarks;

    @Column(name = "otgr_tempfield1", length = 1, nullable = false)
    private String tempfield1 = "-";

    @Column(name = "otgr_tempfield2", length = 1, nullable = false)
    private String tempfield2 = "-";

    @Column(name = "otgr_tempfield3", length = 1, nullable = false)
    private String tempfield3 = "-";

    @Column(name = "otgr_tempfield4", length = 1, nullable = false)
    private String tempfield4 = "-";

    @Column(name = "otgr_tempfield5", length = 1, nullable = false)
    private String tempfield5 = "-";

    @Column(name = "otgr_createdby", length = 15, nullable = false)
    private String createdby;

    @Column(name = "otgr_createdon", nullable = false)
    private LocalDateTime createdon;

    @Column(name = "otgr_modifiedby", length = 15)
    private String modifiedby;

    @Column(name = "otgr_modifiedon")
    private LocalDateTime modifiedon;

    @PrePersist
    protected void onCreate() {
        if (createdon == null) {
            createdon = LocalDateTime.now();
        }
        applyDefaults();
    }

    @PreUpdate
    protected void onUpdate() {
        modifiedon = LocalDateTime.now();
    }

    private void applyDefaults() {
        if (tempfield1 == null) tempfield1 = "-";
        if (tempfield2 == null) tempfield2 = "-";
        if (tempfield3 == null) tempfield3 = "-";
        if (tempfield4 == null) tempfield4 = "-";
        if (tempfield5 == null) tempfield5 = "-";
    }

    // Getters and setters

    public String getKeyid() { return keyid; }
    public void setKeyid(String keyid) { this.keyid = keyid; }

    public String getOtgcKeyid() { return otgcKeyid; }
    public void setOtgcKeyid(String otgcKeyid) { this.otgcKeyid = otgcKeyid; }

    public String getReftype() { return reftype; }
    public void setReftype(String reftype) { this.reftype = reftype; }

    public String getRefkeyid() { return refkeyid; }
    public void setRefkeyid(String refkeyid) { this.refkeyid = refkeyid; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getRemarks() { return remarks; }
    public void setRemarks(String remarks) { this.remarks = remarks; }

    public String getTempfield1() { return tempfield1; }
    public void setTempfield1(String tempfield1) { this.tempfield1 = tempfield1; }

    public String getTempfield2() { return tempfield2; }
    public void setTempfield2(String tempfield2) { this.tempfield2 = tempfield2; }

    public String getTempfield3() { return tempfield3; }
    public void setTempfield3(String tempfield3) { this.tempfield3 = tempfield3; }

    public String getTempfield4() { return tempfield4; }
    public void setTempfield4(String tempfield4) { this.tempfield4 = tempfield4; }

    public String getTempfield5() { return tempfield5; }
    public void setTempfield5(String tempfield5) { this.tempfield5 = tempfield5; }

    public String getCreatedby() { return createdby; }
    public void setCreatedby(String createdby) { this.createdby = createdby; }

    public LocalDateTime getCreatedon() { return createdon; }
    public void setCreatedon(LocalDateTime createdon) { this.createdon = createdon; }

    public String getModifiedby() { return modifiedby; }
    public void setModifiedby(String modifiedby) { this.modifiedby = modifiedby; }

    public LocalDateTime getModifiedon() { return modifiedon; }
    public void setModifiedon(LocalDateTime modifiedon) { this.modifiedon = modifiedon; }
}