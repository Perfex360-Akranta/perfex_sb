package com.akranta.perfex_sb.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class BAL_WomTlManpowercostactualId implements Serializable {

    @Column(name = "mpcs_maintwoid", length = 15, nullable = false)
    private String maintwoid;

    @Column(name = "mpcs_manpowerid", length = 14, nullable = false)
    private String manpowerid;

    @Column(name = "mpcs_skillid", length = 15, nullable = false)
    private String skillid;

    public BAL_WomTlManpowercostactualId() {
    }

    public BAL_WomTlManpowercostactualId(String maintwoid, String manpowerid, String skillid) {
        this.maintwoid = maintwoid;
        this.manpowerid = manpowerid;
        this.skillid = skillid;
    }

    public String getMaintwoid() { return maintwoid; }
    public void setMaintwoid(String maintwoid) { this.maintwoid = maintwoid; }

    public String getManpowerid() { return manpowerid; }
    public void setManpowerid(String manpowerid) { this.manpowerid = manpowerid; }

    public String getSkillid() { return skillid; }
    public void setSkillid(String skillid) { this.skillid = skillid; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BAL_WomTlManpowercostactualId)) return false;
        BAL_WomTlManpowercostactualId that = (BAL_WomTlManpowercostactualId) o;
        return Objects.equals(maintwoid, that.maintwoid)
                && Objects.equals(manpowerid, that.manpowerid)
                && Objects.equals(skillid, that.skillid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(maintwoid, manpowerid, skillid);
    }
}