package com.akranta.perfex_sb.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class BAL_WomTlManpowercostplanId implements Serializable {

    @Column(name = "mpcp_woid", length = 15, nullable = false)
    private String woid;

    @Column(name = "mpcp_manpowerid", length = 14, nullable = false)
    private String manpowerid;

    @Column(name = "mpcp_skillid", length = 15, nullable = false)
    private String skillid;

    public BAL_WomTlManpowercostplanId() {
    }

    public BAL_WomTlManpowercostplanId(String woid, String manpowerid, String skillid) {
        this.woid = woid;
        this.manpowerid = manpowerid;
        this.skillid = skillid;
    }

    public String getWoid() { return woid; }
    public void setWoid(String woid) { this.woid = woid; }

    public String getManpowerid() { return manpowerid; }
    public void setManpowerid(String manpowerid) { this.manpowerid = manpowerid; }

    public String getSkillid() { return skillid; }
    public void setSkillid(String skillid) { this.skillid = skillid; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BAL_WomTlManpowercostplanId)) return false;
        BAL_WomTlManpowercostplanId that = (BAL_WomTlManpowercostplanId) o;
        return Objects.equals(woid, that.woid)
                && Objects.equals(manpowerid, that.manpowerid)
                && Objects.equals(skillid, that.skillid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(woid, manpowerid, skillid);
    }
}