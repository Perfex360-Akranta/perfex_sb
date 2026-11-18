package com.akranta.perfex_sb.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="pcs_tl_dtl", schema="public")
public class BAL_PcsEntryDtl {

    @Id
    @Column(name="pldetailsid", length=15, nullable=false)
    private String pldetailsid;

    @Column(name="plmasterid", length=15, nullable=false)
    private String plmasterid;

    @Column(name="cellid", length=10, nullable=false)
    private String cellid;

    @Column(name="machineid", length=10, nullable=false)
    private String machineid;

    @Column(name="operators", nullable=false)
    private BigDecimal operators;

    @Column(name="calendartime", nullable=true)
    private BigDecimal calendartime;

    @Column(name="noplaninmins", nullable=true)
    private BigDecimal noplaninmins;

    @Column(name="noofproducts", nullable=true)
    private BigDecimal noofproducts;

    @Column(name="productid", length=15, nullable=true)
    private String productid;

    @Column(name="plannedqty", nullable=true)
    private BigDecimal plannedqty;

    @Column(name="rawmaterialtype", length=20, nullable=true)
    private String rawmaterialtype;

    @Column(name="weight", nullable=true)
    private BigDecimal weight;

    @Column(name="wno", length=30, nullable=true)
    private String wno;

    @Column(name="operationno", length=20, nullable=true)
    private String operationno;

    @Column(name="operationdescription", length=100, nullable=true)
    private String operationdescription;

    @Column(name="theoriticalcycletime", nullable=true)
    private BigDecimal theoriticalcycletime;

    @Column(name="actualcycletime", nullable=true)
    private BigDecimal actualcycletime;

    @Column(name="batchno", length=50, nullable=true)
    private String batchno;

    @Column(name="cavityavailable", nullable=true)
    private BigDecimal cavityavailable;

    @Column(name="cavityused", nullable=true)
    private BigDecimal cavityused;

    @Column(name="mandrelavailable", nullable=true)
    private BigDecimal mandrelavailable;

    @Column(name="mandrelused", nullable=true)
    private BigDecimal mandrelused;

    @Column(name="producedqty", nullable=true)
    private BigDecimal producedqty;

    @Column(name="defectsandreworkloss_ml", nullable=true)
    private BigDecimal defectsandreworkloss_ml;

    @Column(name="rejectedqty", nullable=true)
    private BigDecimal rejectedqty;

    @Column(name="reworkqty", nullable=true)
    private BigDecimal reworkqty;

    @Column(name="defecttime_sl", nullable=true)
    private BigDecimal defecttime_sl;

    @Column(name="productionlosses", nullable=true)
    private BigDecimal productionlosses;

    @Column(name="equipmentfailure_ml", nullable=true)
    private BigDecimal equipmentfailure_ml;

    @Column(name="setupandadjustment_ml", nullable=true)
    private BigDecimal setupandadjustment_ml;

    @Column(name="toolchangeloss_ml", nullable=true)
    private BigDecimal toolchangeloss_ml;

    @Column(name="startuploss_ml", nullable=true)
    private BigDecimal startuploss_ml;

    @Column(name="minorstoppageloss_ml", nullable=true)
    private BigDecimal minorstoppageloss_ml;

    @Column(name="speedloss_ml", nullable=true)
    private BigDecimal speedloss_ml;

    @Column(name="shutdownloss_ml", nullable=true)
    private BigDecimal shutdownloss_ml;

    @Column(name="managementloss_ml", nullable=true)
    private BigDecimal managementloss_ml;

    @Column(name="commonutilityloss_sl", nullable=true)
    private BigDecimal commonutilityloss_sl;

    @Column(name="operatingmotionloss_ml", nullable=true)
    private BigDecimal operatingmotionloss_ml;

    @Column(name="lineorganisationloss_ml", nullable=true)
    private BigDecimal lineorganisationloss_ml;

    @Column(name="logisticsloss_ml", nullable=true)
    private BigDecimal logisticsloss_ml;

    @Column(name="measuringandadjloss_ml", nullable=true)
    private BigDecimal measuringandadjloss_ml;

    @Column(name="dietoolandjigloss_ml", nullable=true)
    private BigDecimal dietoolandjigloss_ml;

    @Column(name="energyloss_ml", nullable=true)
    private BigDecimal energyloss_ml;

    @Column(name="yieldloss_ml", nullable=true)
    private BigDecimal yieldloss_ml;

    @Column(name="unaccountedtime", nullable=true)
    private BigDecimal unaccountedtime;

    @Column(name="loadingtime", nullable=true)
    private BigDecimal loadingtime;

    @Column(name="effectiveprodmins", nullable=true)
    private BigDecimal effectiveprodmins;

    @Column(name="mchavailabletime", nullable=true)
    private BigDecimal mchavailabletime;

    @Column(name="productionavltime", nullable=true)
    private BigDecimal productionavltime;

    @Column(name="productiontime", nullable=true)
    private BigDecimal productiontime;

    @Column(name="roa", nullable=true)
    private BigDecimal roa;

    @Column(name="rop", nullable=true)
    private BigDecimal rop;

    @Column(name="roq", nullable=true)
    private BigDecimal roq;

    @Column(name="oee", nullable=true)
    private BigDecimal oee;

    @Column(name="completedflag", length=1, nullable=false)
    private Character completedflag;

    @Column(name="remarks", length=1000, nullable=false)
    private String remarks;

    @Column(name="loss01", nullable=true)
    private BigDecimal loss01;

    @Column(name="loss02", nullable=true)
    private BigDecimal loss02;

    @Column(name="loss03", nullable=true)
    private BigDecimal loss03;

    @Column(name="loss04", nullable=true)
    private BigDecimal loss04;

    @Column(name="loss05", nullable=true)
    private BigDecimal loss05;

    @Column(name="loss06", nullable=true)
    private BigDecimal loss06;

    @Column(name="loss07", nullable=true)
    private BigDecimal loss07;

    @Column(name="loss08", nullable=true)
    private BigDecimal loss08;

    @Column(name="loss09", nullable=true)
    private BigDecimal loss09;

    @Column(name="loss10", nullable=true)
    private BigDecimal loss10;

    @Column(name="loss11", nullable=true)
    private BigDecimal loss11;

    @Column(name="loss12", nullable=true)
    private BigDecimal loss12;

    @Column(name="loss13", nullable=true)
    private BigDecimal loss13;

    @Column(name="loss14", nullable=true)
    private BigDecimal loss14;

    @Column(name="loss15", nullable=true)
    private BigDecimal loss15;

    @Column(name="loss16", nullable=true)
    private BigDecimal loss16;

    @Column(name="loss17", nullable=true)
    private BigDecimal loss17;

    @Column(name="loss18", nullable=true)
    private BigDecimal loss18;

    @Column(name="loss19", nullable=true)
    private BigDecimal loss19;

    @Column(name="loss20", nullable=true)
    private BigDecimal loss20;

    @Column(name="loss21", nullable=true)
    private BigDecimal loss21;

    @Column(name="loss22", nullable=true)
    private BigDecimal loss22;

    @Column(name="loss23", nullable=true)
    private BigDecimal loss23;

    @Column(name="loss24", nullable=true)
    private BigDecimal loss24;

    @Column(name="loss25", nullable=true)
    private BigDecimal loss25;

    @Column(name="loss26", nullable=true)
    private BigDecimal loss26;

    @Column(name="loss27", nullable=true)
    private BigDecimal loss27;

    @Column(name="loss28", nullable=true)
    private BigDecimal loss28;

    @Column(name="loss29", nullable=true)
    private BigDecimal loss29;

    @Column(name="loss30", nullable=true)
    private BigDecimal loss30;

    @Column(name="loss31", nullable=true)
    private BigDecimal loss31;

    @Column(name="loss32", nullable=true)
    private BigDecimal loss32;

    @Column(name="loss33", nullable=true)
    private BigDecimal loss33;

    @Column(name="loss34", nullable=true)
    private BigDecimal loss34;

    @Column(name="loss35", nullable=true)
    private BigDecimal loss35;

    @Column(name="loss36", nullable=true)
    private BigDecimal loss36;

    @Column(name="loss37", nullable=true)
    private BigDecimal loss37;

    @Column(name="loss38", nullable=true)
    private BigDecimal loss38;

    @Column(name="loss39", nullable=true)
    private BigDecimal loss39;

    @Column(name="loss40", nullable=true)
    private BigDecimal loss40;

    @Column(name="loss41", nullable=true)
    private BigDecimal loss41;

    @Column(name="loss42", nullable=true)
    private BigDecimal loss42;

    @Column(name="loss43", nullable=true)
    private BigDecimal loss43;

    @Column(name="loss44", nullable=true)
    private BigDecimal loss44;

    @Column(name="loss45", nullable=true)
    private BigDecimal loss45;

    @Column(name="loss46", nullable=true)
    private BigDecimal loss46;

    @Column(name="loss47", nullable=true)
    private BigDecimal loss47;

    @Column(name="loss48", nullable=true)
    private BigDecimal loss48;

    @Column(name="loss49", nullable=true)
    private BigDecimal loss49;

    @Column(name="loss50", nullable=true)
    private BigDecimal loss50;

    @Column(name="loss51", nullable=true)
    private BigDecimal loss51;

    @Column(name="loss52", nullable=true)
    private BigDecimal loss52;

    @Column(name="loss53", nullable=true)
    private BigDecimal loss53;

    @Column(name="loss54", nullable=true)
    private BigDecimal loss54;

    @Column(name="loss55", nullable=true)
    private BigDecimal loss55;

    @Column(name="loss56", nullable=true)
    private BigDecimal loss56;

    @Column(name="loss57", nullable=true)
    private BigDecimal loss57;

    @Column(name="loss58", nullable=true)
    private BigDecimal loss58;

    @Column(name="loss59", nullable=true)
    private BigDecimal loss59;

    @Column(name="loss60", nullable=true)
    private BigDecimal loss60;

    @Column(name="loss61", nullable=true)
    private BigDecimal loss61;

    @Column(name="loss62", nullable=true)
    private BigDecimal loss62;

    @Column(name="loss63", nullable=true)
    private BigDecimal loss63;

    @Column(name="loss64", nullable=true)
    private BigDecimal loss64;

    @Column(name="loss65", nullable=true)
    private BigDecimal loss65;

    @Column(name="loss66", nullable=true)
    private BigDecimal loss66;

    @Column(name="loss67", nullable=true)
    private BigDecimal loss67;

    @Column(name="loss68", nullable=true)
    private BigDecimal loss68;

    @Column(name="loss69", nullable=true)
    private BigDecimal loss69;

    @Column(name="loss70", nullable=true)
    private BigDecimal loss70;

    @Column(name="loss71", nullable=true)
    private BigDecimal loss71;

    @Column(name="loss72", nullable=true)
    private BigDecimal loss72;

    @Column(name="loss73", nullable=true)
    private BigDecimal loss73;

    @Column(name="loss74", nullable=true)
    private BigDecimal loss74;

    @Column(name="loss75", nullable=true)
    private BigDecimal loss75;

    @Column(name="loss76", nullable=true)
    private BigDecimal loss76;

    @Column(name="loss77", nullable=true)
    private BigDecimal loss77;

    @Column(name="loss78", nullable=true)
    private BigDecimal loss78;

    @Column(name="loss79", nullable=true)
    private BigDecimal loss79;

    @Column(name="loss80", nullable=true)
    private BigDecimal loss80;

    @Column(name="loss81", nullable=true)
    private BigDecimal loss81;

    @Column(name="loss82", nullable=true)
    private BigDecimal loss82;

    @Column(name="loss83", nullable=true)
    private BigDecimal loss83;

    @Column(name="loss84", nullable=true)
    private BigDecimal loss84;

    @Column(name="loss85", nullable=true)
    private BigDecimal loss85;

    @Column(name="loss86", nullable=true)
    private BigDecimal loss86;

    @Column(name="loss87", nullable=true)
    private BigDecimal loss87;

    @Column(name="loss88", nullable=true)
    private BigDecimal loss88;

    @Column(name="loss89", nullable=true)
    private BigDecimal loss89;

    @Column(name="loss90", nullable=true)
    private BigDecimal loss90;

    @Column(name="loss91", nullable=true)
    private BigDecimal loss91;

    @Column(name="loss92", nullable=true)
    private BigDecimal loss92;

    @Column(name="loss93", nullable=true)
    private BigDecimal loss93;

    @Column(name="loss94", nullable=true)
    private BigDecimal loss94;

    @Column(name="loss95", nullable=true)
    private BigDecimal loss95;

    @Column(name="loss96", nullable=true)
    private BigDecimal loss96;

    @Column(name="loss97", nullable=true)
    private BigDecimal loss97;

    @Column(name="trimmingqty", nullable=true)
    private BigDecimal trimmingqty;

    @Column(name="expansionqty", nullable=true)
    private BigDecimal expansionqty;

    @Column(name="modelchangepart", length=20, nullable=false)
    private String modelchangepart;

    @Column(name="reprocessing", nullable=true)
    private BigDecimal reprocessing;

    @Column(name="inspectedqty", nullable=true)
    private BigDecimal inspectedqty;

    @Column(name="qaacceptedqty", nullable=true)
    private BigDecimal qaacceptedqty;

    @Column(name="backlogqty", nullable=true)
    private BigDecimal backlogqty;

    @Column(name="actualruntime_old", nullable=true)
    private BigDecimal actualruntime_old;

    @Column(name="netutilisationtime_old", nullable=true)
    private BigDecimal netutilisationtime_old;

    @Column(name="plantavltime_old", nullable=true)
    private BigDecimal plantavltime_old;

    @Column(name="ae", nullable=true)
    private BigDecimal ae;

    @Column(name="au", nullable=true)
    private BigDecimal au;

    @Column(name="oe", nullable=true)
    private BigDecimal oe;

    @Column(name="pfprocessfailureloss", nullable=true)
    private BigDecimal pfprocessfailureloss;

    @Column(name="ancillaryutilityloss", nullable=true)
    private BigDecimal ancillaryutilityloss;

    @Column(name="mgmloadingtime", nullable=true)
    private BigDecimal mgmloadingtime;

    @Column(name="mgmproductiontime", nullable=true)
    private BigDecimal mgmproductiontime;

    @Column(name="mgmoe", nullable=true)
    private BigDecimal mgmoe;

    @Column(name="mgmroa", nullable=true)
    private BigDecimal mgmroa;

    @Column(name="mgmrop", nullable=true)
    private BigDecimal mgmrop;

    @Column(name="mgmroq", nullable=true)
    private BigDecimal mgmroq;

    @Column(name="mgmoee", nullable=true)
    private BigDecimal mgmoee;

    @Column(name="active", length=1, nullable=true)
    private Character active;

    @Column(name="createdby", length=10, nullable=true)
    private String createdby;

    @Column(name="createdon", nullable=true)
    private LocalDateTime createdon;

    @Column(name="modifiedon", nullable=true)
    private LocalDateTime modifiedon;

    @Column(name="loss98", nullable=true)
    private BigDecimal loss98;

    @Column(name="loss99", nullable=true)
    private BigDecimal loss99;

    @Column(name="loss100", nullable=true)
    private BigDecimal loss100;

    @Column(name="loss101", nullable=true)
    private BigDecimal loss101;

    @Column(name="pokayokelinestop_ml", nullable=true)
    private BigDecimal pokayokelinestop_ml;

    @Column(name="loss102", nullable=true)
    private BigDecimal loss102;

    @Column(name="loss103", nullable=true)
    private BigDecimal loss103;

    @Column(name="loss104", nullable=true)
    private BigDecimal loss104;

    @Column(name="loss105", nullable=true)
    private BigDecimal loss105;

    @Column(name="loss106", nullable=true)
    private BigDecimal loss106;

    @Column(name="loss107", nullable=true)
    private BigDecimal loss107;

    @Column(name="jigfixtureloss_ml", nullable=true)
    private BigDecimal jigfixtureloss_ml;

    @Column(name="loss108", nullable=true)
    private BigDecimal loss108;

    @Column(name="loss109", nullable=true)
    private BigDecimal loss109;

    @Column(name="loss110", nullable=true)
    private BigDecimal loss110;

    @Column(name="loss111", nullable=true)
    private BigDecimal loss111;

    @Column(name="loss112", nullable=true)
    private BigDecimal loss112;

    @Column(name="loss113", nullable=true)
    private BigDecimal loss113;

    @Column(name="loss114", nullable=true)
    private BigDecimal loss114;

    @Column(name="loss115", nullable=true)
    private BigDecimal loss115;

    @Column(name="loss116", nullable=true)
    private BigDecimal loss116;

    @Column(name="loss117", nullable=true)
    private BigDecimal loss117;

    @Column(name="loss118", nullable=true)
    private BigDecimal loss118;

    @Column(name="loss119", nullable=true)
    private BigDecimal loss119;

    @Column(name="loss120", nullable=true)
    private BigDecimal loss120;

    @Column(name="loss121", nullable=true)
    private BigDecimal loss121;

    @Column(name="loss122", nullable=true)
    private BigDecimal loss122;

    public String getPldetailsid() {
        return pldetailsid;
    }

    public void setPldetailsid(String pldetailsid) {
        this.pldetailsid = pldetailsid;
    }

    public String getPlmasterid() {
        return plmasterid;
    }

    public void setPlmasterid(String plmasterid) {
        this.plmasterid = plmasterid;
    }

    public String getCellid() {
        return cellid;
    }

    public void setCellid(String cellid) {
        this.cellid = cellid;
    }

    public String getMachineid() {
        return machineid;
    }

    public void setMachineid(String machineid) {
        this.machineid = machineid;
    }

    public BigDecimal getOperators() {
        return operators;
    }

    public void setOperators(BigDecimal operators) {
        this.operators = operators;
    }

    public BigDecimal getCalendartime() {
        return calendartime;
    }

    public void setCalendartime(BigDecimal calendartime) {
        this.calendartime = calendartime;
    }

    public BigDecimal getNoplaninmins() {
        return noplaninmins;
    }

    public void setNoplaninmins(BigDecimal noplaninmins) {
        this.noplaninmins = noplaninmins;
    }

    public BigDecimal getNoofproducts() {
        return noofproducts;
    }

    public void setNoofproducts(BigDecimal noofproducts) {
        this.noofproducts = noofproducts;
    }

    public String getProductid() {
        return productid;
    }

    public void setProductid(String productid) {
        this.productid = productid;
    }

    public BigDecimal getPlannedqty() {
        return plannedqty;
    }

    public void setPlannedqty(BigDecimal plannedqty) {
        this.plannedqty = plannedqty;
    }

    public String getRawmaterialtype() {
        return rawmaterialtype;
    }

    public void setRawmaterialtype(String rawmaterialtype) {
        this.rawmaterialtype = rawmaterialtype;
    }

    public BigDecimal getWeight() {
        return weight;
    }

    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }

    public String getWno() {
        return wno;
    }

    public void setWno(String wno) {
        this.wno = wno;
    }

    public String getOperationno() {
        return operationno;
    }

    public void setOperationno(String operationno) {
        this.operationno = operationno;
    }

    public String getOperationdescription() {
        return operationdescription;
    }

    public void setOperationdescription(String operationdescription) {
        this.operationdescription = operationdescription;
    }

    public BigDecimal getTheoriticalcycletime() {
        return theoriticalcycletime;
    }

    public void setTheoriticalcycletime(BigDecimal theoriticalcycletime) {
        this.theoriticalcycletime = theoriticalcycletime;
    }

    public BigDecimal getActualcycletime() {
        return actualcycletime;
    }

    public void setActualcycletime(BigDecimal actualcycletime) {
        this.actualcycletime = actualcycletime;
    }

    public String getBatchno() {
        return batchno;
    }

    public void setBatchno(String batchno) {
        this.batchno = batchno;
    }

    public BigDecimal getCavityavailable() {
        return cavityavailable;
    }

    public void setCavityavailable(BigDecimal cavityavailable) {
        this.cavityavailable = cavityavailable;
    }

    public BigDecimal getCavityused() {
        return cavityused;
    }

    public void setCavityused(BigDecimal cavityused) {
        this.cavityused = cavityused;
    }

    public BigDecimal getMandrelavailable() {
        return mandrelavailable;
    }

    public void setMandrelavailable(BigDecimal mandrelavailable) {
        this.mandrelavailable = mandrelavailable;
    }

    public BigDecimal getMandrelused() {
        return mandrelused;
    }

    public void setMandrelused(BigDecimal mandrelused) {
        this.mandrelused = mandrelused;
    }

    public BigDecimal getProducedqty() {
        return producedqty;
    }

    public void setProducedqty(BigDecimal producedqty) {
        this.producedqty = producedqty;
    }

    public BigDecimal getDefectsandreworkloss_ml() {
        return defectsandreworkloss_ml;
    }

    public void setDefectsandreworkloss_ml(BigDecimal defectsandreworkloss_ml) {
        this.defectsandreworkloss_ml = defectsandreworkloss_ml;
    }

    public BigDecimal getRejectedqty() {
        return rejectedqty;
    }

    public void setRejectedqty(BigDecimal rejectedqty) {
        this.rejectedqty = rejectedqty;
    }

    public BigDecimal getReworkqty() {
        return reworkqty;
    }

    public void setReworkqty(BigDecimal reworkqty) {
        this.reworkqty = reworkqty;
    }

    public BigDecimal getDefecttime_sl() {
        return defecttime_sl;
    }

    public void setDefecttime_sl(BigDecimal defecttime_sl) {
        this.defecttime_sl = defecttime_sl;
    }

    public BigDecimal getProductionlosses() {
        return productionlosses;
    }

    public void setProductionlosses(BigDecimal productionlosses) {
        this.productionlosses = productionlosses;
    }

    public BigDecimal getEquipmentfailure_ml() {
        return equipmentfailure_ml;
    }

    public void setEquipmentfailure_ml(BigDecimal equipmentfailure_ml) {
        this.equipmentfailure_ml = equipmentfailure_ml;
    }

    public BigDecimal getSetupandadjustment_ml() {
        return setupandadjustment_ml;
    }

    public void setSetupandadjustment_ml(BigDecimal setupandadjustment_ml) {
        this.setupandadjustment_ml = setupandadjustment_ml;
    }

    public BigDecimal getToolchangeloss_ml() {
        return toolchangeloss_ml;
    }

    public void setToolchangeloss_ml(BigDecimal toolchangeloss_ml) {
        this.toolchangeloss_ml = toolchangeloss_ml;
    }

    public BigDecimal getStartuploss_ml() {
        return startuploss_ml;
    }

    public void setStartuploss_ml(BigDecimal startuploss_ml) {
        this.startuploss_ml = startuploss_ml;
    }

    public BigDecimal getMinorstoppageloss_ml() {
        return minorstoppageloss_ml;
    }

    public void setMinorstoppageloss_ml(BigDecimal minorstoppageloss_ml) {
        this.minorstoppageloss_ml = minorstoppageloss_ml;
    }

    public BigDecimal getSpeedloss_ml() {
        return speedloss_ml;
    }

    public void setSpeedloss_ml(BigDecimal speedloss_ml) {
        this.speedloss_ml = speedloss_ml;
    }

    public BigDecimal getShutdownloss_ml() {
        return shutdownloss_ml;
    }

    public void setShutdownloss_ml(BigDecimal shutdownloss_ml) {
        this.shutdownloss_ml = shutdownloss_ml;
    }

    public BigDecimal getManagementloss_ml() {
        return managementloss_ml;
    }

    public void setManagementloss_ml(BigDecimal managementloss_ml) {
        this.managementloss_ml = managementloss_ml;
    }

    public BigDecimal getCommonutilityloss_sl() {
        return commonutilityloss_sl;
    }

    public void setCommonutilityloss_sl(BigDecimal commonutilityloss_sl) {
        this.commonutilityloss_sl = commonutilityloss_sl;
    }

    public BigDecimal getOperatingmotionloss_ml() {
        return operatingmotionloss_ml;
    }

    public void setOperatingmotionloss_ml(BigDecimal operatingmotionloss_ml) {
        this.operatingmotionloss_ml = operatingmotionloss_ml;
    }

    public BigDecimal getLineorganisationloss_ml() {
        return lineorganisationloss_ml;
    }

    public void setLineorganisationloss_ml(BigDecimal lineorganisationloss_ml) {
        this.lineorganisationloss_ml = lineorganisationloss_ml;
    }

    public BigDecimal getLogisticsloss_ml() {
        return logisticsloss_ml;
    }

    public void setLogisticsloss_ml(BigDecimal logisticsloss_ml) {
        this.logisticsloss_ml = logisticsloss_ml;
    }

    public BigDecimal getMeasuringandadjloss_ml() {
        return measuringandadjloss_ml;
    }

    public void setMeasuringandadjloss_ml(BigDecimal measuringandadjloss_ml) {
        this.measuringandadjloss_ml = measuringandadjloss_ml;
    }

    public BigDecimal getDietoolandjigloss_ml() {
        return dietoolandjigloss_ml;
    }

    public void setDietoolandjigloss_ml(BigDecimal dietoolandjigloss_ml) {
        this.dietoolandjigloss_ml = dietoolandjigloss_ml;
    }

    public BigDecimal getEnergyloss_ml() {
        return energyloss_ml;
    }

    public void setEnergyloss_ml(BigDecimal energyloss_ml) {
        this.energyloss_ml = energyloss_ml;
    }

    public BigDecimal getYieldloss_ml() {
        return yieldloss_ml;
    }

    public void setYieldloss_ml(BigDecimal yieldloss_ml) {
        this.yieldloss_ml = yieldloss_ml;
    }

    public BigDecimal getUnaccountedtime() {
        return unaccountedtime;
    }

    public void setUnaccountedtime(BigDecimal unaccountedtime) {
        this.unaccountedtime = unaccountedtime;
    }

    public BigDecimal getLoadingtime() {
        return loadingtime;
    }

    public void setLoadingtime(BigDecimal loadingtime) {
        this.loadingtime = loadingtime;
    }

    public BigDecimal getEffectiveprodmins() {
        return effectiveprodmins;
    }

    public void setEffectiveprodmins(BigDecimal effectiveprodmins) {
        this.effectiveprodmins = effectiveprodmins;
    }

    public BigDecimal getMchavailabletime() {
        return mchavailabletime;
    }

    public void setMchavailabletime(BigDecimal mchavailabletime) {
        this.mchavailabletime = mchavailabletime;
    }

    public BigDecimal getProductionavltime() {
        return productionavltime;
    }

    public void setProductionavltime(BigDecimal productionavltime) {
        this.productionavltime = productionavltime;
    }

    public BigDecimal getProductiontime() {
        return productiontime;
    }

    public void setProductiontime(BigDecimal productiontime) {
        this.productiontime = productiontime;
    }

    public BigDecimal getRoa() {
        return roa;
    }

    public void setRoa(BigDecimal roa) {
        this.roa = roa;
    }

    public BigDecimal getRop() {
        return rop;
    }

    public void setRop(BigDecimal rop) {
        this.rop = rop;
    }

    public BigDecimal getRoq() {
        return roq;
    }

    public void setRoq(BigDecimal roq) {
        this.roq = roq;
    }

    public BigDecimal getOee() {
        return oee;
    }

    public void setOee(BigDecimal oee) {
        this.oee = oee;
    }

    public Character getCompletedflag() {
        return completedflag;
    }

    public void setCompletedflag(Character completedflag) {
        this.completedflag = completedflag;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public BigDecimal getLoss01() {
        return loss01;
    }

    public void setLoss01(BigDecimal loss01) {
        this.loss01 = loss01;
    }

    public BigDecimal getLoss02() {
        return loss02;
    }

    public void setLoss02(BigDecimal loss02) {
        this.loss02 = loss02;
    }

    public BigDecimal getLoss03() {
        return loss03;
    }

    public void setLoss03(BigDecimal loss03) {
        this.loss03 = loss03;
    }

    public BigDecimal getLoss04() {
        return loss04;
    }

    public void setLoss04(BigDecimal loss04) {
        this.loss04 = loss04;
    }

    public BigDecimal getLoss05() {
        return loss05;
    }

    public void setLoss05(BigDecimal loss05) {
        this.loss05 = loss05;
    }

    public BigDecimal getLoss06() {
        return loss06;
    }

    public void setLoss06(BigDecimal loss06) {
        this.loss06 = loss06;
    }

    public BigDecimal getLoss07() {
        return loss07;
    }

    public void setLoss07(BigDecimal loss07) {
        this.loss07 = loss07;
    }

    public BigDecimal getLoss08() {
        return loss08;
    }

    public void setLoss08(BigDecimal loss08) {
        this.loss08 = loss08;
    }

    public BigDecimal getLoss09() {
        return loss09;
    }

    public void setLoss09(BigDecimal loss09) {
        this.loss09 = loss09;
    }

    public BigDecimal getLoss10() {
        return loss10;
    }

    public void setLoss10(BigDecimal loss10) {
        this.loss10 = loss10;
    }

    public BigDecimal getLoss11() {
        return loss11;
    }

    public void setLoss11(BigDecimal loss11) {
        this.loss11 = loss11;
    }

    public BigDecimal getLoss12() {
        return loss12;
    }

    public void setLoss12(BigDecimal loss12) {
        this.loss12 = loss12;
    }

    public BigDecimal getLoss13() {
        return loss13;
    }

    public void setLoss13(BigDecimal loss13) {
        this.loss13 = loss13;
    }

    public BigDecimal getLoss14() {
        return loss14;
    }

    public void setLoss14(BigDecimal loss14) {
        this.loss14 = loss14;
    }

    public BigDecimal getLoss15() {
        return loss15;
    }

    public void setLoss15(BigDecimal loss15) {
        this.loss15 = loss15;
    }

    public BigDecimal getLoss16() {
        return loss16;
    }

    public void setLoss16(BigDecimal loss16) {
        this.loss16 = loss16;
    }

    public BigDecimal getLoss17() {
        return loss17;
    }

    public void setLoss17(BigDecimal loss17) {
        this.loss17 = loss17;
    }

    public BigDecimal getLoss18() {
        return loss18;
    }

    public void setLoss18(BigDecimal loss18) {
        this.loss18 = loss18;
    }

    public BigDecimal getLoss19() {
        return loss19;
    }

    public void setLoss19(BigDecimal loss19) {
        this.loss19 = loss19;
    }

    public BigDecimal getLoss20() {
        return loss20;
    }

    public void setLoss20(BigDecimal loss20) {
        this.loss20 = loss20;
    }

    public BigDecimal getLoss21() {
        return loss21;
    }

    public void setLoss21(BigDecimal loss21) {
        this.loss21 = loss21;
    }

    public BigDecimal getLoss22() {
        return loss22;
    }

    public void setLoss22(BigDecimal loss22) {
        this.loss22 = loss22;
    }

    public BigDecimal getLoss23() {
        return loss23;
    }

    public void setLoss23(BigDecimal loss23) {
        this.loss23 = loss23;
    }

    public BigDecimal getLoss24() {
        return loss24;
    }

    public void setLoss24(BigDecimal loss24) {
        this.loss24 = loss24;
    }

    public BigDecimal getLoss25() {
        return loss25;
    }

    public void setLoss25(BigDecimal loss25) {
        this.loss25 = loss25;
    }

    public BigDecimal getLoss26() {
        return loss26;
    }

    public void setLoss26(BigDecimal loss26) {
        this.loss26 = loss26;
    }

    public BigDecimal getLoss27() {
        return loss27;
    }

    public void setLoss27(BigDecimal loss27) {
        this.loss27 = loss27;
    }

    public BigDecimal getLoss28() {
        return loss28;
    }

    public void setLoss28(BigDecimal loss28) {
        this.loss28 = loss28;
    }

    public BigDecimal getLoss29() {
        return loss29;
    }

    public void setLoss29(BigDecimal loss29) {
        this.loss29 = loss29;
    }

    public BigDecimal getLoss30() {
        return loss30;
    }

    public void setLoss30(BigDecimal loss30) {
        this.loss30 = loss30;
    }

    public BigDecimal getLoss31() {
        return loss31;
    }

    public void setLoss31(BigDecimal loss31) {
        this.loss31 = loss31;
    }

    public BigDecimal getLoss32() {
        return loss32;
    }

    public void setLoss32(BigDecimal loss32) {
        this.loss32 = loss32;
    }

    public BigDecimal getLoss33() {
        return loss33;
    }

    public void setLoss33(BigDecimal loss33) {
        this.loss33 = loss33;
    }

    public BigDecimal getLoss34() {
        return loss34;
    }

    public void setLoss34(BigDecimal loss34) {
        this.loss34 = loss34;
    }

    public BigDecimal getLoss35() {
        return loss35;
    }

    public void setLoss35(BigDecimal loss35) {
        this.loss35 = loss35;
    }

    public BigDecimal getLoss36() {
        return loss36;
    }

    public void setLoss36(BigDecimal loss36) {
        this.loss36 = loss36;
    }

    public BigDecimal getLoss37() {
        return loss37;
    }

    public void setLoss37(BigDecimal loss37) {
        this.loss37 = loss37;
    }

    public BigDecimal getLoss38() {
        return loss38;
    }

    public void setLoss38(BigDecimal loss38) {
        this.loss38 = loss38;
    }

    public BigDecimal getLoss39() {
        return loss39;
    }

    public void setLoss39(BigDecimal loss39) {
        this.loss39 = loss39;
    }

    public BigDecimal getLoss40() {
        return loss40;
    }

    public void setLoss40(BigDecimal loss40) {
        this.loss40 = loss40;
    }

    public BigDecimal getLoss41() {
        return loss41;
    }

    public void setLoss41(BigDecimal loss41) {
        this.loss41 = loss41;
    }

    public BigDecimal getLoss42() {
        return loss42;
    }

    public void setLoss42(BigDecimal loss42) {
        this.loss42 = loss42;
    }

    public BigDecimal getLoss43() {
        return loss43;
    }

    public void setLoss43(BigDecimal loss43) {
        this.loss43 = loss43;
    }

    public BigDecimal getLoss44() {
        return loss44;
    }

    public void setLoss44(BigDecimal loss44) {
        this.loss44 = loss44;
    }

    public BigDecimal getLoss45() {
        return loss45;
    }

    public void setLoss45(BigDecimal loss45) {
        this.loss45 = loss45;
    }

    public BigDecimal getLoss46() {
        return loss46;
    }

    public void setLoss46(BigDecimal loss46) {
        this.loss46 = loss46;
    }

    public BigDecimal getLoss47() {
        return loss47;
    }

    public void setLoss47(BigDecimal loss47) {
        this.loss47 = loss47;
    }

    public BigDecimal getLoss48() {
        return loss48;
    }

    public void setLoss48(BigDecimal loss48) {
        this.loss48 = loss48;
    }

    public BigDecimal getLoss49() {
        return loss49;
    }

    public void setLoss49(BigDecimal loss49) {
        this.loss49 = loss49;
    }

    public BigDecimal getLoss50() {
        return loss50;
    }

    public void setLoss50(BigDecimal loss50) {
        this.loss50 = loss50;
    }

    public BigDecimal getLoss51() {
        return loss51;
    }

    public void setLoss51(BigDecimal loss51) {
        this.loss51 = loss51;
    }

    public BigDecimal getLoss52() {
        return loss52;
    }

    public void setLoss52(BigDecimal loss52) {
        this.loss52 = loss52;
    }

    public BigDecimal getLoss53() {
        return loss53;
    }

    public void setLoss53(BigDecimal loss53) {
        this.loss53 = loss53;
    }

    public BigDecimal getLoss54() {
        return loss54;
    }

    public void setLoss54(BigDecimal loss54) {
        this.loss54 = loss54;
    }

    public BigDecimal getLoss55() {
        return loss55;
    }

    public void setLoss55(BigDecimal loss55) {
        this.loss55 = loss55;
    }

    public BigDecimal getLoss56() {
        return loss56;
    }

    public void setLoss56(BigDecimal loss56) {
        this.loss56 = loss56;
    }

    public BigDecimal getLoss57() {
        return loss57;
    }

    public void setLoss57(BigDecimal loss57) {
        this.loss57 = loss57;
    }

    public BigDecimal getLoss58() {
        return loss58;
    }

    public void setLoss58(BigDecimal loss58) {
        this.loss58 = loss58;
    }

    public BigDecimal getLoss59() {
        return loss59;
    }

    public void setLoss59(BigDecimal loss59) {
        this.loss59 = loss59;
    }

    public BigDecimal getLoss60() {
        return loss60;
    }

    public void setLoss60(BigDecimal loss60) {
        this.loss60 = loss60;
    }

    public BigDecimal getLoss61() {
        return loss61;
    }

    public void setLoss61(BigDecimal loss61) {
        this.loss61 = loss61;
    }

    public BigDecimal getLoss62() {
        return loss62;
    }

    public void setLoss62(BigDecimal loss62) {
        this.loss62 = loss62;
    }

    public BigDecimal getLoss63() {
        return loss63;
    }

    public void setLoss63(BigDecimal loss63) {
        this.loss63 = loss63;
    }

    public BigDecimal getLoss64() {
        return loss64;
    }

    public void setLoss64(BigDecimal loss64) {
        this.loss64 = loss64;
    }

    public BigDecimal getLoss65() {
        return loss65;
    }

    public void setLoss65(BigDecimal loss65) {
        this.loss65 = loss65;
    }

    public BigDecimal getLoss66() {
        return loss66;
    }

    public void setLoss66(BigDecimal loss66) {
        this.loss66 = loss66;
    }

    public BigDecimal getLoss67() {
        return loss67;
    }

    public void setLoss67(BigDecimal loss67) {
        this.loss67 = loss67;
    }

    public BigDecimal getLoss68() {
        return loss68;
    }

    public void setLoss68(BigDecimal loss68) {
        this.loss68 = loss68;
    }

    public BigDecimal getLoss69() {
        return loss69;
    }

    public void setLoss69(BigDecimal loss69) {
        this.loss69 = loss69;
    }

    public BigDecimal getLoss70() {
        return loss70;
    }

    public void setLoss70(BigDecimal loss70) {
        this.loss70 = loss70;
    }

    public BigDecimal getLoss71() {
        return loss71;
    }

    public void setLoss71(BigDecimal loss71) {
        this.loss71 = loss71;
    }

    public BigDecimal getLoss72() {
        return loss72;
    }

    public void setLoss72(BigDecimal loss72) {
        this.loss72 = loss72;
    }

    public BigDecimal getLoss73() {
        return loss73;
    }

    public void setLoss73(BigDecimal loss73) {
        this.loss73 = loss73;
    }

    public BigDecimal getLoss74() {
        return loss74;
    }

    public void setLoss74(BigDecimal loss74) {
        this.loss74 = loss74;
    }

    public BigDecimal getLoss75() {
        return loss75;
    }

    public void setLoss75(BigDecimal loss75) {
        this.loss75 = loss75;
    }

    public BigDecimal getLoss76() {
        return loss76;
    }

    public void setLoss76(BigDecimal loss76) {
        this.loss76 = loss76;
    }

    public BigDecimal getLoss77() {
        return loss77;
    }

    public void setLoss77(BigDecimal loss77) {
        this.loss77 = loss77;
    }

    public BigDecimal getLoss78() {
        return loss78;
    }

    public void setLoss78(BigDecimal loss78) {
        this.loss78 = loss78;
    }

    public BigDecimal getLoss79() {
        return loss79;
    }

    public void setLoss79(BigDecimal loss79) {
        this.loss79 = loss79;
    }

    public BigDecimal getLoss80() {
        return loss80;
    }

    public void setLoss80(BigDecimal loss80) {
        this.loss80 = loss80;
    }

    public BigDecimal getLoss81() {
        return loss81;
    }

    public void setLoss81(BigDecimal loss81) {
        this.loss81 = loss81;
    }

    public BigDecimal getLoss82() {
        return loss82;
    }

    public void setLoss82(BigDecimal loss82) {
        this.loss82 = loss82;
    }

    public BigDecimal getLoss83() {
        return loss83;
    }

    public void setLoss83(BigDecimal loss83) {
        this.loss83 = loss83;
    }

    public BigDecimal getLoss84() {
        return loss84;
    }

    public void setLoss84(BigDecimal loss84) {
        this.loss84 = loss84;
    }

    public BigDecimal getLoss85() {
        return loss85;
    }

    public void setLoss85(BigDecimal loss85) {
        this.loss85 = loss85;
    }

    public BigDecimal getLoss86() {
        return loss86;
    }

    public void setLoss86(BigDecimal loss86) {
        this.loss86 = loss86;
    }

    public BigDecimal getLoss87() {
        return loss87;
    }

    public void setLoss87(BigDecimal loss87) {
        this.loss87 = loss87;
    }

    public BigDecimal getLoss88() {
        return loss88;
    }

    public void setLoss88(BigDecimal loss88) {
        this.loss88 = loss88;
    }

    public BigDecimal getLoss89() {
        return loss89;
    }

    public void setLoss89(BigDecimal loss89) {
        this.loss89 = loss89;
    }

    public BigDecimal getLoss90() {
        return loss90;
    }

    public void setLoss90(BigDecimal loss90) {
        this.loss90 = loss90;
    }

    public BigDecimal getLoss91() {
        return loss91;
    }

    public void setLoss91(BigDecimal loss91) {
        this.loss91 = loss91;
    }

    public BigDecimal getLoss92() {
        return loss92;
    }

    public void setLoss92(BigDecimal loss92) {
        this.loss92 = loss92;
    }

    public BigDecimal getLoss93() {
        return loss93;
    }

    public void setLoss93(BigDecimal loss93) {
        this.loss93 = loss93;
    }

    public BigDecimal getLoss94() {
        return loss94;
    }

    public void setLoss94(BigDecimal loss94) {
        this.loss94 = loss94;
    }

    public BigDecimal getLoss95() {
        return loss95;
    }

    public void setLoss95(BigDecimal loss95) {
        this.loss95 = loss95;
    }

    public BigDecimal getLoss96() {
        return loss96;
    }

    public void setLoss96(BigDecimal loss96) {
        this.loss96 = loss96;
    }

    public BigDecimal getLoss97() {
        return loss97;
    }

    public void setLoss97(BigDecimal loss97) {
        this.loss97 = loss97;
    }

    public BigDecimal getTrimmingqty() {
        return trimmingqty;
    }

    public void setTrimmingqty(BigDecimal trimmingqty) {
        this.trimmingqty = trimmingqty;
    }

    public BigDecimal getExpansionqty() {
        return expansionqty;
    }

    public void setExpansionqty(BigDecimal expansionqty) {
        this.expansionqty = expansionqty;
    }

    public String getModelchangepart() {
        return modelchangepart;
    }

    public void setModelchangepart(String modelchangepart) {
        this.modelchangepart = modelchangepart;
    }

    public BigDecimal getReprocessing() {
        return reprocessing;
    }

    public void setReprocessing(BigDecimal reprocessing) {
        this.reprocessing = reprocessing;
    }

    public BigDecimal getInspectedqty() {
        return inspectedqty;
    }

    public void setInspectedqty(BigDecimal inspectedqty) {
        this.inspectedqty = inspectedqty;
    }

    public BigDecimal getQaacceptedqty() {
        return qaacceptedqty;
    }

    public void setQaacceptedqty(BigDecimal qaacceptedqty) {
        this.qaacceptedqty = qaacceptedqty;
    }

    public BigDecimal getBacklogqty() {
        return backlogqty;
    }

    public void setBacklogqty(BigDecimal backlogqty) {
        this.backlogqty = backlogqty;
    }

    public BigDecimal getActualruntime_old() {
        return actualruntime_old;
    }

    public void setActualruntime_old(BigDecimal actualruntime_old) {
        this.actualruntime_old = actualruntime_old;
    }

    public BigDecimal getNetutilisationtime_old() {
        return netutilisationtime_old;
    }

    public void setNetutilisationtime_old(BigDecimal netutilisationtime_old) {
        this.netutilisationtime_old = netutilisationtime_old;
    }

    public BigDecimal getPlantavltime_old() {
        return plantavltime_old;
    }

    public void setPlantavltime_old(BigDecimal plantavltime_old) {
        this.plantavltime_old = plantavltime_old;
    }

    public BigDecimal getAe() {
        return ae;
    }

    public void setAe(BigDecimal ae) {
        this.ae = ae;
    }

    public BigDecimal getAu() {
        return au;
    }

    public void setAu(BigDecimal au) {
        this.au = au;
    }

    public BigDecimal getOe() {
        return oe;
    }

    public void setOe(BigDecimal oe) {
        this.oe = oe;
    }

    public BigDecimal getPfprocessfailureloss() {
        return pfprocessfailureloss;
    }

    public void setPfprocessfailureloss(BigDecimal pfprocessfailureloss) {
        this.pfprocessfailureloss = pfprocessfailureloss;
    }

    public BigDecimal getAncillaryutilityloss() {
        return ancillaryutilityloss;
    }

    public void setAncillaryutilityloss(BigDecimal ancillaryutilityloss) {
        this.ancillaryutilityloss = ancillaryutilityloss;
    }

    public BigDecimal getMgmloadingtime() {
        return mgmloadingtime;
    }

    public void setMgmloadingtime(BigDecimal mgmloadingtime) {
        this.mgmloadingtime = mgmloadingtime;
    }

    public BigDecimal getMgmproductiontime() {
        return mgmproductiontime;
    }

    public void setMgmproductiontime(BigDecimal mgmproductiontime) {
        this.mgmproductiontime = mgmproductiontime;
    }

    public BigDecimal getMgmoe() {
        return mgmoe;
    }

    public void setMgmoe(BigDecimal mgmoe) {
        this.mgmoe = mgmoe;
    }

    public BigDecimal getMgmroa() {
        return mgmroa;
    }

    public void setMgmroa(BigDecimal mgmroa) {
        this.mgmroa = mgmroa;
    }

    public BigDecimal getMgmrop() {
        return mgmrop;
    }

    public void setMgmrop(BigDecimal mgmrop) {
        this.mgmrop = mgmrop;
    }

    public BigDecimal getMgmroq() {
        return mgmroq;
    }

    public void setMgmroq(BigDecimal mgmroq) {
        this.mgmroq = mgmroq;
    }

    public BigDecimal getMgmoee() {
        return mgmoee;
    }

    public void setMgmoee(BigDecimal mgmoee) {
        this.mgmoee = mgmoee;
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

    public BigDecimal getLoss98() {
        return loss98;
    }

    public void setLoss98(BigDecimal loss98) {
        this.loss98 = loss98;
    }

    public BigDecimal getLoss99() {
        return loss99;
    }

    public void setLoss99(BigDecimal loss99) {
        this.loss99 = loss99;
    }

    public BigDecimal getLoss100() {
        return loss100;
    }

    public void setLoss100(BigDecimal loss100) {
        this.loss100 = loss100;
    }

    public BigDecimal getLoss101() {
        return loss101;
    }

    public void setLoss101(BigDecimal loss101) {
        this.loss101 = loss101;
    }

    public BigDecimal getPokayokelinestop_ml() {
        return pokayokelinestop_ml;
    }

    public void setPokayokelinestop_ml(BigDecimal pokayokelinestop_ml) {
        this.pokayokelinestop_ml = pokayokelinestop_ml;
    }

    public BigDecimal getLoss102() {
        return loss102;
    }

    public void setLoss102(BigDecimal loss102) {
        this.loss102 = loss102;
    }

    public BigDecimal getLoss103() {
        return loss103;
    }

    public void setLoss103(BigDecimal loss103) {
        this.loss103 = loss103;
    }

    public BigDecimal getLoss104() {
        return loss104;
    }

    public void setLoss104(BigDecimal loss104) {
        this.loss104 = loss104;
    }

    public BigDecimal getLoss105() {
        return loss105;
    }

    public void setLoss105(BigDecimal loss105) {
        this.loss105 = loss105;
    }

    public BigDecimal getLoss106() {
        return loss106;
    }

    public void setLoss106(BigDecimal loss106) {
        this.loss106 = loss106;
    }

    public BigDecimal getLoss107() {
        return loss107;
    }

    public void setLoss107(BigDecimal loss107) {
        this.loss107 = loss107;
    }

    public BigDecimal getJigfixtureloss_ml() {
        return jigfixtureloss_ml;
    }

    public void setJigfixtureloss_ml(BigDecimal jigfixtureloss_ml) {
        this.jigfixtureloss_ml = jigfixtureloss_ml;
    }

    public BigDecimal getLoss108() {
        return loss108;
    }

    public void setLoss108(BigDecimal loss108) {
        this.loss108 = loss108;
    }

    public BigDecimal getLoss109() {
        return loss109;
    }

    public void setLoss109(BigDecimal loss109) {
        this.loss109 = loss109;
    }

    public BigDecimal getLoss110() {
        return loss110;
    }

    public void setLoss110(BigDecimal loss110) {
        this.loss110 = loss110;
    }

    public BigDecimal getLoss111() {
        return loss111;
    }

    public void setLoss111(BigDecimal loss111) {
        this.loss111 = loss111;
    }

    public BigDecimal getLoss112() {
        return loss112;
    }

    public void setLoss112(BigDecimal loss112) {
        this.loss112 = loss112;
    }

    public BigDecimal getLoss113() {
        return loss113;
    }

    public void setLoss113(BigDecimal loss113) {
        this.loss113 = loss113;
    }

    public BigDecimal getLoss114() {
        return loss114;
    }

    public void setLoss114(BigDecimal loss114) {
        this.loss114 = loss114;
    }

    public BigDecimal getLoss115() {
        return loss115;
    }

    public void setLoss115(BigDecimal loss115) {
        this.loss115 = loss115;
    }

    public BigDecimal getLoss116() {
        return loss116;
    }

    public void setLoss116(BigDecimal loss116) {
        this.loss116 = loss116;
    }

    public BigDecimal getLoss117() {
        return loss117;
    }

    public void setLoss117(BigDecimal loss117) {
        this.loss117 = loss117;
    }

    public BigDecimal getLoss118() {
        return loss118;
    }

    public void setLoss118(BigDecimal loss118) {
        this.loss118 = loss118;
    }

    public BigDecimal getLoss119() {
        return loss119;
    }

    public void setLoss119(BigDecimal loss119) {
        this.loss119 = loss119;
    }

    public BigDecimal getLoss120() {
        return loss120;
    }

    public void setLoss120(BigDecimal loss120) {
        this.loss120 = loss120;
    }

    public BigDecimal getLoss121() {
        return loss121;
    }

    public void setLoss121(BigDecimal loss121) {
        this.loss121 = loss121;
    }

    public BigDecimal getLoss122() {
        return loss122;
    }

    public void setLoss122(BigDecimal loss122) {
        this.loss122 = loss122;
    }

}