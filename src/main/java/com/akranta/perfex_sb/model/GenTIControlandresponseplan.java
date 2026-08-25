package com.akranta.perfex_sb.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "gen_tl_controlandresponseplan", schema = "public")
public class GenTIControlandresponseplan implements Serializable {

    @Id
    @NotNull
    @Size(max = 15)
    @Column(name = "carp_keyid", length = 15, nullable = false)
    private String keyid;

    @NotNull
    @Size(max = 12)
    @Column(name = "carp_flid", length = 12, nullable = false)
    private String flid;

    @NotNull
    @Size(max = 12)
    @Column(name = "carp_elementid", length = 12, nullable = false)
    private String elementid;

    @NotNull
    @Size(max = 500)
    @Column(name = "carp_processstep", length = 500, nullable = false)
    private String processstep;

    @NotNull
    @Size(max = 200)
    @Column(name = "carp_kpov", length = 200, nullable = false)
    private String kpov;

    @NotNull
    @Size(max = 200)
    @Column(name = "carp_frequency", length = 200, nullable = false)
    private String frequency;

    @NotNull
    @Size(max = 500)
    @Column(name = "carp_whererecorded", length = 500, nullable = false)
    private String whererecorded;

    @NotNull
    @Size(max = 500)
    @Column(name = "carp_controllimits", length = 500, nullable = false)
    private String controllimits;

    @NotNull
    @Size(max = 500)
    @Column(name = "carp_measurementmethod", length = 500, nullable = false)
    private String measurementmethod;

    @NotNull
    @Size(max = 200)
    @Column(name = "carp_whomeasures", length = 200, nullable = false)
    private String whomeasures;

    @NotNull
    @Size(max = 500)
    @Column(name = "carp_decisionrule", length = 500, nullable = false)
    private String decisionrule;

    @NotNull
    @Size(max = 10)
    @Column(name = "carp_uom", length = 10, nullable = false)
    private String uom;

    @NotNull
    @Size(max = 100)
    @Column(name = "carp_speclimits", length = 100, nullable = false)
    private String speclimits;

    @NotNull
    @Size(max = 100)
    @Column(name = "carp_samplesize", length = 100, nullable = false)
    private String samplesize;

    @NotNull
    @Size(max = 500)
    @Column(name = "carp_informto", length = 500, nullable = false)
    private String informto;

    @NotNull
    @Size(max = 500)
    @Column(name = "carp_correctiveaction", length = 500, nullable = false)
    private String correctiveaction;

    @NotNull
    @Column(name = "carp_tempfield1", length = 1, nullable = false)
    private Character tempfield1;

    @NotNull
    @Column(name = "carp_tempfield2", length = 1, nullable = false)
    private Character tempfield2;

    @NotNull
    @Column(name = "carp_tempfield3", length = 1, nullable = false)
    private Character tempfield3;

    @NotNull
    @Column(name = "carp_tempfield4", length = 1, nullable = false)
    private Character tempfield4;

    @NotNull
    @Column(name = "carp_tempfield5", length = 1, nullable = false)
    private Character tempfield5;

    @NotNull
    @Column(name = "carp_active", length = 1, nullable = false)
    private Character active;

    @NotNull
    @Size(max = 8)
    @Column(name = "carp_createdby", length = 8, nullable = false)
    private String createdby;

    @NotNull
    @Column(name = "carp_createdon", nullable = false)
    private LocalDateTime createdon;

    @NotNull
    @Column(name = "carp_modifiedon", nullable = false)
    private LocalDateTime modifiedon;

    public GenTIControlandresponseplan() {
    }

    public GenTIControlandresponseplan(String keyid, String flid, String elementid, String processstep, String kpov,
            String frequency, String whererecorded, String controllimits, String measurementmethod, String whomeasures,
            String decisionrule, String uom, String speclimits, String samplesize, String informto,
            String correctiveaction, Character tempfield1, Character tempfield2, Character tempfield3,
            Character tempfield4,
            Character tempfield5, Character active, String createdby, LocalDateTime createdon,
            LocalDateTime modifiedon) {
        this.keyid = keyid;
        this.flid = flid;
        this.elementid = elementid;
        this.processstep = processstep;
        this.kpov = kpov;
        this.frequency = frequency;
        this.whererecorded = whererecorded;
        this.controllimits = controllimits;
        this.measurementmethod = measurementmethod;
        this.whomeasures = whomeasures;
        this.decisionrule = decisionrule;
        this.uom = uom;
        this.speclimits = speclimits;
        this.samplesize = samplesize;
        this.informto = informto;
        this.correctiveaction = correctiveaction;
        this.tempfield1 = tempfield1;
        this.tempfield2 = tempfield2;
        this.tempfield3 = tempfield3;
        this.tempfield4 = tempfield4;
        this.tempfield5 = tempfield5;
        this.active = active;
        this.createdby = createdby;
        this.createdon = createdon;
        this.modifiedon = modifiedon;
    }

    public String getKeyid() {
        return keyid;
    }

    public void setKeyid(String keyid) {
        this.keyid = keyid;
    }

    public String getFlid() {
        return flid;
    }

    public void setFlid(String flid) {
        this.flid = flid;
    }

    public String getElementid() {
        return elementid;
    }

    public void setElementid(String elementid) {
        this.elementid = elementid;
    }

    public String getProcessstep() {
        return processstep;
    }

    public void setProcessstep(String processstep) {
        this.processstep = processstep;
    }

    public String getKpov() {
        return kpov;
    }

    public void setKpov(String kpov) {
        this.kpov = kpov;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public String getWhererecorded() {
        return whererecorded;
    }

    public void setWhererecorded(String whererecorded) {
        this.whererecorded = whererecorded;
    }

    public String getControllimits() {
        return controllimits;
    }

    public void setControllimits(String controllimits) {
        this.controllimits = controllimits;
    }

    public String getMeasurementmethod() {
        return measurementmethod;
    }

    public void setMeasurementmethod(String measurementmethod) {
        this.measurementmethod = measurementmethod;
    }

    public String getWhomeasures() {
        return whomeasures;
    }

    public void setWhomeasures(String whomeasures) {
        this.whomeasures = whomeasures;
    }

    public String getDecisionrule() {
        return decisionrule;
    }

    public void setDecisionrule(String decisionrule) {
        this.decisionrule = decisionrule;
    }

    public String getUom() {
        return uom;
    }

    public void setUom(String uom) {
        this.uom = uom;
    }

    public String getSpeclimits() {
        return speclimits;
    }

    public void setSpeclimits(String speclimits) {
        this.speclimits = speclimits;
    }

    public String getSamplesize() {
        return samplesize;
    }

    public void setSamplesize(String samplesize) {
        this.samplesize = samplesize;
    }

    public String getInformto() {
        return informto;
    }

    public void setInformto(String informto) {
        this.informto = informto;
    }

    public String getCorrectiveaction() {
        return correctiveaction;
    }

    public void setCorrectiveaction(String correctiveaction) {
        this.correctiveaction = correctiveaction;
    }

    public Character getTempfield1() {
        return tempfield1;
    }

    public void setTempfield1(Character tempfield1) {
        this.tempfield1 = tempfield1;
    }

    public Character getTempfield2() {
        return tempfield2;
    }

    public void setTempfield2(Character tempfield2) {
        this.tempfield2 = tempfield2;
    }

    public Character getTempfield3() {
        return tempfield3;
    }

    public void setTempfield3(Character tempfield3) {
        this.tempfield3 = tempfield3;
    }

    public Character getTempfield4() {
        return tempfield4;
    }

    public void setTempfield4(Character tempfield4) {
        this.tempfield4 = tempfield4;
    }

    public Character getTempfield5() {
        return tempfield5;
    }

    public void setTempfield5(Character tempfield5) {
        this.tempfield5 = tempfield5;
    }

    public Character getActive() {
        return active;
    }

    public void setActive(Character active) {
        this.active = active;
    }

    public String getCreatedby() {
        return createdby;
    }

    public void setCreatedby(String createdby) {
        this.createdby = createdby;
    }

    public LocalDateTime getCreatedon() {
        return createdon;
    }

    public void setCreatedon(LocalDateTime createdon) {
        this.createdon = createdon;
    }

    public LocalDateTime getModifiedon() {
        return modifiedon;
    }

    public void setModifiedon(LocalDateTime modifiedon) {
        this.modifiedon = modifiedon;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        GenTIControlandresponseplan that = (GenTIControlandresponseplan) o;
        return Objects.equals(keyid, that.keyid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(keyid);
    }

    @Override
    public String toString() {
        return "GenTIControlandresponseplan{" +
                "keyid='" + keyid + '\'' +
                ", flid='" + flid + '\'' +
                ", elementid='" + elementid + '\'' +
                ", processstep='" + processstep + '\'' +
                ", kpov='" + kpov + '\'' +
                ", frequency='" + frequency + '\'' +
                ", whererecorded='" + whererecorded + '\'' +
                ", controllimits='" + controllimits + '\'' +
                ", measurementmethod='" + measurementmethod + '\'' +
                ", whomeasures='" + whomeasures + '\'' +
                ", decisionrule='" + decisionrule + '\'' +
                ", uom='" + uom + '\'' +
                ", speclimits='" + speclimits + '\'' +
                ", samplesize='" + samplesize + '\'' +
                ", informto='" + informto + '\'' +
                ", correctiveaction='" + correctiveaction + '\'' +
                ", tempfield1='" + tempfield1 + '\'' +
                ", tempfield2='" + tempfield2 + '\'' +
                ", tempfield3='" + tempfield3 + '\'' +
                ", tempfield4='" + tempfield4 + '\'' +
                ", tempfield5='" + tempfield5 + '\'' +
                ", active='" + active + '\'' +
                ", createdby='" + createdby + '\'' +
                ", createdon=" + createdon +
                ", modifiedon=" + modifiedon +
                '}';
    }
}
