package com.akranta.perfex_sb.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

/**
 * Replaces PlmTlSparecostactual (pscaDoctype / pscaQuantity / pscaSparesid) -
 * kept as a distinct table from plm_tl_spareconsumed because that's how the
 * legacy code tracked it (separate bean, separate set calls in
 * saveWODetails()).
 */
@Entity
@Table(name = "plm_tl_sparecostactual")
public class PlmTlSparecostactual {

    @Id
    @Column(name = "psca_keyid")
    private String pscaKeyid;

    @ManyToOne
    @JoinColumn(name = "wofb_feedbackid")
    private BalPlmTlWoFeedBack wofeedback;

    @Column(name = "psca_sparesid")
    private String pscaSparesid;

    @Column(name = "psca_quantity")
    private String pscaQuantity;

    @Column(name = "psca_doctype")
    private String pscaDoctype;

    public String getPscaKeyid() { return pscaKeyid; }
    public void setPscaKeyid(String pscaKeyid) { this.pscaKeyid = pscaKeyid; }

    public BalPlmTlWoFeedBack getWofeedback() { return wofeedback; }
    public void setWofeedback(BalPlmTlWoFeedBack wofeedback) { this.wofeedback = wofeedback; }

    public String getPscaSparesid() { return pscaSparesid; }
    public void setPscaSparesid(String pscaSparesid) { this.pscaSparesid = pscaSparesid; }

    public String getPscaQuantity() { return pscaQuantity; }
    public void setPscaQuantity(String pscaQuantity) { this.pscaQuantity = pscaQuantity; }

    public String getPscaDoctype() { return pscaDoctype; }
    public void setPscaDoctype(String pscaDoctype) { this.pscaDoctype = pscaDoctype; }
}
