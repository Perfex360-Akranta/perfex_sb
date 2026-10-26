package com.akranta.perfex_sb.controller;

import com.akranta.perfex_sb.dto.BdmDto;
import com.akranta.perfex_sb.exception.ResourceNotFoundException;
import com.akranta.perfex_sb.model.BAL_BdmTlDtl;
import com.akranta.perfex_sb.model.BAL_BdmTlMst;
import com.akranta.perfex_sb.model.BAL_WomTlManpowercostactual;
import com.akranta.perfex_sb.model.BAL_WomTlManpowercostplan;
import com.akranta.perfex_sb.model.BAL_WomTlOthercostactual;
import com.akranta.perfex_sb.model.BAL_WomTlOthercostplan;
import com.akranta.perfex_sb.model.BAL_WomTlServicecostactual;
import com.akranta.perfex_sb.model.BAL_WomTlServicecostplan;
import com.akranta.perfex_sb.model.BAL_WomTlSparecostactual;
import com.akranta.perfex_sb.model.BAL_WomTlSparecostplan;
import com.akranta.perfex_sb.model.BAL_WomTlUtilitycostactual;
import com.akranta.perfex_sb.model.BAL_WomTlUtilitycostplan;
import com.akranta.perfex_sb.model.WomTlCommunicationlog;
import com.akranta.perfex_sb.model.WomTlWomst;
import com.akranta.perfex_sb.service.BdmService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/bdm")
public class BdmController {

    private static final Logger logger = LoggerFactory.getLogger(BdmController.class);

    private final BdmService service;

    public BdmController(BdmService service) {
        this.service = service;
    }

    /**
     * Single endpoint for both CREATE and UPDATE.
     *
     * INSERT : send request.master.keyid = null / ""
     * UPDATE : send request.master.keyid = existing BDM keyid
     *
     * POST /api/bdm/save
     */
    @PostMapping("/save")
    public ResponseEntity<BdmDto> saveBdm(@RequestBody BdmDto request) {
        logger.info("saveBdm called – keyid={}", 
                    request.getMaster() != null ? request.getMaster().getKeyid() : "null");
        try {
            return service.saveBdm(request);
        } catch (ResourceNotFoundException e) {
            logger.error("BDM record not found: {}", e.getMessage());
            return ResponseEntity.notFound().build();
        } catch (IllegalArgumentException e) {
            logger.error("Invalid input: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            logger.error("Error saving BDM: {}", e.getMessage(), e);
            return ResponseEntity.internalServerError().build();
        }
    }

    // ─── BdmController.java  (add this endpoint inside the existing class) ────

/**
 * Cost Information grid summary (Estimate vs Actual) for a given Work Order.
 *
 * GET /api/bdm/cost-summary/{woId}
 */
@GetMapping("/cost-summary/{woId}")
public ResponseEntity<List<Map<String, Object>>> getGridSummary(@PathVariable String woId) {
    logger.info("getGridSummary called – woId={}", woId);
    try {
        List<Map<String, Object>> result = service.getGridSummary(woId);
        return ResponseEntity.ok(result);
    } catch (IllegalArgumentException e) {
        logger.error("Invalid input: {}", e.getMessage());
        return ResponseEntity.badRequest().build();
    } catch (Exception e) {
        logger.error("Error fetching cost grid summary: {}", e.getMessage(), e);
        return ResponseEntity.internalServerError().build();
    }
}



@GetMapping("/grid-estimate/{formName}/{woId}")
public ResponseEntity<List<Map<String, Object>>> getGridEstimate(
        @PathVariable String formName, @PathVariable String woId) {
    logger.info("getGridEstimate called – formName={}, woId={}", formName, woId);
    try {
        List<Map<String, Object>> result = service.getGridEstimate(formName, woId);
        return ResponseEntity.ok(result);
    } catch (IllegalArgumentException e) {
        logger.error("Invalid input: {}", e.getMessage());
        return ResponseEntity.badRequest().build();
    } catch (Exception e) {
        logger.error("Error fetching grid estimate: {}", e.getMessage(), e);
        return ResponseEntity.internalServerError().build();
    }
}

@GetMapping("/page-total/{formType}/{formName}/{woId}")
public ResponseEntity<List<Map<String, Object>>> getPageTotal(
        @PathVariable String formType, @PathVariable String formName, @PathVariable String woId) {
    logger.info("getPageTotal called – formType={}, formName={}, woId={}", formType, formName, woId);
    try {
        List<Map<String, Object>> result = service.getPageTotal(formName, formType, woId);
        return ResponseEntity.ok(result);
    } catch (IllegalArgumentException e) {
        logger.error("Invalid input: {}", e.getMessage());
        return ResponseEntity.badRequest().build();
    } catch (Exception e) {
        logger.error("Error fetching page total: {}", e.getMessage(), e);
        return ResponseEntity.internalServerError().build();
    }
}
// ─── BdmController.java (add endpoint) ─────────────────────────────────────

/**
 * Cost Information grid actual rows for a given form type + Work Order.
 * GET /api/bdm/grid-actual/{formName}/{woId}
 */
@GetMapping("/grid-actual/{formName}/{woId}")
public ResponseEntity<List<Map<String, Object>>> getGridActual(
        @PathVariable String formName, @PathVariable String woId) {
    logger.info("getGridActual called – formName={}, woId={}", formName, woId);
    try {
        List<Map<String, Object>> result = service.getGridActual(formName, woId);
        return ResponseEntity.ok(result);
    } catch (IllegalArgumentException e) {
        logger.error("Invalid input: {}", e.getMessage());
        return ResponseEntity.badRequest().build();
    } catch (Exception e) {
        logger.error("Error fetching grid actual: {}", e.getMessage(), e);
        return ResponseEntity.internalServerError().build();
    }
}
@PostMapping("/manpower-cost/save")
public ResponseEntity<BAL_WomTlManpowercostplan> saveManpowerCost(
        @RequestBody BAL_WomTlManpowercostplan request) {
    logger.info("saveManpowerCost called – woid={}, manpowerid={}, skillid={}",
            request.getWoid(), request.getManpowerid(), request.getSkillid());
    try {
        BAL_WomTlManpowercostplan result = service.saveManpowerCost(request);
        return ResponseEntity.ok(result);
    } catch (ResourceNotFoundException e) {
        logger.error("Manpower cost record not found: {}", e.getMessage());
        return ResponseEntity.notFound().build();
    } catch (IllegalArgumentException e) {
        logger.error("Invalid input: {}", e.getMessage());
        return ResponseEntity.badRequest().build();
    } catch (Exception e) {
        logger.error("Error saving manpower cost: {}", e.getMessage(), e);
        return ResponseEntity.internalServerError().build();
    }
}

@PostMapping("/manpower-cost-actual/save")
public ResponseEntity<BAL_WomTlManpowercostactual> saveManpowerCostActual(
        @RequestBody BAL_WomTlManpowercostactual request) {
    logger.info("saveManpowerCostActual called – maintwoid={}, manpowerid={}, skillid={}",
            request.getMaintwoid(), request.getManpowerid(), request.getSkillid());
    try {
        BAL_WomTlManpowercostactual result = service.saveManpowerCostActual(request);
        return ResponseEntity.ok(result);
    } catch (ResourceNotFoundException e) {
        logger.error("Manpower cost actual record not found: {}", e.getMessage());
        return ResponseEntity.notFound().build();
    } catch (IllegalArgumentException e) {
        logger.error("Invalid input: {}", e.getMessage());
        return ResponseEntity.badRequest().build();
    } catch (Exception e) {
        logger.error("Error saving manpower cost actual: {}", e.getMessage(), e);
        return ResponseEntity.internalServerError().build();
    }
}

@PostMapping("/spare-cost/save")
public ResponseEntity<BAL_WomTlSparecostplan> saveSpareCost(
        @RequestBody BAL_WomTlSparecostplan request) {
    logger.info("saveSpareCost called – woid={}, requestedby={}, sparesid={}",
            request.getWoid(), request.getRequestedby(), request.getSparesid());
    try {
        BAL_WomTlSparecostplan result = service.saveSpareCost(request);
        return ResponseEntity.ok(result);
    } catch (ResourceNotFoundException e) {
        logger.error("Spare cost record not found: {}", e.getMessage());
        return ResponseEntity.notFound().build();
    } catch (IllegalArgumentException e) {
        logger.error("Invalid input: {}", e.getMessage());
        return ResponseEntity.badRequest().build();
    } catch (Exception e) {
        logger.error("Error saving spare cost: {}", e.getMessage(), e);
        return ResponseEntity.internalServerError().build();
    }
}
@PostMapping("/service-cost/save")
public ResponseEntity<BAL_WomTlServicecostplan> saveServiceCost(
        @RequestBody BAL_WomTlServicecostplan request) {
    logger.info("saveServiceCost called – woid={}, serviceid={}",
            request.getWoid(), request.getServiceid());
    try {
        BAL_WomTlServicecostplan result = service.saveServiceCost(request);
        return ResponseEntity.ok(result);
    } catch (ResourceNotFoundException e) {
        logger.error("Service cost record not found: {}", e.getMessage());
        return ResponseEntity.notFound().build();
    } catch (IllegalArgumentException e) {
        logger.error("Invalid input: {}", e.getMessage());
        return ResponseEntity.badRequest().build();
    } catch (Exception e) {
        logger.error("Error saving service cost: {}", e.getMessage(), e);
        return ResponseEntity.internalServerError().build();
    }
}
@PostMapping("/utility-cost/save")
public ResponseEntity<BAL_WomTlUtilitycostplan> saveUtilityCost(
        @RequestBody BAL_WomTlUtilitycostplan request) {
    logger.info("saveUtilityCost called – wokeyid={}, requestedby={}, utilitymstid={}",
            request.getWokeyid(), request.getRequestedby(), request.getUtilitymstid());
    try {
        BAL_WomTlUtilitycostplan result = service.saveUtilityCost(request);
        return ResponseEntity.ok(result);
    } catch (ResourceNotFoundException e) {
        logger.error("Utility cost record not found: {}", e.getMessage());
        return ResponseEntity.notFound().build();
    } catch (IllegalArgumentException e) {
        logger.error("Invalid input: {}", e.getMessage());
        return ResponseEntity.badRequest().build();
    } catch (Exception e) {
        logger.error("Error saving utility cost: {}", e.getMessage(), e);
        return ResponseEntity.internalServerError().build();
    }
}
@PostMapping("/other-cost/save")
public ResponseEntity<BAL_WomTlOthercostplan> saveOtherCost(
        @RequestBody BAL_WomTlOthercostplan request) {
    logger.info("saveOtherCost called – woid={}, requestedby={}, othercostmstid={}",
            request.getWoid(), request.getRequestedby(), request.getOthercostmstid());
    try {
        BAL_WomTlOthercostplan result = service.saveOtherCost(request);
        return ResponseEntity.ok(result);
    } catch (ResourceNotFoundException e) {
        logger.error("Other cost record not found: {}", e.getMessage());
        return ResponseEntity.notFound().build();
    } catch (IllegalArgumentException e) {
        logger.error("Invalid input: {}", e.getMessage());
        return ResponseEntity.badRequest().build();
    } catch (Exception e) {
        logger.error("Error saving other cost: {}", e.getMessage(), e);
        return ResponseEntity.internalServerError().build();
    }
}

@PostMapping("/spare-cost-actual/save")
public ResponseEntity<BAL_WomTlSparecostactual> saveSpareCostActual(
        @RequestBody BAL_WomTlSparecostactual request) {
    logger.info("saveSpareCostActual called – woid={}, requestedby={}, sparesid={}",
            request.getWoid(), request.getRequestedby(), request.getSparesid());
    try {
        BAL_WomTlSparecostactual result = service.saveSpareCostActual(request);
        return ResponseEntity.ok(result);
    } catch (ResourceNotFoundException e) {
        logger.error("Spare cost actual record not found: {}", e.getMessage());
        return ResponseEntity.notFound().build();
    } catch (IllegalArgumentException e) {
        logger.error("Invalid input: {}", e.getMessage());
        return ResponseEntity.badRequest().build();
    } catch (Exception e) {
        logger.error("Error saving spare cost actual: {}", e.getMessage(), e);
        return ResponseEntity.internalServerError().build();
    }
}
@PostMapping("/service-cost-actual/save")
public ResponseEntity<BAL_WomTlServicecostactual> saveServiceCostActual(
        @RequestBody BAL_WomTlServicecostactual request) {
    logger.info("saveServiceCostActual called – woid={}, serviceid={}",
            request.getWoid(), request.getServiceid());
    try {
        BAL_WomTlServicecostactual result = service.saveServiceCostActual(request);
        return ResponseEntity.ok(result);
    } catch (ResourceNotFoundException e) {
        logger.error("Service cost actual record not found: {}", e.getMessage());
        return ResponseEntity.notFound().build();
    } catch (IllegalArgumentException e) {
        logger.error("Invalid input: {}", e.getMessage());
        return ResponseEntity.badRequest().build();
    } catch (Exception e) {
        logger.error("Error saving service cost actual: {}", e.getMessage(), e);
        return ResponseEntity.internalServerError().build();
    }
}
@PostMapping("/utility-cost-actual/save")
public ResponseEntity<BAL_WomTlUtilitycostactual> saveUtilityCostActual(
        @RequestBody BAL_WomTlUtilitycostactual request) {
    logger.info("saveUtilityCostActual called – wokeyid={}, requestedby={}, utilitymstid={}",
            request.getWokeyid(), request.getRequestedby(), request.getUtilitymstid());
    try {
        BAL_WomTlUtilitycostactual result = service.saveUtilityCostActual(request);
        return ResponseEntity.ok(result);
    } catch (ResourceNotFoundException e) {
        logger.error("Utility cost actual record not found: {}", e.getMessage());
        return ResponseEntity.notFound().build();
    } catch (IllegalArgumentException e) {
        logger.error("Invalid input: {}", e.getMessage());
        return ResponseEntity.badRequest().build();
    } catch (Exception e) {
        logger.error("Error saving utility cost actual: {}", e.getMessage(), e);
        return ResponseEntity.internalServerError().build();
    }
}
 @PostMapping("/other-cost-actual/save")
    public ResponseEntity<BAL_WomTlOthercostactual> saveOtherCostActual(@RequestBody BAL_WomTlOthercostactual request) {
        logger.info("saveOtherCostActual called – woid={}, requestedby={}, othercostmstid={}",
                request.getWoid(), request.getRequestedby(), request.getOthercostmstid());
        try {
            return ResponseEntity.ok(service.saveOtherCostActual(request));
        } catch (ResourceNotFoundException e) {
            logger.error(e.getMessage()); return ResponseEntity.notFound().build();
        } catch (IllegalArgumentException e) {
            logger.error(e.getMessage()); return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            logger.error("Error saving other cost actual: {}", e.getMessage(), e);
            return ResponseEntity.internalServerError().build();
        }
    }
    @DeleteMapping("/manpower-cost/delete")
public ResponseEntity<Void> deleteManpowerCost(
        @RequestParam String woid,
        @RequestParam String manpowerid,
        @RequestParam String skillid) {
    logger.info("deleteManpowerCost called – woid={}, manpowerid={}, skillid={}", woid, manpowerid, skillid);
    try {
        service.deleteManpowerCost(woid, manpowerid, skillid);
        return ResponseEntity.noContent().build();
    } catch (ResourceNotFoundException e) {
        logger.error("Manpower cost record not found: {}", e.getMessage());
        return ResponseEntity.notFound().build();
    } catch (IllegalArgumentException e) {
        logger.error("Invalid input: {}", e.getMessage());
        return ResponseEntity.badRequest().build();
    } catch (Exception e) {
        logger.error("Error deleting manpower cost: {}", e.getMessage(), e);
        return ResponseEntity.internalServerError().build();
    }
}
@DeleteMapping("/manpower-cost-actual/delete")
public ResponseEntity<Void> deleteManpowerCostActual(
        @RequestParam String maintwoid,
        @RequestParam String manpowerid,
        @RequestParam String skillid) {
    logger.info("deleteManpowerCostActual called – maintwoid={}, manpowerid={}, skillid={}",
            maintwoid, manpowerid, skillid);
    try {
        service.deleteManpowerCostActual(maintwoid, manpowerid, skillid);
        return ResponseEntity.noContent().build();
    } catch (ResourceNotFoundException e) {
        logger.error("Manpower cost actual record not found: {}", e.getMessage());
        return ResponseEntity.notFound().build();
    } catch (IllegalArgumentException e) {
        logger.error("Invalid input: {}", e.getMessage());
        return ResponseEntity.badRequest().build();
    } catch (Exception e) {
        logger.error("Error deleting manpower cost actual: {}", e.getMessage(), e);
        return ResponseEntity.internalServerError().build();
    }
}
@DeleteMapping("/spare-cost/delete")
public ResponseEntity<Void> deleteSpareCost(
        @RequestParam String woid,
        @RequestParam String requestedby,
        @RequestParam String sparesid) {
    logger.info("deleteSpareCost called – woid={}, requestedby={}, sparesid={}", woid, requestedby, sparesid);
    try {
        service.deleteSpareCost(woid, requestedby, sparesid);
        return ResponseEntity.noContent().build();
    } catch (ResourceNotFoundException e) {
        logger.error("Spare cost record not found: {}", e.getMessage());
        return ResponseEntity.notFound().build();
    } catch (IllegalArgumentException e) {
        logger.error("Invalid input: {}", e.getMessage());
        return ResponseEntity.badRequest().build();
    } catch (Exception e) {
        logger.error("Error deleting spare cost: {}", e.getMessage(), e);
        return ResponseEntity.internalServerError().build();
    }
}
@DeleteMapping("/spare-cost-actual/delete")
public ResponseEntity<Void> deleteSpareCostActual(
        @RequestParam String woid,
        @RequestParam String requestedby,
        @RequestParam String sparesid) {
    logger.info("deleteSpareCostActual called – woid={}, requestedby={}, sparesid={}", woid, requestedby, sparesid);
    try {
        service.deleteSpareCostActual(woid, requestedby, sparesid);
        return ResponseEntity.noContent().build();
    } catch (ResourceNotFoundException e) {
        logger.error("Spare cost actual record not found: {}", e.getMessage());
        return ResponseEntity.notFound().build();
    } catch (IllegalArgumentException e) {
        logger.error("Invalid input: {}", e.getMessage());
        return ResponseEntity.badRequest().build();
    } catch (Exception e) {
        logger.error("Error deleting spare cost actual: {}", e.getMessage(), e);
        return ResponseEntity.internalServerError().build();
    }
}
@DeleteMapping("/service-cost/delete")
public ResponseEntity<Void> deleteServiceCost(
        @RequestParam String woid,
        @RequestParam String serviceid) {
    logger.info("deleteServiceCost called – woid={}, serviceid={}", woid, serviceid);
    try {
        service.deleteServiceCost(woid, serviceid);
        return ResponseEntity.noContent().build();
    } catch (ResourceNotFoundException e) {
        logger.error("Service cost record not found: {}", e.getMessage());
        return ResponseEntity.notFound().build();
    } catch (IllegalArgumentException e) {
        logger.error("Invalid input: {}", e.getMessage());
        return ResponseEntity.badRequest().build();
    } catch (Exception e) {
        logger.error("Error deleting service cost: {}", e.getMessage(), e);
        return ResponseEntity.internalServerError().build();
    }
}

@DeleteMapping("/service-cost-actual/delete")
public ResponseEntity<Void> deleteServiceCostActual(
        @RequestParam String woid,
        @RequestParam String serviceid) {
    logger.info("deleteServiceCostActual called – woid={}, serviceid={}", woid, serviceid);
    try {
        service.deleteServiceCostActual(woid, serviceid);
        return ResponseEntity.noContent().build();
    } catch (ResourceNotFoundException e) {
        logger.error("Service cost actual record not found: {}", e.getMessage());
        return ResponseEntity.notFound().build();
    } catch (IllegalArgumentException e) {
        logger.error("Invalid input: {}", e.getMessage());
        return ResponseEntity.badRequest().build();
    } catch (Exception e) {
        logger.error("Error deleting service cost actual: {}", e.getMessage(), e);
        return ResponseEntity.internalServerError().build();
    }
}
@DeleteMapping("/utility-cost/delete")
public ResponseEntity<Void> deleteUtilityCost(
        @RequestParam String wokeyid,
        @RequestParam String requestedby,
        @RequestParam String utilitymstid) {
    logger.info("deleteUtilityCost called – wokeyid={}, requestedby={}, utilitymstid={}",
            wokeyid, requestedby, utilitymstid);
    try {
        service.deleteUtilityCost(wokeyid, requestedby, utilitymstid);
        return ResponseEntity.noContent().build();
    } catch (ResourceNotFoundException e) {
        logger.error("Utility cost record not found: {}", e.getMessage());
        return ResponseEntity.notFound().build();
    } catch (IllegalArgumentException e) {
        logger.error("Invalid input: {}", e.getMessage());
        return ResponseEntity.badRequest().build();
    } catch (Exception e) {
        logger.error("Error deleting utility cost: {}", e.getMessage(), e);
        return ResponseEntity.internalServerError().build();
    }
}
@DeleteMapping("/utility-cost-actual/delete")
public ResponseEntity<Void> deleteUtilityCostActual(
        @RequestParam String wokeyid,
        @RequestParam String requestedby,
        @RequestParam String utilitymstid) {
    logger.info("deleteUtilityCostActual called – wokeyid={}, requestedby={}, utilitymstid={}",
            wokeyid, requestedby, utilitymstid);
    try {
        service.deleteUtilityCostActual(wokeyid, requestedby, utilitymstid);
        return ResponseEntity.noContent().build();
    } catch (ResourceNotFoundException e) {
        logger.error("Utility cost actual record not found: {}", e.getMessage());
        return ResponseEntity.notFound().build();
    } catch (IllegalArgumentException e) {
        logger.error("Invalid input: {}", e.getMessage());
        return ResponseEntity.badRequest().build();
    } catch (Exception e) {
        logger.error("Error deleting utility cost actual: {}", e.getMessage(), e);
        return ResponseEntity.internalServerError().build();
    }
}

@DeleteMapping("/other-cost/delete")
public ResponseEntity<Void> deleteOtherCost(
        @RequestParam String woid,
        @RequestParam String requestedby,
        @RequestParam String othercostmstid) {
    logger.info("deleteOtherCost called – woid={}, requestedby={}, othercostmstid={}",
            woid, requestedby, othercostmstid);
    try {
        service.deleteOtherCost(woid, requestedby, othercostmstid);
        return ResponseEntity.noContent().build();
    } catch (ResourceNotFoundException e) {
        logger.error("Other cost record not found: {}", e.getMessage());
        return ResponseEntity.notFound().build();
    } catch (IllegalArgumentException e) {
        logger.error("Invalid input: {}", e.getMessage());
        return ResponseEntity.badRequest().build();
    } catch (Exception e) {
        logger.error("Error deleting other cost: {}", e.getMessage(), e);
        return ResponseEntity.internalServerError().build();
    }
}

@DeleteMapping("/other-cost-actual/delete")
public ResponseEntity<Void> deleteOtherCostActual(
        @RequestParam String woid,
        @RequestParam String requestedby,
        @RequestParam String othercostmstid) {
    logger.info("deleteOtherCostActual called – woid={}, requestedby={}, othercostmstid={}",
            woid, requestedby, othercostmstid);
    try {
        service.deleteOtherCostActual(woid, requestedby, othercostmstid);
        return ResponseEntity.noContent().build();
    } catch (ResourceNotFoundException e) {
        logger.error("Other cost actual record not found: {}", e.getMessage());
        return ResponseEntity.notFound().build();
    } catch (IllegalArgumentException e) {
        logger.error("Invalid input: {}", e.getMessage());
        return ResponseEntity.badRequest().build();
    } catch (Exception e) {
        logger.error("Error deleting other cost actual: {}", e.getMessage(), e);
        return ResponseEntity.internalServerError().build();
    }
}

    @GetMapping("/master/{keyid}")
    public ResponseEntity<BAL_BdmTlMst> getBdmMaster(@PathVariable String keyid) {
        try {
            return ResponseEntity.ok(service.getBdmMaster(keyid));
        } catch (ResourceNotFoundException e) {
            logger.error("BDM Master not found: {}", e.getMessage());
            return ResponseEntity.notFound().build();
        } catch (IllegalArgumentException e) {
            logger.error("Invalid input: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            logger.error("Error fetching BDM Master: {}", e.getMessage(), e);
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/detail/{bdmsKeyid}")
    public ResponseEntity<BAL_BdmTlDtl> getBdmDetail(@PathVariable String bdmsKeyid) {
        try {
            return ResponseEntity.ok(service.getBdmDetailByMasterKeyid(bdmsKeyid));
        } catch (ResourceNotFoundException e) {
            logger.error("BDM Detail not found: {}", e.getMessage());
            return ResponseEntity.notFound().build();
        } catch (IllegalArgumentException e) {
            logger.error("Invalid input: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            logger.error("Error fetching BDM Detail: {}", e.getMessage(), e);
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/workorder/{keyid}")
    public ResponseEntity<WomTlWomst> getWorkOrder(@PathVariable String keyid) {
        try {
            return ResponseEntity.ok(service.getWorkOrder(keyid));
        } catch (ResourceNotFoundException e) {
            logger.error("WOM Work Order not found: {}", e.getMessage());
            return ResponseEntity.notFound().build();
        } catch (IllegalArgumentException e) {
            logger.error("Invalid input: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            logger.error("Error fetching WOM Work Order: {}", e.getMessage(), e);
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping("/cmmsave")
    public ResponseEntity<WomTlCommunicationlog> saveCommTxt(
            @RequestBody WomTlCommunicationlog log) 
            {

        logger.info("saveCommTxt called – keyid={}", log.getKeyid());

        try {
            return service.saveCommTxt(log);

        } catch (IllegalArgumentException e) {
            logger.error("Invalid input: {}", e.getMessage());
            return ResponseEntity.badRequest().build();

        } catch (Exception e) {
            logger.error("Error saving Communication Log: {}", e.getMessage(), e);
            return ResponseEntity.internalServerError().build();
        }
    }

     @GetMapping("/cmmgettext/{bdId}")
    public ResponseEntity<List<Map<String, Object>>> getCommText(@PathVariable String bdId) {

        logger.info("getCommText called – bdId={}", bdId);

        try {
            return ResponseEntity.ok(service.getCommText(bdId));
        } catch (Exception e) {
            logger.error("Error fetching Communication Log text: {}", e.getMessage(), e);
            return ResponseEntity.internalServerError().build();
        }
    }
}
