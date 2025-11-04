package com.johnmartin.pump.controller;

import java.util.Optional;

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
import com.johnmartin.pump.entities.User;
import com.johnmartin.pump.repository.UserRepository;

@RestController
@RequestMapping(ApiConstants.Path.API_USER)
public class UserController {

    @Autowired
    private UserRepository userRepository;

    // Utility method to fetch currently authenticated user
    private Optional<User> getAuthenticatedUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated()) {
            return Optional.empty();
        }
        return Optional.ofNullable(userRepository.findByEmail(auth.getName()));
    }

    @GetMapping(ApiConstants.Path.PROFILE)
    public ResponseEntity<?> getCurrentUser() {
        Optional<User> userOpt = getAuthenticatedUser();
        if (userOpt.isEmpty()) {
            ApiErrorResponse error = new ApiErrorResponse(HttpStatus.UNAUTHORIZED.value(),
                                                          AppStrings.UNAUTHORIZED,
                                                          AppStrings.USER_IS_NOT_AUTHENTICATED);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
        }

        User user = userOpt.get();
        UserResponse userResponse = new UserResponse(user.getFirstName(),
                                                     user.getLastName(),
                                                     user.getEmail(),
                                                     user.getPhone(),
                                                     user.getRole(),
                                                     user.getProfileImageUrl());

        return ResponseEntity.ok(userResponse);
    }

    // @GetMapping(ApiConstants.Params.ID)
    // public ResponseEntity<?> getUserById(@PathVariable String id) {
    // Optional<User> currentUserOpt = getAuthenticatedUser();
    // if (currentUserOpt.isEmpty()) {
    // ApiErrorResponse error = new ApiErrorResponse(HttpStatus.UNAUTHORIZED.value(),
    // AppStrings.UNAUTHORIZED,
    // AppStrings.USER_IS_NOT_AUTHENTICATED);
    // return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
    // }
    //
    // // Parse UUID
    // UUID userId;
    // try {
    // userId = UUID.fromString(id);
    // } catch (Exception e) {
    // ApiErrorResponse error = new ApiErrorResponse(HttpStatus.BAD_REQUEST.value(),
    // AppStrings.INVALID_ARGUMENT,
    // AppStrings.INVALID_USER_ID_FORMAT);
    // return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    // }
    //
    // // Fetch requested user
    // Optional<User> requestedUserOpt = userRepository.findByEmail(userId);
    // if (requestedUserOpt.isEmpty()) {
    // ApiErrorResponse error = new ApiErrorResponse(HttpStatus.NOT_FOUND.value(),
    // AppStrings.USER_NOT_FOUND,
    // "User with ID " + id + " not found");
    // return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    // }
    //
    // User requestedUser = requestedUserOpt.get();
    // UserResponse userResponse = new UserResponse(requestedUser.getId(),
    // requestedUser.getFirstName(),
    // requestedUser.getLastName(),
    // requestedUser.getEmail(),
    // requestedUser.getPhone(),
    // requestedUser.getRole(),
    // requestedUser.getProfileImageUrl());
    //
    // return ResponseEntity.ok(userResponse);
    // }
}
