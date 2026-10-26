package com.akranta.perfex_sb.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class BAL_WomTlOthercostplanId implements Serializable {

    @Column(name = "otcp_woid", length = 15, nullable = false)
    private String woid;

    @Column(name = "otcp_requestedby", length = 10, nullable = false)
    private String requestedby;

    @Column(name = "otcp_othercostmstid", length = 15, nullable = false)
    private String othercostmstid;

    public BAL_WomTlOthercostplanId() {
    }

    public BAL_WomTlOthercostplanId(String woid, String requestedby, String othercostmstid) {
        this.woid = woid;
        this.requestedby = requestedby;
        this.othercostmstid = othercostmstid;
    }

    public String getWoid() { return woid; }
    public void setWoid(String woid) { this.woid = woid; }

    public String getRequestedby() { return requestedby; }
    public void setRequestedby(String requestedby) { this.requestedby = requestedby; }

    public String getOthercostmstid() { return othercostmstid; }
    public void setOthercostmstid(String othercostmstid) { this.othercostmstid = othercostmstid; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BAL_WomTlOthercostplanId)) return false;
        BAL_WomTlOthercostplanId that = (BAL_WomTlOthercostplanId) o;
        return Objects.equals(woid, that.woid)
                && Objects.equals(requestedby, that.requestedby)
                && Objects.equals(othercostmstid, that.othercostmstid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(woid, requestedby, othercostmstid);
    }
}