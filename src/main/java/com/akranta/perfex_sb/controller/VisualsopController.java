package com.akranta.perfex_sb.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.akranta.perfex_sb.model.Visualsopdtl;
import com.akranta.perfex_sb.model.Visualsopmst;
import com.akranta.perfex_sb.service.VisualsopService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/api/visualsop")
public class VisualsopController {

    @Autowired
    private VisualsopService service;
    
    //create or update for mst table

    @PostMapping("/createorupdatemst")
    public ResponseEntity<Visualsopmst> create(@RequestBody Visualsopmst visualsopmst) {
        Visualsopmst result = service.createorupdateVisualsopmst(visualsopmst);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    // create or update for detail table

    @PostMapping("/createorupdatedl")
    public ResponseEntity<Visualsopdtl> create(@RequestBody Visualsopdtl visualsopdtl) {
        Visualsopdtl result = service.createorupdateVisualsopdtl(visualsopdtl, visualsopdtl.getVsom_keyid());
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    //fetching detail table by keyid

    @GetMapping("/getbykeyid/{keyid}")
    public Visualsopdtl getByKeyid(@PathVariable String keyid) {
        return service.getByKeyid(keyid);

    }

    // @DeleteMapping("/deletebyid/{keyid}")
    // public ResponseEntity<String> deleteById(
    // @PathVariable String keyid) {

    // service.deleteById(keyid);
    // return ResponseEntity.ok("Deleted successfully");

    // }

    // @DeleteMapping("/deletebyimage/{keyid}")
    // public ResponseEntity<String> deletebyimage(@PathVariable String keyId)
    // {
    // service.deletebyImage(keyId);
    // return ResponseEntity.ok("Deleted sucessfully");
    // }


    //deleting the detail table including image

    @DeleteMapping("/deletebyrefkeyid/{keyid}")
    public ResponseEntity<String> deleteByKeyId(@PathVariable("keyid") String keyid) {

        service.deleteBydetailKeyId(keyid);
        return ResponseEntity.ok("Deleted successfully");
    }

    //feteching the master table by keyid

    @GetMapping("/findByMstKeyid/{keyid}")
    public Visualsopmst getbykeyid(@PathVariable("keyid") String keyid)
    {
        return service.getByKeyidMst(keyid);
    }

    //fetchong detail table records
    @GetMapping("/findBydetkeyid/{keyid}")
    public List<Map<String,Object>> getalldetails(@PathVariable("keyid") String keyid)
    {
        return service.getdetails(keyid);
    }

    //deleting two tables by using masterkeyid
    @DeleteMapping("/delete/{keyId}")
    public ResponseEntity<String> delete(@PathVariable String keyId) throws Exception {

        Visualsopmst visualsopmst = new Visualsopmst();
        visualsopmst.setKeyid(keyId);

        service.delete(visualsopmst);

        return ResponseEntity.ok("Deleted Successfully");
    }

    
}
