package com.johnmartin.pump.security.filter;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.johnmartin.pump.constants.SecurityConstants;
import com.johnmartin.pump.constants.api.ApiConstants;
import com.johnmartin.pump.constants.api.ApiErrorMessages;
import com.johnmartin.pump.exception.UnauthorizedException;
import com.johnmartin.pump.security.AuthContext;
import com.johnmartin.pump.service.AuthService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class AuthContextFilter extends BaseFilter {

    private final AuthService authService;

    public AuthContextFilter(AuthService authService, ObjectMapper objectMapper) {
        super(objectMapper);
        this.authService = authService;
    }

    @Override
    protected void doFilterAction(HttpServletRequest request, HttpServletResponse response) {
        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        String requestId = (String) request.getAttribute(SecurityConstants.REQUEST_ID);

        if (StringUtils.isBlank(authHeader)) {
            throw new UnauthorizedException(ApiErrorMessages.MISSING_AUTH_HEADER);
        }

        // Auth details will be handled in AuthService
        AuthContext.set(authService.validate(authHeader, requestId));
    }

    @Override
    protected void afterRequest() {
        AuthContext.clear();
    }

    @Override
    protected boolean shouldSkip(HttpServletRequest request) {
        return request.getRequestURI().startsWith(ApiConstants.Path.HEALTH);
    }
}
