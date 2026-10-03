package com.akranta.perfex_sb.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class BAL_WomTlUtilitycostactualId implements Serializable {

    @Column(name = "utca_wokeyid", length = 15, nullable = false)
    private String wokeyid;

    @Column(name = "utca_requestedby", length = 10, nullable = false)
    private String requestedby;

    @Column(name = "utca_utilitymstid", length = 14, nullable = false)
    private String utilitymstid;

    public BAL_WomTlUtilitycostactualId() {
    }

    public BAL_WomTlUtilitycostactualId(String wokeyid, String requestedby, String utilitymstid) {
        this.wokeyid = wokeyid;
        this.requestedby = requestedby;
        this.utilitymstid = utilitymstid;
    }

    public String getWokeyid() { return wokeyid; }
    public void setWokeyid(String wokeyid) { this.wokeyid = wokeyid; }

    public String getRequestedby() { return requestedby; }
    public void setRequestedby(String requestedby) { this.requestedby = requestedby; }

    public String getUtilitymstid() { return utilitymstid; }
    public void setUtilitymstid(String utilitymstid) { this.utilitymstid = utilitymstid; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BAL_WomTlUtilitycostactualId)) return false;
        BAL_WomTlUtilitycostactualId that = (BAL_WomTlUtilitycostactualId) o;
        return Objects.equals(wokeyid, that.wokeyid)
                && Objects.equals(requestedby, that.requestedby)
                && Objects.equals(utilitymstid, that.utilitymstid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(wokeyid, requestedby, utilitymstid);
    }
}