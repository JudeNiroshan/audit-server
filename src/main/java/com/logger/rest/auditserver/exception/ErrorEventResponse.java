package com.logger.rest.auditserver.exception;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Builder
@Getter
public class ErrorEventResponse {
    private String timestamp;
    private String logStatus;
    private String message;
    private String cause;
    private String path;
    private String version;
    private HttpStatus httpStatus;
}
