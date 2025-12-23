package com.johnmartin.pump.security.filter;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.johnmartin.pump.constants.SecurityConstants;
import com.johnmartin.pump.constants.api.ApiConstants;
import com.johnmartin.pump.constants.api.ApiErrorMessages;
import com.johnmartin.pump.dto.AuthUser;
import com.johnmartin.pump.dto.response.ApiErrorResponse;
import com.johnmartin.pump.dto.response.Result;
import com.johnmartin.pump.service.AuthService;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class AuthenticationFilter implements Filter {

    @Autowired
    private AuthService authService;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
                                                                                              ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String path = httpRequest.getRequestURI();

        if (path.startsWith(ApiConstants.Path.HEALTH)) {
            chain.doFilter(request, response);
            return;
        }

        String authHeader = httpRequest.getHeader(HttpHeaders.AUTHORIZATION);
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {

            httpResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
            httpResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);

            ApiErrorResponse error = new ApiErrorResponse(HttpStatus.UNAUTHORIZED.value(),
                                                          ApiConstants.Error.UNAUTHORIZED,
                                                          ApiErrorMessages.User.USER_IS_NOT_AUTHENTICATED);

            httpResponse.getWriter().write(objectMapper.writeValueAsString(Result.failure(error)));
            return;
        }

        try {
            String requestId = (String) request.getAttribute(SecurityConstants.REQUEST_ID);
            AuthUser user = authService.validate(authHeader, requestId);
            httpRequest.setAttribute(SecurityConstants.AUTH_USER, user);
            chain.doFilter(request, response);
        } catch (Exception e) {
            httpResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
            httpResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);

            ApiErrorResponse error = new ApiErrorResponse(HttpStatus.UNAUTHORIZED.value(),
                                                          ApiConstants.Error.UNAUTHORIZED,
                                                          ApiErrorMessages.User.INVALID_TOKEN);

            httpResponse.getWriter().write(objectMapper.writeValueAsString(Result.failure(error)));
        }
    }
}
