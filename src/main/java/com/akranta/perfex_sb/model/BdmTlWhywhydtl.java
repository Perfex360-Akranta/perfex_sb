package com.akranta.perfex_sb.model;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
//import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "bdm_tl_whywhydtl", schema = "public")
public class BdmTlWhywhydtl {
    
    @Id
    @Column(name = "wwdt_keyid", length = 12, nullable = false)
    private String keyid;

    @Column(name = "wwdt_wwms_keyid", length = 12)
    private String wwmsKeyid;

    @Column(name = "wwdt_slno")
    private Integer slno;

    @Column(name = "wwdt_why", length = 500)
    private String why;

    @Column(name = "wwdt_answer", length = 500)
    private String answer;

    @Column(name = "wwdt_action", length = 500)
    private String action;

    @Column(name = "wwdt_createdby", length = 8)
    private String createdby;

    @Column(name = "wwdt_createdon")
    @CreationTimestamp
    private LocalDateTime createdon;

    @Column(name = "wwdt_modifiedon")
    @UpdateTimestamp
    private LocalDateTime modifiedon;

    // ============================================
    // EXPLICIT GETTER AND SETTER METHODS
    // ============================================

    // Key ID
    public String getKeyid() { 
        return keyid; 
    }
    public void setKeyid(String keyid) { 
        this.keyid = keyid; 
    }

    // WWMS Key ID
    public String getWwmsKeyid() { 
        return wwmsKeyid; 
    }
    public void setWwmsKeyid(String wwmsKeyid) { 
        this.wwmsKeyid = wwmsKeyid; 
    }

    // Serial Number
    public Integer getSlno() { 
        return slno; 
    }
    public void setSlno(Integer slno) { 
        this.slno = slno; 
    }

    // Why
    public String getWhy() { 
        return why; 
    }
    public void setWhy(String why) { 
        this.why = why; 
    }

    // Answer
    public String getAnswer() { 
        return answer; 
    }
    public void setAnswer(String answer) { 
        this.answer = answer; 
    }

    // Action
    public String getAction() { 
        return action; 
    }
    public void setAction(String action) { 
        this.action = action; 
    }

    // Created By
    public String getCreatedby() { 
        return createdby; 
    }
    public void setCreatedby(String createdby) { 
        this.createdby = createdby; 
    }

    // Created On
    public LocalDateTime getCreatedon() { 
        return createdon; 
    }
    public void setCreatedon(LocalDateTime createdon) { 
        this.createdon = createdon; 
    }

    // Modified On
    public LocalDateTime getModifiedon() { 
        return modifiedon; 
    }
    public void setModifiedon(LocalDateTime modifiedon) { 
        this.modifiedon = modifiedon; 
    }

    
}