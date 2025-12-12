package com.johnmartin.pump.service.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.johnmartin.pump.constants.api.ApiErrorMessages;
import com.johnmartin.pump.entities.UserEntity;
import com.johnmartin.pump.exception.UnauthorizedException;
import com.johnmartin.pump.service.UserService;

@Controller
public class BaseService {

    @Autowired
    private UserService userService;

    protected UserEntity getAuthenticatedUser() {
        return userService.getAuthenticatedUser()
                          .orElseThrow(() -> new UnauthorizedException(ApiErrorMessages.User.USER_IS_NOT_AUTHENTICATED));
    }
}
