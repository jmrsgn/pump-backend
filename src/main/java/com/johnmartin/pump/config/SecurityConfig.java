package com.johnmartin.pump.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.johnmartin.pump.constants.ApiConstants;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            // Disable CSRF with lambda
            .csrf(AbstractHttpConfigurer::disable)

            // Authorization rules
            .authorizeHttpRequests(authorize -> authorize.requestMatchers("/api/auth/**")
                                                         .permitAll()
                                                         .anyRequest()
                                                         .authenticated())
            .httpBasic(basic -> basic.realmName(ApiConstants.APP_NAME));

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
