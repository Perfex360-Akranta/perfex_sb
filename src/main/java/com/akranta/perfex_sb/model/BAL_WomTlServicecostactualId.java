package com.akranta.perfex_sb.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class BAL_WomTlServicecostactualId implements Serializable {

    @Column(name = "svca_woid", length = 15, nullable = false)
    private String woid;

    @Column(name = "svca_serviceid", length = 15, nullable = false)
    private String serviceid;

    public BAL_WomTlServicecostactualId() {
    }

    public BAL_WomTlServicecostactualId(String woid, String serviceid) {
        this.woid = woid;
        this.serviceid = serviceid;
    }

    public String getWoid() { return woid; }
    public void setWoid(String woid) { this.woid = woid; }

    public String getServiceid() { return serviceid; }
    public void setServiceid(String serviceid) { this.serviceid = serviceid; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BAL_WomTlServicecostactualId)) return false;
        BAL_WomTlServicecostactualId that = (BAL_WomTlServicecostactualId) o;
        return Objects.equals(woid, that.woid)
                && Objects.equals(serviceid, that.serviceid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(woid, serviceid);
    }
}