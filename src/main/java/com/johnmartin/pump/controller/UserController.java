package com.johnmartin.pump.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.johnmartin.pump.constants.api.ApiConstants;
import com.johnmartin.pump.constants.api.ApiErrorMessages;
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

    private static final Class<UserController> clazz = UserController.class;

    @Autowired
    private UserService userService;

    @GetMapping(ApiConstants.Path.PROFILE)
    public ResponseEntity<Result<UserResponse>> getCurrentUser() {
        Optional<UserEntity> userOpt = userService.getAuthenticatedUser();
        if (userOpt.isEmpty()) {
            return ApiErrorUtils.createUnauthorizedErrorResponse(ApiErrorMessages.User.USER_IS_NOT_AUTHENTICATED);
        }

        UserEntity userEntity = userOpt.get();
        LoggerUtility.t(clazz, String.format("userEntity: [%s]", userEntity));
        return ResponseEntity.ok(Result.success(UserMapper.toResponse(userEntity)));
    }
}
