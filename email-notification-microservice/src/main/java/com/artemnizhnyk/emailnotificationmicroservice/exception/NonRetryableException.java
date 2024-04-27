package com.artemnizhnyk.emailnotificationmicroservice.exception;

public class NonRetryableException extends RuntimeException {

    public NonRetryableException(final String message) {
        super(message);
    }

    public NonRetryableException(final Throwable cause) {
        super(cause);
    }
}
