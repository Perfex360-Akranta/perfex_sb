package com.akranta.perfex_sb.controller;

import com.akranta.perfex_sb.dto.BalPcsEntrySaveDto;
import com.akranta.perfex_sb.model.BAL_PcsEntryProductiondata;
import com.akranta.perfex_sb.service.BAL_PcsEntryService;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/pcsentry")
public class BAL_PcsEntrySaveController {

    private final BAL_PcsEntryService balPcsEntryService;

    public BAL_PcsEntrySaveController(BAL_PcsEntryService balPcsEntrySaveService) {
        this.balPcsEntryService = balPcsEntrySaveService;
    }

    @PostMapping("/save")
    public ResponseEntity<BAL_PcsEntryProductiondata> saveBalEntry(@RequestBody BalPcsEntrySaveDto request) {
        BAL_PcsEntryProductiondata saved = balPcsEntryService.createNew(request);
        return ResponseEntity.ok(saved);
    }

    @GetMapping("/last-shift-product")
public ResponseEntity<List<String[]>> lastShiftProduct(
        @RequestParam("factId") String factId,
        @RequestParam("cellId") String cellId,
        @RequestParam("entryDate") String entryDate) {
    return ResponseEntity.ok(balPcsEntryService.getLastShiftProduct(factId, cellId, entryDate));
}

@GetMapping("/shift-start-end-time")
public ResponseEntity<List<String[]>> shiftStartEndTime(@RequestParam("shiftId") String shiftId) {
    return ResponseEntity.ok(balPcsEntryService.getShiftStarEndTime(shiftId));
}

@GetMapping("/holiday-dates")
public ResponseEntity<List<String[]>> holidayDates(
        @RequestParam("factId") String factId,
        @RequestParam("entryDate") String entryDate) {
    return ResponseEntity.ok(balPcsEntryService.getHolidayDates(factId, entryDate));
}

@GetMapping("/all-time-for-hour")
public ResponseEntity<List<String[]>> allTimeForHour(
        @RequestParam("cellId") String cellId,
        @RequestParam("shiftId") String shiftId,
        @RequestParam("entryDate") String entryDate,
        @RequestParam(value = "hourId", required = false, defaultValue = "") String hourId,
        @RequestParam("shiftWise") String shiftWise) {
    return ResponseEntity.ok(balPcsEntryService.getAllTimeforHour(cellId, shiftId, entryDate, hourId, shiftWise));
}

@GetMapping("/cycletime")
public ResponseEntity<List<String[]>> cycletime(@RequestParam("productId") String productId) {
    return ResponseEntity.ok(balPcsEntryService.getCycletime(productId));
}


@GetMapping("/getShiftTotaltime")
public ResponseEntity<Integer> shiftTotalTime(
        @RequestParam("startdate") String startdate,
        @RequestParam("enddate") String enddate,
        @RequestParam("shiftId") String shiftId) {
    return ResponseEntity.ok(balPcsEntryService.getShiftTotaltime(startdate, enddate, shiftId));
}

}