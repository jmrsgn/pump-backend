package com.johnmartin.pump.dto.request;

import com.johnmartin.pump.constants.api.ApiErrorMessages;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class LoginRequest {

    @NotBlank(message = ApiErrorMessages.User.EMAIL_IS_REQUIRED)
    @Email(message = ApiErrorMessages.User.EMAIL_MUST_BE_VALID)
    private String email;

    @NotBlank(message = ApiErrorMessages.User.PASSWORD_IS_REQUIRED)
    private String password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
