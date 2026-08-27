package com.akranta.perfex_sb.controller;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.akranta.perfex_sb.model.KznTlKaizenBankMst;
import com.akranta.perfex_sb.service.KaizenBankService;

@RestController
@RequestMapping("/api/kznbnk")
public class KaizenBankController {

  @Autowired
  private KaizenBankService kaizenBankService;

  @PostMapping("/save")
  public ResponseEntity<KznTlKaizenBankMst> saveKznTlKaizenBankMst(@RequestBody KznTlKaizenBankMst model)
      throws Exception {
    KznTlKaizenBankMst result = kaizenBankService.save(model);
    return ResponseEntity.ok(result);
  }

  @GetMapping("/getById/{id}")
  public ResponseEntity<KznTlKaizenBankMst> getKznTlKaizenBankMstById(@PathVariable String id) {
    KznTlKaizenBankMst result = kaizenBankService.findById(id);
    return ResponseEntity.ok(result);
  }

  @GetMapping("/selectKznData/{keyId}")
  public ResponseEntity<String> selectKznData(@PathVariable String keyId) {
    String result = kaizenBankService.selectKznData(keyId);
    return ResponseEntity.ok(result);

  }

  @PostMapping("/update/kznworkflowstatus")
  public String getWorkFlowStaus(@RequestBody Map<String, String> req) {

    String keyId = req.get("kznKeyId");
    String status = req.get("wfStatus");
    String kaizen = req.get("kaizen");
    String acrejby = req.get("acrejby");
    String mocRequired = req.get("mocRequired");
    String responsibility = req.get("responsibility");
    String verifyRemarks = req.get("verifyRemarks");
    String mocitem = req.get("mocitem");

    BigDecimal implementCost = req.get("implementCost") != null
        ? new BigDecimal(req.get("implementCost"))
        : BigDecimal.ZERO;

    LocalDateTime targetDate = req.get("targetDate") != null
        ? LocalDateTime.parse(req.get("targetDate"))
        : null;

    return kaizenBankService.updateKaizenWorkflowStatus(
        keyId,
        status,
        kaizen,
        acrejby,
        implementCost,
        targetDate,
        mocRequired,
        responsibility,
        verifyRemarks,
      mocitem);
  }


  @PostMapping("/mltplesuggestion")
    public ResponseEntity<List<KznTlKaizenBankMst>> save(
            @RequestBody List<KznTlKaizenBankMst> mltSugg) {

        List<KznTlKaizenBankMst> saved = kaizenBankService.multipleSave(mltSugg);

        return ResponseEntity.ok(saved);
    }


@GetMapping("/categoryRecall/{keyid}")
    public ResponseEntity<List<Map<String, Object>>> categoryRecall(@PathVariable String keyid) {
        try {
            List<Map<String, Object>> result = kaizenBankService.findCategoryRecall(keyid);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

    
    @PostMapping("/deleteById/{keyId}")
    public String deleteById(@PathVariable String keyId) {

        String deleted = kaizenBankService.deleteSuggestionById(keyId);

        return deleted;
    }
}

// BigDecimal implementCost = req.get("implementCost") != null
// ? new BigDecimal(req.get("implementCost"))
// : BigDecimal.ZERO;

// LocalDateTime targetDate = req.get("targetDate") != null
// ? LocalDateTime.parse(req.get("targetDate"))
// : null;

// @Autowired
// private KaizenBankMstRepository repository;

// // GET all employees
// @GetMapping
// public List<KznTlKaizenBankMst> getAllEmployees() {
// return repository.findAll();
// }

// @GetMapping("/grid")
// public List<Map<String, Object>>
// getSuggestionModificationGrid(@RequestParam("flid") String flid) {

// return kaizenBankService.getSuggestionModificationGrid(flid);

// }

// @GetMapping("/getById")
// public KznTlKaizenBankMst getSuggestionMasterData(@RequestParam("id") String
// id)
// throws Exception {

// return kaizenBankService.getSuggestionMasterData(id);

// }

// @PostMapping("/update/kznbnkstatus")
// public String getWorkFlowStaus(@RequestBody Map<String, String> req) {

// String kznKeyId = req.get("kznKeyId");
// String refType = req.get("refType");
// String transCode = req.get("transCode");
// String isUpdate = req.get("isUpdate");
// String wfStatus = req.get("wfStatus");

// return kaizenBankService.updateKaizenNative(kznKeyId, refType, transCode,
// null, null, isUpdate, wfStatus, wfStatus,
// wfStatus);

// }