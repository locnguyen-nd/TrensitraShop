package com.trendistashop.config;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 *
 * @author Locnd
 */
@Configuration
@RequiredArgsConstructor
public class UploadConfig {

    @Value("${app.upload-dir}")
    private String uploadDir;

    @PostConstruct
    public void init() throws IOException {
        Path root = Paths.get(uploadDir);
        Path invoices = root.resolve("invoices");
        Path exports = root.resolve("exports");
        Path images = root.resolve("images");

        Files.createDirectories(invoices);
        Files.createDirectories(exports);
        Files.createDirectories(images);

        System.out.println("Upload directories created:");
        System.out.println("  - Invoices: " + invoices.toAbsolutePath());
        System.out.println("  - Exports:  " + exports.toAbsolutePath());
        System.out.println("  - Images:   " + images.toAbsolutePath());
    }

    public Path getUploadPath() {
        return Paths.get(uploadDir);
    }

    public Path getInvoicePath() {
        return getUploadPath().resolve("invoices");
    }

    public Path getExportPath() {
        return getUploadPath().resolve("exports");
    }

    public Path getImagePath() {
        return getUploadPath().resolve("images");
    }
}
