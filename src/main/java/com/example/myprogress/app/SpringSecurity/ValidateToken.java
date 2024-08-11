package com.example.myprogress.app.SpringSecurity;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.stereotype.Component;

import com.example.myprogress.app.Entites.Token;
import com.example.myprogress.app.Exceptions.FieldIncorrectException;
import com.example.myprogress.app.RedisService.TokenServices;
import com.example.myprogress.app.RedisService.TokenServices;
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

    private final TokenServices tokenService;
    private String currentUserName;
    private final TokenServices tokenServices;

    public ValidateToken(AuthenticationManager authenticationManager, TokenServices tokenService, TokenServices tokenServices) {
        super(authenticationManager);
        this.tokenServices = tokenServices;
        this.tokenService = tokenService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        
                   // Skip token validation for certain paths
        
            
            try{
            validateToken(request, response, chain);
            // Create an Authentication token and set it in the SecurityContext
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    currentUserName, null, Collections.emptyList());
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);  
             chain.doFilter(request, response); // I sent the next level or the next filter
            } catch (Exception e) {
                Map<String, String> body = new HashMap();
                body.put("error", e.getMessage());
                body.put("message", "Sorry your Token is invalid");
                body.put("error", e.getMessage());
                response.getWriter().write(new ObjectMapper().writeValueAsString(body));
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
                response.setContentType(VariablesGeneral.CONTENT_TYPE);
            }
            }


    public void validateToken(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String header = request.getHeader(VariablesGeneral.AUTHORIZATION);
        if (header == null || !header.startsWith(VariablesGeneral.HEADER_TOKEN)) {
                chain.doFilter(request, response);
            return;
        }

        String token = header.replace(VariablesGeneral.HEADER_TOKEN, "");
            Claims claims = Jwts.parser().verifyWith(VariablesGeneral.SECRET_KEY).build().parseSignedClaims(token)
                    .getPayload(); // Verify if the token is correct with the Secret key,This part is very crucial
            // is where Define if I following with the process
            currentUserName = (String) claims.get("sub");

            if (currentUserName == null) {
                throw new FieldIncorrectException("Token revoked");
            }
         
            Token currentToken = tokenServices.getTokenByUser(currentUserName);
            // Validate if the token is valid (not was revoked)
            String requestURI = request.getRequestURI();

            if ( currentToken != null) {
                if (!shouldSkipValidation(requestURI) && !tokenServices.getTokenByUser(currentUserName).getToken().equals(token)) {
                    throw new JwtException("Token revoked");
                }
            }else{
                throw new FieldIncorrectException("Token revoked");
            }
}

private boolean shouldSkipValidation(String requestURI) {
    // List of endpoints to skip
    String[] pathsToSkip = {"/RefreshToken"}; // Adjust paths as needed
    for (String path : pathsToSkip) {
        if (requestURI.matches(path.replace("**", ".*"))) {
            return true;
        }
    }
    return false;
}

}
