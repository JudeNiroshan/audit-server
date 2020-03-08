package com.logger.rest.auditserver.exception;

public class ValidateException extends RuntimeException {
    public ValidateException(String message, Throwable cause) {
        super(message, cause);
    }

    public ValidateException(String allErrors) {
        super(allErrors);
    }
}
