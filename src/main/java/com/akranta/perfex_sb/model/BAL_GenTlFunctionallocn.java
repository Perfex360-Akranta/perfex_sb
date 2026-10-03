package com.akranta.perfex_sb.model;

import jakarta.persistence.*;

@Entity
@Table(name = "gen_tl_functionallocn", schema = "public")
public class BAL_GenTlFunctionallocn {

    @Column(name = "fnln_originalid", length = 75)
    private String originalid;

    @Column(name = "fnln_elementid", length = 500)
    private String elementid;

    @Column(name = "fnln_parentid", length = 500)
    private String parentid;

    @Column(name = "fnln_displaycode", length = 200)
    private String displaycode;

    @Column(name = "fnln_description", length = 500)
    private String description;

    @Column(name = "fnln_elementtype", length = 5)
    private String elementtype;

    @Column(name = "fnln_active", columnDefinition = "CHAR(1)")
    private Character active;

    @Id
    @Column(name = "fnln_keyid", length = 12, nullable = false)
    private String keyid;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public String getKeyid() {
        return keyid;
    }

    public void setKeyid(String keyid) {
        this.keyid = keyid;
    }
}