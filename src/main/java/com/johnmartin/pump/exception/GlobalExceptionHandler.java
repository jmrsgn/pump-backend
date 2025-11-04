package com.johnmartin.pump.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.johnmartin.pump.constants.AppStrings;
import com.johnmartin.pump.dto.response.ApiErrorResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Handle UserNotFoundException → 404
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleUserNotFound(UserNotFoundException ex) {
        ApiErrorResponse response = new ApiErrorResponse(HttpStatus.NOT_FOUND.value(),
                                                         AppStrings.USER_NOT_FOUND,
                                                         ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    // Handle IllegalArgumentException → 400
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiErrorResponse> handleIllegalArgument(IllegalArgumentException ex) {
        ApiErrorResponse response = new ApiErrorResponse(HttpStatus.BAD_REQUEST.value(),
                                                         AppStrings.INVALID_ARGUMENT,
                                                         ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    // Catch-all handler for other uncaught exceptions → 500
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResponse> handleGeneralException(Exception ex) {
        ApiErrorResponse response = new ApiErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                                                         AppStrings.INTERNAL_SERVER_ERROR,
                                                         ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}
