package com.johnmartin.pump.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.johnmartin.pump.constants.ApiConstants;
import com.johnmartin.pump.constants.ApiErrorMessages;
import com.johnmartin.pump.dto.response.Result;
import com.johnmartin.pump.dto.response.UserResponse;
import com.johnmartin.pump.entities.UserEntity;
import com.johnmartin.pump.mapper.UserMapper;
import com.johnmartin.pump.service.UserService;
import com.johnmartin.pump.utilities.LoggerUtility;
import com.johnmartin.pump.utils.ApiErrorUtils;

@RestController
@RequestMapping(ApiConstants.Path.API_USER)
public class UserController {

    private static final String DEBUG_TAG = UserController.class.getSimpleName();

    @Autowired
    private UserService userService;

    // Utility method to fetch currently authenticated user
    private Optional<UserEntity> getAuthenticatedUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated()) {
            return Optional.empty();
        }
        return userService.findByEmail(auth.getName());
    }

    @GetMapping(ApiConstants.Path.PROFILE)
    public ResponseEntity<Result<UserResponse>> getCurrentUser() {
        Optional<UserEntity> userOpt = getAuthenticatedUser();
        if (userOpt.isEmpty()) {
            return ApiErrorUtils.createUnauthorizedErrorResponse(ApiErrorMessages.User.USER_IS_NOT_AUTHENTICATED);
        }

        UserEntity userEntity = userOpt.get();
        LoggerUtility.v(DEBUG_TAG, String.format("userEntity: [%s]", userEntity));
        return ResponseEntity.ok(Result.success(UserMapper.toResponse(userEntity)));
    }
}
