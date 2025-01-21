package com.trendistra.trendistashop.helper;

import java.text.Normalizer;

public class GenerateSlug {
    public static String generateSlug(String name) {
        // Normalize the string and remove diacritical marks (accents)
        String normalized = Normalizer.normalize(name, Normalizer.Form.NFD);
        normalized = normalized.replaceAll("\\p{M}", "");  // Remove accents
        String slug = normalized.toLowerCase()
                .trim() // Remove leading and trailing spaces
                .replaceAll("\\s+", "-") // Replace multiple spaces with a single hyphen
                .replaceAll("[^a-z0-9-]", ""); // Remove non-alphanumeric characters except hyphens
        slug = slug.replaceAll("^-|-$", "");
        return slug;
    }

}
