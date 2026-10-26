package com.akranta.perfex_sb.model;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.time.LocalDateTime;

@Entity
@Table(name = "bal_plm_tl_standards", schema = "public")
public class BAL_PlmTlStandards {

    @Id
    @Column(name = "pmsd_keyid", length = 15, nullable = false)
    private String keyid;

    @Column(name = "pmsd_date", nullable = false)
    private LocalDateTime date;

    @Column(name = "pmsd_factoryid", length = 10, nullable = false)
    private String factoryid;

    @Column(name = "pmsd_sectionid", length = 10, nullable = false)
    private String sectionid;

    @Column(name = "pmsd_cellid", length = 10, nullable = false)
    private String cellid;

    @Column(name = "pmsd_machineid", length = 10, nullable = false)
    private String machineid;

    @Column(name = "pmsd_assemblyid", length = 15, nullable = false)
    private String assemblyid;

    @Column(name = "pmsd_subassemblyid", length = 10, nullable = false)
    private String subassemblyid;

    @Column(name = "pmsd_eqpgroupid", length = 15, nullable = false)
    private String eqpgroupid;

    @Column(name = "pmsd_source", length = 1, nullable = false)
    private Character source;

    @Column(name = "pmsd_supplierid", length = 10, nullable = false)
    private String supplierid;

    @Column(name = "pmsd_tradeid", length = 15, nullable = false)
    private String tradeid;

    @Column(name = "pmsd_frequency", nullable = false)
    private Double frequency;

    @Column(name = "pmsd_frequencyunit", length = 1, nullable = false)
    private Character frequencyunit;

    @Column(name = "pmsd_uomid", length = 10, nullable = false)
    private String uomid;

    @Column(name = "pmsd_howmethod", length = 500, nullable = false)
    private String howmethod;

    @Column(name = "pmsd_duration", nullable = false)
    private Double duration;

    @Column(name = "pmsd_activitytype", length = 3, nullable = false)
    private String activitytype;

    @Column(name = "pmsd_activitysubtype", length = 500, nullable = false)
    private String activitysubtype;

    @Column(name = "pmsd_bomid", length = 10, nullable = false)
    private String bomid;

    @Column(name = "pmsd_location", length = 500, nullable = false)
    private String location;

    @Column(name = "pmsd_activity", length = 500, nullable = false)
    private String activity;

    @Column(name = "pmsd_standard", length = 500, nullable = false)
    private String standard;

    @Column(name = "pmsd_machinecondition", length = 10, nullable = false)
    private String machinecondition;

    @Column(name = "pmsd_planconfigstatus", length = 15, nullable = false)
    private String planconfigstatus;

    @Column(name = "pmsd_issparesreq", length = 1, nullable = false)
    private Character issparesreq;

    @Column(name = "pmsd_istoolsreq", length = 1, nullable = false)
    private Character istoolsreq;

    @Column(name = "pmsd_refdoctype", length = 6, nullable = false)
    private String refdoctype;

    @Column(name = "pmsd_refdocno", length = 20, nullable = false)
    private String refdocno;

    @Column(name = "pmsd_preparedbyid", length = 10, nullable = false)
    private String preparedbyid;

    @Column(name = "pmsd_formatno", length = 20, nullable = false)
    private String formatno;

    @Column(name = "pmsd_effectivedate", nullable = false)
    private LocalDateTime effectivedate;

    @Column(name = "pmsd_wogenflag", length = 1, nullable = false)
    private Character wogenflag;

    @Column(name = "pmsd_phenomenaid", length = 10, nullable = false)
    private String phenomenaid;

    @Column(name = "pmsd_causeid", length = 10, nullable = false)
    private String causeid;

    @Column(name = "pmsd_routenumber", length = 10, nullable = false)
    private String routenumber;

    @Column(name = "pmsd_includeinshutdownmaint", length = 1, nullable = false)
    private Character includeinshutdownmaint;

    @Column(name = "pmsd_groupno", length = 10, nullable = false)
    private String groupno;

    @Column(name = "pmsd_resultifnotdone", length = 500, nullable = false)
    private String resultifnotdone;

    @Column(name = "pmsd_correctiveaction", length = 500, nullable = false)
    private String correctiveaction;

    @Column(name = "pmsd_issftpermitreq", length = 1, nullable = false)
    private Character issftpermitreq;

    @Column(name = "pmsd_safetyinstruction", length = 500, nullable = false)
    private String safetyinstruction;

    @Column(name = "pmsd_inactivateddate", nullable = false)
    private LocalDateTime inactivateddate;

    @Column(name = "pmsd_monthweekno", length = 1, nullable = false)
    private Character monthweekno;

    @Column(name = "pmsd_relatedto", length = 3, nullable = false)
    private String relatedto;

    @Column(name = "pmsd_mouldid", length = 10, nullable = false)
    private String mouldid;

    @Column(name = "pmsd_locationid", length = 10, nullable = false)
    private String locationid;

    @Column(name = "pmsd_flid", length = 12, nullable = false)
    private String flid;

    @Column(name = "pmsd_elementid", length = 250, nullable = false)
    private String elementid;

    @Column(name = "pmsd_maxvalue", nullable = false)
    private Double maxvalue;

    @Column(name = "pmsd_minvalue", nullable = false)
    private Double minvalue;

    @Column(name = "pmsd_target", nullable = false)
    private Double target;

    @Column(name = "pmsd_tempfield8", length = 1, nullable = false)
    private Character tempfield8;

    @Column(name = "pmsd_tempfield9", length = 1, nullable = false)
    private Character tempfield9;

    @Column(name = "pmsd_tempfield10", length = 7, nullable = false)
    private String tempfield10;

    @Column(name = "pmsd_active", length = 1, nullable = false)
    private Character active = 'Y';

    @Column(name = "pmsd_createdby", length = 10, nullable = false)
    private String createdby;

    @Column(name = "pmsd_createdon", nullable = false)
    @CreationTimestamp
    private LocalDateTime createdon = LocalDateTime.now();

    @Column(name = "pmsd_modifiedon", nullable = false)
    @UpdateTimestamp
    private LocalDateTime modifiedon = LocalDateTime.now();

    // Getters and Setters

    public String getKeyid() { return keyid; }
    public void setKeyid(String keyid) { this.keyid = keyid; }

    public LocalDateTime getDate() { return date; }
    public void setDate(LocalDateTime date) { this.date = date; }

    public String getFactoryid() { return factoryid; }
    public void setFactoryid(String factoryid) { this.factoryid = factoryid; }

    public String getSectionid() { return sectionid; }
    public void setSectionid(String sectionid) { this.sectionid = sectionid; }

    public String getCellid() { return cellid; }
    public void setCellid(String cellid) { this.cellid = cellid; }

    public String getMachineid() { return machineid; }
    public void setMachineid(String machineid) { this.machineid = machineid; }

    public String getAssemblyid() { return assemblyid; }
    public void setAssemblyid(String assemblyid) { this.assemblyid = assemblyid; }

    public String getSubassemblyid() { return subassemblyid; }
    public void setSubassemblyid(String subassemblyid) { this.subassemblyid = subassemblyid; }

    public String getEqpgroupid() { return eqpgroupid; }
    public void setEqpgroupid(String eqpgroupid) { this.eqpgroupid = eqpgroupid; }

    public Character getSource() { return source; }
    public void setSource(Character source) { this.source = source; }

    public String getSupplierid() { return supplierid; }
    public void setSupplierid(String supplierid) { this.supplierid = supplierid; }

    public String getTradeid() { return tradeid; }
    public void setTradeid(String tradeid) { this.tradeid = tradeid; }

    public Double getFrequency() { return frequency; }
    public void setFrequency(Double frequency) { this.frequency = frequency; }

    public Character getFrequencyunit() { return frequencyunit; }
    public void setFrequencyunit(Character frequencyunit) { this.frequencyunit = frequencyunit; }

    public String getUomid() { return uomid; }
    public void setUomid(String uomid) { this.uomid = uomid; }

    public String getHowmethod() { return howmethod; }
    public void setHowmethod(String howmethod) { this.howmethod = howmethod; }

    public Double getDuration() { return duration; }
    public void setDuration(Double duration) { this.duration = duration; }

    public String getActivitytype() { return activitytype; }
    public void setActivitytype(String activitytype) { this.activitytype = activitytype; }

    public String getActivitysubtype() { return activitysubtype; }
    public void setActivitysubtype(String activitysubtype) { this.activitysubtype = activitysubtype; }

    public String getBomid() { return bomid; }
    public void setBomid(String bomid) { this.bomid = bomid; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public String getActivity() { return activity; }
    public void setActivity(String activity) { this.activity = activity; }

    public String getStandard() { return standard; }
    public void setStandard(String standard) { this.standard = standard; }

    public String getMachinecondition() { return machinecondition; }
    public void setMachinecondition(String machinecondition) { this.machinecondition = machinecondition; }

    public String getPlanconfigstatus() { return planconfigstatus; }
    public void setPlanconfigstatus(String planconfigstatus) { this.planconfigstatus = planconfigstatus; }

    public Character getIssparesreq() { return issparesreq; }
    public void setIssparesreq(Character issparesreq) { this.issparesreq = issparesreq; }

    public Character getIstoolsreq() { return istoolsreq; }
    public void setIstoolsreq(Character istoolsreq) { this.istoolsreq = istoolsreq; }

    public String getRefdoctype() { return refdoctype; }
    public void setRefdoctype(String refdoctype) { this.refdoctype = refdoctype; }

    public String getRefdocno() { return refdocno; }
    public void setRefdocno(String refdocno) { this.refdocno = refdocno; }

    public String getPreparedbyid() { return preparedbyid; }
    public void setPreparedbyid(String preparedbyid) { this.preparedbyid = preparedbyid; }

    public String getFormatno() { return formatno; }
    public void setFormatno(String formatno) { this.formatno = formatno; }

    public LocalDateTime getEffectivedate() { return effectivedate; }
    public void setEffectivedate(LocalDateTime effectivedate) { this.effectivedate = effectivedate; }

    public Character getWogenflag() { return wogenflag; }
    public void setWogenflag(Character wogenflag) { this.wogenflag = wogenflag; }

    public String getPhenomenaid() { return phenomenaid; }
    public void setPhenomenaid(String phenomenaid) { this.phenomenaid = phenomenaid; }

    public String getCauseid() { return causeid; }
    public void setCauseid(String causeid) { this.causeid = causeid; }

    public String getRoutenumber() { return routenumber; }
    public void setRoutenumber(String routenumber) { this.routenumber = routenumber; }

    public Character getIncludeinshutdownmaint() { return includeinshutdownmaint; }
    public void setIncludeinshutdownmaint(Character includeinshutdownmaint) { this.includeinshutdownmaint = includeinshutdownmaint; }

    public String getGroupno() { return groupno; }
    public void setGroupno(String groupno) { this.groupno = groupno; }

    public String getResultifnotdone() { return resultifnotdone; }
    public void setResultifnotdone(String resultifnotdone) { this.resultifnotdone = resultifnotdone; }

    public String getCorrectiveaction() { return correctiveaction; }
    public void setCorrectiveaction(String correctiveaction) { this.correctiveaction = correctiveaction; }

    public Character getIssftpermitreq() { return issftpermitreq; }
    public void setIssftpermitreq(Character issftpermitreq) { this.issftpermitreq = issftpermitreq; }

    public String getSafetyinstruction() { return safetyinstruction; }
    public void setSafetyinstruction(String safetyinstruction) { this.safetyinstruction = safetyinstruction; }

    public LocalDateTime getInactivateddate() { return inactivateddate; }
    public void setInactivateddate(LocalDateTime inactivateddate) { this.inactivateddate = inactivateddate; }

    public Character getMonthweekno() { return monthweekno; }
    public void setMonthweekno(Character monthweekno) { this.monthweekno = monthweekno; }

    public String getRelatedto() { return relatedto; }
    public void setRelatedto(String relatedto) { this.relatedto = relatedto; }

    public String getMouldid() { return mouldid; }
    public void setMouldid(String mouldid) { this.mouldid = mouldid; }

    public String getLocationid() { return locationid; }
    public void setLocationid(String locationid) { this.locationid = locationid; }

    public String getFlid() { return flid; }
    public void setFlid(String flid) { this.flid = flid; }

    public String getElementid() { return elementid; }
    public void setElementid(String elementid) { this.elementid = elementid; }

    public Double getMaxvalue() { return maxvalue; }
    public void setMaxvalue(Double maxvalue) { this.maxvalue = maxvalue; }

    public Double getMinvalue() { return minvalue; }
    public void setMinvalue(Double minvalue) { this.minvalue = minvalue; }

    public Double getTarget() { return target; }
    public void setTarget(Double target) { this.target = target; }

    public Character getTempfield8() { return tempfield8; }
    public void setTempfield8(Character tempfield8) { this.tempfield8 = tempfield8; }

    public Character getTempfield9() { return tempfield9; }
    public void setTempfield9(Character tempfield9) { this.tempfield9 = tempfield9; }

    public String getTempfield10() { return tempfield10; }
    public void setTempfield10(String tempfield10) { this.tempfield10 = tempfield10; }

    public Character getActive() { return active; }
    public void setActive(Character active) { this.active = active; }

    public String getCreatedby() { return createdby; }
    public void setCreatedby(String createdby) { this.createdby = createdby; }

    public LocalDateTime getCreatedon() { return createdon; }
    public void setCreatedon(LocalDateTime createdon) { this.createdon = createdon; }

    public LocalDateTime getModifiedon() { return modifiedon; }
    public void setModifiedon(LocalDateTime modifiedon) { this.modifiedon = modifiedon; }
}
