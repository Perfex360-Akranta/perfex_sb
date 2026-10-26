package com.akranta.perfex_sb.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class BAL_WomTlServicecostplanId implements Serializable {

    @Column(name = "svcp_woid", length = 15, nullable = false)
    private String woid;

    @Column(name = "svcp_serviceid", length = 15, nullable = false)
    private String serviceid;

    public BAL_WomTlServicecostplanId() {
    }

    public BAL_WomTlServicecostplanId(String woid, String serviceid) {
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
        if (!(o instanceof BAL_WomTlServicecostplanId)) return false;
        BAL_WomTlServicecostplanId that = (BAL_WomTlServicecostplanId) o;
        return Objects.equals(woid, that.woid)
                && Objects.equals(serviceid, that.serviceid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(woid, serviceid);
    }
}