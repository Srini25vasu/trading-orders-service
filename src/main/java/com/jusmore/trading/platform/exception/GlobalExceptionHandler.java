package com.jusmore.trading.platform.exception;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDate;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFound(ResourceNotFoundException ex) {
        ErrorResponse error = ErrorResponse.builder()
            .status(HttpStatus.NOT_FOUND.value())
            .code(ErrorCode.RESOURCE_NOT_FOUND)
            .message(ex.getMessage())
            .timestamp(LocalDate.now().toString())
            //.path(req.getURI().getPath())
            .build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleConstraintViolationException(ConstraintViolationException ex) {
        ErrorResponse error = ErrorResponse.builder()
              .status(HttpStatus.BAD_REQUEST.value())
              .code(ErrorCode.VALIDATION_ERROR)
              .message(ex.getMessage())
              .timestamp(LocalDate.now().toString())
              //.path(req.getURI().getPath())
              .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleInternalServerError(Exception ex) {
        ErrorResponse error = ErrorResponse.builder()
              .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
              .code(ErrorCode.INTERNAL_SERVER_ERROR)
              .message(ex.getMessage())
              .timestamp(LocalDate.now().toString())
              //.path(req.getURI().getPath())
              .build();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }
}
