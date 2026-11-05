package com.jusmore.trading.platform.common.exception;

import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDate;
import java.util.List;

@RestControllerAdvice
@Slf4j
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

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex)  {
      log.error("Invalid method arguments found: {}", ex.getMessage());
      List<FieldError> fieldErrors = ex.getBindingResult()
            .getFieldErrors()
            .stream()
            .map(fieldError -> new FieldError(fieldError.getField(), fieldError.getDefaultMessage(), fieldError.getRejectedValue().toString()))
            .toList();
      ErrorResponse errorResponse = ErrorResponse.builder()
            .errors(fieldErrors)
            .status(HttpStatus.BAD_REQUEST.value())
            .code(ErrorCode.VALIDATION_ERROR)
            .message(ex.getMessage())
            .timestamp(LocalDate.now().toString())
            .build();
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);

    }
}

