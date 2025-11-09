package com.johnmartin.pump.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.johnmartin.pump.dto.response.Result;
import com.johnmartin.pump.utils.ApiErrorUtils;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Result<Void>> handleUserNotFound(UserNotFoundException ex) {
        return ApiErrorUtils.createNotFoundErrorResponse(ex.getMessage());
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<Result<Void>> handleInvalidCredentials(InvalidCredentialsException ex) {
        return ApiErrorUtils.createUnauthorizedErrorResponse(ex.getMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Result<Void>> handleIllegalArgument(IllegalArgumentException ex) {
        return ApiErrorUtils.createBadRequestErrorResponse(ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Result<Void>> handleGeneralException(Exception ex) {
        return ApiErrorUtils.createInternalServerErrorResponse(ex.getMessage());
    }
}
