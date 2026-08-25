package com.akranta.perfex_sb.controller;


import com.akranta.perfex_sb.dto.LoginHeaderContextDto;
import com.akranta.perfex_sb.dto.LoginRequestDto;
import com.akranta.perfex_sb.dto.LoginResponseDto;
import com.akranta.perfex_sb.dto.UserLoginDetailsDto;
import com.akranta.perfex_sb.model.AdmTlUsermst;
import com.akranta.perfex_sb.model.GenTlEmployeemst;
import com.akranta.perfex_sb.repository.AdmTlUsermstRepository;
import com.akranta.perfex_sb.repository.GenTlEmployeeMstRepository;
import com.akranta.perfex_sb.service.LoginContextService;
import com.akranta.perfex_sb.util.JwtUtil;
import com.akranta.perfex_sb.util.PasswordEncryptionUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;
// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;

import java.nio.file.attribute.UserPrincipal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class LoginController {

    @Autowired
    private AdmTlUsermstRepository userRepository;



    @Autowired
    private GenTlEmployeeMstRepository employeeRepository;

    @Autowired
    private LoginContextService loginContextService;

    @PostMapping("/login")
public ResponseEntity<?> login(@RequestBody LoginRequestDto request) {


    AdmTlUsermst user =userRepository.findByLoginid(request.getUsername().toUpperCase());
    

    if (user == null) {
        return ResponseEntity.status(401)
                .body(Map.of(
                        "success", false,
                        "message", "Invalid username "));
    }

    // User inactive
    if (!"Y".equalsIgnoreCase(user.getIsactive())) {
        return ResponseEntity.status(401)
                .body(Map.of(
                        "success", false,
                        "message", "User is inactive"));
    }

    // Password expired
    if ("Y".equalsIgnoreCase(user.getIsExpired())) {
        return ResponseEntity.status(401)
                .body(Map.of(
                        "success", false,
                        "message", "Password expired"));
    }

    // Account locked
    if (user.getLoginatempt() >= 3) {
        return ResponseEntity.status(401)
                .body(Map.of(
                        "success", false,
                        "message", "Account locked"));
    }

     String encryptedPassword =
            PasswordEncryptionUtil.encryptPassword(
                    request.getPassword(),
                    user.getUserpin()      
            );

    if (!encryptedPassword.equals(user.getPassword())) {
         user.setLoginatempt(user.getLoginatempt() + 1);
        userRepository.save(user);

        return ResponseEntity.status(401)
                .body(Map.of(
                        "success", false,
                        "message", "Invalid  password",
                        "loginAttempts", user.getLoginatempt()));
    
    }

    // Login successful
    user.setLoginatempt(0);
    userRepository.save(user);

    GenTlEmployeemst employee =
            employeeRepository.findByKeyid(user.getCcno());

    Map<String, Object> claims = Map.of(
            "userId", user.getKeyid(),
            "loginId", user.getLoginid(),
            "employeeId", employee.getKeyid(),
            "employeeCode", employee.getCode(),
            "employeeName", employee.getName(),
            "email", employee.getEmail(),
            "location", employee.getLocation()
    );

    String token = JwtUtil.generateToken(claims, user.getLoginid());

    //List<UserLoginDetailsDto> userDetails =userRepository.findUserDetails(user.getKeyid());

    LoginResponseDto response = new LoginResponseDto();
    response.setToken(token);
    response.setUser(claims);

    return ResponseEntity.ok(response);
}

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

    @GetMapping("/login-header-context/{userKeyId}")
    public ResponseEntity<LoginHeaderContextDto> getLoginHeaderContext(
            @PathVariable String userKeyId) {

        LoginHeaderContextDto context = loginContextService.getLoginHeaderContext(userKeyId);

        return ResponseEntity.ok(context);
    }
}
