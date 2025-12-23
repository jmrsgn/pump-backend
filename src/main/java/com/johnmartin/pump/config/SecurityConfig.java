package com.johnmartin.pump.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.johnmartin.pump.constants.api.ApiConstants;
import com.johnmartin.pump.security.filter.CorrelationIdFilter;
import com.johnmartin.pump.security.filter.RequestLoggingFilter;

@Configuration
public class SecurityConfig {

    @Bean
    public CorrelationIdFilter correlationIdFilter() {
        return new CorrelationIdFilter();
    }

    @Bean
    public RequestLoggingFilter requestLoggingFilter() {
        return new RequestLoggingFilter();
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
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
            .addFilterBefore(correlationIdFilter(), UsernamePasswordAuthenticationFilter.class)
            .addFilterAfter(requestLoggingFilter(), CorrelationIdFilter.class)
            .authorizeHttpRequests(authorize -> authorize.requestMatchers(ApiConstants.Path.HEALTH)
                                                         .permitAll()
                                                         .anyRequest()
                                                         .permitAll());
        return http.build();
    }
}
