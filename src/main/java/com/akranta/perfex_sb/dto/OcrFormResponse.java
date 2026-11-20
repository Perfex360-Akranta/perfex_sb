package com.akranta.perfex_sb.dto;

import java.util.ArrayList;
import java.util.List;

/** Full-form OCR response. success=false means the client should show a retry message. */
public class OcrFormResponse {
    private boolean success;
    private String message;
    private List<Item> fields = new ArrayList<>();

    public OcrFormResponse() {}

    public static OcrFormResponse ok(List<Item> fields) {
        OcrFormResponse r = new OcrFormResponse();
        r.success = true;
        r.fields = fields;
        return r;
    }

    public static OcrFormResponse fail(String message) {
        OcrFormResponse r = new OcrFormResponse();
        r.success = false;
        r.message = message;
        return r;
    }

    public boolean isSuccess() { return success; }
    public void setSuccess(boolean success) { this.success = success; }
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    public List<Item> getFields() { return fields; }
    public void setFields(List<Item> fields) { this.fields = fields; }

    public static class Item {
        private String key;
        /** The value exactly as written on paper ("" if not found). */
        private String value;
        /** Dropdown only: the option the written value refers to, or null. */
        private String option;
        /** 0..1 */
        private double confidence;

        public Item() {}

        public Item(String key, String value, String option, double confidence) {
            this.key = key;
            this.value = value;
            this.option = option;
            this.confidence = confidence;
        }

        public String getKey() { return key; }
        public void setKey(String key) { this.key = key; }
        public String getValue() { return value; }
        public void setValue(String value) { this.value = value; }
        public String getOption() { return option; }
        public void setOption(String option) { this.option = option; }
        public double getConfidence() { return confidence; }
        public void setConfidence(double confidence) { this.confidence = confidence; }
    }
}
