package com.example.myprogress.app.SpringSecurity;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

import com.example.myprogress.app.Entites.Token;
import com.example.myprogress.app.Exceptions.FieldIncorrectException;
import com.example.myprogress.app.Exceptions.PasswordIncorrect;
import com.example.myprogress.app.RedisService.TokenServices;
import com.fasterxml.jackson.core.JsonProcessingException;
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
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Service
@Getter
@Lazy
public class LogoutService implements LogoutHandler {

  private final TokenServices tokenRepository;

  public LogoutService(TokenServices tokenRepository) {
    this.tokenRepository = tokenRepository;
  }

  private String currentUser;

  @Override
  public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
    try {
      if (!ValidateToken(request)) {
        generateResponse("No se pudo deslogiar", response);
        return;
      }

      tokenRepository.deleteToken(currentUser); // Here I REVOKE The token to no longer access with that token
      
      SecurityContextHolder.clearContext(); // AND i clean the contextHolder
      generateResponse("Deslogiado con exito", response);


    } catch (JwtException | IllegalArgumentException e) {
      System.err.println("Invalid JWT token: " + e.getMessage());
      Map<String, String> body = new HashMap();
      body.put("error", e.getMessage());
      body.put("message", "Sorry your Token is invalid");
      body.put("error", e.getMessage());
      try {
        response.getWriter().write(new ObjectMapper().writeValueAsString(body));
      } catch (IOException e1) {
        throw new FieldIncorrectException("Error Toke invalido");
      }
      response.setStatus(HttpStatus.UNAUTHORIZED.value());
      response.setContentType(VariablesGeneral.CONTENT_TYPE);
    }
  }

  public boolean ValidateToken(HttpServletRequest request) {
    String header = request.getHeader(VariablesGeneral.AUTHORIZATION);
    if (header == null || !header.startsWith(VariablesGeneral.HEADER_TOKEN)) {
      return false;
    }

    String token = header.replace(VariablesGeneral.HEADER_TOKEN, "");

    Claims claims = Jwts.parser().verifyWith(VariablesGeneral.SECRET_KEY).build().parseSignedClaims(token)
        .getPayload();

    currentUser = (String) claims.get("sub");
    if (currentUser == null) {
      return false;
    }

    if (request.getRequestURI().equalsIgnoreCase("/RefreshToken")) {
     return true; 
    }

    Token currentToken = tokenRepository.getTokenByUser(currentUser);
    // Validate if the token is valid (not was revoked )
    if (currentToken != null) {
      if (currentToken.getToken().equals(token) ) {
        return true; // The token is valid
      }
    }
    return false;
  }


  public void generateResponse(String msj, HttpServletResponse response) {
    Map<String, Object> body = new HashMap<>();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        try {
          response.getWriter().write(new ObjectMapper().writeValueAsString(msj));
        } catch (JsonProcessingException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        } catch (IOException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }
        response.setStatus(200);
        response.setContentType(VariablesGeneral.CONTENT_TYPE); // i give the message in format JSON
  }
}
