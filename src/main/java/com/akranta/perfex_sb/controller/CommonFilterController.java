package com.akranta.perfex_sb.controller;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.akranta.perfex_sb.dto.CommonFilterDto;
import com.akranta.perfex_sb.dto.DropDownDto;
import com.akranta.perfex_sb.service.CommonFilterService;

@RestController
@RequestMapping("/api/commonFilter")
public class CommonFilterController 
{
    @Autowired
    private CommonFilterService service;

    
    //employee.commonFilter
    @PostMapping("/employee_swetha")
    public ResponseEntity<List<DropDownDto>> employeeCommonFilter(@RequestBody(required = false) CommonFilterDto commonFilterDto) 
    {

        return ResponseEntity.ok(service.getEmployeeComboList(commonFilterDto));

    }

    //***********************************Abnormality CommonFilters*******************************************************//

    @PostMapping("/abnForm/Combo_Status")
    public List<Map<String, String>> status() {

        return List.of(
                Map.of("value", "P", "label", "Pending"),
                Map.of("value", "C", "label", "Completed"));
        // Map.of("value", "REJECTED", "label", "Rejected"));
    }

    //Tag Class in abnormality
    @PostMapping("/abnForm/Combo_TagClass")
    public ResponseEntity<List<DropDownDto>> getAbnTagClasscombo(@RequestBody(required = false) CommonFilterDto commonFilterDto) 
    {

        return ResponseEntity.ok(service.getTAbnTagClassCombo(commonFilterDto));

    }

    //Abnormality Type
    @PostMapping("/abnForm/Combo_Type")
    public ResponseEntity<List<DropDownDto>> getAbnTypecombo(@RequestBody(required = false) CommonFilterDto commonFilterDto) 
    {

        return ResponseEntity.ok(service.getAbnTypeCombo(commonFilterDto));

    }

    //Abnormality SubType
    @PostMapping("/abnForm/Combo_SubType")
    public ResponseEntity<List<DropDownDto>> getAbnSubTypecombo(@RequestBody(required = false) CommonFilterDto commonFilterDto) 
    {

        return ResponseEntity.ok(service.getAbnSubTypecombo(commonFilterDto));

    }

    //Abnormality Impact
    @PostMapping("/abnForm/Combo_Impact")
    public ResponseEntity<List<DropDownDto>> getAbnImpactcombo(@RequestBody(required = false) CommonFilterDto commonFilterDto) 
    {

        return ResponseEntity.ok(service.getAbnImpactcombo(commonFilterDto));

    }

    //Abnormality Impact
    @PostMapping("/abnForm/Combo_Category")
    public ResponseEntity<List<DropDownDto>> getAbnCategorycombo(@RequestBody(required = false) CommonFilterDto commonFilterDto) 
    {

        return ResponseEntity.ok(service.getAbnCategorycombo(commonFilterDto));

    }


    //Abnormality Trade
    @PostMapping("/abnForm/Combo_Trade")
    public ResponseEntity<List<DropDownDto>> getAbnTradecombo(@RequestBody(required = false) CommonFilterDto commonFilterDto) 
    {

        return ResponseEntity.ok(service.getAbnTradecombo(commonFilterDto));

    }

    @PostMapping("/creat/Combo_Ccno")
    public ResponseEntity<List<DropDownDto>> getcombo_ccno(@RequestBody(required = false) CommonFilterDto commonFilterDto) 
    {


        return ResponseEntity.ok(service.getcombo_ccno(commonFilterDto));

    }

    //Login Framework / Profile Id combo
    @PostMapping("/creat/Combo_Profid")
    public ResponseEntity<List<DropDownDto>> getProfidCombo(@RequestBody(required = false) CommonFilterDto commonFilterDto) 
    {

        return ResponseEntity.ok(service.getProfidComboList(commonFilterDto));

    }

    //Department combo
    @PostMapping("/emp/Combo_Department")
    public ResponseEntity<List<DropDownDto>> getDepartmentCombo(@RequestBody(required = false) CommonFilterDto commonFilterDto) 
    {

        return ResponseEntity.ok(service.getDepartmentComboList(commonFilterDto));

    }

    //Designation combo
    @PostMapping("/emp/Combo_Designation")
    public ResponseEntity<List<DropDownDto>> getDesignationCombo(@RequestBody(required = false) CommonFilterDto commonFilterDto) 
    {

        return ResponseEntity.ok(service.getDesignationComboList(commonFilterDto));

    }

    // //Role combo
    // @PostMapping("/Combo_Role")
    // public ResponseEntity<List<DropDownDto>> getRoleCombo(@RequestBody(required = false) CommonFilterDto commonFilterDto) 
    // {

    //     return ResponseEntity.ok(service.getRoleComboList(commonFilterDto));

    // } 

    //LOPC Category combo
    @PostMapping("/Combo_LopcCategory")
    public ResponseEntity<List<DropDownDto>> getLopcCategoryCombo(@RequestBody(required = false) CommonFilterDto commonFilterDto) 
    {

        return ResponseEntity.ok(service.getLopcCategoryCombo(commonFilterDto));

    }

    //***********************************Abnormality CommonFilters*******************************************************//
    //UOM combo
    // @PostMapping("/criticalprocess/Combo_Uom")
    // public ResponseEntity<List<DropDownDto>> getUomCombo(@RequestBody(required = false) CommonFilterDto commonFilterDto) 
    // {

    //     return ResponseEntity.ok(service.getUomComboList(commonFilterDto));

    // }

    //Shift combo
    @PostMapping("/Shift")
    public ResponseEntity<List<DropDownDto>> getShiftCombo(@RequestBody(required = false) CommonFilterDto commonFilterDto) 
    {

        return ResponseEntity.ok(service.getShiftComboList(commonFilterDto));

    }

//Action Plan Employee combo
    @PostMapping("/actionplnemployee")
    public ResponseEntity<List<DropDownDto>> getActionPlanEmployeeCombo(@RequestBody(required = false) CommonFilterDto commonFilterDto) 
    {

        return ResponseEntity.ok(service.getActionPlanEmployeeComboList(commonFilterDto));

    }

//Company combo
    @PostMapping("/companyCombo")
    public ResponseEntity<List<DropDownDto>> getCompanyCombo(@RequestBody(required = false) CommonFilterDto commonFilterDto) 
    {

        return ResponseEntity.ok(service.getCompanyComboList(commonFilterDto));

    }

    //Location combo
    @PostMapping("/location")
    public ResponseEntity<List<DropDownDto>> getLocationCombo(@RequestBody(required = false) CommonFilterDto commonFilterDto) 
    {

        return ResponseEntity.ok(service.getLocationComboList(commonFilterDto));

    }
    
    //SBU combo
    @PostMapping("/sbuCombo")
    public ResponseEntity<List<DropDownDto>> getSbuCombo(@RequestBody(required = false) CommonFilterDto commonFilterDto) 
    {

        return ResponseEntity.ok(service.getSbuComboList(commonFilterDto));

    }

    //PBU combo
    @PostMapping("/pbuCombo")
    public ResponseEntity<List<DropDownDto>> getPbuCombo(@RequestBody(required = false) CommonFilterDto commonFilterDto) 
    {

        return ResponseEntity.ok(service.getPbuComboList(commonFilterDto));

    }

    //Section combo
    @PostMapping("/sectionCombo")
    public ResponseEntity<List<DropDownDto>> getSectionCombo(@RequestBody(required = false) CommonFilterDto commonFilterDto) 
    {

        return ResponseEntity.ok(service.getSectionComboList(commonFilterDto));

    }

    //Cell combo
    @PostMapping("/cellCombo")
    public ResponseEntity<List<DropDownDto>> getCellCombo(@RequestBody(required = false) CommonFilterDto commonFilterDto) 
    {

        return ResponseEntity.ok(service.getCellComboList(commonFilterDto));

    }

//Phenomena combo
    @PostMapping("/pcs/Combo_Phenomena")
    public ResponseEntity<List<DropDownDto>> getPhenomenaCombo(@RequestBody(required = false) CommonFilterDto commonFilterDto) 
    {

        return ResponseEntity.ok(service.getPhenomenaCombo(commonFilterDto));

    }

    //Loss combo
    @PostMapping("/pcs/Combo_Loss")
    public ResponseEntity<List<DropDownDto>> getLossCombo(@RequestBody(required = false) CommonFilterDto commonFilterDto) 
    {

        return ResponseEntity.ok(service.getLossCombo(commonFilterDto));

    }
    //Equipment combo
    @PostMapping("/pcs/comboEquipment")
    public ResponseEntity<List<DropDownDto>> getEquipmentNameCombo(@RequestBody(required = false) CommonFilterDto commonFilterDto) 
    {

        return ResponseEntity.ok(service.getEquipmentNameCombo(commonFilterDto));

    }
    

@PostMapping("/loss")
public ResponseEntity<List<DropDownDto>> getLossComboList(@RequestBody CommonFilterDto commonFilterDto)
 {
    return ResponseEntity.ok(service.getLossComboList(commonFilterDto));
}

@PostMapping("/prpo/kaizenBelt")
    public ResponseEntity<List<DropDownDto>> getJHKaizenBeltCombo(@RequestBody(required = false) CommonFilterDto commonFilterDto) 
    {

        return ResponseEntity.ok(service.getJHKaizenBeltComboList(commonFilterDto));

    }
//Kaizen Category combo
    @PostMapping("/kaizenCategory")
    public ResponseEntity<List<DropDownDto>> getJHKaizenCategoryCombo(@RequestBody(required = false) CommonFilterDto commonFilterDto) 
    {

        return ResponseEntity.ok(service.getJHKaizenCategoryComboList(commonFilterDto));

    }

    //KPI Indicator combo
    @PostMapping("/prpo/combo_KPIIndicator")
    public ResponseEntity<List<DropDownDto>> getProjectMetricsKpiIndicatorCombo(@RequestBody(required = false) CommonFilterDto commonFilterDto) 
    {

        return ResponseEntity.ok(service.getProjectMetricsKpiIndicator(commonFilterDto));

    }

//Wave combo
    @PostMapping("/prpo/comboWave")
    public ResponseEntity<List<DropDownDto>> getComboWave(@RequestBody(required = false) CommonFilterDto commonFilterDto) 
    {

        return ResponseEntity.ok(service.getComboWave(commonFilterDto));

    }

    //DMC Employee combo
    @PostMapping("/prpo/combo_dmcproject")
    public ResponseEntity<List<DropDownDto>> getDmcEmployeeCombo(@RequestBody(required = false) CommonFilterDto commonFilterDto) 
    {

        return ResponseEntity.ok(service.getDmcEmployeeCombo(commonFilterDto));

    }

     @PostMapping("/employee")
    public ResponseEntity<List<DropDownDto>> getEmployeeList(@RequestBody(required = false) CommonFilterDto commonFilterDto) 
    {

        return ResponseEntity.ok(service.getEmployeeList(commonFilterDto));

    }
   
    @PostMapping("/employeeCombo")
    public ResponseEntity<List<DropDownDto>> getEmployeeCombo(@RequestBody(required = false) CommonFilterDto commonFilterDto) 
    {

        return ResponseEntity.ok(service.getEmployeeCombo(commonFilterDto));

    }
//------------------------------HARI---------------------------
//***************************************TrainingCalender************************************************

@PostMapping("/ntrc/TrainingIdentified")
public List<Map<String, String>> trainingIdentified() {

    return List.of(
            Map.of("id", "KSA", "text", "KSA (GAP Based)"),
            Map.of("id", "NB", "text", "Need Basis"),
            Map.of("id", "SD", "text", "Section D"),
            Map.of("id", "SI", "text", "Skill Index"),
            Map.of("id", "KU", "text", "Knowledge Upgradation"),
            Map.of("id", "RT", "text", "Refresher Training"),
            Map.of("id", "EHS", "text", "EHS"),
            Map.of("id", "GN", "text", "General"));
}


    @PostMapping("/ntrc/ettrade")
    public ResponseEntity<List<DropDownDto>> getETTradeComboList(
            @RequestBody(required = false) CommonFilterDto commonFilterDto) {
        return ResponseEntity.ok(service.getETTradeComboList(commonFilterDto));
    }

    @PostMapping("/tcl/facultyCombo")
    public ResponseEntity<List<DropDownDto>> getFacultyComboList(
            @RequestBody(required = false) CommonFilterDto commonFilterDto) {
        return ResponseEntity.ok(service.getFacultyComboList(commonFilterDto));
    }

    @PostMapping("/ntrc/topic_fillcombo")
    public ResponseEntity<List<DropDownDto>> getTopicComboList(
            @RequestBody(required = false) CommonFilterDto commonFilterDto) {
        return ResponseEntity.ok(service.getTopicComboList(commonFilterDto));
    }

    @PostMapping("/roleMst")
    public ResponseEntity<List<DropDownDto>> getRoleComboList(
            @RequestBody(required = false) CommonFilterDto commonFilterDto) {
        return ResponseEntity.ok(service.getRoleComboList(commonFilterDto));
    }

    @PostMapping("/entbatch/entVenueCombo")
    public ResponseEntity<List<DropDownDto>> getVenueComboList(
            @RequestBody(required = false) CommonFilterDto commonFilterDto) {
        return ResponseEntity.ok(service.getVenueComboList(commonFilterDto));
    }

    @PostMapping("/topms/deliveryMode_Combo")
    public ResponseEntity<List<DropDownDto>> getDeliveryModeCombo(
            @RequestBody(required = false) CommonFilterDto commonFilterDto) {
        return ResponseEntity.ok(service.getDeliveryModeCombo(commonFilterDto));
    }

    @PostMapping("/topi/combo_Category")
    public ResponseEntity<List<DropDownDto>> getTopicCategoryComboList(
            @RequestBody(required = false) CommonFilterDto commonFilterDto) {
        return ResponseEntity.ok(service.getCategoryComboList(commonFilterDto));
    }

    @PostMapping("/trade")
    public ResponseEntity<List<DropDownDto>> getTradeComboList(
            @RequestBody(required = false) CommonFilterDto commonFilterDto) {
        return ResponseEntity.ok(service.getTradeComboList(commonFilterDto));
    }

    @PostMapping("/ntrc/rating_combo")
    public List<Map<String, String>> ratings() {

        return List.of(
                Map.of("id", "1", "text", "1-Needs Improvement"),
                Map.of("id", "2", "text", "2-Fair"),
                Map.of("id", "3", "text", "3-Average"),
                Map.of("id", "4", "text", "4-Good"),
                Map.of("id", "5", "text", "5-Excellent"));
    }

    @PostMapping("/ntrc/AnchoredBy_combo")
    public List<Map<String, String>> anchoredBy() {

        return List.of(
                Map.of("id", "Corp.HR", "text", "Corp.HR"),
                Map.of("id", "DHR", "text", "DHR"),
                Map.of("id", "Unit HR", "text", "Unit HR"),
                Map.of("id", "DMT", "text", "DMT"),
                Map.of("id", "Safety", "text", "Safety"));
    }

    @PostMapping("/ntrc/trainingType")
    public List<Map<String, String>> trainingType() {

        return List.of(
                Map.of("id", "UQ", "text", "Unique Position)"),
                Map.of("id", "GN", "text", "General"),
                Map.of("id", "MS", "text", "MSD"));
    }

    @PostMapping("/gbtc/topic_fillcombo")
    public ResponseEntity<List<DropDownDto>> getGridTopicComboList(
            @RequestBody(required = false) CommonFilterDto commonFilterDto) {
        return ResponseEntity.ok(service.getGridTopicComboList(commonFilterDto));
    }


    @PostMapping("/ntrc/AssessmentReq_combo")
    public List<Map<String, String>> assessmentReq() {

        return List.of(
                Map.of("id", "Y", "text", "Yes"),
                Map.of("id", "N", "text", "No"));
                
    }

    @PostMapping("/ntrc/MaterialReady_combo")
    public List<Map<String, String>> MaterialReady() {

        return List.of(
                Map.of("id", "Y", "text", "Yes"),
                Map.of("id", "N", "text", "No"));
                
    }

    @PostMapping("/ntrc/MarksReq_combo")
    public List<Map<String, String>> MarksReq() {

        return List.of(
                Map.of("id", "Y", "text", "Yes"),
                Map.of("id", "N", "text", "No"));
                
    }
    
    //******************SkillIndex*************

    @PostMapping("/sirp/combo_empType")
    public ResponseEntity<List<DropDownDto>> getEmpTypeCombo(
            @RequestBody(required = false) CommonFilterDto commonFilterDto) {
        return ResponseEntity.ok(service.getEmpTypeCombo(commonFilterDto));
    }
    //**************BEST KAIZEN**************************************************
@PostMapping("/bzlv/level_Combo")
    public List<Map<String, String>> level() {

        return List.of(
                Map.of("id", "J", "text", "JH"),
                Map.of("id", "D", "text", "DMT"),
                Map.of("id", "P", "text", "PBU"),
                Map.of("id", "S", "text", "SBU"),
                Map.of("id", "L", "text", "Location"),
                Map.of("id", "C", "text", "Company"));
    }

    
    //**************KnowWhy*************

@PostMapping("/defphen")
    public ResponseEntity<List<DropDownDto>> getDefactPhenamenComboList(
            @RequestBody(required = false) CommonFilterDto commonFilterDto) {
        return ResponseEntity.ok(service.getDefactPhenamenComboList(commonFilterDto));
    }



    //*****Kaizen*************

 @PostMapping("/kaizen/kaizenactegoryfillcombo")
    public ResponseEntity<List<DropDownDto>> getKznThemeCategory(
            @RequestBody(required = false) CommonFilterDto commonFilterDto) {
        return ResponseEntity.ok(service.getKznThemeCategory(commonFilterDto));
    }

    @PostMapping("/Kaizen/Kazennoname")
    public ResponseEntity<List<DropDownDto>> getKznNoName(
            @RequestBody(required = false) CommonFilterDto commonFilterDto) {
        return ResponseEntity.ok(service.getKznNoName(commonFilterDto));
    }

    @PostMapping("/kaizen/combo_whywhy")
    public ResponseEntity<List<DropDownDto>> getWhyWhyCombo(
            @RequestBody(required = false) CommonFilterDto commonFilterDto) {
        return ResponseEntity.ok(service.getWhyWhyCombo(commonFilterDto));

    }

    @PostMapping("/kaizen/kaizenfillcombo")
    public ResponseEntity<List<DropDownDto>> getKpiCombo(
            @RequestBody(required = false) CommonFilterDto commonFilterDto) {
        return ResponseEntity.ok(service.getKpiCombo(commonFilterDto));
    }

    @PostMapping("/mould")
    public ResponseEntity<List<DropDownDto>> getMould(@RequestBody(required = false) CommonFilterDto commonFilterDto) {
        return ResponseEntity.ok(service.getMould(commonFilterDto));
    }

    @PostMapping("/kaizen/IndustryCategory")
    public List<Map<String, String>> industry() {

        return List.of(
                Map.of("id", "Historian", "text", "Historian"),
                Map.of("id", "Data Analytics", "text", "Data Analytics"),
                Map.of("id", "AET", "text", "Automation& Emerging Technology"),
                Map.of("id", "Capability", "text", "Capability"));
    }
//----------------------------------------------------GOPI--------------------------
//WhyWhy Pillar
@PostMapping("/pillar")
public ResponseEntity<List<DropDownDto>> getWhyWhyPillarCombo(@RequestBody(required = false) CommonFilterDto commonFilterDto) 
{
    return ResponseEntity.ok(service.getWhyWhyPillarCombo(commonFilterDto));
}
    //***********************************OPL CommonFilters*******************************************************//

//OPL Process
@PostMapping("/Process")
public ResponseEntity<List<DropDownDto>> getOplProcessCombo(@RequestBody(required = false) CommonFilterDto commonFilterDto) 
{
    return ResponseEntity.ok(service.getOplProcessCombo(commonFilterDto));
}

//***********************************OPL CommonFilters*******************************************************//

//Field Audit Sheet - Service Provider
@PostMapping("/fass/ServiceProvider")
public ResponseEntity<List<DropDownDto>> getFieldAuditServiceProviderCombo(@RequestBody(required = false) CommonFilterDto commonFilterDto) 
{
    return ResponseEntity.ok(service.getFieldAuditServiceProviderCombo(commonFilterDto));
}
//***********************************Visual SOP CommonFilters*************************************************//

//Visual SOP - Maintenance Section
@PostMapping("/VisualSop/trademst")
public ResponseEntity<List<DropDownDto>> getVisualSopMaintSectionCombo(@RequestBody(required = false) CommonFilterDto commonFilterDto) 
{
    return ResponseEntity.ok(service.getVisualSopMaintSectionCombo(commonFilterDto));
}
//***********************************Critical Process CommonFilters*******************************************************//

    //Critical Process - Unit of Measurement
    @PostMapping("/uomCombo")
    public ResponseEntity<List<DropDownDto>> getUomCombo(@RequestBody(required = false) CommonFilterDto commonFilterDto) 
    {
        return ResponseEntity.ok(service.getUomCombo(commonFilterDto));
    }

    //***********************************Critical Process CommonFilters*******************************************************//
    //***********************************Process FMEA CommonFilters*******************************************************//


    //Process FMEA - Sub Process
    @PostMapping("/subprocess")
    public ResponseEntity<List<DropDownDto>> getFmeaSubProcessCombo(@RequestBody(required = false) CommonFilterDto commonFilterDto) 
    {
        return ResponseEntity.ok(service.getFmeaSubProcessCombo(commonFilterDto));
    }

    //***********************************Process FMEA CommonFilters*******************************************************//
    //***********************************EQUIPMENT FMEA CommonFilters*************************************************//

//FMEA - Equipment Area
@PostMapping("/fmeaf/subEqpmnCombo")
public ResponseEntity<List<DropDownDto>> getFmeaEquipmentAreaCombo(@RequestBody(required = false) CommonFilterDto commonFilterDto) 
{
    return ResponseEntity.ok(service.getFmeaEquipmentAreaCombo(commonFilterDto));
}

//***********************************EQUIPMENTFMEA CommonFilters*************************************************//
//***********************************UPSTREAM DEFECT*************************************************************//
@PostMapping("/upd/Upstream_DefectForm")
public ResponseEntity<List<DropDownDto>> getUpstreamDefectCombo(@RequestBody(required = false) CommonFilterDto commonFilterDto) 
{
    return ResponseEntity.ok(service.getUpstreamDefectCombo(commonFilterDto));
}
//***********************************UPSTREAM DEFECT*************************************************************//
//***********************************Complaint Gallery CommonFilters*************************************************//

//Complaint Gallery - Customer Name
@PostMapping("/customer")
public ResponseEntity<List<DropDownDto>> getComplaintGalleryCustomerCombo(@RequestBody(required = false) CommonFilterDto commonFilterDto) 
{
    return ResponseEntity.ok(service.getComplaintGalleryCustomerCombo(commonFilterDto));
}

//Complaint Gallery - Grade Specification
@PostMapping("/compg/comboGradeSpec")
public ResponseEntity<List<DropDownDto>> getComplaintGalleryGradeSpecCombo(@RequestBody(required = false) CommonFilterDto commonFilterDto) {
    return ResponseEntity.ok(service.getComplaintGalleryGradeSpecCombo(commonFilterDto));
}

//Complaint Gallery - Defect Phenomena
@PostMapping("/compg/Phenomena_DefectForm")
public ResponseEntity<List<DropDownDto>> getComplaintGalleryDefectPhenomenaCombo(@RequestBody(required = false) CommonFilterDto commonFilterDto) {
    return ResponseEntity.ok(service.getComplaintGalleryDefectPhenomenaCombo(commonFilterDto));
}

//***********************************Complaint Gallery CommonFilters*************************************************//
//********************************************PRIYANKA********************************* */

//*******************************controller****************************
@PostMapping("/checkType")
    public ResponseEntity<List<DropDownDto>> getCheckTypeCombo(@RequestBody(required = false) CommonFilterDto commonFilterDto) 
    {
        return ResponseEntity.ok(service.getCheckTypeCombo(commonFilterDto));
    }

    //Spare combo
    @PostMapping("/spareCombo")
    public ResponseEntity<List<DropDownDto>> getSpareCombo(@RequestBody(required = false) CommonFilterDto commonFilterDto) 
    {
        return ResponseEntity.ok(service.getSpareComboList(commonFilterDto));
    }

    //Checking Tool combo
    @PostMapping("/condapp/checkingtool_combo")
    public ResponseEntity<List<DropDownDto>> getCheckingToolCombo(@RequestBody(required = false) CommonFilterDto commonFilterDto) 
    {
        return ResponseEntity.ok(service.getCheckingToolCombo(commonFilterDto));
    }

    @PostMapping("/condapp/component_combo")
    public List<Map<String, String>> componentCombo() {
        return List.of(
            Map.of("id", "E", "text", "EXISTING"),
            Map.of("id", "N", "text", "NEW"));
    }

    @PostMapping("/condapp/idealType_combo")
    public List<Map<String, String>> idealTypeCombo() {
        return List.of(
            Map.of("id", "M", "text", "MIN-MAX"),
            Map.of("id", "O", "text", "OK- NOT OK"),
            Map.of("id", "T", "text", "TEXT"));
    }

    @PostMapping("/condapp/refurbishment_combo")
    public List<Map<String, String>> refurbishmentCombo() {
        return List.of(
            Map.of("id", "Y", "text", "YES"),
            Map.of("id", "N", "text", "NO"));
    }
    @PostMapping("/condapp/status_combo")
    public List<Map<String, String>> statusCombo() {
        return List.of(
            Map.of("id", "G", "text", "OK"),
            Map.of("id", "R", "text", "NOT OK"));
    }
//***************************************************************
//******************************KPI**********************************
//*********************************kpi controller****************************
@PostMapping("/keyPerInd/Combo_KeyPerformParent")
    public ResponseEntity<List<DropDownDto>> getKeyPerformParentCombo(@RequestBody(required = false) CommonFilterDto commonFilterDto) 
    {
        return ResponseEntity.ok(service.getParentComboList(commonFilterDto));
    }
//***********************************************************************

//**************************************MOM***************************** */

 @PostMapping("/mom/MeetingType")
    public List<Map<String, String>> meetingtype() {
        return List.of(
            Map.of("id", "J", "text", "JH"),
            Map.of("id", "D", "text", " DMT"),
             Map.of("id", "P", "text", " PILLAR"),
              Map.of("id", "O", "text", " OTHERS"),
               Map.of("id", "PD", "text", " PRODUCTION MEETING"),
                Map.of("id", "FIP", "text", " FI PROJECT"),
                 Map.of("id", "CEC", "text", "CENTRAL EHS COMMITTEE"),
                  Map.of("id", "DEC", "text", " DEPARTMENT EHS COMMITTEE"),
                   Map.of("id", "UMC", "text", " UMC"),
                    Map.of("id", "OGM", "text", " OGM")
        );
    }
   // ***********************************MOM***********************************
    //Pillar Group combo
    @PostMapping("/mom/pillargroup")
    public ResponseEntity<List<DropDownDto>> getPillarGroupCombo(@RequestBody(required = false) CommonFilterDto commonFilterDto) 
    {

        return ResponseEntity.ok(service.getPillarGroupCombo(commonFilterDto));

    }

    //Role combo
    @PostMapping("/mom/Rolecombo")
    public ResponseEntity<List<DropDownDto>> getMOMRoleCombo(@RequestBody(required = false) CommonFilterDto commonFilterDto) 
    {

        return ResponseEntity.ok(service.getMOMRoleComboList(commonFilterDto));

    }


   
@PostMapping("/mom/MeetingAttendance")
    public List<Map<String, String>> MeetingAttendance() {
        return List.of(
            Map.of("id", "P", "text", "PRESENT"),
            Map.of("id", "A", "text", " ABSENT"),
             Map.of("id", "D", "text", " ON-DUTY"),
              Map.of("id", "L", "text", " LEAVE"),
               Map.of("id", "W", "text", " WEEKLY-OFF"),
                Map.of("id", "-", "text", " BLANK")
                
        );
    }


    //Role combo (New MOM)
    @PostMapping("/nmom/Rolecombo")
    public ResponseEntity<List<DropDownDto>> getRoleComboNewMom(@RequestBody(required = false) CommonFilterDto commonFilterDto) 
    {

        return ResponseEntity.ok(service.getRoleComboListNewMom(commonFilterDto));

    }
//Role-based Employee combo
    @PostMapping("/mom/rolebasedemployee")
    public ResponseEntity<List<DropDownDto>> getRoleBasedEmployeeCombo(@RequestBody(required = false) CommonFilterDto commonFilterDto) 
    {

        return ResponseEntity.ok(service.getRoleBasedEmployee(commonFilterDto));

    }

    //Machine combo
    @PostMapping("/machineCombo")
    public ResponseEntity<List<DropDownDto>> getMachineCombo(@RequestBody(required = false) CommonFilterDto commonFilterDto) 
    {

        return ResponseEntity.ok(service.getMachineComboList(commonFilterDto));

    }
//Effectiveness combo
    @PostMapping("/effective")
    public ResponseEntity<List<DropDownDto>> getEffectiveCombo(@RequestBody(required = false) CommonFilterDto commonFilterDto) 
    {

        return ResponseEntity.ok(service.getEffectiveComboList(commonFilterDto));

    }
    //PPE Type combo
    @PostMapping("/fass/ppetype")
    public ResponseEntity<List<DropDownDto>> getPPETypeCombo(@RequestBody(required = false) CommonFilterDto commonFilterDto) 
    {

        return ResponseEntity.ok(service.getPPEType(commonFilterDto));

    }

    //***********************************************role****************************************

    //User Role combo
    @PostMapping("/creat/Combo_UserRoll")
    public ResponseEntity<List<DropDownDto>> getUserRollCombo(@RequestBody(required = false) CommonFilterDto commonFilterDto) 
    {

        return ResponseEntity.ok(service.getUserRollComboList(commonFilterDto));

    }
//Current shift lookup
    @PostMapping("/pcs/getCurrentShift")
    public ResponseEntity<java.util.Map<String, String>> getCurrentShift(@RequestBody(required = false) CommonFilterDto commonFilterDto) 
    {
        String shift = service.getCurrentShift(commonFilterDto);
        return ResponseEntity.ok(java.util.Collections.singletonMap("shift", shift != null ? shift : ""));
    }
}
