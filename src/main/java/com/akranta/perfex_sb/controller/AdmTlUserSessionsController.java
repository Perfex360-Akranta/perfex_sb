package com.akranta.perfex_sb.controller;

// import com.akranta.perfex_sb.model.AbnTlAbnormality;
// import com.example.perfix_demo.dto.IdTextDto;
import com.akranta.perfex_sb.model.AdmTlUsersessions;
import com.akranta.perfex_sb.repository.AdmTlUserSessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
// import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
// import java.time.LocalDateTime;
import java.util.List;
// import java.util.Optional;

@RestController
@RequestMapping("/api/sessions")
public class AdmTlUserSessionsController {

    @Autowired
    private AdmTlUserSessionRepository repository;

    // GET all employees
    @GetMapping
    public List<AdmTlUsersessions> getAllEmployees() {
        return repository.findAll();
    }

    // GET employee by ID
    // @GetMapping("/{id}")
    // public ResponseEntity<AdmTlUsersessions> getEmployeeById(@PathVariable String id) {
    //     Optional<AdmTlUsersessions> employee = repository.findById(id);
    //     return employee.map(ResponseEntity::ok)
    //                    .orElse(ResponseEntity.notFound().build());
    // }

    @GetMapping("/max/{userId}")
    public Integer getMaxSessionNo(@PathVariable String userId) {
        int max = repository.findMaxSessionNo(userId,LocalDate.now());
        return max;
    }

     @PostMapping
    public ResponseEntity<AdmTlUsersessions> create(@RequestBody AdmTlUsersessions admTlUsersessions) {
        try {
            // Save the entity
            AdmTlUsersessions savedEntity = repository.save(admTlUsersessions);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedEntity);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .header("Error-Message", e.getMessage())
                    .build();
        }
    }

}
