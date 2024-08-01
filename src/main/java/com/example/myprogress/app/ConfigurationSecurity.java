package com.example.myprogress.app;

import java.util.Arrays;

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
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import com.example.myprogress.app.GeneralServices.MessagesFinal;
import com.example.myprogress.app.LoginService.LoginGeneral;
import com.example.myprogress.app.RegisterService.RegisterGeneral;
import com.example.myprogress.app.Repositories.AppUserRepository;
import com.example.myprogress.app.SpringSecurity.AuthenticationStart;
import com.example.myprogress.app.SpringSecurity.BuildToken;
import com.example.myprogress.app.SpringSecurity.RegisterSecurity;
import com.example.myprogress.app.SpringSecurity.ValidateToken;
import com.example.myprogress.app.updateInformationService.caloriesIntakeService;

@Configuration
@EnableMethodSecurity(prePostEnabled = true)
public class ConfigurationSecurity {

    // Here I configure the authentication
    private final AuthenticationConfiguration authenticationConfiguration;
    private final BuildToken buildToken;
    private final caloriesIntakeService caloriesIntakeService;
    private final LoginGeneral loginGeneral;
    private final RegisterGeneral registerGeneral;
    private final AppUserRepository repósitory;
    private final MessagesFinal messagesFinal;

    public ConfigurationSecurity(AuthenticationConfiguration authenticationConfiguration, BuildToken buildToken,
            caloriesIntakeService caloriesIntakeService, LoginGeneral loginGeneral, RegisterGeneral registerGeneral,
            AppUserRepository repósitory, MessagesFinal messagesFinal) {
        this.authenticationConfiguration = authenticationConfiguration;
        this.buildToken = buildToken;
        this.messagesFinal = messagesFinal;
        this.caloriesIntakeService = caloriesIntakeService;
        this.registerGeneral = registerGeneral;
        this.loginGeneral = loginGeneral;
        this.repósitory = repósitory;
    }

    @Bean
    AuthenticationManager authenticationManager() throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    PasswordEncoder encryptPasswordUser() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.authorizeHttpRequests(authz -> authz
                .requestMatchers(HttpMethod.POST, "/login/User").permitAll()
                .requestMatchers(HttpMethod.POST, "/register/User").permitAll()
                .anyRequest().authenticated())
                .addFilterBefore(
                        new RegisterSecurity(registerGeneral, caloriesIntakeService, buildToken, encryptPasswordUser()),
                        UsernamePasswordAuthenticationFilter.class) // Add before an existing filter
                .addFilter(new AuthenticationStart(authenticationManager(), buildToken, repósitory, loginGeneral, messagesFinal))
                .addFilter(new ValidateToken(authenticationManager())) // Add
                                                                                                              // before
                                                                                                              // an
                                                                                                              // existing
                                                                                                              // filter
                .csrf(config -> config.disable())
                .cors(cors -> cors.configurationSource(corsConfigurationSource())) // Configure CORS
                .sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
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
