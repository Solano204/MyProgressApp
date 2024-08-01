package com.example.myprogress.app;


import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import com.example.myprogress.app.SpringSecurity.AuthenticationStart;
import com.example.myprogress.app.SpringSecurity.RegisterSecurity;
import com.example.myprogress.app.SpringSecurity.ValidateToken;


@Configuration
@EnableMethodSecurity(prePostEnabled = true)
public class ConfigurationSecurity {

    // Here I configure the authentication
    @Autowired
    private AuthenticationConfiguration authenticationConfiguration;

    @Bean
    AuthenticationManager authenticationManager() throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    PasswordEncoder encryptPasswordUser() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    SecurityFilterChain seciroFilterChain(HttpSecurity http) throws Exception {
        return http.authorizeHttpRequests((authz) -> authz
                .requestMatchers(HttpMethod.GET, "/api/users").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/users/register").permitAll() 
                .anyRequest().authenticated()) 
                .addFilter(new AuthenticationStart(authenticationManager())) // Here I add the authentication process generation of the token
                .addFilter(new ValidateToken(authenticationManager()))
                .addFilter(new RegisterSecurity())
                .csrf(config -> config.disable())
                .cors(cors -> cors.configurationSource(corsConfigurationSource())) // Hability the config to accept one
                                                                                   // frontend in my backend
                .sessionManagement(magnament -> magnament.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) 
                .build();
    }







    // C:\Maven\apache-maven-3.8.6\bin).
    // In this method I ALLOW PERMISSION TO FRONTEND TO CONNECT WIYTH MY BACKEND AND
    // HE CAN DO SOME ACTION(GET,POST,DELETE,PUT)
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOriginPatterns(Arrays.asList("*")); // // Allow all origins
        config.setAllowedMethods(Arrays.asList("GET", "POST", "DELETE", "PUT")); // Here i specify the type of the
                                                                                 // allowed to query
        config.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type"));// This specifies which HTTP headers
                                                                                 // are allowed in the request. Here, it
                                                                                 // allows Authorization and
                                                                                 // Content-Type headers. This is
                                                                                 // important for allowing JWT tokens to
                                                                                 // be sent in the Authorization header.
        config.setAllowCredentials(true);// This indicates whether the browser should include any credentials (such as
                                         // cookies, authorization headers, or TLS client certificates) with the
                                         // requests to your API.
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config); // // Apply to all URLs
        return source;
    }

    @Bean
    FilterRegistrationBean<CorsFilter> corsFilter() {
        FilterRegistrationBean<CorsFilter> corsBean = new FilterRegistrationBean<>(
                new CorsFilter(corsConfigurationSource()));
        corsBean.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return corsBean;
    }

}
