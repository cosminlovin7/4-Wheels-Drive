package com.upb.fourwheelsdrive.model.exception;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ExceptionResponse {
    private final Integer status;
    private final String message;
    private final LocalDateTime timestamp;

    public ExceptionResponse(final Integer status,
                             final String message,
                             final LocalDateTime timestamp) {
        this.status = status;
        this.message = message;
        this.timestamp = timestamp;
    }
}
