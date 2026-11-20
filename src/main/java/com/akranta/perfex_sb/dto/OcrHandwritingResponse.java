package com.akranta.perfex_sb.dto;

public class OcrHandwritingResponse {
    private boolean success;
    private String text;
    private String message;

    public OcrHandwritingResponse() {
    }

    public OcrHandwritingResponse(boolean success, String text, String message) {
        this.success = success;
        this.text = text;
        this.message = message;
    }

    public static OcrHandwritingResponse ok(String text) {
        return new OcrHandwritingResponse(true, text, null);
    }

    public static OcrHandwritingResponse fail(String message) {
        return new OcrHandwritingResponse(false, "", message);
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}