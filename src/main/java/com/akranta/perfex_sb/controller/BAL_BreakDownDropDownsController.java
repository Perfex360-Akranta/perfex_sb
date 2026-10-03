package com.akranta.perfex_sb.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.akranta.perfex_sb.dto.Bal_AssemblyMstDto;
import com.akranta.perfex_sb.dto.Bal_SubAssemblyMstDto;
import com.akranta.perfex_sb.model.BAL_BdmTlCausemst;
import com.akranta.perfex_sb.model.BAL_BdmTlPhenomenamst;
import com.akranta.perfex_sb.model.BAL_GenTlAssemblymst;
import com.akranta.perfex_sb.model.BAL_GenTlSubAssemblymst;
import com.akranta.perfex_sb.service.BAL_BreakDownDropDownsService;


@RestController
@RequestMapping("/api/breakdown/dropdown")
public class BAL_BreakDownDropDownsController 
{
    @Autowired
    private BAL_BreakDownDropDownsService service;

    @PostMapping("/assembly")
    public ResponseEntity<BAL_GenTlAssemblymst> saveAssembly(@RequestBody Bal_AssemblyMstDto dto ) throws Exception
    {
        BAL_GenTlAssemblymst result = service.saveAssembly(dto);
        return ResponseEntity.ok(result);

    }

    @PutMapping("/assemblyupdate")
    public ResponseEntity<BAL_GenTlAssemblymst> updateAssembly(@RequestBody Bal_AssemblyMstDto dto) throws Exception
    {
        BAL_GenTlAssemblymst result = service.updateAssembly(dto);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/assemblyrecall/{keyid}")
    public ResponseEntity<BAL_GenTlAssemblymst> selectAssembly(@PathVariable String keyid) throws Exception
    {
        BAL_GenTlAssemblymst result = service.selectAssembly(keyid);
        return ResponseEntity.ok(result);
    }


    @PostMapping("/assembly/delete")
    public ResponseEntity<Void> deleteAssembly(@RequestBody Bal_AssemblyMstDto dto) throws Exception
    {
        service.deleteAssembly(dto.getDelemode(), dto.getAssemblymst().getKeyid());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/subassemblysave")
    public ResponseEntity<BAL_GenTlSubAssemblymst> saveSubAssembly(@RequestBody Bal_SubAssemblyMstDto dto) throws Exception
    {
        BAL_GenTlSubAssemblymst result = service.saveSubAssembly(dto);
        return ResponseEntity.ok(result);
    }

    @PutMapping("/subassemblyupdate")
    public ResponseEntity<BAL_GenTlSubAssemblymst> updateSubAssembly(@RequestBody Bal_SubAssemblyMstDto dto) throws Exception
    {
        BAL_GenTlSubAssemblymst result = service.updateSubAssembly(dto);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/subassemblyrecall/{keyid}")
    public ResponseEntity<Map<String, Object>> selectSubAssembly(@PathVariable String keyid) throws Exception
    {
        BAL_GenTlSubAssemblymst result = service.selectSubAssembly(keyid);
        String machineId = service.getMachineIdBySubAssembly(keyid);

        Map<String, Object> response = new HashMap<>();
        response.put("subAssemblymst", result);
        response.put("machineId", machineId);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/subassembly/delete")
    public ResponseEntity<?> deleteSubAssembly(
        @RequestBody Bal_SubAssemblyMstDto dto)
{
    try
    {
        if (dto == null ||
            dto.getSubAssemblymst() == null ||
            dto.getSubAssemblymst().getKeyid() == null)
        {
            return ResponseEntity.badRequest()
                .body("Subassembly keyid is required");
        }

        service.deleteSubAssembly(
            dto.getDelemode(),
            dto.getSubAssemblymst().getKeyid()
        );

        return ResponseEntity.ok(
            "Subassembly deleted/inactivated successfully"
        );
    }
    catch (IllegalArgumentException e)
    {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
    catch (Exception e)
    {
        return ResponseEntity.internalServerError()
            .body(e.getMessage());
    }
}

@PostMapping("/phenomenasave")
public ResponseEntity<BAL_BdmTlPhenomenamst> savePhenomena(@RequestBody BAL_BdmTlPhenomenamst phenomenamst) throws Exception
{
    BAL_BdmTlPhenomenamst result = service.savePhenomena(phenomenamst);
    return ResponseEntity.ok(result);
}

@PutMapping("/phenomenaupdate")
public ResponseEntity<BAL_BdmTlPhenomenamst> updatePhenomena(@RequestBody BAL_BdmTlPhenomenamst phenomenamst) throws Exception
{
    BAL_BdmTlPhenomenamst result = service.updatePhenomena(phenomenamst);
    return ResponseEntity.ok(result);
}

@GetMapping("/phenomenarecall/{keyid}")
public ResponseEntity<BAL_BdmTlPhenomenamst> selectPhenomena(@PathVariable String keyid) throws Exception
{
    BAL_BdmTlPhenomenamst result = service.selectPhenomena(keyid);
    return ResponseEntity.ok(result);
}

@PostMapping("/phenomena/delete")
public ResponseEntity<Void> deletePhenomena(@RequestBody BAL_BdmTlPhenomenamst phenomenamst) throws Exception
{
    service.deletePhenomena(phenomenamst.getKeyid());
    return ResponseEntity.ok().build();
}

@PostMapping("/causesave")
public ResponseEntity<BAL_BdmTlCausemst> saveCause(@RequestBody BAL_BdmTlCausemst causemst) throws Exception
{
    System.out.println(">>>>>>>> ENTERED CAUSE SAVE CONTROLLER");
    System.out.println("Cause name: " + causemst.getName());
    System.out.println("Phenomena ID: " + causemst.getPhenomenaid());
    
    BAL_BdmTlCausemst result = service.saveCause(causemst);
    return ResponseEntity.ok(result);
}

@PutMapping("/causeupdate")
public ResponseEntity<BAL_BdmTlCausemst> updateCause(@RequestBody BAL_BdmTlCausemst causemst) throws Exception
{
    System.out.println(">>>>>>>> ENTERED CAUSE UPDATE CONTROLLER");
    System.out.println("Cause name: " + causemst.getName());
    System.out.println("Phenomena ID: " + causemst.getPhenomenaid());
    BAL_BdmTlCausemst result = service.updateCause(causemst);
    return ResponseEntity.ok(result);
}

@GetMapping("/causerecall/{keyid}")
public ResponseEntity<BAL_BdmTlCausemst> selectCause(@PathVariable String keyid) throws Exception
{
    BAL_BdmTlCausemst result = service.selectCause(keyid);
    return ResponseEntity.ok(result);
}

@PostMapping("/cause/delete")
public ResponseEntity<Void> deleteCause(@RequestBody BAL_BdmTlCausemst causemst) throws Exception
{
    service.deleteCause(causemst.getKeyid());
    return ResponseEntity.ok().build();
}
    
}