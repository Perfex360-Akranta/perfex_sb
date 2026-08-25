package com.akranta.perfex_sb.controller;

import com.akranta.perfex_sb.dto.GridVisitorDto;
import com.akranta.perfex_sb.dto.MomMstAndDtlDto;
import com.akranta.perfex_sb.dto.MommstRecallDto;
import com.akranta.perfex_sb.model.GenTlMommst;
import com.akranta.perfex_sb.model.GenTlVisitors;
import com.akranta.perfex_sb.service.MoMeetingService;

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
@RequestMapping("/api/mom")
public class MoMeetingController {

    private static final Logger logger = LoggerFactory.getLogger(MoMeetingController.class);

    @Autowired
    private MoMeetingService moMeetingService;

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

        return moMeetingService.mommstRecall(mstDate, flid, shift, type,pillarid);
    }

    // String KeyId, String flid, LocalDate momdate, String shift, String type,
    // String pillarid
    @PostMapping("/recallMomGrid")
    public List<Map<String, Object>> mommstGridRecall(@RequestBody MommstRecallDto mstDto) {
        String keyId = mstDto.getKeyId();
        String flid = mstDto.getFlid();
        String shift = mstDto.getShift();
        String type = mstDto.getType();
        String pillarid = mstDto.getPillarid();
        String momdate = mstDto.getMomDate();

        if (momdate == "null") {

            momdate = null;
        }

        // LocalDate date = LocalDate.parse(momdate);
        return moMeetingService.mommstGridRecall(keyId, flid, momdate, shift, type, pillarid);
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

    @DeleteMapping("/deleteFullMom")
    public void DeleteFullMom(@RequestParam("keyId") String mstKeyId) {
        moMeetingService.deleteFullMom(mstKeyId);
    }

    @DeleteMapping("/deleteNewRow")
    public void DeleteNewMomRow(@RequestParam("keyId") String mstKeyId) {
        moMeetingService.deleteDetails(mstKeyId);
    }

    @DeleteMapping("/deleteVisitor")
    public void DeleteVisitor(@RequestParam("keyId") String visiKeyid) {
        moMeetingService.deleteVisitor(visiKeyid);
    }

}
