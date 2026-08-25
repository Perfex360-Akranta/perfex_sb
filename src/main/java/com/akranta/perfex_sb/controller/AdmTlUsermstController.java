package com.akranta.perfex_sb.controller;


import com.akranta.perfex_sb.dto.UpdateUserLoginTimeRequest;
import com.akranta.perfex_sb.dto.UserLoginDetailsDto;
import com.akranta.perfex_sb.model.AdmTlUsermst;
import com.akranta.perfex_sb.model.GenTlEmployeemst;
import com.akranta.perfex_sb.repository.AdmTlUsermstRepository;
import com.akranta.perfex_sb.repository.GenTlEmployeeMstRepository;
import com.akranta.perfex_sb.util.JwtUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api/users")
public class AdmTlUsermstController {

    private static final Logger logger = LoggerFactory.getLogger(AdmTlUsermstController.class);

    @Autowired
    private AdmTlUsermstRepository repository;

    @Autowired
    private GenTlEmployeeMstRepository employeeMstRepositoryrepository;

    @GetMapping
    public List<AdmTlUsermst> getAllUsers() {
        return repository.findAll();
    }

    @GetMapping("/loginFailCount/{loginId}")
    public Integer getLoginFailCount(@PathVariable String loginId) {
        AdmTlUsermst user =repository.findByLoginid(loginId);
        return user.getLoginatempt();
    }

    @GetMapping("/isExpired/{loginId}")
    public String getIsExpired(@PathVariable String loginId) {
        AdmTlUsermst user =repository.findByLoginid(loginId);
        return user.getIsExpired();
    }

     @GetMapping("/userByLogin/{loginId}")
    public AdmTlUsermst getUserByLoginId(@PathVariable String loginId) {
        AdmTlUsermst user =repository.findByLoginid(loginId);
        return user;
    }

    @GetMapping("/loginUserDetails/{userKeyId}")
    public UserLoginDetailsDto getLoginUserDetails(@PathVariable String userKeyId) {
        List<UserLoginDetailsDto> user =repository.findUserDetails(userKeyId);
        return user.get(0);
    }

    @GetMapping("/location/{user_code}")
    public String getEmployeeLocation(@PathVariable String user_code) {
        GenTlEmployeemst employee = employeeMstRepositoryrepository.findByCode(user_code);
        return employee.getLocation();
    }

    @GetMapping("/login/{userKeyId}")
    public ResponseEntity<?> createState(@PathVariable String userKeyId) {

        AdmTlUsermst user = repository.findByKeyid(userKeyId);
        if (user == null) return ResponseEntity.status(401).body(Map.of("error", "Invalid credentials"));
        GenTlEmployeemst employee = employeeMstRepositoryrepository.findByKeyid(user.getCcno());
         Map<String, Object> claims = Map.of(
                    "email", employee.getEmail(),
                    "code", employee.getCode(),
                    "name", employee.getName(),
                    "id" , employee.getKeyid(),
                    "location", employee.getLocation()
            );

            String token = JwtUtil.generateToken(claims, employee.getEmail());

//      return  ResponseEntity.ok(Map.of(
//     "response", "Success",
//     "message", "Logged In Successfully",
//     "user" , claims,
//     "token", token
// ));
  return  ResponseEntity.ok(token);
    }


    @PutMapping("/updateUserLoginTime")
public ResponseEntity<?> updateUserLoginTime(@RequestBody UpdateUserLoginTimeRequest  user ) {
    logger.info("Updating UserLoginTime: KeyId={}, lastlogindate={}",
        user.getKeyid(),
        user.getLastlogindate());
    AdmTlUsermst entity = repository.findByKeyid(user.getKeyid());

    entity.setLastlogindate(user.getLastlogindate());

    AdmTlUsermst updateEntity = repository.save(entity);
    logger.info("Successfully updated abnormality with Key ID: {}", updateEntity.getKeyid());

    return ResponseEntity.status(HttpStatus.OK).body(updateEntity);
}



}
