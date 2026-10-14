package com.akranta.perfex_sb.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

/**
 * Replaces PlmTlSpareconsumed as populated inline in saveWODetails() /
 * saveGDWODetails() (pspcQuantity / pspcSpareid).
 */
@Entity
@Table(name = "plm_tl_spareconsumed")
public class PlmTlSpareconsumed {

    @Id
    @Column(name = "pspc_keyid")
    private String pspcKeyid;

    @ManyToOne
    @JoinColumn(name = "wofb_feedbackid")
    private BalPlmTlWoFeedBack wofeedback;

    @Column(name = "pspc_spareid")
    private String pspcSpareid;

    @Column(name = "pspc_quantity")
    private String pspcQuantity;

    public String getPspcKeyid() { return pspcKeyid; }
    public void setPspcKeyid(String pspcKeyid) { this.pspcKeyid = pspcKeyid; }

    public BalPlmTlWoFeedBack getWofeedback() { return wofeedback; }
    public void setWofeedback(BalPlmTlWoFeedBack wofeedback) { this.wofeedback = wofeedback; }

    public String getPspcSpareid() { return pspcSpareid; }
    public void setPspcSpareid(String pspcSpareid) { this.pspcSpareid = pspcSpareid; }

    public String getPspcQuantity() { return pspcQuantity; }
    public void setPspcQuantity(String pspcQuantity) { this.pspcQuantity = pspcQuantity; }
}
