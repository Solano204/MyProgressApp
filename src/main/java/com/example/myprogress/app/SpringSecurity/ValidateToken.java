package com.example.myprogress.app.SpringSecurity;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.lang.Arrays;
import io.jsonwebtoken.lang.Collections;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

// THIS CLASS IS TO VALIDATE IF THE TOKEN IS CORRECT
public class ValidateToken extends BasicAuthenticationFilter {

    public ValidateToken(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        String header = request.getHeader(VariablesGeneral.AUTHORIZATION);

        System.out.println("header " + header);

        if (header == null || !header.startsWith(VariablesGeneral.HEADER_TOKEN)) {
            chain.doFilter(request, response);
            return;
        }

        String token = header.replace(VariablesGeneral.HEADER_TOKEN, "");
        System.out.println("token " + token);

        try {
            Claims claims = Jwts.parser().verifyWith(VariablesGeneral.SECRET_KEY).build().parseSignedClaims(token)
                    .getPayload(); // Verify if the token is correct with the Secret key,This part is very crucial
                                   // is where Define if I following with the process


            String username = (String) claims.get("sub");

            if (username == null) {
                chain.doFilter(request, response);
                return;
            }

            // Create an Authentication token and set it in the SecurityContext
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    username, null, Collections.emptyList());
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        } catch (JwtException | IllegalArgumentException e) {
            System.err.println("Invalid JWT token: " + e.getMessage());
            Map<String, String> body = new HashMap();
            body.put("error", e.getMessage());
            body.put("message", "Sorry your Token is invalid");
            body.put("error", e.getMessage());
            response.getWriter().write(new ObjectMapper().writeValueAsString(body));
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setContentType(VariablesGeneral.CONTENT_TYPE);
        }
        chain.doFilter(request, response);
    }

}
