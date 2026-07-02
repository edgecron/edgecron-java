package com.edgecron;

public final class ApiError extends RuntimeException {
    private final int code;
    private final String message;
    private final String requestId;

    public ApiError(int code, String message, String requestId) {
        super(message);
        this.code = code;
        this.message = message;
        this.requestId = requestId;
    }

    public int getCode() { return code; }
    @Override public String getMessage() { return message; }
    public String getRequestId() { return requestId; }

    @Override public String toString() {
        return "edgecron: code=" + code + " message=" + message + " request_id=" + requestId;
    }

    public static ApiError isApiError(Throwable t) {
        if (t instanceof ApiError) return (ApiError) t;
        return null;
    }
}
