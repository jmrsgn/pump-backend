package com.johnmartin.pump.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import com.johnmartin.pump.dto.AuthUser;

@Service
public class AuthService {

    @Autowired
    private RestClient authWebClient;

    public AuthUser validate(String authorizationHeader) {
        return authWebClient.post()
                            .uri("/api/v1/internal/auth/validate")
                            .header(HttpHeaders.AUTHORIZATION, authorizationHeader)
                            .retrieve()
                            .body(AuthUser.class);
    }
}
