package com.akranta.perfex_sb.controller;

import com.akranta.perfex_sb.dto.EmployeeDto;
import com.akranta.perfex_sb.dto.RecallEmployeeDto;
// import com.example.perfix_demo.dto.IdTextDto;
import com.akranta.perfex_sb.model.GenTlEmployeemst;
import com.akranta.perfex_sb.repository.GenTlEmployeeMstRepository;
import com.akranta.perfex_sb.service.EmployeeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/employees")
public class GenTlEmployeeMstController {

    // @Autowired
    // private GenTlEmployeeMstRepository repository;

    // // GET all employees
    // @GetMapping
    // public List<GenTlEmployeemst> getAllEmployees() {
    // return repository.findAll();
    // }

    // // GET employee by ID
    // @GetMapping("/{id}")
    // public ResponseEntity<GenTlEmployeemst> getEmployeeById(@PathVariable String
    // id) {
    // Optional<GenTlEmployeemst> employee = repository.findById(id);
    // return employee.map(ResponseEntity::ok)
    // .orElse(ResponseEntity.notFound().build());
    // }

    // @GetMapping("/location/{user_code}")
    // public String getEmployeeLocation(@PathVariable String user_code) {
    // GenTlEmployeemst employee = repository.findByCode(user_code);
    // return employee.getLocation();
    // }

    // // @GetMapping("/commonFilter")
    // // public List<IdTextDto> getSuggestedByDropdown(@RequestParam List<String>
    // ids) {
    // // return repository.getSuggestedByDropdown(ids);
    // // }

    // // POST create employee
    // @PostMapping
    // public GenTlEmployeemst createEmployee(@RequestBody GenTlEmployeemst
    // employee) {
    // return repository.save(employee);
    // }

    // // PUT update employee
    // @PutMapping("/{id}")
    // public ResponseEntity<GenTlEmployeemst> updateEmployee(
    // @PathVariable String id,
    // @RequestBody GenTlEmployeemst updatedEmployee) {

    // return repository.findById(id).map(existing -> {
    // updatedEmployee.setKeyid(id);
    // return ResponseEntity.ok(repository.save(updatedEmployee));
    // }).orElse(ResponseEntity.notFound().build());
    // }

    // // DELETE employee
    // @DeleteMapping("/{id}")
    // public ResponseEntity<Void> deleteEmployee(@PathVariable String id) {
    // if (repository.existsById(id)) {
    // repository.deleteById(id);
    // return ResponseEntity.noContent().build();
    // }
    // return ResponseEntity.notFound().build();
    // }

    @Autowired
    private EmployeeService service;

    @PostMapping("/save")
    public ResponseEntity<EmployeeDto> saveEmployee(@RequestBody EmployeeDto dto) throws Exception {
        EmployeeDto result = service.saveEmployee(dto);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/recall")
    public ResponseEntity<RecallEmployeeDto> findMstDtlById(@RequestParam("keyId") String keyid) throws Exception {
        RecallEmployeeDto result = service.findMstDtlById(keyid);
        return ResponseEntity.ok(result);
    }
}
