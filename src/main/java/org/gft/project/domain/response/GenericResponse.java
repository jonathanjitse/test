package org.gft.project.domain.response;

public class GenericResponse {

    private String message;
    private int code;

    public GenericResponse() {
    }
    public GenericResponse(String errorMessage, int code) {
        this.message = errorMessage;
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "GenericResponse{" +
                "message='" + message + '\'' +
                ", code=" + code +
                '}';
    }
}
