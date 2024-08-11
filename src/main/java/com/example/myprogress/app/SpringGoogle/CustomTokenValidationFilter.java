package com.example.myprogress.app.SpringGoogle;


import java.io.IOException;
import java.util.Map;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

// This class will make a some validation after the user was authenticated
public class CustomTokenValidationFilter extends BasicAuthenticationFilter {

    private final OAuth2AuthorizedClientService clientService; // this save internally save the OAuth2AuthorizedClient
                                                               // that is a class that provides information about the
                                                               // authorized client

    public CustomTokenValidationFilter(AuthenticationManager authenticationManager,
            OAuth2AuthorizedClientService clientService) {
        super(authenticationManager); // `super(null)` as the default constructor since we are not using the default
        // authentication manager
        this.clientService = clientService;
    }

    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        OAuth2AuthenticationToken authentication = (OAuth2AuthenticationToken) SecurityContextHolder.getContext()
                .getAuthentication(); // He

        OAuth2AuthorizedClient client = clientService.loadAuthorizedClient(
                authentication.getAuthorizedClientRegistrationId(),
                authentication.getName()); //

        String accessToken = client.getAccessToken().getTokenValue(); // Here I get the access token
                                                                      // token

        Map<String, Object> userInfo = authentication.getPrincipal().getAttributes(); // Here I get the user information

        // Log or process user information as needed
        System.out.println("User Info: " + userInfo + "token" + accessToken);

        chain.doFilter(request, response);
    }
}