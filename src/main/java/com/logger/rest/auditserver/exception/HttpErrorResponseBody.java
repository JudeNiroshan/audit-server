package com.logger.rest.auditserver.exception;

import lombok.Builder;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;

@Builder
@Getter
public class HttpErrorResponseBody {
    private String timestamp;
    private String message;
    private String cause;
    private String path;
    private String version;
    private HttpStatus httpStatus;
}
