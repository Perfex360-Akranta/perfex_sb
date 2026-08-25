package com.akranta.perfex_sb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class PerfexSbApplication  extends SpringBootServletInitializer  {
  
	@Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(PerfexSbApplication.class);
    }

	public static void main(String[] args) {
		SpringApplication.run(PerfexSbApplication.class, args);
	}

}
