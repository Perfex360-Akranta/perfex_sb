package com.akranta.perfex_sb.service.impl;

import com.akranta.perfex_sb.dto.OcrHandwritingRequest;
import com.akranta.perfex_sb.dto.OcrHandwritingResponse;
import com.akranta.perfex_sb.service.OcrService;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import jakarta.annotation.PostConstruct;
import java.time.Duration;
import java.util.List;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

@Service
public class OcrServiceImpl implements OcrService {

    private static final Logger log = LoggerFactory.getLogger(OcrServiceImpl.class);

    private static final String SYSTEM_INSTRUCTION =
            "You are an industrial OCR engine specialised in reading handwriting on shop-floor forms, "
            + "labels and notes captured with a phone camera. "
            + "Transcribe handwritten or printed text exactly as written. "
            + "The image may be rotated, skewed, blurred, low-contrast or have lines/grid in the background; "
            + "read it anyway. Ignore ruled lines, boxes, stamps and the background. "
            + "Do not translate, correct spelling, or guess missing words. "
            + "Pay close attention to commonly confused characters (0/O, 1/l/I/7, 5/S, 2/Z, 8/B, 6/b, 9/g) "
            + "and use context (numbers vs words) to decide. "
            + "Output only raw transcribed text with no markdown, backticks, quotes, labels or commentary. "
            + "If there is no readable text, output NO_TEXT.";

    private static final String GEMINI_URL =
            "https://generativelanguage.googleapis.com/v1beta/models/%s:generateContent";

    @Value("${ocr.gemini.enabled:true}")
    private boolean enabled;
    @Value("${ocr.gemini.mock:false}")
    private boolean mock;
    @Value("${ocr.gemini.model:gemini-2.5-flash}")
    private String model;
    @Value("${ocr.gemini.api-key:}")
    private String apiKey;
    @Value("${ocr.gemini.timeout-seconds:30}")
    private int timeoutSeconds;
    /** Gemini 3.x thinking level: minimal | low | medium | high. */
    @Value("${ocr.gemini.thinking-level:low}")
    private String thinkingLevel;
    /** MEDIA_RESOLUTION_LOW | MEDIA_RESOLUTION_MEDIUM | MEDIA_RESOLUTION_HIGH (blank = model default). */
    @Value("${ocr.gemini.media-resolution:MEDIA_RESOLUTION_HIGH}")
    private String mediaResolution;
    @Value("${ocr.max-concurrent:3}")
    private int maxConcurrent;
    @Value("${ocr.max-image-base64-chars:7000000}")
    private int maxBase64Chars;

   
    private Semaphore semaphore;
    private RestTemplate restTemplate;

    @PostConstruct
    void init() {
        semaphore = new Semaphore(Math.max(1, maxConcurrent), true);
        SimpleClientHttpRequestFactory f = new SimpleClientHttpRequestFactory();
        f.setConnectTimeout((int) Duration.ofSeconds(Math.min(10, timeoutSeconds)).toMillis());
        f.setReadTimeout((int) Duration.ofSeconds(timeoutSeconds).toMillis());
        restTemplate = new RestTemplate(f);
        log.info("OCR initialised: enabled={}, mock={}, model={}, apiKeyConfigured={}",
                enabled, mock, model, apiKey != null && !apiKey.isBlank());
    }

    @Override
    public OcrHandwritingResponse recognizeHandwriting(OcrHandwritingRequest req) {
        if (!enabled) return OcrHandwritingResponse.fail("OCR disabled");
        if (req == null || req.getImageBase64() == null || req.getImageBase64().isBlank())
            return OcrHandwritingResponse.fail("imageBase64 is required");

        String b64 = sanitizeBase64(req.getImageBase64());
        if (b64.isEmpty()) {
            log.warn("OCR rejected: invalid base64 payload (length={})", req.getImageBase64().length());
            return OcrHandwritingResponse.fail("Invalid image data");
        }
        if (b64.length() > maxBase64Chars) {
            log.warn("OCR rejected: image too large ({} base64 chars, limit {})", b64.length(), maxBase64Chars);
            return OcrHandwritingResponse.fail("Image too large");
        }

        String mimeType = detectMimeType(req.getImageBase64(), b64);
        if (mimeType == null) {
            log.warn("OCR rejected: unsupported or corrupt image format");
            return OcrHandwritingResponse.fail("Unsupported image format");
        }

        if (mock) return OcrHandwritingResponse.ok("MOCK_TEXT");

        if (apiKey == null || apiKey.isBlank()) return OcrHandwritingResponse.fail("OCR not configured");

        boolean acquired;
        try {
            acquired = semaphore.tryAcquire(Math.max(1, timeoutSeconds / 2), TimeUnit.SECONDS);
        } catch (InterruptedException ie) {
            Thread.currentThread().interrupt();
            return OcrHandwritingResponse.fail("OCR busy");
        }
        if (!acquired) return OcrHandwritingResponse.fail("OCR busy");
        try {
            String raw = callGeminiWithRetry(req, b64, mimeType);
            String text = sanitizeOutput(raw, req);
            if (text.isEmpty()) {
                log.info("OCR returned no usable text (raw length={})", raw == null ? 0 : raw.length());
                return OcrHandwritingResponse.fail("No text recognized");
            }
            return OcrHandwritingResponse.ok(text);
        } catch (HttpStatusCodeException e) {
            // Log Gemini's status + error body (never the URL/key) so failures are diagnosable
            log.warn("Gemini OCR failed: HTTP {} - {}", e.getStatusCode().value(), abbreviate(e.getResponseBodyAsString(), 500));
            return OcrHandwritingResponse.fail("OCR unavailable");
        } catch (ResourceAccessException e) {
            log.warn("Gemini OCR network/timeout error: {}", e.getClass().getSimpleName());
            return OcrHandwritingResponse.fail("OCR timeout");
        } catch (Exception e) {
            log.warn("Gemini OCR failed: {}: {}", e.getClass().getSimpleName(), abbreviate(e.getMessage(), 300));
            return OcrHandwritingResponse.fail("OCR unavailable");
        } finally {
            semaphore.release();
        }
    }

    private String sanitizeBase64(String in) {
        String s = in.trim();
        int comma = s.indexOf(',');
        if (s.startsWith("data:") && comma > 0) s = s.substring(comma + 1);
        s = s.replaceAll("\\s", "");
        // Gemini requires standard base64: convert URL-safe alphabet and fix padding
        s = s.replace('-', '+').replace('_', '/');
        s = s.replaceAll("=+$", "");
        if (!s.matches("^[A-Za-z0-9+/]+$")) return "";
        int rem = s.length() % 4;
        if (rem == 1) return "";
        if (rem == 2) s += "==";
        else if (rem == 3) s += "=";
        return s;
    }

    /** Detects the real image type from magic bytes (falls back to data-URI header). */
    private String detectMimeType(String original, String b64) {
        byte[] h;
        try {
            // decode only the first few bytes for the header check
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
        if (h.length >= 12 && h[4] == 'f' && h[5] == 't' && h[6] == 'y' && h[7] == 'p') {
            String brand = new String(h, 8, 4, java.nio.charset.StandardCharsets.US_ASCII).toLowerCase(Locale.ROOT);
            if (brand.startsWith("hei") || brand.startsWith("hev")) return "image/heic";
            if (brand.startsWith("mif") || brand.startsWith("msf") || brand.startsWith("hei")) return "image/heif";
        }
        String s = original.trim();
        if (s.startsWith("data:image/")) {
            int semi = s.indexOf(';');
            if (semi > 5) return s.substring(5, semi).toLowerCase(Locale.ROOT);
        }
        return null;
    }

    private String buildPrompt(OcrHandwritingRequest req) {
        StringBuilder p = new StringBuilder("Transcribe the handwritten text in this image.");
        if (req.getLabel() != null && !req.getLabel().isBlank())
            p.append(" It is the value for the form field \"").append(req.getLabel().trim())
             .append("\"; do not include the field name itself in the output.");
        List<String> options = req.getOptions();
        if (options != null && !options.isEmpty()) {
            p.append(" The value must be exactly one of these options; output the closest option verbatim: ")
             .append(String.join(" | ", options)).append(".");
        }
        if (Boolean.TRUE.equals(req.getMultiline()))
            p.append(" Preserve line breaks.");
        else
            p.append(" Output a single line; join lines with a space.");
        if (req.getMaxLength() != null && req.getMaxLength() > 0)
            p.append(" Output at most ").append(req.getMaxLength()).append(" characters.");
        return p.toString();
    }

    private boolean isGemini3OrNewer() {
        String m = model == null ? "" : model.toLowerCase(Locale.ROOT).replace("models/", "");
        java.util.regex.Matcher mt = java.util.regex.Pattern.compile("gemini-(\\d+)").matcher(m);
        return mt.find() && Integer.parseInt(mt.group(1)) >= 3;
    }

    private String callGeminiWithRetry(OcrHandwritingRequest req, String b64, String mimeType) {
        try {
            return callGemini(req, b64, mimeType);
        } catch (HttpStatusCodeException e) {
            int code = e.getStatusCode().value();
            if (code == 429 || code == 500 || code == 503) {
                log.info("Gemini OCR transient HTTP {}, retrying once", code);
                try { Thread.sleep(800); } catch (InterruptedException ie) { Thread.currentThread().interrupt(); throw e; }
                return callGemini(req, b64, mimeType);
            }
            throw e;
        }
    }

    @SuppressWarnings("unchecked")
    private String callGemini(OcrHandwritingRequest req, String b64, String mimeType) {
        Map<String, Object> body = new LinkedHashMap<>();

        body.put("system_instruction",
                Map.of("parts", List.of(Map.of("text", SYSTEM_INSTRUCTION))));

        // Image first, then the instruction (recommended ordering for vision prompts)
        body.put("contents", List.of(Map.of(
                "role", "user",
                "parts", List.of(
                        Map.of("inline_data", Map.of(
                                "mime_type", mimeType,
                                "data", b64)),
                        Map.of("text", buildPrompt(req))))));

        Map<String, Object> gen = new LinkedHashMap<>();
        gen.put("maxOutputTokens", 2048);
        if (isGemini3OrNewer()) {
            // Gemini 3.x: thinkingBudget is not supported the same way; use thinkingLevel and default temperature
            gen.put("thinkingConfig", Map.of("thinkingLevel", thinkingLevel));
        } else {
            gen.put("temperature", 0);
            if (model != null && model.contains("flash"))
                gen.put("thinkingConfig", Map.of("thinkingBudget", 0));
        }
        if (mediaResolution != null && !mediaResolution.isBlank())
            gen.put("mediaResolution", mediaResolution.trim());
        body.put("generationConfig", gen);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("x-goog-api-key", apiKey);

        ResponseEntity<Map> res = restTemplate.exchange(
                String.format(GEMINI_URL, model.trim()), HttpMethod.POST,
                new HttpEntity<>(body, headers), Map.class);

        if (!res.getStatusCode().is2xxSuccessful() || res.getBody() == null)
            throw new IllegalStateException("Bad Gemini response");

        Object candidates = res.getBody().get("candidates");
        if (!(candidates instanceof List<?> cl) || cl.isEmpty()) {
            Object feedback = res.getBody().get("promptFeedback");
            log.warn("Gemini OCR returned no candidates. promptFeedback={}", feedback);
            return "";
        }
        Map<String, Object> first = (Map<String, Object>) cl.get(0);
        Object finish = first.get("finishReason");
        Object content = first.get("content");
        if (!(content instanceof Map<?, ?> cm)) {
            log.warn("Gemini OCR candidate has no content. finishReason={}", finish);
            return "";
        }
        Object parts = cm.get("parts");
        if (!(parts instanceof List<?> pl) || pl.isEmpty()) {
            log.warn("Gemini OCR candidate has no parts. finishReason={}", finish);
            return "";
        }
        // Concatenate all answer parts, skipping "thought" parts emitted by thinking models
        StringBuilder sb = new StringBuilder();
        for (Object po : pl) {
            if (!(po instanceof Map<?, ?> pm)) continue;
            if (Boolean.TRUE.equals(pm.get("thought"))) continue;
            Object t = pm.get("text");
            if (t != null) sb.append(t);
        }
        if (sb.length() == 0) log.warn("Gemini OCR returned empty text. finishReason={}", finish);
        return sb.toString();
    }

    private String sanitizeOutput(String raw, OcrHandwritingRequest req) {
        if (raw == null) return "";
        String s = raw.trim();
        if (s.startsWith("```")) {
            s = s.replaceFirst("^```[a-zA-Z]*\\s*", "");
            s = s.replaceFirst("\\s*```\\s*$", "");
        }
        s = s.replace("```", "").trim();
        // strip wrapping quotes the model sometimes adds
        if (s.length() >= 2 && ((s.startsWith("\"") && s.endsWith("\"")) || (s.startsWith("'") && s.endsWith("'"))))
            s = s.substring(1, s.length() - 1).trim();
        if (s.isEmpty() || s.equalsIgnoreCase("NO_TEXT")) return "";

        if (!Boolean.TRUE.equals(req.getMultiline()))
            s = s.replaceAll("\\s*\\R\\s*", " ").trim();

        List<String> options = req.getOptions();
        if (options != null && !options.isEmpty()) {
            String ns = normalize(s);
            // 1) exact (normalized) match
            for (String o : options)
                if (o != null && !o.isBlank() && normalize(o).equals(ns)) return o;
            // 2) an option fully contained in the output (prefer the longest to avoid "1" matching "10")
            List<String> sorted = new ArrayList<>();
            for (String o : options) if (o != null && !o.isBlank()) sorted.add(o);
            sorted.sort(Comparator.comparingInt((String o) -> normalize(o).length()).reversed());
            for (String o : sorted) {
                String no = normalize(o);
                if (!no.isEmpty() && (" " + ns + " ").contains(" " + no + " ")) return o;
            }
            // 3) output is a unique prefix/substring of one option
            String hit = null;
            for (String o : sorted) {
                if (ns.length() >= 2 && normalize(o).contains(ns)) {
                    if (hit != null) { hit = null; break; }
                    hit = o;
                }
            }
            if (hit != null) return hit;
            return ""; // no match → client falls back
        }

        Integer max = req.getMaxLength();
        if (max != null && max > 0 && s.length() > max) s = s.substring(0, max);
        return s;
    }

    private static String normalize(String v) {
        return v == null ? "" : v.toLowerCase(Locale.ROOT).replaceAll("[^\\p{L}\\p{N}]+", " ").trim();
    }

    private static String abbreviate(String v, int max) {
        if (v == null) return "";
        String s = v.replaceAll("\\s+", " ");
        return s.length() <= max ? s : s.substring(0, max) + "...";
    }
}
