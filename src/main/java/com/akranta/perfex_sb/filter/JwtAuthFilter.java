package com.akranta.perfex_sb.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.util.Collections;

import com.akranta.perfex_sb.security.PerfexAuthenticationDetails;
import com.akranta.perfex_sb.util.*;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            try {
                var claims = JwtUtil.validateToken(token);
                String email = claims.get("email", String.class);

                String employeeKeyId = clean(
                        claims.get(
                                "id",
                                String.class));

                if (employeeKeyId.isBlank()) {
                    throw new IllegalArgumentException(
                            "Employee key ID is missing from the JWT.");
                }

                String principalName = !email.isBlank()
                        ? email
                        : employeeKeyId;

                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        principalName,
                        null,
                        Collections.emptyList());

                authentication.setDetails(
                        new PerfexAuthenticationDetails(
                                request,
                                employeeKeyId));

                SecurityContextHolder
                        .getContext()
                        .setAuthentication(authentication);

                // UsernamePasswordAuthenticationToken auth =new
                // UsernamePasswordAuthenticationToken(email, null, Collections.emptyList());

                // SecurityContextHolder.getContext().setAuthentication(auth);

            } 
            catch (Exception e) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("Invalid or expired token");
                return;
            }
        }

        filterChain.doFilter(request, response);
    }

    private static String clean(String value) {

        return value == null
                ? ""
                : value.trim();
    }
    
}
