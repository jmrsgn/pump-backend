package com.johnmartin.pump.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

import com.johnmartin.pump.constants.api.ApiConstants;

@Configuration
public class SecurityConfig {

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
            .authorizeHttpRequests(authorize -> authorize.requestMatchers(ApiConstants.Path.HEALTH)
                                                         .permitAll()
                                                         .anyRequest()
                                                         .permitAll());

        return http.build();
    }
}
