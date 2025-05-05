package ar.edu.utn.frbb.tup.model.dto;

public class ErrorResponse {
    private final String code;
    private final String message;
    private final long timestamp;

    public ErrorResponse(String code, String message) {
        this.code = code;
        this.message = message;
        this.timestamp = System.currentTimeMillis();
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public long getTimestamp() {
        return timestamp;
    }
}