package com.akranta.perfex_sb.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "JHA_TL_VISUALSOPDTL", schema = "public")
public class Visualsopdtl {

    @Id
    @Column(name = "vsod_keyid", length = 15)
    private String keyid;

    @Column(name = "vsod_vsom_keyid", length = 15, nullable = false)
    private String vsom_keyid;

    @Column(name = "vsod_instruction", length = 500, nullable = false)
    private String instruction;

    @Column(name = "vsod_keypoint", length = 500, nullable = false)
    private String keypoint;

    @Column(name = "vsod_importanceofkeypoint", length = 500, nullable = false)
    private String importanceofkeypoint;

    @Column(name = "vsod_toolused", length = 500, nullable = false)
    private String toolused;

    @Column(name = "vsod_imgtoolused", length = 100, nullable = false)
    private String imgtoolused;

    @Column(name = "vsod_imgppe", length = 100, nullable = false)
    private String imgppe;

    @Column(name = "vsod_tempfield1", length = 1, nullable = false)
    private Character tempfield1;

    @Column(name = "vsod_tempfield2", length = 1, nullable = false)
    private Character tempfield2;

    @Column(name = "vsod_tempfield3", length = 1, nullable = false)
    private Character tempfield3;

    @Column(name = "vsod_tempfield4", length = 1, nullable = false)
    private Character tempfield4;

    @Column(name = "vsod_tempfield5", length = 1, nullable = false)
    private Character tempfield5;

    @Column(name = "vsod_active", length = 1, nullable = false)
    private Character active;

    @Column(name = "vsod_createdby", length = 10, nullable = false)
    private String createdby;

    @Column(name = "vsod_createdon", nullable = false)
    private LocalDateTime createdon;

    @Column(name = "vsod_modifiedon", nullable = false)
    private LocalDateTime modifiedon;

    public String getKeyid() {
        return keyid;
    }

    public void setKeyid(String keyid) {
        this.keyid = keyid;
    }

    public String getVsom_keyid() {
        return vsom_keyid;
    }

    public void setVsom_keyid(String vsom_keyid) {
        this.vsom_keyid = vsom_keyid;
    }

    public String getInstruction() {
        return instruction;
    }

    public void setInstruction(String instruction) {
        this.instruction = instruction;
    }

    public String getKeypoint() {
        return keypoint;
    }

    public void setKeypoint(String keypoint) {
        this.keypoint = keypoint;
    }

    public String getImportanceofkeypoint() {
        return importanceofkeypoint;
    }

    public void setImportanceofkeypoint(String importanceofkeypoint) {
        this.importanceofkeypoint = importanceofkeypoint;
    }

    public String getToolused() {
        return toolused;
    }

    public void setToolused(String toolused) {
        this.toolused = toolused;
    }

    public String getImgtoolused() {
        return imgtoolused;
    }

    public void setImgtoolused(String imgtoolused) {
        this.imgtoolused = imgtoolused;
    }

    public String getImgppe() {
        return imgppe;
    }

    public void setImgppe(String imgppe) {
        this.imgppe = imgppe;
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

    

    // getters and setters
}



