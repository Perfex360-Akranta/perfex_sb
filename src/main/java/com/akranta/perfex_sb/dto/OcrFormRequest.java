package com.akranta.perfex_sb.dto;

import java.util.List;

/** Full-form OCR request: one photo + the list of fields visible on the app screen. */
public class OcrFormRequest {
    private String imageBase64;
    private List<Field> fields;

    public String getImageBase64() { return imageBase64; }
    public void setImageBase64(String imageBase64) { this.imageBase64 = imageBase64; }
    public List<Field> getFields() { return fields; }
    public void setFields(List<Field> fields) { this.fields = fields; }

    public static class Field {
        private String key;
        private String label;
        /** "text" or "dropdown" */
        private String type;
        private Boolean multiline;
        private Integer maxLength;
        private List<String> options;

        public String getKey() { return key; }
        public void setKey(String key) { this.key = key; }
        public String getLabel() { return label; }
        public void setLabel(String label) { this.label = label; }
        public String getType() { return type; }
        public void setType(String type) { this.type = type; }
        public Boolean getMultiline() { return multiline; }
        public void setMultiline(Boolean multiline) { this.multiline = multiline; }
        public Integer getMaxLength() { return maxLength; }
        public void setMaxLength(Integer maxLength) { this.maxLength = maxLength; }
        public List<String> getOptions() { return options; }
        public void setOptions(List<String> options) { this.options = options; }
    }
}
