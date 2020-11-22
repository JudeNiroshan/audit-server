package com.logger.rest.auditserver.exception;

public class InvalidDataException extends RuntimeException {
    public InvalidDataException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidDataException(String allErrors) {
        super(allErrors);
    }
}
