package com.johnmartin.pump.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;

import com.johnmartin.pump.constants.SecurityConstants;
import com.johnmartin.pump.constants.UIConstants;
import com.johnmartin.pump.constants.api.ApiConstants;
import com.johnmartin.pump.constants.api.ApiErrorMessages;
import com.johnmartin.pump.dto.AuthUser;
import com.johnmartin.pump.exception.UnauthorizedException;

@Service
public class AuthService {

    @Autowired
    private RestClient authWebClient;

    @Retryable(retryFor = Exception.class, maxAttempts = ApiConstants.RETRIES_COUNT, backoff = @Backoff(delay = UIConstants.DELAY_2000))
    public AuthUser validate(String authorizationHeader, String requestId) {
        try {
            return authWebClient.post()
                                .uri(ApiConstants.PumpAuthService.API_VALIDATE)
                                .header(HttpHeaders.AUTHORIZATION, authorizationHeader)
                                .header(SecurityConstants.REQUEST_ID, requestId)
                                .retrieve()
                                .body(AuthUser.class);
        } catch (HttpClientErrorException.Unauthorized ex) {
            throw new UnauthorizedException(ApiErrorMessages.User.INVALID_TOKEN);
        }
    }
}
