package com.johnmartin.pump.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.johnmartin.pump.constants.ApiConstants;
import com.johnmartin.pump.constants.ApiMessages;
import com.johnmartin.pump.model.User;
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
    public ResponseEntity<?> register(@RequestBody User user) {
        if (StringUtils.isBlank(user.getEmail()) || StringUtils.isBlank(user.getPassword())) {
            return ResponseEntity.badRequest().body(ApiMessages.User.EMAIL_AND_PASSWORD_ARE_REQUIRED);
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return ResponseEntity.ok(ApiMessages.User.USER_REGISTERED_SUCCESSFULLY);
    }

    @PostMapping(ApiConstants.Path.LOGIN)
    public ResponseEntity<?> login(@RequestBody User user) {
        Optional<User> dbUser = userRepository.findByUsername(user.getUsername());

        if (dbUser.isPresent() && passwordEncoder.matches(user.getPassword(), dbUser.get().getPassword())) {
            String token = jwtUtil.generateToken(dbUser.get().getEmail());
            Map<String, String> response = new HashMap<>();
            response.put("token", token);
            return ResponseEntity.ok(response);
        }

        return ResponseEntity.status(ApiConstants.Status.UNAUTHORIZED).body(ApiMessages.User.INVALID_CREDENTIALS);
    }
}
