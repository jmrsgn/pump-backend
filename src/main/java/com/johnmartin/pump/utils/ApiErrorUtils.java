package com.johnmartin.pump.utils;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.johnmartin.pump.constants.ApiErrorMessages;
import com.johnmartin.pump.dto.response.ApiErrorResponse;
import com.johnmartin.pump.dto.response.Result;

public class ApiErrorUtils {

    public static <T> ResponseEntity<Result<T>> createInternalServerErrorResponse(String message) {
        ApiErrorResponse error = new ApiErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                                                      ApiErrorMessages.INTERNAL_SERVER_ERROR,
                                                      message);

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Result.failure(error));
    }

    public static <T> ResponseEntity<Result<T>> createNotFoundErrorResponse(String message) {
        ApiErrorResponse error = new ApiErrorResponse(HttpStatus.NOT_FOUND.value(),
                                                      ApiErrorMessages.NOT_FOUND,
                                                      message);

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Result.failure(error));
    }

    public static <T> ResponseEntity<Result<T>> createUnauthorizedErrorResponse(String message) {
        ApiErrorResponse error = new ApiErrorResponse(HttpStatus.UNAUTHORIZED.value(),
                                                      ApiErrorMessages.UNAUTHORIZED,
                                                      message);

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Result.failure(error));
    }

    public static <T> ResponseEntity<Result<T>> createBadRequestErrorResponse(String message) {
        ApiErrorResponse error = new ApiErrorResponse(HttpStatus.BAD_REQUEST.value(),
                                                      ApiErrorMessages.BAD_REQUEST,
                                                      message);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Result.failure(error));
    }

    public static <T> ResponseEntity<Result<T>> createConflictErrorResponse(String message) {
        ApiErrorResponse error = new ApiErrorResponse(HttpStatus.CONFLICT.value(), ApiErrorMessages.CONFLICT, message);

        return ResponseEntity.status(HttpStatus.CONFLICT).body(Result.failure(error));
    }
}
