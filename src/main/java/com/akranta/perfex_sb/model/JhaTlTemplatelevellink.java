package com.akranta.perfex_sb.model;

import jakarta.persistence.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;





@Entity
@IdClass(JhaTlTemplatelevellink.TemplateLevelLinkId.class)
@Table(name = "jha_tl_templatelevellink", schema = "public")
public class JhaTlTemplatelevellink  {


    @Id
    @Column(name = "jtll_templateid", length = 10, nullable = false)
    private String templateid;

    @Id
    @Column(name = "jtll_auditlevelid", length = 10, nullable = false)
    private String auditlevelid;

    @Column(name = "jtll_minimumpoints")
    private BigDecimal minimumpoints;

    @Column(name = "jtll_tempfield1", length = 2)
    private String tempfield1;

    @Column(name = "jtll_tempfield2", length = 2)
    private String tempfield2;

    @Column(name = "jtll_tempfield3", length = 2)
    private String tempfield3;

    @Column(name = "jtll_tempfield4", length = 2)
    private String tempfield4;

    @Column(name = "jtll_tempfield5", length = 2)
    private String tempfield5;

    @Column(name = "jtll_active", length = 1)
    private Character active;

    @Column(name = "jtll_createdby", length = 10,updatable = false)
    private String createdby;

    @Column(name = "jtll_createdon")
    private LocalDateTime createdon;

    @Column(name = "jtll_modifiedon")
    private LocalDateTime modifiedon;



    
    public static class TemplateLevelLinkId implements Serializable {
    private String templateid;
     private String auditlevelid;

     public TemplateLevelLinkId() {}

 public TemplateLevelLinkId(String templateId, String auditLevelId) {
            this.templateid = templateId;
            this.auditlevelid = auditLevelId;
           
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof TemplateLevelLinkId)) return false;
            TemplateLevelLinkId that = (TemplateLevelLinkId) o;
            return  Objects.equals(templateid, that.templateid)
                && Objects.equals(auditlevelid, that.auditlevelid);
        }

        @Override
        public int hashCode() {
            return Objects.hash(templateid, auditlevelid);
        }

 public String getTemplateid() {
    return templateid;
 }

 public void setTemplateid(String templateid) {
    this.templateid = templateid;
 }

 public String getAuditlevelid() {
    return auditlevelid;
 }

 public void setAuditlevelid(String auditLevelid) {
    this.auditlevelid = auditLevelid;
 }
        
    }



    public String getTemplateid() {
        return templateid;
    }



    public void setTemplateid(String templateid) {
        this.templateid = templateid;
    }



    public String getAuditlevelid() {
        return auditlevelid;
    }



    public void setAuditlevelid(String auditLevelid) {
        this.auditlevelid = auditLevelid;
    }



    public BigDecimal getMinimumpoints() {
        return minimumpoints;
    }



    public void setMinimumpoints(BigDecimal minimumpoints) {
        this.minimumpoints = minimumpoints;
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



    public void Character(Character active) {
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



    public void setJtllJhstepid(String string) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setJtllJhstepid'");
    }

    

   

   

  
}