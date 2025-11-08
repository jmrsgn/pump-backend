package com.johnmartin.pump.controller;

import java.util.Optional;

import com.johnmartin.pump.entities.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.johnmartin.pump.constants.ApiConstants;
import com.johnmartin.pump.constants.AppStrings;
import com.johnmartin.pump.dto.response.ApiErrorResponse;
import com.johnmartin.pump.dto.response.UserResponse;
import com.johnmartin.pump.service.UserService;

@RestController
@RequestMapping(ApiConstants.Path.API_USER)
public class UserController {

    @Autowired
    private UserService userService;

    // Utility method to fetch currently authenticated user
    private Optional<UserEntity> getAuthenticatedUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated()) {
            return Optional.empty();
        }
        return Optional.ofNullable(userService.findByEmail(auth.getName()));
    }

    @GetMapping(ApiConstants.Path.PROFILE)
    public ResponseEntity<?> getCurrentUser() {
        Optional<UserEntity> userOpt = getAuthenticatedUser();
        if (userOpt.isEmpty()) {
            ApiErrorResponse error = new ApiErrorResponse(HttpStatus.UNAUTHORIZED.value(),
                                                          AppStrings.UNAUTHORIZED,
                                                          AppStrings.USER_IS_NOT_AUTHENTICATED);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
        }

        UserEntity userEntity = userOpt.get();
        UserResponse userResponse = new UserResponse(userEntity.getFirstName(),
                                                     userEntity.getLastName(),
                                                     userEntity.getEmail(),
                                                     userEntity.getPhone(),
                                                     userEntity.getRole(),
                                                     userEntity.getProfileImageUrl());

        return ResponseEntity.ok(userResponse);
    }
}
