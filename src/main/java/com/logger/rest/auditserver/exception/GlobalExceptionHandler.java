package com.logger.rest.auditserver.exception;

import org.springframework.boot.info.BuildProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.Optional;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private final BuildProperties buildProperties;

    public GlobalExceptionHandler(BuildProperties buildProperties) {
        this.buildProperties = buildProperties;
    }

    @ExceptionHandler(ValidationException.class)
    @ResponseBody
    public ResponseEntity<HttpErrorResponseBody> handleValidations(Exception ex, WebRequest request){
        HttpErrorResponseBody responseBody = new HttpErrorResponseBody.HttpErrorResponseBodyBuilder()
                .timestamp(LocalDateTime.now().toString())
                .message(ex.getMessage())
                .cause(Optional.ofNullable(ex.getCause()).map(Throwable::toString).orElse("Not available"))
                .path(((ServletWebRequest)request).getRequest().getRequestURI())
                .httpStatus(HttpStatus.NOT_ACCEPTABLE)
                .version(buildProperties.getVersion())
                .build();
        return new ResponseEntity<>(responseBody, HttpStatus.NOT_ACCEPTABLE);
    }
}
