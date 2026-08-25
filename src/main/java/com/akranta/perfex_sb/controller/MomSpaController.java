package com.akranta.perfex_sb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/mom")
public class MomSpaController {

    // Catch all /mom/* paths and serve the React shell
    // Spring Boot will find index.html in resources/static/
    @GetMapping(value = { "", "/", "/create", "/view", "/edit" })
    public String momApp() {
        return "forward:/index.html";
    }
}