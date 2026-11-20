package com.akranta.perfex_sb.service.impl;

import com.akranta.perfex_sb.dto.OcrFormRequest;
import com.akranta.perfex_sb.dto.OcrFormResponse;
import com.akranta.perfex_sb.service.FormOcrService;
import jakarta.annotation.PostConstruct;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.json.JsonParserFactory;
import org.springframework.http.*;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

/**
 * Full-form OCR: reads every field label + handwritten value from one photo and
 * maps
 * them onto the field list sent by the app. Independent of OcrServiceImpl.
 */
@Service
public class FormOcrServiceImpl implements FormOcrService {

    private static final Logger log = LoggerFactory.getLogger(FormOcrServiceImpl.class);

    private static final String GEMINI_URL = "https://generativelanguage.googleapis.com/v1beta/models/%s:generateContent";

    private static final int MAX_FIELDS = 60;
    private static final int MAX_OPTIONS = 200;

    private static final String SYSTEM_INSTRUCTION = "You are an industrial form-reading engine. You receive a phone photo of a filled form "
            + "(paper or screen) and a list of target fields from a mobile app. "
            + "For each target field, locate its label in the photo and transcribe the value written next to "
            + "or under it EXACTLY as written: no spelling correction, no translation, no guessing. "
            + "Labels in the photo may be abbreviated, in a different order, or worded slightly differently; "
            + "match them by meaning. Ignore ruled lines, boxes, stamps and printed instructions. "
            + "Pay attention to commonly confused characters (0/O, 1/l/I/7, 5/S, 2/Z, 8/B). "
            + "If a field is not present in the photo or nothing is written for it, return an empty value. "
            + "Return only JSON that matches the response schema.";

    @Value("${ocr.form.enabled:true}")
    private boolean enabled;
    @Value("${ocr.gemini.mock:false}")
    private boolean mock;
    @Value("${ocr.gemini.model:gemini-2.5-flash}")
    private String model;
    @Value("${ocr.gemini.fallback-model:gemini-3.1-flash-lite}")
    private String fallbackModel;
    @Value("${ocr.gemini.api-key:}")
    private String apiKey;
    @Value("${ocr.form.timeout-seconds:90}")
    private int timeoutSeconds;
    @Value("${ocr.form.thinking-level:minimal}")
    private String thinkingLevel;
    @Value("${ocr.form.media-resolution:MEDIA_RESOLUTION_MEDIUM}")
    private String mediaResolution;
    @Value("${ocr.form.max-concurrent:2}")
    private int maxConcurrent;
    @Value("${ocr.max-image-base64-chars:7000000}")
    private int maxBase64Chars;

    private Semaphore semaphore;
    private RestTemplate restTemplate;

    @PostConstruct
    void init() {
        semaphore = new Semaphore(Math.max(1, maxConcurrent), true);
        SimpleClientHttpRequestFactory f = new SimpleClientHttpRequestFactory();
        f.setConnectTimeout((int) Duration.ofSeconds(10).toMillis());
        f.setReadTimeout((int) Duration.ofSeconds(timeoutSeconds).toMillis());
        restTemplate = new RestTemplate(f);
        log.info("Form OCR initialised: enabled={}, model={}", enabled, model);
    }

    @Override
    public OcrFormResponse recognizeForm(OcrFormRequest req) {
        if (!enabled)
            return OcrFormResponse.fail("Form scan is disabled");
        if (req == null || req.getImageBase64() == null || req.getImageBase64().isBlank())
            return OcrFormResponse.fail("imageBase64 is required");
        if (req.getFields() == null || req.getFields().isEmpty())
            return OcrFormResponse.fail("No fields to fill");

        String b64 = sanitizeBase64(req.getImageBase64());
        if (b64.isEmpty())
            return OcrFormResponse.fail("Invalid image data");
        if (b64.length() > maxBase64Chars)
            return OcrFormResponse.fail("Image too large");
        String mimeType = detectMimeType(req.getImageBase64(), b64);
        if (mimeType == null)
            return OcrFormResponse.fail("Unsupported image format");

        // Keep only well-formed fields, de-duplicated by key
        Map<String, OcrFormRequest.Field> fields = new LinkedHashMap<>();
        for (OcrFormRequest.Field f : req.getFields()) {
            if (f == null || f.getKey() == null || f.getKey().isBlank())
                continue;
            if (fields.size() >= MAX_FIELDS)
                break;
            fields.putIfAbsent(f.getKey().trim(), f);
        }
        if (fields.isEmpty())
            return OcrFormResponse.fail("No fields to fill");

        if (mock) {
            List<OcrFormResponse.Item> items = new ArrayList<>();
            fields.forEach((k, f) -> items.add(new OcrFormResponse.Item(k, "MOCK", null, 0.5)));
            return OcrFormResponse.ok(items);
        }
        if (apiKey == null || apiKey.isBlank())
            return OcrFormResponse.fail("OCR not configured");

        boolean acquired;
        try {
            acquired = semaphore.tryAcquire(5, TimeUnit.SECONDS);
        } catch (InterruptedException ie) {
            Thread.currentThread().interrupt();
            return OcrFormResponse.fail("Form scan busy, please retry");
        }
        if (!acquired)
            return OcrFormResponse.fail("Form scan busy, please retry");

        try {
            String raw = callWithRetry(fields, b64, mimeType);
            List<OcrFormResponse.Item> items = parseAndSanitize(raw, fields);
            if (items == null)
                return OcrFormResponse.fail("Could not read the form, please retry");
            return OcrFormResponse.ok(items);
        } catch (HttpStatusCodeException e) {
            log.warn("Form OCR failed: HTTP {} - {}", e.getStatusCode().value(),
                    abbreviate(e.getResponseBodyAsString(), 500));
            int code = e.getStatusCode().value();
            return OcrFormResponse.fail(code == 429 || code == 503
                    ? "The AI service is busy, please retry in a moment"
                    : "Form OCR unavailable");
        } catch (ResourceAccessException e) {
            // log.warn("Form OCR network/timeout error: {}", e.getClass().getSimpleName());
            log.warn("Form OCR network/timeout error: {}", e.getMessage());
            return OcrFormResponse.fail("Reading the form timed out, please retry");
        } catch (Exception e) {
            log.warn("Form OCR failed: {}: {}", e.getClass().getSimpleName(), abbreviate(e.getMessage(), 300));
            return OcrFormResponse.fail("Form OCR unavailable");
        } finally {
            semaphore.release();
        }
    }

    // ---------------------------------------------------------------- prompt

    private String buildPrompt(Map<String, OcrFormRequest.Field> fields) {
        StringBuilder p = new StringBuilder();
        p.append("Target fields (one per line: key | label | type | rules):\n");
        fields.forEach((key, f) -> {
            String label = clean(f.getLabel(), 120);
            boolean dropdown = "dropdown".equalsIgnoreCase(f.getType());
            p.append(key).append(" | ").append(label.isEmpty() ? "(no label)" : label)
                    .append(" | ").append(dropdown ? "dropdown" : "text").append(" | ");
            List<String> rules = new ArrayList<>();
            if (!dropdown) {
                rules.add(Boolean.TRUE.equals(f.getMultiline()) ? "may span several lines" : "single line");
                if (f.getMaxLength() != null && f.getMaxLength() > 0)
                    rules.add("max " + f.getMaxLength() + " chars");
            } else {
                List<String> opts = options(f);
                if (!opts.isEmpty())
                    rules.add("options: " + String.join("; ", opts));
                else
                    rules.add("options not provided");
            }
            p.append(String.join(", ", rules)).append('\n');
        });
        p.append("\nRules:\n")
                .append("- Return exactly one entry per key above, using the same key.\n")
                .append("- value: what the person WROTE for that field, exactly as written. Empty string if absent or blank. ")
                .append("Never copy the label itself into the value.\n")
                .append("- option (dropdown only): copy verbatim the single option that the written value clearly refers to ")
                .append("(same word, abbreviation or obvious spelling variant). Use null if the written value does not clearly ")
                .append("match exactly one option. Never choose an option just because it is the closest guess. ")
                .append("Always null for text fields.\n")
                .append("- confidence: 0 to 1, how sure you are about both the label match and the reading.\n");
        return p.toString();
    }

    private Map<String, Object> responseSchema() {
        Map<String, Object> item = new LinkedHashMap<>();
        item.put("type", "OBJECT");
        Map<String, Object> props = new LinkedHashMap<>();
        props.put("key", Map.of("type", "STRING"));
        props.put("value", Map.of("type", "STRING"));
        props.put("option", Map.of("type", "STRING", "nullable", true));
        props.put("confidence", Map.of("type", "NUMBER"));
        item.put("properties", props);
        item.put("required", List.of("key", "value", "confidence"));

        Map<String, Object> root = new LinkedHashMap<>();
        root.put("type", "OBJECT");
        root.put("properties", Map.of("fields", Map.of("type", "ARRAY", "items", item)));
        root.put("required", List.of("fields"));
        return root;
    }

    // ---------------------------------------------------------------- gemini
    
    private boolean isGemini3OrNewer(String targetModel) {
        String m = targetModel == null ? "" : targetModel.toLowerCase(Locale.ROOT).replace("models/", "");
        java.util.regex.Matcher mt = java.util.regex.Pattern.compile("gemini-(\\d+)").matcher(m);
        return mt.find() && Integer.parseInt(mt.group(1)) >= 3;
    }

    private String callWithRetry(Map<String, OcrFormRequest.Field> fields, String b64, String mimeType) {
        try {
            return callGemini(fields, b64, mimeType, model);
        } catch (HttpStatusCodeException e) {
            int code = e.getStatusCode().value();
            if (code == 429 || code == 500 || code == 503) {
                String retryModel = (fallbackModel != null && !fallbackModel.isBlank()
                        && !fallbackModel.trim().equalsIgnoreCase(model.trim()))
                        ? fallbackModel.trim()
                        : model;
                log.info("Form OCR transient HTTP {}, retrying with {}", code, retryModel);
                try {
                    Thread.sleep(500);
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                    throw e;
                }
                return callGemini(fields, b64, mimeType, retryModel);
            }
            throw e;
        }
    }

    @SuppressWarnings("unchecked")
    private String callGemini(Map<String, OcrFormRequest.Field> fields, String b64, String mimeType, String targetModel) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("system_instruction", Map.of("parts", List.of(Map.of("text", SYSTEM_INSTRUCTION))));
        body.put("contents", List.of(Map.of(
                "role", "user",
                "parts", List.of(
                        Map.of("inline_data", Map.of("mime_type", mimeType, "data", b64)),
                        Map.of("text", buildPrompt(fields))))));

        Map<String, Object> gen = new LinkedHashMap<>();
        gen.put("maxOutputTokens", 4096);
        gen.put("responseMimeType", "application/json");
        gen.put("responseSchema", responseSchema());
        if (isGemini3OrNewer(targetModel)) {
            gen.put("thinkingConfig", Map.of("thinkingLevel", thinkingLevel));
        } else {
            gen.put("temperature", 0);
        }
        if (mediaResolution != null && !mediaResolution.isBlank())
            gen.put("mediaResolution", mediaResolution.trim());
        body.put("generationConfig", gen);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("x-goog-api-key", apiKey);

        ResponseEntity<Map> res = restTemplate.exchange(
                String.format(GEMINI_URL, targetModel.trim()), HttpMethod.POST,
                new HttpEntity<>(body, headers), Map.class);
        if (!res.getStatusCode().is2xxSuccessful() || res.getBody() == null)
            throw new IllegalStateException("Bad Gemini response");

        Object candidates = res.getBody().get("candidates");
        if (!(candidates instanceof List<?> cl) || cl.isEmpty()) {
            log.warn("Form OCR returned no candidates. promptFeedback={}", res.getBody().get("promptFeedback"));
            return "";
        }
        Map<String, Object> first = (Map<String, Object>) cl.get(0);
        Object content = first.get("content");
        if (!(content instanceof Map<?, ?> cm) || !(cm.get("parts") instanceof List<?> pl)) {
            log.warn("Form OCR candidate has no content. finishReason={}", first.get("finishReason"));
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (Object po : pl) {
            if (!(po instanceof Map<?, ?> pm))
                continue;
            if (Boolean.TRUE.equals(pm.get("thought")))
                continue;
            Object t = pm.get("text");
            if (t != null)
                sb.append(t);
        }
        return sb.toString();
    }

    // ---------------------------------------------------------------- output

    /** Returns null when the model output cannot be parsed at all. */
    private List<OcrFormResponse.Item> parseAndSanitize(String raw, Map<String, OcrFormRequest.Field> fields) {
        if (raw == null || raw.isBlank())
            return null;
        String s = raw.trim();
        if (s.startsWith("```")) {
            s = s.replaceFirst("^```[a-zA-Z]*\\s*", "").replaceFirst("\\s*```\\s*$", "");
        }
        Map<String, Object> root;
        try {
            root = JsonParserFactory.getJsonParser().parseMap(s);
        } catch (Exception e) {
            log.warn("Form OCR returned unparseable JSON: {}", abbreviate(s, 300));
            return null;
        }
        Object arr = root.get("fields");
        Map<String, Map<?, ?>> byKey = new HashMap<>();
        if (arr instanceof List<?> list) {
            for (Object o : list) {
                if (o instanceof Map<?, ?> m && m.get("key") != null) {
                    byKey.putIfAbsent(String.valueOf(m.get("key")).trim(), m);
                }
            }
        }

        List<OcrFormResponse.Item> out = new ArrayList<>();
        fields.forEach((key, f) -> {
            Map<?, ?> m = byKey.get(key);
            String value = m == null || m.get("value") == null ? "" : String.valueOf(m.get("value")).trim();
            if (value.equalsIgnoreCase("NO_TEXT") || value.equalsIgnoreCase("null"))
                value = "";
            boolean dropdown = "dropdown".equalsIgnoreCase(f.getType());

            if (!dropdown) {
                if (!Boolean.TRUE.equals(f.getMultiline()))
                    value = value.replaceAll("\\s*\\R\\s*", " ").trim();
                Integer max = f.getMaxLength();
                if (max != null && max > 0 && value.length() > max)
                    value = value.substring(0, max);
            }

            // Option must be one of the supplied options (exact, case-insensitive) or it is
            // dropped.
            String option = null;
            if (dropdown && m != null && m.get("option") != null) {
                String suggested = String.valueOf(m.get("option")).trim();
                for (String o : options(f)) {
                    if (o.equalsIgnoreCase(suggested)) {
                        option = o;
                        break;
                    }
                }
            }

            double conf = 0;
            if (m != null && m.get("confidence") instanceof Number n)
                conf = n.doubleValue();
            conf = Math.max(0, Math.min(1, conf));
            if (value.isEmpty())
                conf = 0;

            out.add(new OcrFormResponse.Item(key, value, option, conf));
        });
        return out;
    }

    private static List<String> options(OcrFormRequest.Field f) {
        List<String> out = new ArrayList<>();
        if (f.getOptions() == null)
            return out;
        for (String o : f.getOptions()) {
            String c = clean(o, 80);
            if (!c.isEmpty())
                out.add(c);
            if (out.size() >= MAX_OPTIONS)
                break;
        }
        return out;
    }

    /** Strips characters that could break the line-based prompt format. */
    private static String clean(String v, int max) {
        if (v == null)
            return "";
        String s = v.replaceAll("[\\r\\n|;]+", " ").replaceAll("\\s+", " ").trim();
        return s.length() <= max ? s : s.substring(0, max);
    }

    private String sanitizeBase64(String in) {
        String s = in.trim();
        int comma = s.indexOf(',');
        if (s.startsWith("data:") && comma > 0)
            s = s.substring(comma + 1);
        s = s.replaceAll("\\s", "").replace('-', '+').replace('_', '/').replaceAll("=+$", "");
        if (!s.matches("^[A-Za-z0-9+/]+$"))
            return "";
        int rem = s.length() % 4;
        if (rem == 1)
            return "";
        if (rem == 2)
            s += "==";
        else if (rem == 3)
            s += "=";
        return s;
    }

    private String detectMimeType(String original, String b64) {
        byte[] h;
        try {
            h = Base64.getDecoder().decode(b64.substring(0, Math.min(b64.length(), 64)));
        } catch (IllegalArgumentException e) {
            return null;
        }
        if (h.length >= 3 && (h[0] & 0xFF) == 0xFF && (h[1] & 0xFF) == 0xD8 && (h[2] & 0xFF) == 0xFF)
            return "image/jpeg";
        if (h.length >= 4 && (h[0] & 0xFF) == 0x89 && h[1] == 'P' && h[2] == 'N' && h[3] == 'G')
            return "image/png";
        if (h.length >= 12 && h[0] == 'R' && h[1] == 'I' && h[2] == 'F' && h[3] == 'F'
                && h[8] == 'W' && h[9] == 'E' && h[10] == 'B' && h[11] == 'P')
            return "image/webp";
        String s = original.trim();
        if (s.startsWith("data:image/")) {
            int semi = s.indexOf(';');
            if (semi > 5)
                return s.substring(5, semi).toLowerCase(Locale.ROOT);
        }
        return null;
    }

    private static String abbreviate(String v, int max) {
        if (v == null)
            return "";
        String s = v.replaceAll("\\s+", " ");
        return s.length() <= max ? s : s.substring(0, max) + "...";
    }
}
