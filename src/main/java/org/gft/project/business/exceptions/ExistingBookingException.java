package org.gft.project.business.exceptions;

public class ExistingBookingException extends RuntimeException {
    public ExistingBookingException() {
    }

    public ExistingBookingException(String message) {
        super(message);
    }
}
