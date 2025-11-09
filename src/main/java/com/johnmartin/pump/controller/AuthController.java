package com.johnmartin.pump.controller;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.johnmartin.pump.constants.ApiConstants;
import com.johnmartin.pump.constants.ApiErrorMessages;
import com.johnmartin.pump.dto.request.LoginRequest;
import com.johnmartin.pump.dto.request.RegisterRequest;
import com.johnmartin.pump.dto.response.AuthResponse;
import com.johnmartin.pump.dto.response.UserResponse;
import com.johnmartin.pump.entities.UserEntity;
import com.johnmartin.pump.exception.UserNotFoundException;
import com.johnmartin.pump.security.JwtUtil;
import com.johnmartin.pump.service.UserService;
import com.johnmartin.pump.utilities.LoggerUtility;

import io.micrometer.common.util.StringUtils;

@RestController
@RequestMapping(ApiConstants.Path.API_AUTH)
public class AuthController {
    private static final String DEBUG_TAG = AuthController.class.getSimpleName();

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping(ApiConstants.Path.REGISTER)
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request) {
        AuthResponse authResponse = new AuthResponse();

        if (StringUtils.isBlank(request.getEmail()) || StringUtils.isBlank(request.getPassword())) {
            authResponse.setErrorMessage(ApiErrorMessages.User.EMAIL_AND_PASSWORD_ARE_REQUIRED);
            return ResponseEntity.badRequest().body(authResponse);
        }

        // Check if user exists
        if (ObjectUtils.isNotEmpty(userService.findByEmail(request.getEmail()))) {
            authResponse.setErrorMessage(ApiErrorMessages.User.USER_WITH_THIS_EMAIL_ALREADY_EXISTS);
            return ResponseEntity.status(HttpStatus.CONFLICT).body(authResponse);
        }

        // Create a User class and save to DB
        UserEntity userEntity = new UserEntity();
        userEntity.setFirstName(request.getFirstName());
        userEntity.setLastName(request.getLastName());
        userEntity.setEmail(request.getEmail());
        userEntity.setPhone(request.getPhone());
        userEntity.setRole(request.getRole());
        userEntity.setProfileImageUrl(request.getProfileImageUrl());
        userEntity.setPassword(passwordEncoder.encode(request.getPassword()));
        userService.save(userEntity);

        UserResponse userResponse = new UserResponse(userEntity.getFirstName(),
                                                     userEntity.getLastName(),
                                                     userEntity.getEmail(),
                                                     userEntity.getPhone(),
                                                     userEntity.getRole(),
                                                     userEntity.getProfileImageUrl());

        authResponse.setUserResponse(userResponse);
        return ResponseEntity.ok(authResponse);
    }

    @PostMapping(ApiConstants.Path.LOGIN)
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        UserEntity dbUserEntity = userService.findByEmail(request.getEmail());
        AuthResponse authResponse = new AuthResponse();

        if (dbUserEntity == null) {
            throw new UserNotFoundException("User with email " + request.getEmail() + " not found");
        }

        try {
            if (passwordEncoder.matches(request.getPassword(), dbUserEntity.getPassword())) {
                String token = jwtUtil.generateToken(dbUserEntity.getEmail());

                UserResponse userResponse = new UserResponse(dbUserEntity.getFirstName(),
                                                             dbUserEntity.getLastName(),
                                                             dbUserEntity.getEmail(),
                                                             dbUserEntity.getPhone(),
                                                             dbUserEntity.getRole(),
                                                             dbUserEntity.getProfileImageUrl());

                authResponse.setToken(token);
                authResponse.setUserResponse(userResponse);
                return ResponseEntity.ok(authResponse);
            }
        } catch (Exception e) {
            LoggerUtility.error(DEBUG_TAG, e.getMessage(), e);
        }

        authResponse.setErrorMessage(ApiErrorMessages.User.INVALID_CREDENTIALS);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(authResponse);
    }
}
