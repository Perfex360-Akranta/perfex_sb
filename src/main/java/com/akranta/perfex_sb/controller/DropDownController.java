package com.akranta.perfex_sb.controller;

import com.akranta.perfex_sb.dto.CommonFilterDto;
import com.akranta.perfex_sb.dto.DropDownDto;

import com.akranta.perfex_sb.service.DropDownService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;

// import java.sql.SQLException;
// import java.time.LocalDate;
import java.util.List;
// import java.util.Map;
import java.util.Map;
import com.akranta.perfex_sb.dto.FunctionalLocationHierarchyDto;
import com.akranta.perfex_sb.dto.FunctionalLocationOptionDto;

@RestController
@RequestMapping("/api/dropdown")
@CrossOrigin(origins = "*")
public class DropDownController {
    @Autowired
    private DropDownService dropdownService;

    @GetMapping("/abnormality-types")
    public List<DropDownDto> abnormalityTypes() {
        return dropdownService.getDropdown(
                "ABN_TL_TYPEMST",
                "ABTM_KEYID",
                "ABTM_NAME", "");
    }

    @GetMapping("/abnormality-tags")
    public List<DropDownDto> abnormalityTags() {
        return dropdownService.getDropdown(
                "ABN_TL_TAGMST",
                "TAGM_KEYID",
                "TAGM_NAME", "");
    }

    @GetMapping("/abnormality-impact")
    public List<DropDownDto> abnormalityImpact() {
        return dropdownService.getDropdown(
                "ABN_TL_IMPACTMST",
                "ABIM_KEYID",
                "ABIM_NAME",
                "");
    }

    @GetMapping("/abnormality-catogery")
    public List<DropDownDto> abnormalityCatogery() {
        return dropdownService.getDropdown(
                "ABN_TL_CATEGORYMST",
                "ABCM_KEYID",
                "ABCM_NAME", "ABCM_ACTIVE='Y'");
    }

    @GetMapping("/abnormality-employees")
    public List<DropDownDto> abnormalityEmployees() {
        return dropdownService.getDropdown(
                "GEN_TL_EMPLOYEEMST",
                "EMPM_KEYID",
                "EMPM_NAME", "EMPM_ACTIVE = 'Y'");
    }

    @GetMapping("/abnormality-trade")
    public List<DropDownDto> abnormalityTrade() {
        return dropdownService.getDropdown(
                "GEN_TL_TRADEMST",
                "TRDM_KEYID",
                "TRDM_NAME", "");
    }

    @GetMapping("/abnormality-subType")
    public List<DropDownDto> abnormalitySubType() {
        return dropdownService.getDropdown(
                "ABN_TL_HTASOCMST",
                "AHSM_KEYID",
                "AHSM_NAME", "AHSM_ACTIVE='Y'");
    }

    @GetMapping("/machineCombo")
    public List<DropDownDto> MACHINECOMBO() {
        return dropdownService.getDropdown(
                "GEN_VW_FACTORYLAYOUT",
                "MCHM_KEYID",
                "MCHM_MACHINENAME", "MCHM_ACTIVE='Y'");
    }

    @GetMapping("/abnStatus")
    public List<Map<String, String>> status() {

        return List.of(
                Map.of("value", "P", "label", "Pending"),
                Map.of("value", "C", "label", "Completed"));
        // Map.of("value", "REJECTED", "label", "Rejected"));
    }

    @GetMapping("/functional-location/company")
    public List<FunctionalLocationOptionDto> functionalLocationCompanies() {
        return dropdownService.getFunctionalCompanies();
    }

    @GetMapping("/functional-location/location")
    public List<FunctionalLocationOptionDto> functionalLocationLocations(
            @RequestParam(required = false) String companyId) {

        return dropdownService.getFunctionalLocations(companyId);
    }

    @GetMapping("/functional-location/sbu")
    public List<FunctionalLocationOptionDto> functionalLocationSbu(
            @RequestParam(required = false) String companyId,
            @RequestParam(required = false) String locationId) {

        return dropdownService.getFunctionalSbus(companyId, locationId);
    }

    @GetMapping("/functional-location/pbu")
    public List<FunctionalLocationOptionDto> functionalLocationPbu(
            @RequestParam(required = false) String companyId,
            @RequestParam(required = false) String locationId,
            @RequestParam(required = false) String sbuId) {

        return dropdownService.getFunctionalPbus(companyId, locationId, sbuId);
    }

    @GetMapping("/functional-location/section")
    public List<FunctionalLocationOptionDto> functionalLocationSection(
            @RequestParam(required = false) String companyId,
            @RequestParam(required = false) String locationId,
            @RequestParam(required = false) String sbuId,
            @RequestParam(required = false) String pbuId) {

        return dropdownService.getFunctionalSections(companyId, locationId, sbuId, pbuId);
    }

    @GetMapping("/functional-location/cell")
    public List<FunctionalLocationOptionDto> functionalLocationCell(
            @RequestParam(required = false) String companyId,
            @RequestParam(required = false) String locationId,
            @RequestParam(required = false) String sbuId,
            @RequestParam(required = false) String pbuId,
            @RequestParam(required = false) String sectionId) {

        return dropdownService.getFunctionalCells(companyId, locationId, sbuId, pbuId, sectionId);
    }

    @GetMapping("/functional-location/machine")
    public List<FunctionalLocationOptionDto> functionalLocationMachine(
            @RequestParam(required = false) String companyId,
            @RequestParam(required = false) String locationId,
            @RequestParam(required = false) String sbuId,
            @RequestParam(required = false) String pbuId,
            @RequestParam(required = false) String sectionId,
            @RequestParam(required = false) String cellId,
            @RequestParam(required = false) String flid) {

        return dropdownService.getFunctionalMachines(
                companyId,
                locationId,
                sbuId,
                pbuId,
                sectionId,
                cellId,
                flid);
    }

    @GetMapping("/functional-location/hierarchy")
    public FunctionalLocationHierarchyDto functionalLocationHierarchy(
            @RequestParam(required = false) String originalId,
            @RequestParam(required = false) String flid) {

        return dropdownService.getFunctionalLocationHierarchy(originalId, flid);
    }

    @GetMapping("/functional-location/default")
    public FunctionalLocationHierarchyDto functionalLocationDefault(
            @RequestParam String employeeId,
            @RequestParam(required = false) String roleId,
            @RequestParam(required = false) String flid) {

        return dropdownService.getDefaultFunctionalLocation(employeeId, roleId, flid);
    }

    @GetMapping("/functional-location/login-default")
    public FunctionalLocationHierarchyDto getLoginDefaultFunctionalLocation(
            @RequestParam String employeeId) {

        return dropdownService.getLoginDefaultFunctionalLocation(employeeId);
    }

    @PostMapping("/employee/commonFilter")
    public ResponseEntity<List<DropDownDto>> employeeCommonFilter(@RequestBody CommonFilterDto commonFilterDto) {

        return ResponseEntity.ok(dropdownService.getEmployeeComboList(commonFilterDto));

    }

    @PostMapping("/commonFilter/abnormality/subtype")
    public ResponseEntity<List<DropDownDto>> abnSubTypeCommonFilter(@RequestBody CommonFilterDto commonFilterDto) {

        return ResponseEntity.ok(dropdownService.getAbnSubTypeComboList(commonFilterDto));

    }

}
