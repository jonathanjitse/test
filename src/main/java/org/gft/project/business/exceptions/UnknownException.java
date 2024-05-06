package org.gft.project.business.exceptions;

public class UnknownException extends RuntimeException {
    private int code;

    public UnknownException() {
    }

    public UnknownException(String message, int code) {
        super(message);
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
