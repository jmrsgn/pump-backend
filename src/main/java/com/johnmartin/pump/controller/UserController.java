package com.johnmartin.pump.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.johnmartin.pump.constants.ApiConstants;
import com.johnmartin.pump.dto.response.UserResponse;
import com.johnmartin.pump.entities.User;
import com.johnmartin.pump.repository.UserRepository;

@RestController
@RequestMapping(ApiConstants.Path.API_USER)
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping(ApiConstants.Path.PROFILE)
    public ResponseEntity<UserResponse> getProfile() {
        // Get the authenticated user (from SecurityContext set by JwtAuthenticationFilter)
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null || !auth.isAuthenticated()) {
            return ResponseEntity.status(ApiConstants.Status.UNAUTHORIZED).build();
        }

        User user = userRepository.findByEmail(auth.getName());
        if (user == null) {
            return ResponseEntity.status(ApiConstants.Status.UNAUTHORIZED).build();
        }

        UserResponse userResponse = new UserResponse(user.getId(),
                                                     user.getFirstName(),
                                                     user.getLastName(),
                                                     user.getEmail(),
                                                     user.getPhone(),
                                                     user.getRole(),
                                                     user.getProfileImageUrl());
        return ResponseEntity.ok(userResponse);
    }

    @GetMapping(ApiConstants.Params.EMAIL)
    public ResponseEntity<UserResponse> getUserByEmail(@PathVariable String email) {
        // TODO: make this param ID
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String currentUserEmail = auth.getName();

        User currentUser = userRepository.findByEmail(currentUserEmail);
        User requestedUser = userRepository.findByEmail(email);

        if (currentUser == null || requestedUser == null) {
            return ResponseEntity.notFound().build();
        }

        UserResponse userResponse = new UserResponse(requestedUser.getId(),
                                                     requestedUser.getFirstName(),
                                                     requestedUser.getLastName(),
                                                     requestedUser.getEmail(),
                                                     requestedUser.getPhone(),
                                                     requestedUser.getRole(),
                                                     requestedUser.getProfileImageUrl());
        return ResponseEntity.ok(userResponse);
    }

}
