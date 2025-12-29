package com.johnmartin.pump.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.johnmartin.pump.constants.api.ApiConstants;
import com.johnmartin.pump.security.filter.AuthContextFilter;
import com.johnmartin.pump.security.filter.CorrelationIdFilter;
import com.johnmartin.pump.security.filter.RequestLoggingFilter;
import com.johnmartin.pump.service.AuthService;

@Configuration
public class SecurityConfig {

    @Bean
    public CorrelationIdFilter correlationIdFilter(ObjectMapper objectMapper) {
        return new CorrelationIdFilter(objectMapper);
    }

    @Bean
    public RequestLoggingFilter requestLoggingFilter() {
        return new RequestLoggingFilter();
    }

    @Bean
    public AuthContextFilter authContextFilter(AuthService authService, ObjectMapper objectMapper) {
        return new AuthContextFilter(authService, objectMapper);
    }

    /**
     * A filter that exposes the health endpoint for status checking
     * 
     * @param http
     *            - HttpSecurity
     * @return SecurityFilterChain
     * @throws Exception-
     *             Exception
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http,
                                                   AuthContextFilter authContextFilter,
                                                   CorrelationIdFilter correlationIdFilter,
                                                   RequestLoggingFilter requestLoggingFilter) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
            .addFilterBefore(correlationIdFilter, UsernamePasswordAuthenticationFilter.class)
            .addFilterAfter(authContextFilter, CorrelationIdFilter.class)
            .addFilterAfter(requestLoggingFilter, AuthContextFilter.class)
            .authorizeHttpRequests(authorize -> authorize.requestMatchers(ApiConstants.Path.HEALTH,
                                                                          ApiConstants.Path.HEALTH + "/**")
                                                         .permitAll()
                                                         .anyRequest()
                                                         .permitAll());
        return http.build();
    }
}
