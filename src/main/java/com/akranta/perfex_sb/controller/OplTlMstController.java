package com.akranta.perfex_sb.controller;

import com.akranta.perfex_sb.dto.OplSaveRequest;
import com.akranta.perfex_sb.dto.OplStudentRecallDto;
import com.akranta.perfex_sb.model.OplTlMst;
import com.akranta.perfex_sb.service.OplTlMstService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/opl")
public class OplTlMstController {

    private final OplTlMstService service;

    public OplTlMstController(OplTlMstService service) {
        this.service = service;
    }

    // -------------------------------------------------
    // NORMAL CRUD (OPTIONAL if you still want them)
    // -------------------------------------------------

    @PostMapping
    public ResponseEntity<OplTlMst> create(@RequestBody OplTlMst opl) {
        OplTlMst saved = service.create(opl);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

      // GET /api/opl/OPB2500090
    @GetMapping("/{keyid}")
    public OplTlMst getByKeyid(@PathVariable String keyid) {
        return service.getByKeyid(keyid);
    }

    // @GetMapping("/{keyid}")
    // public ResponseEntity<OplTlMst> getOne(@PathVariable String keyid) {
    //     return ResponseEntity.ok(service.getByKeyid(keyid));
    // }

    @GetMapping
    public ResponseEntity<List<OplTlMst>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @PutMapping("/{keyid}")
    public ResponseEntity<OplTlMst> update(@PathVariable String keyid, @RequestBody OplTlMst opl) {
        return ResponseEntity.ok(service.update(keyid, opl));
    }

    @DeleteMapping("/{keyid}")
    public ResponseEntity<Void> delete(@PathVariable String keyid) {
        service.delete(keyid);
        return ResponseEntity.noContent().build();
    }

    // -------------------------------------------------
    // YOUR 3-PARAMETER SAVE ENDPOINT
    // -------------------------------------------------

    @PostMapping("/save")
    public ResponseEntity<OplTlMst> save(@RequestBody OplSaveRequest req) {

       
        if (req == null || req.getOplTlMst() == null) {
            return ResponseEntity.badRequest().build();
        }

        OplTlMst saved = service.save(
                req.getOplTlMst(),
                req.getBdmTlYycountermeasurelink(),
                req.getGenTlDocupdates()
        );

        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

      @PostMapping("/recallStudents")
    public List<Map<String,Object>> recallStudents(@RequestBody OplStudentRecallDto dto) {
        return service.recallStudents(dto.getOplId(), dto.getCellId(), dto.getOplKeyid());
    }
    
}
