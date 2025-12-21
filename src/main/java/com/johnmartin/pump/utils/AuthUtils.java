package com.johnmartin.pump.utils;

import com.johnmartin.pump.constants.SecurityConstants;
import com.johnmartin.pump.constants.api.ApiErrorMessages;
import com.johnmartin.pump.dto.AuthUser;
import com.johnmartin.pump.exception.UnauthorizedException;
import com.johnmartin.pump.utilities.LoggerUtility;

import jakarta.servlet.http.HttpServletRequest;

public final class AuthUtils {
    private static final String DEBUG_TAG = AuthUtils.class.getSimpleName();

    /**
     * Get authenticated user from boundary
     * 
     * @param request
     * @return
     */
    public static AuthUser requireAuthUser(HttpServletRequest request) {
        LoggerUtility.d(DEBUG_TAG, "Execute method: [requireAuthUser]");
        AuthUser user = (AuthUser) request.getAttribute(SecurityConstants.AUTH_USER);
        if (user == null) {
            throw new UnauthorizedException(ApiErrorMessages.User.USER_IS_NOT_AUTHENTICATED);
        }
        return user;
    }
}
