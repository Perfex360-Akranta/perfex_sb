package com.akranta.perfex_sb.model;

import jakarta.persistence.*;   
import java.time.LocalDateTime;


@Entity
@Table(name = "BAL_PCS_TL_PCSBD", schema = "public")

public class BAL_PcsTlPcsBd {

    @Id
    @Column(name = "pbd_keyid")          
    private String keyid;

    @Column(name = "pbd_bdm_keyid")     
    private String bdmKeyid;

    @Column(name = "pbd_active")         
    private Character active;

    @Column(name = "pbd_createdon")      
    private LocalDateTime createdon;

    @Column(name = "pbd_createdby")      
     private String createdby;

    public String getKeyid() {
        return keyid;
    }

    public void setKeyid(String keyid) {
        this.keyid = keyid;
    }

    public String getBdmKeyid() {
        return bdmKeyid;
    }

    public void setBdmKeyid(String bdmKeyid) {
        this.bdmKeyid = bdmKeyid;
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

    public String getCreatedby() {
        return createdby;
    }

     public void setCreatedby(String createdby) {
        this.createdby = createdby;
    }
    
}