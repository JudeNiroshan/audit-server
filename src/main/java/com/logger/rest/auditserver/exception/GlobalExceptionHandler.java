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

    @ExceptionHandler(InvalidDataException.class)
    @ResponseBody
    public ResponseEntity<ErrorEventResponse> handleValidations(Exception ex, WebRequest request) {
        ErrorEventResponse responseBody = new ErrorEventResponse.ErrorEventResponseBuilder()
                .timestamp(LocalDateTime.now().toString())
                .logStatus("Error")
                .message(ex.getMessage())
                .cause(Optional.ofNullable(ex.getCause()).map(Throwable::toString).orElse("Not available"))
                .path(((ServletWebRequest) request).getRequest().getRequestURI())
                .httpStatus(HttpStatus.NOT_ACCEPTABLE)
                .version(buildProperties.getVersion())
                .build();
        return new ResponseEntity<>(responseBody, HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler(GPBFrameworkNotFound.class)
    @ResponseBody
    public ResponseEntity<ErrorEventResponse> handleFrameworkNotFound(Exception ex, WebRequest request) {
        ErrorEventResponse responseBody = new ErrorEventResponse.ErrorEventResponseBuilder()
                .timestamp(LocalDateTime.now().toString())
                .logStatus("Error")
                .message("Audit server error")
                .cause(Optional.ofNullable(ex.getCause()).map(Throwable::toString)
                        .orElse("audit-server is not properly configured."))
                .path(((ServletWebRequest) request).getRequest().getRequestURI())
                .httpStatus(HttpStatus.INTERNAL_SERVER_ERROR)
                .version(buildProperties.getVersion())
                .build();
        return new ResponseEntity<>(responseBody, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(RemoteInstancesNotFound.class)
    @ResponseBody
    public ResponseEntity<ErrorEventResponse> handleRemoteInstances(Exception ex, WebRequest request) {
        ErrorEventResponse responseBody = new ErrorEventResponse.ErrorEventResponseBuilder()
                .timestamp(LocalDateTime.now().toString())
                .logStatus("Error")
                .message("Audit server is not properly started!")
                .cause(Optional.ofNullable(ex.getCause()).map(Throwable::toString)
                        .orElse("Audit server is not properly started."))
                .path(((ServletWebRequest) request).getRequest().getRequestURI())
                .httpStatus(HttpStatus.ALREADY_REPORTED)
                .version(buildProperties.getVersion())
                .build();
        return new ResponseEntity<>(responseBody, HttpStatus.ALREADY_REPORTED);
    }
}
