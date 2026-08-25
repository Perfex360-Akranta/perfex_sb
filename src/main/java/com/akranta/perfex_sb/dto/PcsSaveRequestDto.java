package com.akranta.perfex_sb.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

public class PcsSaveRequestDto {

    private PcsMasterDto master;
    private PcsDetailDto detail;
    private PcsLossReasonLinkDto lossReasonLink;
    private PcsLossCaptureDto lossCapture;
    private String detailTableName;
    private String dmtLevel;

    public PcsMasterDto getMaster() {
        return master;
    }

    public void setMaster(PcsMasterDto master) {
        this.master = master;
    }

    public PcsDetailDto getDetail() {
        return detail;
    }

    public void setDetail(PcsDetailDto detail) {
        this.detail = detail;
    }

    public PcsLossReasonLinkDto getLossReasonLink() {
        return lossReasonLink;
    }

    public void setLossReasonLink(PcsLossReasonLinkDto lossReasonLink) {
        this.lossReasonLink = lossReasonLink;
    }

    public PcsLossCaptureDto getLossCapture() {
        return lossCapture;
    }

    public void setLossCapture(PcsLossCaptureDto lossCapture) {
        this.lossCapture = lossCapture;
    }

    public String getDetailTableName() {
        return detailTableName;
    }

    public void setDetailTableName(String detailTableName) {
        this.detailTableName = detailTableName;
    }

    public String getDmtLevel() {
        return dmtLevel;
    }

    public void setDmtLevel(String dmtLevel) {
        this.dmtLevel = dmtLevel;
    }

    public static class PcsMasterDto {

        private String prlmKeyid;
        private LocalDateTime prlmDate;
        private String prlmFactoryid;
        private String prlmSectionid;
        private String prlmCellid;
        private String prlmMachineid;
        private String prlmShiftid;
        private LocalDateTime prlmEntrydate;
        private String prlmEntryby;
        private String prlmUpdatedby;
        private LocalDateTime prlmUpdateddate;
        private String prlmApprovedby;
        private LocalDateTime prlmApprovedate;
        private String prlmApporvedflag;
        private String prlmShiftincharge;
        private String prlmSubgroupid;
        private String prlmIsnoplan;
        private String prlmLogtype;
        private String prlmTimeorqty;
        private String prlmCompletedflag;
        private String prlmTempfield2;
        private String prlmFlid;
        private String prlmElementid;
        private String prlmActive;
        private String prlmCreatedby;
        private LocalDateTime prlmCreatedon;
        private LocalDateTime prlmModifiedon;

        public String getPrlmKeyid() {
            return prlmKeyid;
        }

        public void setPrlmKeyid(String prlmKeyid) {
            this.prlmKeyid = prlmKeyid;
        }

        public LocalDateTime getPrlmDate() {
            return prlmDate;
        }

        public void setPrlmDate(LocalDateTime prlmDate) {
            this.prlmDate = prlmDate;
        }

        public String getPrlmFactoryid() {
            return prlmFactoryid;
        }

        public void setPrlmFactoryid(String prlmFactoryid) {
            this.prlmFactoryid = prlmFactoryid;
        }

        public String getPrlmSectionid() {
            return prlmSectionid;
        }

        public void setPrlmSectionid(String prlmSectionid) {
            this.prlmSectionid = prlmSectionid;
        }

        public String getPrlmCellid() {
            return prlmCellid;
        }

        public void setPrlmCellid(String prlmCellid) {
            this.prlmCellid = prlmCellid;
        }

        public String getPrlmMachineid() {
            return prlmMachineid;
        }

        public void setPrlmMachineid(String prlmMachineid) {
            this.prlmMachineid = prlmMachineid;
        }

        public String getPrlmShiftid() {
            return prlmShiftid;
        }

        public void setPrlmShiftid(String prlmShiftid) {
            this.prlmShiftid = prlmShiftid;
        }

        public LocalDateTime getPrlmEntrydate() {
            return prlmEntrydate;
        }

        public void setPrlmEntrydate(LocalDateTime prlmEntrydate) {
            this.prlmEntrydate = prlmEntrydate;
        }

        public String getPrlmEntryby() {
            return prlmEntryby;
        }

        public void setPrlmEntryby(String prlmEntryby) {
            this.prlmEntryby = prlmEntryby;
        }

        public String getPrlmUpdatedby() {
            return prlmUpdatedby;
        }

        public void setPrlmUpdatedby(String prlmUpdatedby) {
            this.prlmUpdatedby = prlmUpdatedby;
        }

        public LocalDateTime getPrlmUpdateddate() {
            return prlmUpdateddate;
        }

        public void setPrlmUpdateddate(LocalDateTime prlmUpdateddate) {
            this.prlmUpdateddate = prlmUpdateddate;
        }

        public String getPrlmApprovedby() {
            return prlmApprovedby;
        }

        public void setPrlmApprovedby(String prlmApprovedby) {
            this.prlmApprovedby = prlmApprovedby;
        }

        public LocalDateTime getPrlmApprovedate() {
            return prlmApprovedate;
        }

        public void setPrlmApprovedate(LocalDateTime prlmApprovedate) {
            this.prlmApprovedate = prlmApprovedate;
        }

        public String getPrlmApporvedflag() {
            return prlmApporvedflag;
        }

        public void setPrlmApporvedflag(String prlmApporvedflag) {
            this.prlmApporvedflag = prlmApporvedflag;
        }

        public String getPrlmShiftincharge() {
            return prlmShiftincharge;
        }

        public void setPrlmShiftincharge(String prlmShiftincharge) {
            this.prlmShiftincharge = prlmShiftincharge;
        }

        public String getPrlmSubgroupid() {
            return prlmSubgroupid;
        }

        public void setPrlmSubgroupid(String prlmSubgroupid) {
            this.prlmSubgroupid = prlmSubgroupid;
        }

        public String getPrlmIsnoplan() {
            return prlmIsnoplan;
        }

        public void setPrlmIsnoplan(String prlmIsnoplan) {
            this.prlmIsnoplan = prlmIsnoplan;
        }

        public String getPrlmLogtype() {
            return prlmLogtype;
        }

        public void setPrlmLogtype(String prlmLogtype) {
            this.prlmLogtype = prlmLogtype;
        }

        public String getPrlmTimeorqty() {
            return prlmTimeorqty;
        }

        public void setPrlmTimeorqty(String prlmTimeorqty) {
            this.prlmTimeorqty = prlmTimeorqty;
        }

        public String getPrlmCompletedflag() {
            return prlmCompletedflag;
        }

        public void setPrlmCompletedflag(String prlmCompletedflag) {
            this.prlmCompletedflag = prlmCompletedflag;
        }

        public String getPrlmTempfield2() {
            return prlmTempfield2;
        }

        public void setPrlmTempfield2(String prlmTempfield2) {
            this.prlmTempfield2 = prlmTempfield2;
        }

        public String getPrlmFlid() {
            return prlmFlid;
        }

        public void setPrlmFlid(String prlmFlid) {
            this.prlmFlid = prlmFlid;
        }

        public String getPrlmElementid() {
            return prlmElementid;
        }

        public void setPrlmElementid(String prlmElementid) {
            this.prlmElementid = prlmElementid;
        }

        public String getPrlmActive() {
            return prlmActive;
        }

        public void setPrlmActive(String prlmActive) {
            this.prlmActive = prlmActive;
        }

        public String getPrlmCreatedby() {
            return prlmCreatedby;
        }

        public void setPrlmCreatedby(String prlmCreatedby) {
            this.prlmCreatedby = prlmCreatedby;
        }

        public LocalDateTime getPrlmCreatedon() {
            return prlmCreatedon;
        }

        public void setPrlmCreatedon(LocalDateTime prlmCreatedon) {
            this.prlmCreatedon = prlmCreatedon;
        }

        public LocalDateTime getPrlmModifiedon() {
            return prlmModifiedon;
        }

        public void setPrlmModifiedon(LocalDateTime prlmModifiedon) {
            this.prlmModifiedon = prlmModifiedon;
        }
    }

    public static class PcsDetailDto {

        private String pldetailsid;
        private String plmasterid;
        private String cellid;
        private String machineid;
        private BigDecimal operators;
        private BigDecimal calendartime;
        private BigDecimal noplaninmins;
        private BigDecimal noofproducts;
        private String productid;
        private BigDecimal plannedqty;
        private String rawmaterialtype;
        private BigDecimal weight;
        private String wno;
        private String operationno;
        private String operationdescription;
        private BigDecimal theoriticalcycletime;
        private BigDecimal actualcycletime;
        private String batchno;
        private BigDecimal cavityavailable;
        private BigDecimal cavityused;
        private BigDecimal mandrelavailable;
        private BigDecimal mandrelused;
        private BigDecimal producedqty;
        private BigDecimal defectsandreworklossMl;
        private BigDecimal rejectedqty;
        private BigDecimal reworkqty;
        private BigDecimal defecttimeSl;
        private BigDecimal productionlosses;
        private BigDecimal equipmentfailureMl;
        private BigDecimal setupandadjustmentMl;
        private BigDecimal toolchangelossMl;
        private BigDecimal startuplossMl;
        private BigDecimal minorstoppagelossMl;
        private BigDecimal speedlossMl;
        private BigDecimal shutdownlossMl;
        private BigDecimal managementlossMl;
        private BigDecimal commonutilitylossSl;
        private BigDecimal operatingmotionlossMl;
        private BigDecimal lineorganisationlossMl;
        private BigDecimal logisticslossMl;
        private BigDecimal measuringandadjlossMl;
        private BigDecimal dietoolandjiglossMl;
        private BigDecimal energylossMl;
        private BigDecimal yieldlossMl;
        private BigDecimal unaccountedtime;
        private BigDecimal loadingtime;
        private BigDecimal effectiveprodmins;
        private BigDecimal mchavailabletime;
        private BigDecimal productionavltime;
        private BigDecimal productiontime;
        private BigDecimal roa;
        private BigDecimal rop;
        private BigDecimal roq;
        private BigDecimal oee;
        private String completedflag;
        private String remarks;
        // loss01..loss97 are captured here (preserve order if provided)
        private Map<String, BigDecimal> losses = new LinkedHashMap<>();
        private BigDecimal trimmingqty;
        private BigDecimal expansionqty;
        private String modelchangepart;
        private BigDecimal reprocessing;
        private BigDecimal inspectedqty;
        private BigDecimal qaacceptedqty;
        private BigDecimal backlogqty;
        private BigDecimal actualruntimeOld;
        private BigDecimal netutilisationtimeOld;
        private BigDecimal plantavltimeOld;
        private BigDecimal ae;
        private BigDecimal au;
        private BigDecimal oe;
        private BigDecimal pfprocessfailureloss;
        private BigDecimal ancillaryutilityloss;
        private BigDecimal mgmloadingtime;
        private BigDecimal mgmproductiontime;
        private BigDecimal mgmoe;
        private BigDecimal mgmroa;
        private BigDecimal mgmrop;
        private BigDecimal mgmroq;
        private BigDecimal mgmoee;
        private String active;
        private String createdby;
        private LocalDateTime createdon;
        private LocalDateTime modifiedon;

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

        public BigDecimal getDefectsandreworklossMl() {
            return defectsandreworklossMl;
        }

        public void setDefectsandreworklossMl(BigDecimal defectsandreworklossMl) {
            this.defectsandreworklossMl = defectsandreworklossMl;
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

        public BigDecimal getDefecttimeSl() {
            return defecttimeSl;
        }

        public void setDefecttimeSl(BigDecimal defecttimeSl) {
            this.defecttimeSl = defecttimeSl;
        }

        public BigDecimal getProductionlosses() {
            return productionlosses;
        }

        public void setProductionlosses(BigDecimal productionlosses) {
            this.productionlosses = productionlosses;
        }

        public BigDecimal getEquipmentfailureMl() {
            return equipmentfailureMl;
        }

        public void setEquipmentfailureMl(BigDecimal equipmentfailureMl) {
            this.equipmentfailureMl = equipmentfailureMl;
        }

        public BigDecimal getSetupandadjustmentMl() {
            return setupandadjustmentMl;
        }

        public void setSetupandadjustmentMl(BigDecimal setupandadjustmentMl) {
            this.setupandadjustmentMl = setupandadjustmentMl;
        }

        public BigDecimal getToolchangelossMl() {
            return toolchangelossMl;
        }

        public void setToolchangelossMl(BigDecimal toolchangelossMl) {
            this.toolchangelossMl = toolchangelossMl;
        }

        public BigDecimal getStartuplossMl() {
            return startuplossMl;
        }

        public void setStartuplossMl(BigDecimal startuplossMl) {
            this.startuplossMl = startuplossMl;
        }

        public BigDecimal getMinorstoppagelossMl() {
            return minorstoppagelossMl;
        }

        public void setMinorstoppagelossMl(BigDecimal minorstoppagelossMl) {
            this.minorstoppagelossMl = minorstoppagelossMl;
        }

        public BigDecimal getSpeedlossMl() {
            return speedlossMl;
        }

        public void setSpeedlossMl(BigDecimal speedlossMl) {
            this.speedlossMl = speedlossMl;
        }

        public BigDecimal getShutdownlossMl() {
            return shutdownlossMl;
        }

        public void setShutdownlossMl(BigDecimal shutdownlossMl) {
            this.shutdownlossMl = shutdownlossMl;
        }

        public BigDecimal getManagementlossMl() {
            return managementlossMl;
        }

        public void setManagementlossMl(BigDecimal managementlossMl) {
            this.managementlossMl = managementlossMl;
        }

        public BigDecimal getCommonutilitylossSl() {
            return commonutilitylossSl;
        }

        public void setCommonutilitylossSl(BigDecimal commonutilitylossSl) {
            this.commonutilitylossSl = commonutilitylossSl;
        }

        public BigDecimal getOperatingmotionlossMl() {
            return operatingmotionlossMl;
        }

        public void setOperatingmotionlossMl(BigDecimal operatingmotionlossMl) {
            this.operatingmotionlossMl = operatingmotionlossMl;
        }

        public BigDecimal getLineorganisationlossMl() {
            return lineorganisationlossMl;
        }

        public void setLineorganisationlossMl(BigDecimal lineorganisationlossMl) {
            this.lineorganisationlossMl = lineorganisationlossMl;
        }

        public BigDecimal getLogisticslossMl() {
            return logisticslossMl;
        }

        public void setLogisticslossMl(BigDecimal logisticslossMl) {
            this.logisticslossMl = logisticslossMl;
        }

        public BigDecimal getMeasuringandadjlossMl() {
            return measuringandadjlossMl;
        }

        public void setMeasuringandadjlossMl(BigDecimal measuringandadjlossMl) {
            this.measuringandadjlossMl = measuringandadjlossMl;
        }

        public BigDecimal getDietoolandjiglossMl() {
            return dietoolandjiglossMl;
        }

        public void setDietoolandjiglossMl(BigDecimal dietoolandjiglossMl) {
            this.dietoolandjiglossMl = dietoolandjiglossMl;
        }

        public BigDecimal getEnergylossMl() {
            return energylossMl;
        }

        public void setEnergylossMl(BigDecimal energylossMl) {
            this.energylossMl = energylossMl;
        }

        public BigDecimal getYieldlossMl() {
            return yieldlossMl;
        }

        public void setYieldlossMl(BigDecimal yieldlossMl) {
            this.yieldlossMl = yieldlossMl;
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

        public String getCompletedflag() {
            return completedflag;
        }

        public void setCompletedflag(String completedflag) {
            this.completedflag = completedflag;
        }

        public String getRemarks() {
            return remarks;
        }

        public void setRemarks(String remarks) {
            this.remarks = remarks;
        }

        public Map<String, BigDecimal> getLosses() {
            return losses;
        }

        public void setLosses(Map<String, BigDecimal> losses) {
            this.losses = losses;
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

        public BigDecimal getActualruntimeOld() {
            return actualruntimeOld;
        }

        public void setActualruntimeOld(BigDecimal actualruntimeOld) {
            this.actualruntimeOld = actualruntimeOld;
        }

        public BigDecimal getNetutilisationtimeOld() {
            return netutilisationtimeOld;
        }

        public void setNetutilisationtimeOld(BigDecimal netutilisationtimeOld) {
            this.netutilisationtimeOld = netutilisationtimeOld;
        }

        public BigDecimal getPlantavltimeOld() {
            return plantavltimeOld;
        }

        public void setPlantavltimeOld(BigDecimal plantavltimeOld) {
            this.plantavltimeOld = plantavltimeOld;
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

        public String getActive() {
            return active;
        }

        public void setActive(String active) {
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
    }

    public static class PcsLossReasonLinkDto {

        private String plrkKeyid;
        private String plrkLossid;
        private String plrkReasonid;
        private String plrkCauseid;
        private String plrkRootcauseid;
        private String plrkPldetailid;
        private String plrkWno;
        private BigDecimal plrkMinutes;
        private BigDecimal plrkInstance;
        private LocalDateTime plrkDate;
        private String plrkShiftid;
        private BigDecimal plrkHourno;
        private String plrkFactoryid;
        private String plrkSectionid;
        private String plrkCellid;
        private String plrkMachineid;
        private String plrkSubgroupid;
        private String plrkRemarks;
        private String plrkMsrno;
        private String plrkProcessid;
        private String plrkFlid;
        private String plrkElementid;
        private String plrkActive;
        private String plrkCreatedby;
        private LocalDateTime plrkCreatedon;
        private LocalDateTime plrkModifiedon;

        public String getPlrkKeyid() {
            return plrkKeyid;
        }

        public void setPlrkKeyid(String plrkKeyid) {
            this.plrkKeyid = plrkKeyid;
        }

        public String getPlrkLossid() {
            return plrkLossid;
        }

        public void setPlrkLossid(String plrkLossid) {
            this.plrkLossid = plrkLossid;
        }

        public String getPlrkReasonid() {
            return plrkReasonid;
        }

        public void setPlrkReasonid(String plrkReasonid) {
            this.plrkReasonid = plrkReasonid;
        }

        public String getPlrkCauseid() {
            return plrkCauseid;
        }

        public void setPlrkCauseid(String plrkCauseid) {
            this.plrkCauseid = plrkCauseid;
        }

        public String getPlrkRootcauseid() {
            return plrkRootcauseid;
        }

        public void setPlrkRootcauseid(String plrkRootcauseid) {
            this.plrkRootcauseid = plrkRootcauseid;
        }

        public String getPlrkPldetailid() {
            return plrkPldetailid;
        }

        public void setPlrkPldetailid(String plrkPldetailid) {
            this.plrkPldetailid = plrkPldetailid;
        }

        public String getPlrkWno() {
            return plrkWno;
        }

        public void setPlrkWno(String plrkWno) {
            this.plrkWno = plrkWno;
        }

        public BigDecimal getPlrkMinutes() {
            return plrkMinutes;
        }

        public void setPlrkMinutes(BigDecimal plrkMinutes) {
            this.plrkMinutes = plrkMinutes;
        }

        public BigDecimal getPlrkInstance() {
            return plrkInstance;
        }

        public void setPlrkInstance(BigDecimal plrkInstance) {
            this.plrkInstance = plrkInstance;
        }

        public LocalDateTime getPlrkDate() {
            return plrkDate;
        }

        public void setPlrkDate(LocalDateTime plrkDate) {
            this.plrkDate = plrkDate;
        }

        public String getPlrkShiftid() {
            return plrkShiftid;
        }

        public void setPlrkShiftid(String plrkShiftid) {
            this.plrkShiftid = plrkShiftid;
        }

        public BigDecimal getPlrkHourno() {
            return plrkHourno;
        }

        public void setPlrkHourno(BigDecimal plrkHourno) {
            this.plrkHourno = plrkHourno;
        }

        public String getPlrkFactoryid() {
            return plrkFactoryid;
        }

        public void setPlrkFactoryid(String plrkFactoryid) {
            this.plrkFactoryid = plrkFactoryid;
        }

        public String getPlrkSectionid() {
            return plrkSectionid;
        }

        public void setPlrkSectionid(String plrkSectionid) {
            this.plrkSectionid = plrkSectionid;
        }

        public String getPlrkCellid() {
            return plrkCellid;
        }

        public void setPlrkCellid(String plrkCellid) {
            this.plrkCellid = plrkCellid;
        }

        public String getPlrkMachineid() {
            return plrkMachineid;
        }

        public void setPlrkMachineid(String plrkMachineid) {
            this.plrkMachineid = plrkMachineid;
        }

        public String getPlrkSubgroupid() {
            return plrkSubgroupid;
        }

        public void setPlrkSubgroupid(String plrkSubgroupid) {
            this.plrkSubgroupid = plrkSubgroupid;
        }

        public String getPlrkRemarks() {
            return plrkRemarks;
        }

        public void setPlrkRemarks(String plrkRemarks) {
            this.plrkRemarks = plrkRemarks;
        }

        public String getPlrkMsrno() {
            return plrkMsrno;
        }

        public void setPlrkMsrno(String plrkMsrno) {
            this.plrkMsrno = plrkMsrno;
        }

        public String getPlrkProcessid() {
            return plrkProcessid;
        }

        public void setPlrkProcessid(String plrkProcessid) {
            this.plrkProcessid = plrkProcessid;
        }

        public String getPlrkFlid() {
            return plrkFlid;
        }

        public void setPlrkFlid(String plrkFlid) {
            this.plrkFlid = plrkFlid;
        }

        public String getPlrkElementid() {
            return plrkElementid;
        }

        public void setPlrkElementid(String plrkElementid) {
            this.plrkElementid = plrkElementid;
        }

        public String getPlrkActive() {
            return plrkActive;
        }

        public void setPlrkActive(String plrkActive) {
            this.plrkActive = plrkActive;
        }

        public String getPlrkCreatedby() {
            return plrkCreatedby;
        }

        public void setPlrkCreatedby(String plrkCreatedby) {
            this.plrkCreatedby = plrkCreatedby;
        }

        public LocalDateTime getPlrkCreatedon() {
            return plrkCreatedon;
        }

        public void setPlrkCreatedon(LocalDateTime plrkCreatedon) {
            this.plrkCreatedon = plrkCreatedon;
        }

        public LocalDateTime getPlrkModifiedon() {
            return plrkModifiedon;
        }

        public void setPlrkModifiedon(LocalDateTime plrkModifiedon) {
            this.plrkModifiedon = plrkModifiedon;
        }
    }

    public static class PcsLossCaptureDto {

        private String plosKeyid;
        private String plosFlid;
        private LocalDateTime plosDate;
        private String plosShiftid;
        private LocalDateTime plosFromtime;
        private LocalDateTime plosTotime;
        private String plosLosstime;
        private String plosLossreason;
        private String plosLossid;
        private String plosTradeid;
        private String plosProdImpact;
        private BigDecimal plosProdImpQty;
        private String plosLossdescription;
        private String plosPldetailsid;
        private String plosEquipment;
        private String plosDetectedby;
        private String plosTempfield3;
        private String plosTempfield4;
        private String plosTempfield5;
        private String plosActive;
        private String plosCreatedby;
        private LocalDateTime plosCreatedon;
        private LocalDateTime plosModifiedon;

        public String getPlosKeyid() {
            return plosKeyid;
        }

        public void setPlosKeyid(String plosKeyid) {
            this.plosKeyid = plosKeyid;
        }

        public String getPlosFlid() {
            return plosFlid;
        }

        public void setPlosFlid(String plosFlid) {
            this.plosFlid = plosFlid;
        }

        public LocalDateTime getPlosDate() {
            return plosDate;
        }

        public void setPlosDate(LocalDateTime plosDate) {
            this.plosDate = plosDate;
        }

        public String getPlosShiftid() {
            return plosShiftid;
        }

        public void setPlosShiftid(String plosShiftid) {
            this.plosShiftid = plosShiftid;
        }

        public LocalDateTime getPlosFromtime() {
            return plosFromtime;
        }

        public void setPlosFromtime(LocalDateTime plosFromtime) {
            this.plosFromtime = plosFromtime;
        }

        public LocalDateTime getPlosTotime() {
            return plosTotime;
        }

        public void setPlosTotime(LocalDateTime plosTotime) {
            this.plosTotime = plosTotime;
        }

        public String getPlosLosstime() {
            return plosLosstime;
        }

        public void setPlosLosstime(String plosLosstime) {
            this.plosLosstime = plosLosstime;
        }

        public String getPlosLossreason() {
            return plosLossreason;
        }

        public void setPlosLossreason(String plosLossreason) {
            this.plosLossreason = plosLossreason;
        }

        public String getPlosLossid() {
            return plosLossid;
        }

        public void setPlosLossid(String plosLossid) {
            this.plosLossid = plosLossid;
        }

        public String getPlosTradeid() {
            return plosTradeid;
        }

        public void setPlosTradeid(String plosTradeid) {
            this.plosTradeid = plosTradeid;
        }

        public String getPlosProdImpact() {
            return plosProdImpact;
        }

        public void setPlosProdImpact(String plosProdImpact) {
            this.plosProdImpact = plosProdImpact;
        }

        public BigDecimal getPlosProdImpQty() {
            return plosProdImpQty;
        }

        public void setPlosProdImpQty(BigDecimal plosProdImpQty) {
            this.plosProdImpQty = plosProdImpQty;
        }

        public String getPlosLossdescription() {
            return plosLossdescription;
        }

        public void setPlosLossdescription(String plosLossdescription) {
            this.plosLossdescription = plosLossdescription;
        }

        public String getPlosPldetailsid() {
            return plosPldetailsid;
        }

        public void setPlosPldetailsid(String plosPldetailsid) {
            this.plosPldetailsid = plosPldetailsid;
        }

        public String getPlosEquipment() {
            return plosEquipment;
        }

        public void setPlosEquipment(String plosEquipment) {
            this.plosEquipment = plosEquipment;
        }

        public String getPlosDetectedby() {
            return plosDetectedby;
        }

        public void setPlosDetectedby(String plosDetectedby) {
            this.plosDetectedby = plosDetectedby;
        }

        public String getPlosTempfield3() {
            return plosTempfield3;
        }

        public void setPlosTempfield3(String plosTempfield3) {
            this.plosTempfield3 = plosTempfield3;
        }

        public String getPlosTempfield4() {
            return plosTempfield4;
        }

        public void setPlosTempfield4(String plosTempfield4) {
            this.plosTempfield4 = plosTempfield4;
        }

        public String getPlosTempfield5() {
            return plosTempfield5;
        }

        public void setPlosTempfield5(String plosTempfield5) {
            this.plosTempfield5 = plosTempfield5;
        }

        public String getPlosActive() {
            return plosActive;
        }

        public void setPlosActive(String plosActive) {
            this.plosActive = plosActive;
        }

        public String getPlosCreatedby() {
            return plosCreatedby;
        }

        public void setPlosCreatedby(String plosCreatedby) {
            this.plosCreatedby = plosCreatedby;
        }

        public LocalDateTime getPlosCreatedon() {
            return plosCreatedon;
        }

        public void setPlosCreatedon(LocalDateTime plosCreatedon) {
            this.plosCreatedon = plosCreatedon;
        }

        public LocalDateTime getPlosModifiedon() {
            return plosModifiedon;
        }

        public void setPlosModifiedon(LocalDateTime plosModifiedon) {
            this.plosModifiedon = plosModifiedon;
        }
    }
}
