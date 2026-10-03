package com.akranta.perfex_sb.model;

import jakarta.persistence.*;

@Entity
@Table(name = "bal_bdm_tl_phncauselink", schema = "public")
public class BAL_BdmTlPhncauselink {

    @Id
    @Column(name = "bpcl_originalid", length = 15, nullable = false)
    private String originalid;

    @Column(name = "bpcl_elementid", length = 250, nullable = false)
    private String elementid;

    @Column(name = "bpcl_parentid", length = 250, nullable = false)
    private String parentid;

    @Column(name = "bpcl_displaycode", length = 250, nullable = false)
    private String displaycode;

    @Column(name = "bpcl_elementtype", length = 3, nullable = false)
    private String elementtype;

    @Column(name = "bpcl_active", columnDefinition = "CHAR(1)", nullable = false)
    private Character active;

    public String getOriginalid() {
        return originalid;
    }

    public void setOriginalid(String originalid) {
        this.originalid = originalid;
    }

    public String getElementid() {
        return elementid;
    }

    public void setElementid(String elementid) {
        this.elementid = elementid;
    }

    public String getParentid() {
        return parentid;
    }

    public void setParentid(String parentid) {
        this.parentid = parentid;
    }

    public String getDisplaycode() {
        return displaycode;
    }

    public void setDisplaycode(String displaycode) {
        this.displaycode = displaycode;
    }

    public String getElementtype() {
        return elementtype;
    }

    public void setElementtype(String elementtype) {
        this.elementtype = elementtype;
    }

    public Character getActive() {
        return active;
    }

    public void setActive(Character active) {
        this.active = active;
    }
}
