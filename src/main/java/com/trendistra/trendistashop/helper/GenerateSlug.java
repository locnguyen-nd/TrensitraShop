package com.trendistra.trendistashop.helper;

import java.text.Normalizer;

public class GenerateSlug {
    // Tiện ích sinh slug
    public static String generateSlug(String name) {
        return Normalizer.normalize(name, Normalizer.Form.NFKD)
                .replaceAll("\\p{InCombiningDiacriticalMarks}+", "")
                .toLowerCase()
                .replace(" ", "-")
                .replaceAll("[^a-z0-9-]", "");
    }

}
