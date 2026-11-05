package com.jusmore.trading.platform.common.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.validation.FieldError;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse {
    private int status;
    private ErrorCode code;
    private String message;
    private String timestamp;
    private String path;
    private List<FieldError> errors;
}

