package com.example.myprogress.app.SpringSecurity;

import java.io.IOException;

import org.springframework.web.filter.GenericFilterBean;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class RegisterSecurity extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        // Check if the request is a POST to the /User endpoint
        if ("POST".equalsIgnoreCase(request.getMethod()) && "/User".equalsIgnoreCase(request.getRequestURI())) {
            // Custom logic for registration process
            System.out.println("RegisterSecurity Filter triggered for /User endpoint");

            // You can add custom validation, logging, etc., here
            // For example, check request parameters or headers
            String username = request.getParameter("username");
            if (username == null || username.isEmpty()) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Username is required");
                return;
            }
        }

        // Continue with the filter chain
        chain.doFilter(request, response);
    }
}
