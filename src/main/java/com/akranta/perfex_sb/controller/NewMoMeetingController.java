package com.akranta.perfex_sb.controller;

import com.akranta.perfex_sb.dto.GridVisitorDto;
import com.akranta.perfex_sb.dto.MomMstAndDtlDto;
import com.akranta.perfex_sb.dto.MommstRecallDto;
import com.akranta.perfex_sb.dto.momActionPlanDto;
import com.akranta.perfex_sb.model.GenTlActionPlanMst;
import com.akranta.perfex_sb.model.GenTlMommst;
import com.akranta.perfex_sb.model.GenTlVisitors;

import com.akranta.perfex_sb.service.NewMoMeetingService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/simplifiedMom")
public class NewMoMeetingController {

    private static final Logger logger = LoggerFactory.getLogger(MoMeetingController.class);

    @Autowired
    private NewMoMeetingService moMeetingService;

    // private static final String SEQ_IDENTIFIER_DTL = "GEN_TL_MOMDTL";
    // private static final String PREFIX_DTL = "MOD";

    @GetMapping("/getAll")
    public List<MomMstAndDtlDto> getAllMeetings() {

        // GenTlMommst genTlMommst = new GenTlMommst();

        return null;

    }

    @GetMapping("/getFlid")
    public ResponseEntity<String> getFild(@RequestParam("originalId") String originalId) {
        String flid = moMeetingService.getFild(originalId);
        return ResponseEntity.ok(flid);
    }

    @PostMapping
    public ResponseEntity<MomMstAndDtlDto> saveMOM(@RequestBody MomMstAndDtlDto momMstAndDtlDto) {

        try {
            logger.info("Entered into Controller - Springboot");
            ResponseEntity<MomMstAndDtlDto> result = moMeetingService.saveMOM(momMstAndDtlDto);
            return result;

        } catch (Exception e)

        {
            logger.error("Error creating Mom Meeting: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .header("Error-Message", e.getMessage())
                    .build();

        }
    }

    @PostMapping("/attendance")
    public ResponseEntity<MomMstAndDtlDto> saveAttendance(@RequestBody MomMstAndDtlDto momMstAndDtlDto) {

        try {

            ResponseEntity<MomMstAndDtlDto> result = moMeetingService.saveAttendance(momMstAndDtlDto);
            return result;

        } catch (Exception e)

        {
            logger.error("Error creating Mom Meeting: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .header("Error-Message", e.getMessage())
                    .build();

        }
    }

    @GetMapping("/recallMom")
    public List<Map<String, Object>> mommstRecall(
            @RequestParam("mstDate") @DateTimeFormat(pattern = "dd-MMM-yyyy") LocalDate mstDate,
            @RequestParam("flid") String flid,
            @RequestParam("shift") String shift,
            @RequestParam("type") String type,
            @RequestParam("pillarid") String pillarid) {

        return moMeetingService.mommstRecall(mstDate, flid, shift, type, pillarid);
    }

    // String KeyId, String flid, LocalDate momdate, String shift, String type,
    // String pillarid
    // @PostMapping("/newMomGrid")
    // public List<Map<String, Object>> newMomGrid(@RequestBody MommstRecallDto
    // mstDto) {
    // String keyId = mstDto.getKeyId();
    // String flid = mstDto.getFlid();
    // String shift = mstDto.getShift();
    // String type = mstDto.getType();
    // String momdate = mstDto.getMomDate();

    // if ("null".equalsIgnoreCase(momdate)) {
    // momdate = null;
    // }

    // if ("null".equalsIgnoreCase(shift)) {
    // shift = null;
    // }

    // // LocalDate date = LocalDate.parse(momdate);
    // return moMeetingService.newMomGrid(keyId, flid, momdate, shift, type);
    // }

    // @PostMapping("/newMomGrid")
    // public List<Map<String, Object>> newMomGrid(@RequestBody MommstRecallDto
    // mstDto) {
    // String keyId = mstDto.getKeyId();
    // String flid = mstDto.getFlid();
    // String shift = mstDto.getShift();
    // String type = mstDto.getType();
    // String momdate = mstDto.getMomDate();
    // String pillarid = mstDto.getPillarid();//Added Pillar Id

    // if ("null".equalsIgnoreCase(momdate)) {
    // momdate = null;
    // }

    // if ("null".equalsIgnoreCase(shift)) {
    // shift = null;
    // }

    // // LocalDate date = LocalDate.parse(momdate);
    // return moMeetingService.newMomGrid(keyId, flid, momdate, shift, type,
    // pillarid);//Added Pillar Id
    // }

    @PostMapping("/newMomGrid")
    public List<Map<String, Object>> newMomGrid(@RequestBody MommstRecallDto mstDto) {
        String keyId = mstDto.getKeyId();
        String flid = mstDto.getFlid();
        String shift = mstDto.getShift();
        String type = mstDto.getType();
        String momdate = mstDto.getMomDate();
        String pillarid = mstDto.getPillarid();
        // Sanitize ALL "null" strings to actual null
        if ("null".equalsIgnoreCase(momdate) || "".equals(momdate))
            momdate = null;
        if ("null".equalsIgnoreCase(shift) || "".equals(shift))
            shift = null; // ✅ null, not "SHIFT001"
        if ("null".equalsIgnoreCase(flid) || "".equals(flid))
            flid = null; // ✅ was missing
        if ("null".equalsIgnoreCase(pillarid) || "".equals(pillarid))
            pillarid = null; // ✅ was missing

        logger.info("newMomGrid → keyId:{} flid:{} type:{} momdate:{} shift:{} pillarid:{}",
                keyId, flid, type, momdate, shift, pillarid);

        // ✅ Pass in correct order matching service → repo signature
        // return moMeetingService.newMomGrid(keyId, flid, type, momdate, shift,
        // pillarid);
        return moMeetingService.newMomGrid(keyId, flid, momdate, shift, type, pillarid);
    }

    @PostMapping("/gridVisitor")
    public List<Map<String, Object>> momGridVisitor(@RequestBody GridVisitorDto visitorDto) {
        String masterKeyid = visitorDto.getMasterKeyid();
        String shift = visitorDto.getShift();
        String date = visitorDto.getDate();
        String flid = visitorDto.getFlid();
        String type = visitorDto.getType();
        String pillarid = visitorDto.getPillarid();
        String recall = visitorDto.getRecall();

        return moMeetingService.momGridVisitor(masterKeyid, flid, date, shift, type, pillarid, recall);

    }

    @PostMapping("/saveVisitor")
    public ResponseEntity<GenTlVisitors> saveVisitor(@RequestBody GenTlVisitors genTlVisitors) throws Exception {

        GenTlVisitors genTlVisitorsResult = moMeetingService.saveVisitors(genTlVisitors);
        return ResponseEntity.ok(genTlVisitorsResult);
    }

    @GetMapping("/getById")
    public ResponseEntity<GenTlMommst> getMommstById(@RequestParam("keyId") String keyId) {

        logger.info("Entered into Controller - Springboot GET BY ID");
        GenTlMommst result = moMeetingService.getMommstById(keyId);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/fillagendadata")
    public ResponseEntity<List<Map<String, Object>>> fillagendadata(@RequestParam("momdate") String date,
            @RequestParam("flid") String location) {
        List<Map<String, Object>> result = moMeetingService.fillagendadata(date, location);
        return ResponseEntity.ok(result);

    }

    @DeleteMapping("/deleteNewRow")
    public void DeleteNewMomRow(@RequestParam("keyId") String mstKeyId,
            @RequestParam("actionPlanMasterId") String actionPlanMstId) {
        moMeetingService.deleteDetails(mstKeyId, actionPlanMstId);
    }

    @DeleteMapping("/deleteVisitor")
    public void DeleteVisitor(@RequestParam("keyId") String visiKeyid) {
        moMeetingService.deleteVisitor(visiKeyid);
    }

    @PostMapping("/createActionPlan")
    public ResponseEntity<GenTlActionPlanMst> createActionPlan(@RequestBody momActionPlanDto dto) throws Exception {
        GenTlActionPlanMst result = moMeetingService.createActionPlan(dto);
        return ResponseEntity.ok(result);

    }

    @PostMapping("/updateActionPlan")
    public ResponseEntity<GenTlActionPlanMst> updateActionPlan(@RequestBody momActionPlanDto dto) throws Exception {
        GenTlActionPlanMst result = moMeetingService.updateActionPlan(dto);
        return ResponseEntity.ok(result);

    }

}
