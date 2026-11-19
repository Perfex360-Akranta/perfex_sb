package com.akranta.perfex_sb.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "gen_tl_otgcapture", schema = "public")
public class GenTlOtgCapture {

    @Id
    @Column(name = "otgc_keyid", length = 15, nullable = false)
    private String keyid;

    @Column(name = "otgc_title", length = 250, nullable = false)
    private String title;

    @Column(name = "otgc_description", nullable = false, columnDefinition = "text")
    private String description;

    @Column(name = "otgc_remarks", nullable = false, columnDefinition = "text")
    private String remarks;

    @Column(name = "otgc_flid", length = 15, nullable = false)
    private String flid;

    @Column(name = "otgc_empm_keyid", length = 15, nullable = false)
    private String empmKeyid;

    @Column(name = "otgc_entrydate", nullable = false)
    private LocalDateTime entrydate;

    @Column(name = "otgc_priority", length = 15, nullable = false)
    private String priority = "M";

    @Column(name = "otgc_status", length = 15, nullable = false)
    private String status = "N";

    @Column(name = "otgc_type", length = 10)
    private String type;

    @Column(name = "otgc_tempfield1", length = 1, nullable = false)
    private String tempfield1 = "-";

    @Column(name = "otgc_tempfield2", length = 1, nullable = false)
    private String tempfield2 = "-";

    @Column(name = "otgc_tempfield3", length = 1, nullable = false)
    private String tempfield3 = "-";

    @Column(name = "otgc_tempfield4", length = 1, nullable = false)
    private String tempfield4 = "-";

    @Column(name = "otgc_tempfield5", length = 1, nullable = false)
    private String tempfield5 = "-";

    @Column(name = "otgc_tempfield6", length = 1, nullable = false)
    private String tempfield6 = "-";

    @Column(name = "otgc_tempfield7", length = 1, nullable = false)
    private String tempfield7 = "-";

    @Column(name = "otgc_tempfield8", length = 1, nullable = false)
    private String tempfield8 = "-";

    @Column(name = "otgc_tempfield9", length = 1, nullable = false)
    private String tempfield9 = "-";

    @Column(name = "otgc_tempfield10", length = 1, nullable = false)
    private String tempfield10 = "-";

    @Column(name = "otgc_createdby", length = 15, nullable = false)
    private String createdby;

    @Column(name = "otgc_createdon", nullable = false)
    private LocalDateTime createdon;

    @Column(name = "otgc_modifiedby", length = 15)
    private String modifiedby;

    @Column(name = "otgc_modifiedon")
    private LocalDateTime modifiedon;

    @PrePersist
    protected void onCreate() {
        LocalDateTime now = LocalDateTime.now();

        if (entrydate == null) {
            entrydate = now;
        }
        if (createdon == null) {
            createdon = now;
        }

        applyDefaults();
    }

    @PreUpdate
    protected void onUpdate() {
        modifiedon = LocalDateTime.now();
    }

    private void applyDefaults() {
        if (priority == null) priority = "M";
        if (status == null) status = "N";
        if (tempfield1 == null) tempfield1 = "-";
        if (tempfield2 == null) tempfield2 = "-";
        if (tempfield3 == null) tempfield3 = "-";
        if (tempfield4 == null) tempfield4 = "-";
        if (tempfield5 == null) tempfield5 = "-";
        if (tempfield6 == null) tempfield6 = "-";
        if (tempfield7 == null) tempfield7 = "-";
        if (tempfield8 == null) tempfield8 = "-";
        if (tempfield9 == null) tempfield9 = "-";
        if (tempfield10 == null) tempfield10 = "-";
    }

    // Getters and setters

    public String getKeyid() { return keyid; }
    public void setKeyid(String keyid) { this.keyid = keyid; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getRemarks() { return remarks; }
    public void setRemarks(String remarks) { this.remarks = remarks; }

    public String getFlid() { return flid; }
    public void setFlid(String flid) { this.flid = flid; }

    public String getEmpmKeyid() { return empmKeyid; }
    public void setEmpmKeyid(String empmKeyid) { this.empmKeyid = empmKeyid; }

    public LocalDateTime getEntrydate() { return entrydate; }
    public void setEntrydate(LocalDateTime entrydate) { this.entrydate = entrydate; }

    public String getPriority() { return priority; }
    public void setPriority(String priority) { this.priority = priority; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

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

    public String getTempfield6() { return tempfield6; }
    public void setTempfield6(String tempfield6) { this.tempfield6 = tempfield6; }

    public String getTempfield7() { return tempfield7; }
    public void setTempfield7(String tempfield7) { this.tempfield7 = tempfield7; }

    public String getTempfield8() { return tempfield8; }
    public void setTempfield8(String tempfield8) { this.tempfield8 = tempfield8; }

    public String getTempfield9() { return tempfield9; }
    public void setTempfield9(String tempfield9) { this.tempfield9 = tempfield9; }

    public String getTempfield10() { return tempfield10; }
    public void setTempfield10(String tempfield10) { this.tempfield10 = tempfield10; }

    public String getCreatedby() { return createdby; }
    public void setCreatedby(String createdby) { this.createdby = createdby; }

    public LocalDateTime getCreatedon() { return createdon; }
    public void setCreatedon(LocalDateTime createdon) { this.createdon = createdon; }

    public String getModifiedby() { return modifiedby; }
    public void setModifiedby(String modifiedby) { this.modifiedby = modifiedby; }

    public LocalDateTime getModifiedon() { return modifiedon; }
    public void setModifiedon(LocalDateTime modifiedon) {
        this.modifiedon = modifiedon;
    }
}