package com.akranta.perfex_sb.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class BAL_WomTlSparecostactualId implements Serializable {

    @Column(name = "wsca_woid", length = 15, nullable = false)
    private String woid;

    @Column(name = "wsca_requestedby", length = 10, nullable = false)
    private String requestedby;

    @Column(name = "wsca_sparesid", length = 15, nullable = false)
    private String sparesid;

    public BAL_WomTlSparecostactualId() {
    }

    public BAL_WomTlSparecostactualId(String woid, String requestedby, String sparesid) {
        this.woid = woid;
        this.requestedby = requestedby;
        this.sparesid = sparesid;
    }

    public String getWoid() { return woid; }
    public void setWoid(String woid) { this.woid = woid; }

    public String getRequestedby() { return requestedby; }
    public void setRequestedby(String requestedby) { this.requestedby = requestedby; }

    public String getSparesid() { return sparesid; }
    public void setSparesid(String sparesid) { this.sparesid = sparesid; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BAL_WomTlSparecostactualId)) return false;
        BAL_WomTlSparecostactualId that = (BAL_WomTlSparecostactualId) o;
        return Objects.equals(woid, that.woid)
                && Objects.equals(requestedby, that.requestedby)
                && Objects.equals(sparesid, that.sparesid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(woid, requestedby, sparesid);
    }
}