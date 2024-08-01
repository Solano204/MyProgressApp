package com.example.myprogress.app.SpringSecurity;


import javax.crypto.SecretKey;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;


public class VariablesGeneral {

    public static final SecretKey SECRET_KEY = Jwts.SIG.HS256.key().build(); // The key is binary
    public static final String HEADER_TOKEN = "Bearer ";
    public static final String AUTHORIZATION = "Authorization";
    public static final String CONTENT_TYPE = "application/json";
  
}
