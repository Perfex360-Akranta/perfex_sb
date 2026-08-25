package com.akranta.perfex_sb.service;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import com.akranta.perfex_sb.dto.CommonFilterDto;
import com.akranta.perfex_sb.dto.DropDownDto;

public interface CommonFilterService {

    public List<DropDownDto> getEmployeeComboList(CommonFilterDto commonFilterDto) ;

    public List<DropDownDto> getTAbnTagClassCombo(CommonFilterDto commonFilterDto);

    public List<DropDownDto> getAbnTypeCombo(CommonFilterDto commonFilterDto);

    public List<DropDownDto> getAbnSubTypecombo(CommonFilterDto commonFilterDto);

    public List<DropDownDto> getAbnImpactcombo(CommonFilterDto commonFilterDto);

    public List<DropDownDto> getAbnCategorycombo(CommonFilterDto commonFilterDto);

    public List<DropDownDto> getAbnTradecombo(CommonFilterDto commonFilterDto);

     public List<DropDownDto> getcombo_ccno(CommonFilterDto commonFilterDto);

     public List<DropDownDto> getProfidComboList(CommonFilterDto commonFilterDto);

     public List<DropDownDto> getDepartmentComboList(CommonFilterDto commonFilterDto); 

     public List<DropDownDto> getDesignationComboList(CommonFilterDto commonFilterDto);

    //public List<DropDownDto> getRoleComboList(CommonFilterDto commonFilterDto);

     public List<DropDownDto> getLopcCategoryCombo(CommonFilterDto commonFilterDto);

     public List<DropDownDto> getUomComboList(CommonFilterDto commonFilterDto);

     public List<DropDownDto> getShiftComboList(CommonFilterDto commonFilterDto);

     public List<DropDownDto> getActionPlanEmployeeComboList(CommonFilterDto commonFilterDto);

     public List<DropDownDto> getCompanyComboList(CommonFilterDto commonFilterDto);

     public List<DropDownDto> getLocationComboList(CommonFilterDto commonFilterDto);

     public List<DropDownDto> getSbuComboList(CommonFilterDto commonFilterDto);

     public List<DropDownDto> getPbuComboList(CommonFilterDto commonFilterDto);

     public List<DropDownDto> getSectionComboList(CommonFilterDto commonFilterDto);

     public List<DropDownDto> getCellComboList(CommonFilterDto commonFilterDto);

     public List<DropDownDto> getPhenomenaCombo(CommonFilterDto commonFilterDto);

     public List<DropDownDto> getLossCombo(CommonFilterDto commonFilterDto);

     public List<DropDownDto> getEquipmentNameCombo(CommonFilterDto commonFilterDto);

     public List<DropDownDto> getLossComboList(CommonFilterDto commonFilterDto);

     public List<DropDownDto> getJHKaizenBeltComboList(CommonFilterDto commonFilterDto);

     public List<DropDownDto> getJHKaizenCategoryComboList(CommonFilterDto commonFilterDto);

     public List<DropDownDto> getProjectMetricsKpiIndicator(CommonFilterDto commonFilterDto);

     public List<DropDownDto> getComboWave(CommonFilterDto commonFilterDto);

     public List<DropDownDto> getDmcEmployeeCombo(CommonFilterDto commonFilterDto);

     public List<DropDownDto> getEmployeeList(CommonFilterDto commonFilterDto) ;

     public List<DropDownDto> getEmployeeCombo(CommonFilterDto commonFilterDto);
     //------------------------------------------HARI------------------------------------------------

     // ****************************************TrainingCalender CommonFilters**********************************************************************************

    

    public List<DropDownDto> getETTradeComboList(CommonFilterDto commonFilterDto);

    public List<DropDownDto> getFacultyComboList(CommonFilterDto commonFilterDto);

    public List<DropDownDto> getTopicComboList(CommonFilterDto commonFilterDto);

    public List<DropDownDto> getRoleComboList(CommonFilterDto commonFilterDto);

    public List<DropDownDto> getVenueComboList(CommonFilterDto commonFilterDto);

    public List<DropDownDto> getDeliveryModeCombo(CommonFilterDto commonFilterDto);

    public List<DropDownDto> getCategoryComboList(CommonFilterDto commonFilterDto);

    public List<DropDownDto> getGridTopicComboList(CommonFilterDto commonFilterDto);

    public List<DropDownDto> getTradeComboList(CommonFilterDto commonFilterDto);

    //***************SkillIndex****************
public List<DropDownDto> getEmpTypeCombo(CommonFilterDto commonFilterDto);

//**************KnowWhy*************
 public List<DropDownDto> getDefactPhenamenComboList(CommonFilterDto commonFilterDto);


 // ***************************************************************KAIZEN**********************************************************
    public List<DropDownDto> getKznThemeCategory(CommonFilterDto commonFilterDto);

    public List<DropDownDto> getKznNoName(CommonFilterDto commonFilterDto);

    public List<DropDownDto> getWhyWhyCombo(CommonFilterDto commonFilterDto);

    public List<DropDownDto> getKpiCombo(CommonFilterDto commonFilterDto);

    public List<DropDownDto> getMould(CommonFilterDto commonFilterDto);

    //----------------------------------------GOPI--------------------------------------------------

//--------------------------why why---------------------------------------------

    public List<DropDownDto> getWhyWhyPillarCombo(CommonFilterDto commonFilterDto);
    public List<DropDownDto> getEffectiveComboList(CommonFilterDto commonFilterDto);


  //  ***************************************fieldaudit*************************************
  public List<DropDownDto> getPPEType(CommonFilterDto commonFilterDto);

    //---------------------------opl------------------------------------
    public List<DropDownDto> getOplProcessCombo(CommonFilterDto commonFilterDto);

    public List<DropDownDto> getFieldAuditServiceProviderCombo(CommonFilterDto commonFilterDto);

    //-------------------------------------visual SOP--------------
    public List<DropDownDto> getVisualSopMaintSectionCombo(CommonFilterDto commonFilterDto);

    //-------------------------------------Critical Process--------------
    public List<DropDownDto> getUomCombo(CommonFilterDto commonFilterDto);
    //-------------------------------------Process FMEA--------------

    public List<DropDownDto> getFmeaSubProcessCombo(CommonFilterDto commonFilterDto);
        
//-------------------------------------equipment  FMEA--------------
public List<DropDownDto> getFmeaEquipmentAreaCombo(CommonFilterDto commonFilterDto);
//-------------------------------------Upstream Defect-----------------
public List<DropDownDto> getUpstreamDefectCombo(CommonFilterDto commonFilterDto);
//---------------COMPLAINT Gallaery----------------------------------------
    public List<DropDownDto> getComplaintGalleryCustomerCombo(CommonFilterDto commonFilterDto);

    public List<DropDownDto> getComplaintGalleryGradeSpecCombo(CommonFilterDto commonFilterDto);
    
    public List<DropDownDto> getComplaintGalleryDefectPhenomenaCombo(CommonFilterDto commonFilterDto);
    //******************************************PRIYANKA*********************************************

   // ***********************condition appraisal ***************************
public List<DropDownDto> getCheckTypeCombo(CommonFilterDto commonFilterDto);

     public List<DropDownDto> getSpareComboList(CommonFilterDto commonFilterDto);

     public List<DropDownDto> getCheckingToolCombo(CommonFilterDto commonFilterDto);
     //*************************kpi***********************************

     public List<DropDownDto> getParentComboList(CommonFilterDto commonFilterDto);
     //********************************************************************

    //  public List<DropDownDto> getDmcEmployeeCombo(CommonFilterDto commonFilterDto);

    //  public List<DropDownDto> getProjectMetricsKpiIndicator(CommonFilterDto commonFilterDto);

    //  public List<DropDownDto> getJHKaizenBeltComboList(CommonFilterDto commonFilterDto);
//********************************************************************************
//******************************************MOM*****************************
public List<DropDownDto> getPillarGroupCombo(CommonFilterDto commonFilterDto);

public List<DropDownDto> getMOMRoleComboList(CommonFilterDto commonFilterDto);

public List<DropDownDto> getRoleComboListNewMom(CommonFilterDto commonFilterDto);

public List<DropDownDto> getRoleBasedEmployee(CommonFilterDto commonFilterDto);


//******************************************machine*******************************
public List<DropDownDto> getMachineComboList(CommonFilterDto commonFilterDto);
//**********************************************role ******************************
public List<DropDownDto> getUserRollComboList(CommonFilterDto commonFilterDto);

public String getCurrentShift(CommonFilterDto commonFilterDto);
















}
