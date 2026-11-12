package com.akranta.perfex_sb.controller;

import com.akranta.perfex_sb.model.Qtmtlcapa;
import com.akranta.perfex_sb.dto.QtmTlCapaWorkflowDTO;
import com.akranta.perfex_sb.dto.QtmTlCapaActionmstDTO;
import com.akranta.perfex_sb.service.QtmtlcapaService;
import com.akranta.perfex_sb.service.QtmTlCapaWorkflowService;
import com.akranta.perfex_sb.service.QtmTlCapaActionmstService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/qtmtlcapa")
public class QtmtlcapaController {

    @Autowired
    private QtmtlcapaService qtmtlcapaService;

    private static final Logger logger = LoggerFactory.getLogger(QtmtlcapaController.class);

    @Autowired
    private QtmTlCapaWorkflowService qtmTlCapaWorkflowService;

    @Autowired
    private QtmTlCapaActionmstService qtmTlCapaActionmstService;

    @PostMapping("/save")
    public ResponseEntity<Qtmtlcapa> save(@RequestBody Qtmtlcapa model) throws Exception {
        Qtmtlcapa result = qtmtlcapaService.save(model);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/saveWorkflow")
    public ResponseEntity<QtmTlCapaWorkflowDTO> saveWorkflow(@RequestBody QtmTlCapaWorkflowDTO dto) throws Exception {
        QtmTlCapaWorkflowDTO result = qtmTlCapaWorkflowService.save(dto);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/saveActionMst")
    public ResponseEntity<QtmTlCapaActionmstDTO> saveActionMst(@RequestBody QtmTlCapaActionmstDTO dto)
            throws Exception {
        QtmTlCapaActionmstDTO result = qtmTlCapaActionmstService.save(dto);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/updateCapaWorkflow")
    public int updateCapaWorkflow(
            @RequestParam("empId") String empId,
            @RequestParam("remarks") String remarks,
            @RequestParam("status") String status,
            @RequestParam("date") String date,
            @RequestParam("capaId") String capaId) throws Exception {

        return qtmTlCapaWorkflowService.updateCapaWorkflow(
                empId,
                remarks,
                status,
                date,
                capaId);
    }

    @GetMapping("/getFnlnKeyIdByOriginalId/{targetJH}")
    public ResponseEntity<String> getFnlnKeyIdByOriginalId(@PathVariable String targetJH) {
        logger.info("Entered into Controller - Springboot GET BY ID");
        String result = qtmTlCapaActionmstService.getFnlnKeyIdByOriginalId(targetJH);
        return ResponseEntity.ok(result);
    }

    

    @DeleteMapping("/deleteCapaActionPlanDetail/{qcpaKeyid}")
    public ResponseEntity<Void> deleteCapaActionPlanDetail(@PathVariable String qcpaKeyid) throws Exception {
        qtmTlCapaActionmstService.deleteCapaActionPlanDetail(qcpaKeyid);
        return ResponseEntity.ok().build();
    }

}







// @DeleteMapping("/deleteCapaActionPlanDetail")
    // public ResponseEntity<QtmTlCapaActionmst> deleteCapaActionPlanDetail(
    // @RequestBody QtmTlCapaActionmst qtmTlCapaActionmst) throws Exception {

    // QtmTlCapaActionmst response =
    // qtmTlCapaActionmstService.deleteCapaActionPlanDetail(qtmTlCapaActionmst);

    // return ResponseEntity.ok(response);
    // }

// @PostMapping("/updateCapaWorkflow")
// public ResponseEntity<Integer> updateCapaWorkflow(@RequestBody
// QtmTlCapaWorkflowDTO dto) throws Exception {
// int result = qtmTlCapaWorkflowService.updateCapaWorkflow(dto.getEmpId(),
// dto.getRemarks(), dto.getStatus(),
// dto.getDate(), dto.getCapaId());
// return ResponseEntity.ok(result);
// }

// @GetMapping("/getById/{id}")
// public ResponseEntity<Qtmtlcapa> getById(@PathVariable String id) {
// Qtmtlcapa result = qtmtlcapaService.findById(id);
// return ResponseEntity.ok(result);
// }

// @GetMapping("/getDtoById/{id}")
// public ResponseEntity<QtmtlcapaDto> getDtoById(@PathVariable String id) {
// QtmtlcapaDto result = qtmtlcapaService.findDtoById(id);
// return ResponseEntity.ok(result);
// }

// @GetMapping("/getAll")
// public ResponseEntity<List<Qtmtlcapa>> getAll() {
// List<Qtmtlcapa> result = qtmtlcapaService.findAll();
// return ResponseEntity.ok(result);
// }

// @DeleteMapping("/delete/{id}")
// public ResponseEntity<Void> delete(@PathVariable String id) {
// qtmtlcapaService.deleteById(id);
// return ResponseEntity.ok().build();
// }