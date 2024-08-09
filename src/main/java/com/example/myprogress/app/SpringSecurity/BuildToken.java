package com.example.myprogress.app.SpringSecurity;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.example.myprogress.app.Entites.appUser;
import com.example.myprogress.app.Exceptions.FieldIncorrectException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.IOException;
import jakarta.servlet.http.HttpServletResponse;

@Component

public class BuildToken {

    private String accessToken;
    private String refreshToken;
    

    public String generateToken(String user, long expiritionTime) {
        return Jwts.builder().subject(user)
                .expiration(new Date(System.currentTimeMillis() + expiritionTime))
                .issuedAt(new Date())
                .signWith(VariablesGeneral.SECRET_KEY)
                .compact(); // Here I generate the token final to assign to user
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

}
