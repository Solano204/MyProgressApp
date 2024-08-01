package com.example.myprogress.app.SpringSecurity;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;

import com.example.myprogress.app.Entites.InfoRegister;
import com.example.myprogress.app.Entites.appUser;
import com.example.myprogress.app.Entites.infoLogged;
import com.example.myprogress.app.Exceptions.UnsuccessfulLoginException;
import com.example.myprogress.app.LoginService.LoginGeneral;
import com.example.myprogress.app.RegisterService.RegisterGeneral;
import com.example.myprogress.app.updateInformationService.caloriesIntakeService;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

// This filter will execute only when the request is a POST to the /User endpoint to login the user
public class ValidateLogin extends OncePerRequestFilter {

    private final BuildToken buildToken;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final LoginGeneral loginGeneral;
    private final PasswordEncoder passwordEncoder;

    public ValidateLogin(caloriesIntakeService caloriesIntakeService,
            BuildToken buildToken, LoginGeneral loginGeneral, PasswordEncoder passwordEncoder) {
        this.buildToken = buildToken;
        this.loginGeneral = loginGeneral;
        this.passwordEncoder = passwordEncoder;

    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain Chain)
            throws ServletException, IOException {
        System.out.println("LOGIN FILTER");
        if ("POST".equalsIgnoreCase(request.getMethod()) 
            && request.getServletPath().equalsIgnoreCase("/login/User")) {
            // Wrap the request to cache the request body
            ContentCachingRequestWrapper wrappedRequest = new ContentCachingRequestWrapper(request);
            byte[] requestBody = wrappedRequest.getContentAsByteArray();
            if (requestBody.length > 0) {
                appUser user = objectMapper.readValue(requestBody, appUser.class);
                user.setInfoLogged(new infoLogged());
                user.setRegisterInformation(new InfoRegister());
                user.setPassWord(passwordEncoder.encode(user.getPassWord())); // Encode the password
                if (!loginGeneral.LoginUser(user)) {
                    throw new UnsuccessfulLoginException("The user couldn't be logined"); // throw an exception
                }
            } // Here I generate the final response
        }
        Chain.doFilter(request, response);
    }

}
