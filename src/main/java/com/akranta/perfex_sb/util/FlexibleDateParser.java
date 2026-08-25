package com.akranta.perfex_sb.util;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

/**
 * Helper to parse date values coming from Excel/CSV with multiple formats
 * into a LocalDateTime that can be bound as a SQL timestamp.
 *
 * Supported formats (case-insensitive):
 *  - dd-MMM-yyyy        (e.g., 09-Jan-2026)
 *  - dd/MMM/yyyy
 *  - dd-MM-yyyy
 *  - dd/MM/yyyy
 *  - yyyy-MM-dd
 *  - MM/dd/yyyy
 *
 * Also supports Excel serial numeric dates (days since 1899-12-31, with the
 * 1900 leap-year bug adjustment).
 */
public final class FlexibleDateParser {

    private static final List<DateTimeFormatter> DT_FORMATTERS = List.of(
            caseInsensitive("yyyy-MM-dd'T'HH:mm:ss"),
            caseInsensitive("yyyy-MM-dd'T'HH:mm:ss.SSS"),
            caseInsensitive("d-MMM-yyyy HH:mm:ss", Locale.ENGLISH),
            caseInsensitive("dd-MMM-yyyy HH:mm:ss", Locale.ENGLISH),
            caseInsensitive("d-MMM-yyyy HH:mm", Locale.ENGLISH),
            caseInsensitive("dd-MMM-yyyy HH:mm", Locale.ENGLISH)
    );

    private static final List<DateTimeFormatter> D_FORMATTERS = Arrays.asList(
            caseInsensitive("d-MMM-yyyy", Locale.ENGLISH),
            caseInsensitive("dd-MMM-yyyy", Locale.ENGLISH),
            caseInsensitive("dd/MMM/yyyy", Locale.ENGLISH),
            caseInsensitive("d/MMM/yyyy", Locale.ENGLISH),
            caseInsensitive("dd-MM-yyyy"),
            caseInsensitive("dd/MM/yyyy"),
            caseInsensitive("yyyy-MM-dd"),
            caseInsensitive("MM/dd/yyyy")
    );
    

    private FlexibleDateParser() {
    }

    /**
     * Parse a date string (various formats) to LocalDateTime at start of day.
     */
    public static LocalDateTime parseToDateTime(String value) {
        if (value == null || value.trim().isEmpty()) {
            return null;
        }
        String v = value.trim();

        // Try date-time patterns first
        for (DateTimeFormatter fmt : DT_FORMATTERS) {
            try {
                return LocalDateTime.parse(v, fmt);
            } catch (DateTimeParseException ignore) {
            }
        }

        // Then date-only patterns
        for (DateTimeFormatter fmt : D_FORMATTERS) {
            try {
                LocalDate ld = LocalDate.parse(v, fmt);
                return ld.atStartOfDay();
            } catch (DateTimeParseException ignore) {
            }
        }

        // Try numeric Excel serial (days since 1899-12-31 with 1900 bug)
        try {
            double serial = Double.parseDouble(v);
            int offset = serial >= 60 ? -1 : 0; // Excel's leap-year bug adjustment
            long epochDay = (long) serial - 25569 + offset; // 25569 days between 1899-12-31 and 1970-01-01
            return LocalDate.ofEpochDay(epochDay).atStartOfDay();
        } catch (NumberFormatException ignore) {
        }

        // Fallback: try ISO date-time
        try {
            return LocalDateTime.parse(v);
        } catch (DateTimeParseException ignore) {
        }
        return null;
    }

    /** Alias for readability. */
    public static LocalDateTime parseDateTime(String value) {
        return parseToDateTime(value);
    }

    private static DateTimeFormatter caseInsensitive(String pattern) {
        return new DateTimeFormatterBuilder()
                .parseCaseInsensitive()
                .appendPattern(pattern)
                .toFormatter();
    }

    private static DateTimeFormatter caseInsensitive(String pattern, Locale locale) {
        return new DateTimeFormatterBuilder()
                .parseCaseInsensitive()
                .appendPattern(pattern)
                .toFormatter(locale);
    }

    /**
     * Parse a date cell that might already be a numeric Excel date or a string.
     */
    public static LocalDateTime parseCellValue(Object cellVal) {
        if (cellVal == null) return null;
        if (cellVal instanceof Number) {
            double serial = ((Number) cellVal).doubleValue();
            int offset = serial >= 60 ? -1 : 0;
            long epochDay = (long) serial - 25569 + offset;
            return LocalDate.ofEpochDay(epochDay).atStartOfDay();
        }
        return parseToDateTime(cellVal.toString());
    }

    /**
     * Convenience to convert to java.sql.Timestamp (nullable).
     */
    public static java.sql.Timestamp toTimestamp(LocalDateTime ldt) {
        return ldt == null ? null : java.sql.Timestamp.from(ldt.atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * Convenience to convert to java.sql.Timestamp from Instant (nullable).
     */
    public static java.sql.Timestamp toTimestamp(Instant instant) {
        return instant == null ? null : java.sql.Timestamp.from(instant);
    }
}
