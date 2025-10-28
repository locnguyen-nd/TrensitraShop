package com.trendistashop.helper;

import java.text.Normalizer;
import java.util.HashMap;
import java.util.Map;

public class GenerateSlug {

    // Map các ký tự đặc biệt → từ thay thế (ĐÃ LOẠI BỎ dấu -)
    private static final Map<String, String> SPECIAL_CHAR_REPLACEMENTS = new HashMap<>();

    static {
        SPECIAL_CHAR_REPLACEMENTS.put("&", "va");
        SPECIAL_CHAR_REPLACEMENTS.put("@", "at");
        SPECIAL_CHAR_REPLACEMENTS.put("#", "sharp");
        SPECIAL_CHAR_REPLACEMENTS.put("%", "percent");
        SPECIAL_CHAR_REPLACEMENTS.put("\\+", "plus");
        SPECIAL_CHAR_REPLACEMENTS.put("\\*", "star");
        SPECIAL_CHAR_REPLACEMENTS.put("!", "exclamation");
        SPECIAL_CHAR_REPLACEMENTS.put("\\?", "question");
        SPECIAL_CHAR_REPLACEMENTS.put("\\.", "");
        SPECIAL_CHAR_REPLACEMENTS.put("'", "");
        SPECIAL_CHAR_REPLACEMENTS.put("\"", "");
        SPECIAL_CHAR_REPLACEMENTS.put("\\(", "");
        SPECIAL_CHAR_REPLACEMENTS.put("\\)", "");
        SPECIAL_CHAR_REPLACEMENTS.put("\\[", "");
        SPECIAL_CHAR_REPLACEMENTS.put("\\]", "");
        SPECIAL_CHAR_REPLACEMENTS.put(",", "");
        SPECIAL_CHAR_REPLACEMENTS.put(":", "");
        SPECIAL_CHAR_REPLACEMENTS.put(";", "");
    }

    public static String generateSlug(String name) {
        if (name == null || name.isEmpty()) {
            return "";
        }
        String slug = name;
        slug = Normalizer.normalize(slug, Normalizer.Form.NFD);
        slug = slug.replaceAll("\\p{M}", "");
        slug = slug.replace("đ", "d").replace("Đ", "d");
        for (Map.Entry<String, String> entry : SPECIAL_CHAR_REPLACEMENTS.entrySet()) {
            slug = slug.replaceAll(entry.getKey(), entry.getValue());
        }
        slug = slug.replaceAll("-", " ");
        slug = slug
                .toLowerCase()
                .trim()
                .replaceAll("\\s+", "-")
                .replaceAll("-+", "-")
                .replaceAll("[^a-z0-9-]", "")
                .replaceAll("^-+|-+$", "");

        return slug.isEmpty() ? "" : slug;
    }
}
