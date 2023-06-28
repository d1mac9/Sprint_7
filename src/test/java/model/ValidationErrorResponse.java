package model;

public class ValidationErrorResponse {
    private int code;
    private String message;

    public ValidationErrorResponse(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public ValidationErrorResponse() {
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
