package com.qiraht.food_order.handler;

import com.qiraht.food_order.dto.ApiResponse;
import com.qiraht.food_order.exception.NotFoundException;
import com.qiraht.food_order.exception.ValidationException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    // Not Found Error
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handleBusinessException(NotFoundException ex, HttpServletRequest request) {
        log.error("error while processing request", ex);

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.failed(ex.getMessage()));
    }

    // Jakarta Validation Error
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Void>> handleBusinessException(MethodArgumentNotValidException ex, HttpServletRequest request) {
        log.error("error while processing request", ex);

        String message = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .findFirst()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .orElse("Validation Failed");

        ApiResponse<Void> body = ApiResponse.failed(message);

        return ResponseEntity.badRequest().body(body);
    }


    // Bad Credentials error
    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ApiResponse<?>> handleBusinessException(AuthenticationException ex, HttpServletRequest request) {
        log.error("error while processing request", ex);

        ApiResponse<Void> body = ApiResponse.failed(
                "Authentication failed/Invalid credentials"
        );

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(body);
    }

    // Manual Validation Error
    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ApiResponse<?>> handleBusinessException(ValidationException ex, HttpServletRequest request) {
        log.error("error while processing request", ex);

        ApiResponse<Void> body = ApiResponse.failed(
                ex.getMessage()
        );

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(body);
    }

    // Server Error
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleBusinessException(Exception ex, HttpServletRequest request) {
        log.error("error while processing request", ex);

        ApiResponse<Void> body = ApiResponse.failed(ex.getMessage());

        return ResponseEntity.internalServerError().body(body);
    }
}
