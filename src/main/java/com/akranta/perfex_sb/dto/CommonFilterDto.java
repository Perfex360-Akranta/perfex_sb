package com.akranta.perfex_sb.dto;

public class CommonFilterDto {
    private String cellId;
    private String sectionId;
    private String sbuId;
    private String pbuId;
    private String locationId;
    private String companyId;
    private String comboKey;
    private String flId;

 
    //Abnormality Dropdowns
    private String abnormalityTypeId;
    private String abnmOthers;
    private String abnormalitySubTypeId;
    private String ccno;
    private String wostatus;
private String factoryId;
private String isPbuHead;
private String roleId;
private String tradeid;
private String type;
private String pillarid;
private String locnId;
private String others;
private String rtal;
private String code;
private String keyid;
private String ap;
private String sectionid;
private String lineNotToShown;
private String pcsEnabled;
private String costCenterId;
private String lossId;
private String isQtyLoss;
private String lossFrom;
private String pillarCode;
private String pillargrp;
//------employee-------
private String gradeId;
private String mgr;
private String loginEmpshow;
private String locn;
private String isDmtLeader;
private String dmtId;
private String currentUserCcno;
private String yymode;
//-----------------------------------HARI--------------------------------
//TRAINING CALENDER
private String facultyOthers;
    private String topicType;
    private String topicId;
    private String childFlids;
private String wwmsKeyid;

private String mchId;
//Complaint Gallery Dropdowns
    private String gradeMode;
    private String defectMode;
    private String assmId;
private String machineId;
private String machineNotToShown;
private String pcsEnable; // reuse existing pcsEnabled if already added
private String empId;
private String eqpGrpId;
private String eqpSubGrpId;
private String circleId;
//private String cellid2; // note: distinct casing from existing cellId — see below
private String mouldId;
private String othersFlag;
//private String pillarCode;


  public String getOthersFlag() {
    return othersFlag;
}
public void setOthersFlag(String othersFlag) {
    this.othersFlag = othersFlag;
}
  public String getMachineNotToShown() {
    return machineNotToShown;
}
public void setMachineNotToShown(String machineNotToShown) {
    this.machineNotToShown = machineNotToShown;
}
public String getPcsEnable() {
    return pcsEnable;
}
public void setPcsEnable(String pcsEnable) {
    this.pcsEnable = pcsEnable;
}
public String getEmpId() {
    return empId;
}
public void setEmpId(String empId) {
    this.empId = empId;
}
public String getEqpGrpId() {
    return eqpGrpId;
}
public void setEqpGrpId(String eqpGrpId) {
    this.eqpGrpId = eqpGrpId;
}
public String getEqpSubGrpId() {
    return eqpSubGrpId;
}
public void setEqpSubGrpId(String eqpSubGrpId) {
    this.eqpSubGrpId = eqpSubGrpId;
}
public String getCircleId() {
    return circleId;
}
public void setCircleId(String circleId) {
    this.circleId = circleId;
}
public String getMouldId() {
    return mouldId;
}
public void setMouldId(String mouldId) {
    this.mouldId = mouldId;
}
  public String getGradeMode() {
        return gradeMode;
    }
    public void setGradeMode(String gradeMode) {
        this.gradeMode = gradeMode;
    }
    public String getDefectMode() {
        return defectMode;
    }
    public void setDefectMode(String defectMode) {
        this.defectMode = defectMode;
    }
  private String uomId;

//Process FMEA Dropdowns
    private String processId;
    //Equipment FMEA Dropdowns
    //private String cellid;
   
   
  
    public String getProcessId() {
        return processId;
    }
    public void setProcessId(String processId) {
        this.processId = processId;
    }
    public String getUomId() {
    return uomId;
}
  public void setUomId(String uomId) {
    this.uomId = uomId;
  }
    public String getMchId() {
    return mchId;
}
public void setMchId(String mchId) {
    this.mchId = mchId;
}
    public String getWwmsKeyid() {
    return wwmsKeyid;
}
public void setWwmsKeyid(String wwmsKeyid) {
    this.wwmsKeyid = wwmsKeyid;
}
    public String getFacultyOthers() {
        return facultyOthers;
    }
    public void setFacultyOthers(String facultyOthers) {
        this.facultyOthers = facultyOthers;
    }
    public String getTopicType() {
        return topicType;
    }
    public void setTopicType(String topicType) {
        this.topicType = topicType;
    }
    public String getTopicId() {
        return topicId;
    }
    public void setTopicId(String topicId) {
        this.topicId = topicId;
    }
    public String getChildFlids() {
        return childFlids;
    }
    public void setChildFlids(String childFlids) {
        this.childFlids = childFlids;
    }
    public String getYymode() {
    return yymode;
}
public void setYymode(String yymode) {
    this.yymode = yymode;
}
    public String getGradeId() {
    return gradeId;
}
public void setGradeId(String gradeId) {
    this.gradeId = gradeId;
}
public String getMgr() {
    return mgr;
}
public void setMgr(String mgr) {
    this.mgr = mgr;
}
public String getLoginEmpshow() {
    return loginEmpshow;
}
public void setLoginEmpshow(String loginEmpshow) {
    this.loginEmpshow = loginEmpshow;
}
public String getLocn() {
    return locn;
}
public void setLocn(String locn) {
    this.locn = locn;
}
public String getIsDmtLeader() {
    return isDmtLeader;
}
public void setIsDmtLeader(String isDmtLeader) {
    this.isDmtLeader = isDmtLeader;
}
public String getDmtId() {
    return dmtId;
}
public void setDmtId(String dmtId) {
    this.dmtId = dmtId;
}
public String getCurrentUserCcno() {
    return currentUserCcno;
}
public void setCurrentUserCcno(String currentUserCcno) {
    this.currentUserCcno = currentUserCcno;
}
    public String getPillargrp() {
    return pillargrp;
}
public void setPillargrp(String pillargrp) {
    this.pillargrp = pillargrp;
}
    public String getPillarCode() {
    return pillarCode;
}
public void setPillarCode(String pillarCode) {
    this.pillarCode = pillarCode;
}
    public String getLossFrom() {
    return lossFrom;
}
public void setLossFrom(String lossFrom) {
    this.lossFrom = lossFrom;
}
    public String getIsQtyLoss() {
    return isQtyLoss;
}
public void setIsQtyLoss(String isQtyLoss) {
    this.isQtyLoss = isQtyLoss;
}
    public String getLossId() {
    return lossId;
}
public void setLossId(String lossId) {
    this.lossId = lossId;
}
    public String getSectionid() {
    return sectionid;
}
public void setSectionid(String sectionid) {
    this.sectionid = sectionid;
}
public String getLineNotToShown() {
    return lineNotToShown;
}
public void setLineNotToShown(String lineNotToShown) {
    this.lineNotToShown = lineNotToShown;
}
public String getPcsEnabled() {
    return pcsEnabled;
}
public void setPcsEnabled(String pcsEnabled) {
    this.pcsEnabled = pcsEnabled;
}
public String getCostCenterId() {
    return costCenterId;
}
public void setCostCenterId(String costCenterId) {
    this.costCenterId = costCenterId;
}
    public String getAp() {
    return ap;
}
public void setAp(String ap) {
    this.ap = ap;
}
    public String getIsPbuHead() {
    return isPbuHead;
}
public void setIsPbuHead(String isPbuHead) {
    this.isPbuHead = isPbuHead;
}
public String getRoleId() {
    return roleId;
}
public void setRoleId(String roleId) {
    this.roleId = roleId;
}
public String getTradeid() {
    return tradeid;
}
public void setTradeid(String tradeid) {
    this.tradeid = tradeid;
}
public String getType() {
    return type;
}
public void setType(String type) {
    this.type = type;
}
public String getPillarid() {
    return pillarid;
}
public void setPillarid(String pillarid) {
    this.pillarid = pillarid;
}
public String getLocnId() {
    return locnId;
}
public void setLocnId(String locnId) {
    this.locnId = locnId;
}
public String getOthers() {
    return others;
}
public void setOthers(String others) {
    this.others = others;
}
public String getRtal() {
    return rtal;
}
public void setRtal(String rtal) {
    this.rtal = rtal;
}
    public String getCellId() {
        return cellId;
    }
    public void setCellId(String cellId) {
        this.cellId = cellId;
    }
    public String getSectionId() {
        return sectionId;
    }
    public void setSectionId(String sectionId) {
        this.sectionId = sectionId;
    }
    public String getSbuId() {
        return sbuId;
    }
    public void setSbuId(String sbuId) {
        this.sbuId = sbuId;
    }
    public String getPbuId() {
        return pbuId;
    }
    public void setPbuId(String pbuId) {
        this.pbuId = pbuId;
    }
    public String getLocationId() {
        return locationId;
    }
    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }
    public String getCompanyId() {
        return companyId;
    }
    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

     //Abnormality Dropdowns
    public String getAbnormalityTypeId() {
        return abnormalityTypeId;
    }
    public void setAbnormalityTypeId(String abnormalityTypeId) {
        this.abnormalityTypeId = abnormalityTypeId;
    }
    public String getAbnmOthers() {
        return abnmOthers;
    }
    public void setAbnmOthers(String abnmOthers) {
        this.abnmOthers = abnmOthers;
    }
    public String getAbnormalitySubTypeId() {
        return abnormalitySubTypeId;
    }
    public void setAbnormalitySubTypeId(String abnormalitySubTypeId) {
        this.abnormalitySubTypeId = abnormalitySubTypeId;
    }
    public String getComboKey() {
        return comboKey;
    }
    public void setComboKey(String comboKey) {
        this.comboKey = comboKey;
    }


    public String getCcno() {
    return ccno;
}

public void setCcno(String ccno) {
    this.ccno = ccno;
}

   
   
    public String getWostatus() {
    return wostatus;
}

public void setWostatus(String wostatus) {
    this.wostatus = wostatus;
}

public String getFactoryId() {
    return factoryId;
}

public void setFactoryId(String factoryId) {
    this.factoryId = factoryId;
}

public String getCode() {
    return code;
}

public void setCode(String code) {
    this.code = code;
}
public String getKeyid() {
    return keyid;
}

public void setKeyid(String keyid) {
    this.keyid = keyid;
}
   public String getFlId() {
        return flId;
    }
    public void setFlId(String flId) {
        this.flId = flId;
    }
    public String getAssmId() {
    return assmId;
}
public void setAssmId(String assmId) {
    this.assmId = assmId;
}
public String getMachineId() {
    return machineId;
}
public void setMachineId(String machineId) {
    this.machineId = machineId;
}

    
}
