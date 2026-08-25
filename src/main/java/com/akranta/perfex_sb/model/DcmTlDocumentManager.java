    package com.akranta.perfex_sb.model;

    import java.sql.Types;
import java.time.LocalDateTime;

import org.hibernate.annotations.JdbcTypeCode;

import jakarta.persistence.Column;
    import jakarta.persistence.Entity;
    import jakarta.persistence.Id;
    import jakarta.persistence.Lob;
    import jakarta.persistence.Table;

    @Entity
    @Table(name = "DCM_TL_DOCUMENTMANAGER", schema = "public")
    public class DcmTlDocumentManager {

        @Id
        @Column(name = "dmdm_keyid", length = 15, nullable = false)
        private String keyid;

        @Column(name = "dmdm_refdocno", length = 20)
        private String refdocno;

        @Column(name = "dmdm_refdoctype", length = 6)
        private String refdoctype;

        @Column(name = "dmdm_isodoctype", length = 15)
        private String isodoctype;

        @Column(name = "dmdm_slno", nullable = false)
        private Integer slno;

        @Column(name = "dmdm_filename", length = 100, nullable = false)
        private String filename;

        @Column(name = "dmdm_description", length = 3000, nullable = false)
        private String description;

        @Column(name = "dmdm_keywords", length = 3000, nullable = false)
        private String keywords;

        @Column(name = "dmdm_bloblength", nullable = false)
        private Integer bloblength;

    
        // @Lob
        // @Column(name = "dmdm_blobfile")
        // private byte[] blobfile;

        @Column(name = "dmdm_blobfile")
        @JdbcTypeCode(Types.BINARY) // Forces mapping to VARBINARY / bytea instead of OID
        private byte[] blobfile;

        @Column(name = "dmdm_category", length = 8, nullable = false)
        private String category;

        @Column(name = "dmdm_owner", length = 8, nullable = false)
        private String owner;

        @Column(name = "dmdm_approvedby", length = 8, nullable = false)
        private String approvedby;

        @Column(name = "dmdm_subjectarea", length = 8, nullable = false)
        private String subjectarea;

        @Column(name = "dmdm_title", length = 100, nullable = false)
        private String title;

        @Column(name = "dmdm_path", length = 100, nullable = false)
        private String path;

        @Column(name = "dmdm_type", length = 10)
        private String type;

        @Column(name = "dmdm_active", length = 1, columnDefinition = "CHAR(1)")
        private Character active;

        @Column(name = "dmdm_createdby", length = 8)
        private String createdby;

        @Column(name = "dmdm_createdon")
        private LocalDateTime createdon;

        @Column(name = "dmdm_modifiedon")
        private LocalDateTime modifiedon;

        public String getKeyid() {
            return keyid;
        }

        public void setKeyid(String keyid) {
            this.keyid = keyid;
        }

        public String getRefdocno() {
            return refdocno;
        }

        public void setRefdocno(String refdocno) {
            this.refdocno = refdocno;
        }

        public String getRefdoctype() {
            return refdoctype;
        }

        public void setRefdoctype(String refdoctype) {
            this.refdoctype = refdoctype;
        }

        public String getIsodoctype() {
            return isodoctype;
        }

        public void setIsodoctype(String isodoctype) {
            this.isodoctype = isodoctype;
        }

        public Integer getSlno() {
            return slno;
        }

        public void setSlno(Integer slno) {
            this.slno = slno;
        }

        public String getFilename() {
            return filename;
        }

        public void setFilename(String filename) {
            this.filename = filename;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getKeywords() {
            return keywords;
        }

        public void setKeywords(String keywords) {
            this.keywords = keywords;
        }

        public Integer getBloblength() {
            return bloblength;
        }

        public void setBloblength(Integer bloblength) {
            this.bloblength = bloblength;
        }

      
        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public String getOwner() {
            return owner;
        }

        public void setOwner(String owner) {
            this.owner = owner;
        }

        public String getApprovedby() {
            return approvedby;
        }

        public void setApprovedby(String approvedby) {
            this.approvedby = approvedby;
        }

        public String getSubjectarea() {
            return subjectarea;
        }

        public void setSubjectarea(String subjectarea) {
            this.subjectarea = subjectarea;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
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

        public byte[] getBlobfile() {
            return blobfile;
        }

        public void setBlobfile(byte[] blobfile) {
            this.blobfile = blobfile;
        }

        

    
    }