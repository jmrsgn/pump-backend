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
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest registerRequest) {
        AuthResponse authResponse = new AuthResponse();

        if (StringUtils.isBlank(registerRequest.getEmail()) || StringUtils.isBlank(registerRequest.getPassword())) {
            authResponse.setAuthMessage(ApiMessages.User.EMAIL_AND_PASSWORD_ARE_REQUIRED);
            return ResponseEntity.badRequest().body(authResponse);
        }

        authResponse.setAuthMessage(ApiMessages.User.USER_REGISTERED_SUCCESSFULLY);

        User user = new User();
        user.setFirstName(registerRequest.getFirstName());
        user.setLastName(registerRequest.getLastName());
        user.setUsername(registerRequest.getUsername());
        user.setEmail(registerRequest.getEmail());
        user.setPhone(registerRequest.getPhone());
        user.setRole(registerRequest.getRole());
        user.setProfileImageUrl(registerRequest.getProfileImageUrl());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        userRepository.save(user);
        return ResponseEntity.ok(authResponse);
    }

    @PostMapping(ApiConstants.Path.LOGIN)
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest loginRequest) {
        User dbUser = userRepository.findByUsername(loginRequest.getUsername());
        AuthResponse authResponse = new AuthResponse();

        if (dbUser != null && passwordEncoder.matches(loginRequest.getPassword(), dbUser.getPassword())) {
            String token = jwtUtil.generateToken(dbUser.getEmail());

            UserResponse userResponse = new UserResponse(dbUser.getId(),
                                                         dbUser.getFirstName(),
                                                         dbUser.getLastName(),
                                                         dbUser.getUsername(),
                                                         dbUser.getEmail(),
                                                         dbUser.getPhone(),
                                                         dbUser.getRole(),
                                                         dbUser.getProfileImageUrl());

            authResponse.setToken(token);
            authResponse.setUserResponse(userResponse);
            return ResponseEntity.ok(authResponse);
        }

        authResponse.setAuthMessage(ApiMessages.User.INVALID_CREDENTIALS);
        return ResponseEntity.status(ApiConstants.Status.UNAUTHORIZED).body(authResponse);
    }
}
