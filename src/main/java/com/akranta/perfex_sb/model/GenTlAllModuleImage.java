package com.akranta.perfex_sb.model;

import java.time.LocalDateTime;

public class GenTlAllModuleImage {

    private String refkeyid;
    private String refdoctype;
    private String imagetype;

    private byte[] blobimage;
    private long bloblength;

    private String filename;
    private String tempfield1;
    private String tempfield2;

    private LocalDateTime modifiedon;

    public String getRefkeyid() {
        return refkeyid;
    }

    public void setRefkeyid(String refkeyid) {
        this.refkeyid = refkeyid;
    }

    public String getRefdoctype() {
        return refdoctype;
    }

    public void setRefdoctype(String refdoctype) {
        this.refdoctype = refdoctype;
    }

    public String getImagetype() {
        return imagetype;
    }

    public void setImagetype(String imagetype) {
        this.imagetype = imagetype;
    }

    public byte[] getBlobimage() {
        return blobimage;
    }

    public void setBlobimage(byte[] blobimage) {
        this.blobimage = blobimage;
    }

    public long getBloblength() {
        return bloblength;
    }

    public void setBloblength(long bloblength) {
        this.bloblength = bloblength;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
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

    public LocalDateTime getModifiedon() {
        return modifiedon;
    }

    public void setModifiedon(LocalDateTime modifiedon) {
        this.modifiedon = modifiedon;
    }


    

}
