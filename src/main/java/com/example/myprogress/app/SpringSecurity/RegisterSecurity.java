package com.example.myprogress.app.SpringSecurity;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.autoconfigure.web.WebProperties.Resources.Chain;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.filter.GenericFilterBean;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;

import com.example.myprogress.app.Entites.CaloriesIntake;
import com.example.myprogress.app.Entites.appUser;
import com.example.myprogress.app.Entites.infoLogged;
import com.example.myprogress.app.Exceptions.UnsuccessfulRegisterException;
import com.example.myprogress.app.RegisterService.RegisterGeneral;
import com.example.myprogress.app.updateInformationService.caloriesIntakeService;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

//This filter will execute only when the request is a POST to the /User endpoint to register A new user
public class RegisterSecurity extends OncePerRequestFilter {

    private final RegisterGeneral registerGeneral;
    private final caloriesIntakeService caloriesIntakeService;
    private final BuildToken buildToken;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final PasswordEncoder passwordEncoder;

    public RegisterSecurity(RegisterGeneral registerGeneral, caloriesIntakeService caloriesIntakeService,
            BuildToken buildToken, PasswordEncoder passwordEncoder) {
        this.registerGeneral = registerGeneral;
        this.buildToken = buildToken;
        this.caloriesIntakeService = caloriesIntakeService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        System.out.println("REGISTER FILTER");
        // Check if the request is a POST to the /User endpoint
        int maxBufferSize = 2048;
        if ("POST".equalsIgnoreCase(request.getMethod()) 
            && request.getServletPath().equalsIgnoreCase("/register/User")) {
                    ContentCachingRequestWrapper wrappedRequest = new ContentCachingRequestWrapper(request, maxBufferSize);
                // Read the input stream to cache the request body
                wrappedRequest.getReader(); // or wrappedRequest.getReader()
                
                // Now get the cached content
                byte[] requestBody = wrappedRequest.getContentAsByteArray();

            if (requestBody.length > 0) {
                System.out.println("REGISTER FILTER BODY");
                // Deserialize the request body into the appUser object
                appUser user = objectMapper.readValue(requestBody, appUser.class);
                user.setPassWord(passwordEncoder.encode(user.getPassWord())); // Encode the password
                user.setInfoLogged(new infoLogged());
                if (!registerGeneral.RegisterUser(user)) {
                    throw new UnsuccessfulRegisterException("The user couldn't be registered"); // throw an exception
                }
                CaloriesIntake newUser = new CaloriesIntake();
                newUser.setId(user.getUser());
                caloriesIntakeService.saveUser(newUser);

            }
            System.out.println("REGISTER FILTER FINAL");

        }
        chain.doFilter(request, response);

    }

}