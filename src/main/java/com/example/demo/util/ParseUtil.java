package com.example.demo.util;

import java.math.BigDecimal;

public final class ParseUtil {
    private ParseUtil() {}

    /** true if null/blank or the literal strings "undefined"/"null" */
    public static boolean isBlankOrUndefined(String s) {
        return s == null || s.trim().isEmpty()
                || "undefined".equalsIgnoreCase(s)
                || "null".equalsIgnoreCase(s);
    }

    /** Parse int, treating "", "Nil", "-" etc. as defaultVal (no exceptions). */
    public static Integer safeInt(String s, Integer defaultVal) {
        if (isBlankOrUndefined(s)) return defaultVal;
        s = s.trim();
        if (s.equalsIgnoreCase("nil") || s.equals("-")) return defaultVal;
        s = s.replace(",", "");
        try { return Integer.valueOf(s); }
        catch (NumberFormatException e) { return defaultVal; }
    }

    /** Parse long, treating "", "Nil", "-" etc. as defaultVal. */
    public static Long safeLong(String s, Long defaultVal) {
        if (isBlankOrUndefined(s)) return defaultVal;
        s = s.trim();
        if (s.equalsIgnoreCase("nil") || s.equals("-")) return defaultVal;
        s = s.replace(",", "");
        try { return Long.valueOf(s); }
        catch (NumberFormatException e) { return defaultVal; }
    }

    /** Parse money/amount, treating "", "Nil", "-" etc. as defaultVal. */
    public static BigDecimal safeMoney(String s, BigDecimal defaultVal) {
        if (isBlankOrUndefined(s)) return defaultVal;
        s = s.trim();
        if (s.equalsIgnoreCase("nil") || s.equals("-")) return defaultVal;
        s = s.replace(",", "");
        try { return new BigDecimal(s); }
        catch (NumberFormatException e) { return defaultVal; }
    }
}
