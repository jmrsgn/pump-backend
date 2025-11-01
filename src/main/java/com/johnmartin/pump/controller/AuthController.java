package com.johnmartin.pump.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.johnmartin.pump.constants.ApiConstants;
import com.johnmartin.pump.constants.ApiMessages;
import com.johnmartin.pump.dto.request.LoginRequest;
import com.johnmartin.pump.dto.request.RegisterRequest;
import com.johnmartin.pump.dto.response.AuthResponse;
import com.johnmartin.pump.dto.response.UserResponse;
import com.johnmartin.pump.entities.User;
import com.johnmartin.pump.repository.UserRepository;
import com.johnmartin.pump.security.JwtUtil;

import io.micrometer.common.util.StringUtils;

@RestController
@RequestMapping(ApiConstants.Path.API_BASE)
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping(ApiConstants.Path.REGISTER)
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request) {
        AuthResponse authResponse = new AuthResponse();

        if (StringUtils.isBlank(request.getEmail()) || StringUtils.isBlank(request.getPassword())) {
            authResponse.setErrorMessage(ApiMessages.User.EMAIL_AND_PASSWORD_ARE_REQUIRED);
            return ResponseEntity.badRequest().body(authResponse);
        }

        User user = new User();
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setPhone(request.getPhone());
        user.setRole(request.getRole());
        user.setProfileImageUrl(request.getProfileImageUrl());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        userRepository.save(user);

        UserResponse userResponse = new UserResponse(null,
                                                     user.getFirstName(),
                                                     user.getLastName(),
                                                     user.getEmail(),
                                                     user.getPhone(),
                                                     user.getRole(),
                                                     user.getProfileImageUrl());

        authResponse.setUserResponse(userResponse);
        return ResponseEntity.ok(authResponse);
    }

    @PostMapping(ApiConstants.Path.LOGIN)
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        User dbUser = userRepository.findByEmail(request.getEmail());
        AuthResponse authResponse = new AuthResponse();

        if (dbUser != null && passwordEncoder.matches(request.getPassword(), dbUser.getPassword())) {
            String token = jwtUtil.generateToken(dbUser.getEmail());

            UserResponse userResponse = new UserResponse(dbUser.getId(),
                                                         dbUser.getFirstName(),
                                                         dbUser.getLastName(),
                                                         dbUser.getEmail(),
                                                         dbUser.getPhone(),
                                                         dbUser.getRole(),
                                                         dbUser.getProfileImageUrl());

            authResponse.setToken(token);
            authResponse.setUserResponse(userResponse);
            return ResponseEntity.ok(authResponse);
        }

        authResponse.setErrorMessage(ApiMessages.User.INVALID_CREDENTIALS);
        return ResponseEntity.status(ApiConstants.Status.UNAUTHORIZED).body(authResponse);
    }
}
