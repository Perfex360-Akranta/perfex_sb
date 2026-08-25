package com.akranta.perfex_sb.controller;


import com.akranta.perfex_sb.dto.UserLoginDetailsDto;
import com.akranta.perfex_sb.model.AdmTlUsermst;
import com.akranta.perfex_sb.model.GenTlEmployeemst;
import com.akranta.perfex_sb.repository.AdmTlUsermstRepository;
import com.akranta.perfex_sb.repository.GenTlEmployeeMstRepository;
import com.akranta.perfex_sb.util.JwtUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;


import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class LoginController {

    @Autowired
    private AdmTlUsermstRepository userRepository;



    @Autowired
    private GenTlEmployeeMstRepository employeeRepository;

     @GetMapping("/loginFailCount/{loginId}")
    public ResponseEntity<?> getLoginFailCount(@PathVariable String loginId) {
        AdmTlUsermst user =userRepository.findByLoginid(loginId.toUpperCase());
        return ResponseEntity.ok(user != null ? user.getLoginatempt() : "" );
    }

    @GetMapping("/isExpired/{loginId}")
    public String getIsExpired(@PathVariable String loginId) {
        AdmTlUsermst user =userRepository.findByLoginid(loginId.toUpperCase());
        return user.getIsExpired();
    }

     @GetMapping("/userByLogin/{loginId}")
    public AdmTlUsermst getUserByLoginId(@PathVariable String loginId) {
        AdmTlUsermst user =userRepository.findByLoginid(loginId.toUpperCase());
        return user;
    }

    @GetMapping("/loginUserDetails/{userKeyId}")
    public UserLoginDetailsDto getLoginUserDetails(@PathVariable String userKeyId) {
        List<UserLoginDetailsDto> user =userRepository.findUserDetails(userKeyId);
        return user.get(0);
    }

    @GetMapping("/location/{user_code}")
    public String getEmployeeLocation(@PathVariable String user_code) {
        GenTlEmployeemst employee = employeeRepository.findByCode(user_code);
        return employee.getLocation();
    }

    @GetMapping("/login/{userKeyId}")
    public ResponseEntity<?> createState(@PathVariable String userKeyId) {

        AdmTlUsermst user = userRepository.findByKeyid(userKeyId);
        if (user == null) return ResponseEntity.status(401).body(Map.of("error", "Invalid credentials"));
        GenTlEmployeemst employee = employeeRepository.findByKeyid(user.getCcno());
         Map<String, Object> claims = Map.of(
                    "email", employee.getEmail(),
                    "code", employee.getCode(),
                    "name", employee.getName(),
                    "id" , employee.getKeyid(),
                    "location", employee.getLocation()
            );

        String token = JwtUtil.generateToken(claims, employee.getEmail());
        return  ResponseEntity.ok(token);
    }
}
