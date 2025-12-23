package com.johnmartin.pump.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import com.johnmartin.pump.constants.SecurityConstants;
import com.johnmartin.pump.constants.api.ApiConstants;
import com.johnmartin.pump.dto.AuthUser;

@Service
public class AuthService {

    @Autowired
    private RestClient authWebClient;

    public AuthUser validate(String authorizationHeader, String requestId) {
        return authWebClient.post()
                            .uri(ApiConstants.PumpAuthService.API_VALIDATE)
                            .header(HttpHeaders.AUTHORIZATION, authorizationHeader)
                            .header(SecurityConstants.REQUEST_ID, requestId)
                            .retrieve()
                            .body(AuthUser.class);
    }
}
