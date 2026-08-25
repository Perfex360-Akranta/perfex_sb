package com.akranta.perfex_sb.dto;

import com.fasterxml.jackson.annotation.JsonAlias;

import java.time.LocalDateTime;

public class FishboneChildSaveRequest {

    @JsonAlias({"fisdKeyId", "detailId"})
    private String fisdKeyid;

    @JsonAlias({"fisdFismKeyid", "masterId", "fismKeyid"})
    private String fisdFismKeyid;

    @JsonAlias({"fisdCause", "cause"})
    private String fisdCause;

    @JsonAlias({"parentId", "fisdParentId"})
    private String fisdParentid;

    private Integer fisdOrderno;
    private Integer fisdLevelno;
    private String fisdTempfield1;
    private String fisdTempfield2;
    private String fisdTempfield3;
    private String fisdTempfield4;
    private String fisdTempfield5;
    private String fisdActive;
    private String fisdCreatedby;
    private LocalDateTime fisdCreatedon;
    private LocalDateTime fisdModifiedon;
    private String fisdRemarks;

    @JsonAlias({"editval", "editFlag"})
    private String editMode;

    @JsonAlias({"fismParent", "updateCause"})
    private String updateCause;

    // extra flags from legacy servlet flow
    @JsonAlias({"detlid", "detlIdFlag"})
    private String detlIdFlag; // "detlid" for child, "Smelvl" for same-level

    @JsonAlias({"DtlId", "detailId"})
    private String detailId;   // parent detail id when detlIdFlag == detlid

    @JsonAlias({"level"})
    private String levelFlag;  // "level" when adding under root

    public String getFisdKeyid() {
        return fisdKeyid;
    }

    public void setFisdKeyid(String fisdKeyid) {
        this.fisdKeyid = fisdKeyid;
    }

    public String getFisdFismKeyid() {
        return fisdFismKeyid;
    }

    public void setFisdFismKeyid(String fisdFismKeyid) {
        this.fisdFismKeyid = fisdFismKeyid;
    }

    public String getFisdCause() {
        return fisdCause;
    }

    public void setFisdCause(String fisdCause) {
        this.fisdCause = fisdCause;
    }

    public String getFisdParentid() {
        return fisdParentid;
    }

    public void setFisdParentid(String fisdParentid) {
        this.fisdParentid = fisdParentid;
    }

    public Integer getFisdOrderno() {
        return fisdOrderno;
    }

    public void setFisdOrderno(Integer fisdOrderno) {
        this.fisdOrderno = fisdOrderno;
    }

    public Integer getFisdLevelno() {
        return fisdLevelno;
    }

    public void setFisdLevelno(Integer fisdLevelno) {
        this.fisdLevelno = fisdLevelno;
    }

    public String getFisdTempfield1() {
        return fisdTempfield1;
    }

    public void setFisdTempfield1(String fisdTempfield1) {
        this.fisdTempfield1 = fisdTempfield1;
    }

    public String getFisdTempfield2() {
        return fisdTempfield2;
    }

    public void setFisdTempfield2(String fisdTempfield2) {
        this.fisdTempfield2 = fisdTempfield2;
    }

    public String getFisdTempfield3() {
        return fisdTempfield3;
    }

    public void setFisdTempfield3(String fisdTempfield3) {
        this.fisdTempfield3 = fisdTempfield3;
    }

    public String getFisdTempfield4() {
        return fisdTempfield4;
    }

    public void setFisdTempfield4(String fisdTempfield4) {
        this.fisdTempfield4 = fisdTempfield4;
    }

    public String getFisdTempfield5() {
        return fisdTempfield5;
    }

    public void setFisdTempfield5(String fisdTempfield5) {
        this.fisdTempfield5 = fisdTempfield5;
    }

    public String getFisdActive() {
        return fisdActive;
    }

    public void setFisdActive(String fisdActive) {
        this.fisdActive = fisdActive;
    }

    public String getFisdCreatedby() {
        return fisdCreatedby;
    }

    public void setFisdCreatedby(String fisdCreatedby) {
        this.fisdCreatedby = fisdCreatedby;
    }

    public LocalDateTime getFisdCreatedon() {
        return fisdCreatedon;
    }

    public void setFisdCreatedon(LocalDateTime fisdCreatedon) {
        this.fisdCreatedon = fisdCreatedon;
    }

    public LocalDateTime getFisdModifiedon() {
        return fisdModifiedon;
    }

    public void setFisdModifiedon(LocalDateTime fisdModifiedon) {
        this.fisdModifiedon = fisdModifiedon;
    }

    public String getFisdRemarks() {
        return fisdRemarks;
    }

    public void setFisdRemarks(String fisdRemarks) {
        this.fisdRemarks = fisdRemarks;
    }

    public String getEditMode() {
        return editMode;
    }

    public void setEditMode(String editMode) {
        this.editMode = editMode;
    }

    public String getUpdateCause() {
        return updateCause;
    }

    public void setUpdateCause(String updateCause) {
        this.updateCause = updateCause;
    }

    public String getDetlIdFlag() {
        return detlIdFlag;
    }

    public void setDetlIdFlag(String detlIdFlag) {
        this.detlIdFlag = detlIdFlag;
    }

    public String getDetailId() {
        return detailId;
    }

    public void setDetailId(String detailId) {
        this.detailId = detailId;
    }

    public String getLevelFlag() {
        return levelFlag;
    }

    public void setLevelFlag(String levelFlag) {
        this.levelFlag = levelFlag;
    }
}
