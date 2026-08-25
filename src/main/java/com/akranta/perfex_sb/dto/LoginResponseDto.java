package com.akranta.perfex_sb.dto;
import java.util.List;
import java.util.Map;

public class LoginResponseDto {


    
    private String token;

    private Map<String, Object> user;
    
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Map<String, Object> getUser() {
        return user;
    }

    public void setUser(Map<String, Object> user) {
        this.user = user;
    }


} 
