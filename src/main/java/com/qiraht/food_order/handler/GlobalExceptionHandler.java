package com.qiraht.food_order.handler;

import com.qiraht.food_order.dto.ApiResponse;
import com.qiraht.food_order.exception.NotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    // Not Found Error
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ApiResponse<?>> handleBusinessException(NotFoundException ex, HttpServletRequest request) {
        log.error("error while processing request", ex);

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.failed(ex.getMessage()));
    }

    // Jakarta Validation Error
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<?>> handleBusinessException(MethodArgumentNotValidException ex, HttpServletRequest request) {
        log.error("error while processing request", ex);

        String message = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .findFirst()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .orElse("Validation Failed");

        return ResponseEntity.badRequest().body(ApiResponse.failed(message));
    }


    // Server Error
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<?>> handleBusinessException(Exception ex, HttpServletRequest request) {
        log.error("error while processing request", ex);

        return ResponseEntity.internalServerError().body(ApiResponse.failed(ex.getMessage()));
    }
}
