package com.trendistashop.services;

import com.cloudinary.Cloudinary;
import com.cloudinary.Transformation;
import com.cloudinary.utils.ObjectUtils;
import com.trendistashop.enums.CloudinaryEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Slf4j
public class CloudinaryService { 
    private final Cloudinary cloudinary;
    private final Executor taskExecutor;
    private static final int CACHE_TTL_SECONDS = 300;
    private final Map<String, CacheEntry> listCache = new ConcurrentHashMap<>();

    public CloudinaryService(Cloudinary cloudinary, @Qualifier("taskExecutor") Executor taskExecutor) {
        this.cloudinary = cloudinary;
        this.taskExecutor = taskExecutor;
    }
    public Executor getExecutor() {
        return taskExecutor;
    }
    @Async("taskExecutor")
    public CompletableFuture<Map<String, Object>> listResourcesAsync(
            CloudinaryEnum folderEnum, String subFolder, String search, String type,
            int page, int size, String sortBy, String sortDir) {

        return CompletableFuture.supplyAsync(() -> {
            try {
                return listResources(folderEnum, subFolder, search, type, page, size, sortBy, sortDir);
            } catch (Exception e) {
                log.error(e.getMessage(), e);
                throw new CompletionException(e);
            }
        }, taskExecutor);
    }

    @SuppressWarnings("unchecked")
    public Map<String, Object> listResources(
            CloudinaryEnum folderEnum, String subFolder, String search, String type,
            int page, int size, String sortBy, String sortDir) throws Exception {

        String prefix = buildFolderPath(folderEnum, subFolder) + "/";
        String cacheKey = prefix + "|" + (search != null ? search : "") + "|" +
                (type != null ? type : "") + "|" + page + "|" + size + "|" + sortBy + "|" + sortDir;

        CacheEntry cached = listCache.get(cacheKey);
        if (cached != null && !cached.isExpired()) {
            return cached.data;
        }

        Map<String, Object> options = new HashMap<>();
        options.put("max_results", Math.min(size, 500));
        options.put("type", "upload");

        if (search != null && !search.trim().isEmpty()) {
            options.remove("prefix");
        } else {
            options.put("prefix", prefix);
            options.put("resource_type", getValidResourceType(type));
        }

        List<Map<String, Object>> allResources = new ArrayList<>();
        String nextCursor = null;

        do {
            if (nextCursor != null) options.put("next_cursor", nextCursor);
            Map<String, Object> result = cloudinary.api().resources(options);
            List<Map<String, Object>> batch = (List<Map<String, Object>>) result.get("resources");
            if (batch == null || batch.isEmpty()) break;
            allResources.addAll(batch);
            nextCursor = (String) result.get("next_cursor");
        } while (nextCursor != null && allResources.size() < (page + 1) * size + 200);

        Stream<Map<String, Object>> stream = allResources.stream();
        if (search != null && !search.trim().isEmpty()) {
            String keyword = search.trim().toLowerCase();
            stream = stream.filter(r -> {
                String publicId = (String) r.get("public_id");
                String fileName = publicId.substring(publicId.lastIndexOf("/") + 1);
                return fileName.toLowerCase().contains(keyword);
            });
        }

        List<Map<String, Object>> processed = stream
                .map(res -> {
                    String publicId = (String) res.get("public_id");
                    String originalSecureUrl = (String) res.get("secure_url");
                    String fileName = publicId.substring(publicId.lastIndexOf("/") + 1);
                    String folderPath = publicId.substring(0, Math.max(publicId.lastIndexOf("/"), 0));
                    String desktopThumbnail = cloudinary.url()
                            .secure(true)
                            .transformation(new Transformation()
                                    .width(200).height(200).crop("fill").gravity("auto")
                                    .quality("auto:best")
                                    .fetchFormat("auto")
                                    .flags("progressive"))
                            .generate(publicId);

                    String mobileThumbnail = cloudinary.url()
                            .secure(true)
                            .transformation(new Transformation()
                                    .width(80).height(80).crop("limit")
                                    .quality("auto:eco")
                                    .fetchFormat("auto")
                                    .dpr("auto")
                                    .flags("progressive"))
                            .generate(publicId);

                    String placeholder = cloudinary.url()
                            .secure(true)
                            .transformation(new Transformation()
                                    .width(20).height(20).crop("fill")
                                    .quality("auto:low")
                                    .effect("blur:1000")
                                    .fetchFormat("auto"))
                            .generate(publicId);

                    Map<String, Object> r = new HashMap<>(res);
                    r.put("url", originalSecureUrl);
                    r.put("thumbnail", desktopThumbnail);
                    r.put("thumbnail_mobile", mobileThumbnail);
                    r.put("placeholder", placeholder);
                    r.put("public_id", publicId);
                    r.put("filename", fileName);
                    r.put("folder", folderPath.isEmpty() ? "/" : folderPath);
                    r.put("resource_type", res.get("resource_type"));

                    return r;
                })
                .collect(Collectors.toList());

        Comparator<Map<String, Object>> comparator = switch (sortBy == null ? "created_at" : sortBy) {
            case "filename" -> Comparator.comparing(m -> (String) m.get("filename"));
            case "bytes" -> Comparator.comparingLong(m -> (Long) m.get("bytes"));
            default -> Comparator.comparing(m -> (String) m.get("created_at"));
        };
        if ("desc".equalsIgnoreCase(sortDir == null ? "desc" : sortDir)) {
            comparator = comparator.reversed();
        }

        List<Map<String, Object>> finalList = processed.stream()
                .sorted(comparator)
                .skip((long) page * size)
                .limit(size)
                .collect(Collectors.toList());

        Map<String, Object> result = new HashMap<>();
        result.put("items", finalList);
        result.put("total", processed.size());
        result.put("page", page);
        result.put("size", size);
        result.put("hasMore", processed.size() > (page + 1) * size);
        listCache.put(cacheKey, new CacheEntry(result, System.currentTimeMillis()));
        log.info("Get list media success !");
        return result;
    }

    private String getValidResourceType(String type) {
        if (type == null || type.trim().isEmpty()) return "image";
        return "video".equalsIgnoreCase(type.trim()) ? "video" : "image";
    }
    @Async("taskExecutor")
    public CompletableFuture<Map<String, String>> uploadFileAsync(MultipartFile file, String fileName, CloudinaryEnum folderEnum, String subFolder) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                String resourceType = file.getContentType() != null && file.getContentType().startsWith("video") ? "video" : "image";
                String folderPath = buildFolderPath(folderEnum, subFolder);
                String publicId = generateUniqueFileName(fileName, file.getOriginalFilename());

                Map<String, Object> options = Map.of(
                        "folder", folderPath,
                        "public_id", publicId,
                        "overwrite", true,
                        "resource_type", resourceType,
                        "quality", "auto",
                        "fetch_format", "auto"
                );

                long start = System.currentTimeMillis();
                Map result = cloudinary.uploader().upload(file.getBytes(), options);
                log.info("Uploaded {} in {}ms", publicId, System.currentTimeMillis() - start);

                return Map.of(
                        "url", result.get("secure_url").toString(),
                        "public_id", result.get("public_id").toString()
                );
            } catch (Exception e) {
                log.error("Upload failed: {}", e.getMessage(), e);
                return Map.of("error", "Upload failed: " + e.getMessage());
            }
        }, taskExecutor);
    }
    @Async("taskExecutor")
    public CompletableFuture<List<Map<String, String>>> uploadFilesAsync(List<MultipartFile> files, CloudinaryEnum folderEnum, String subFolder) {
        List<CompletableFuture<Map<String, String>>> futures = files.stream()
                .map(f -> uploadFileAsync(f, null, folderEnum, subFolder))
                .toList();

        return CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]))
                .thenApply(v -> futures.stream().map(CompletableFuture::join).toList());
    }

    public Map<String, String> uploadFile(MultipartFile file, String fileName, CloudinaryEnum folderEnum, String subFolder) throws IOException {
        Map<String, String> result = uploadFileAsync(file, fileName, folderEnum, subFolder).join();
        if (result.containsKey("error")) throw new IOException(result.get("error"));
        return result;
    }

    public List<Map<String, String>> uploadFiles(List<MultipartFile> files, CloudinaryEnum folderEnum, String subFolder) throws IOException {
        List<Map<String, String>> results = uploadFilesAsync(files, folderEnum, subFolder).join();
        List<String> errors = results.stream().filter(r -> r.containsKey("error")).map(r -> r.get("error")).toList();
        if (!errors.isEmpty()) throw new IOException("Upload failed: " + String.join(", ", errors));
        return results;
    }
    public Map<String, Object> renameFile(String publicId, String newName) throws IOException {
        try {
            String folder = publicId.substring(0, publicId.lastIndexOf("/"));
            String newPublicId = folder + "/" + newName.trim();
            Map result = cloudinary.uploader().rename(publicId, newPublicId, ObjectUtils.asMap("overwrite", true, "invalidate", true));
            listCache.clear();
            return Map.of("old_public_id", publicId, "new_public_id", newPublicId, "url", result.get("secure_url"));
        } catch (Exception e) {
            throw new IOException("Rename failed: " + e.getMessage(), e);
        }
    }

    public String moveFile(String publicId, CloudinaryEnum toFolder, String toSubFolder) throws IOException {
        try {
            String fileName = publicId.substring(publicId.lastIndexOf("/") + 1);
            String newFolder = buildFolderPath(toFolder, toSubFolder);
            String newPublicId = newFolder + "/" + fileName;
            Map result = cloudinary.uploader().rename(publicId, newPublicId, ObjectUtils.asMap("overwrite", true, "invalidate", true));
            listCache.clear();
            return (String) result.get("secure_url");
        } catch (Exception e) {
            throw new IOException("Move failed", e);
        }
    }

    public void deleteFile(String url) {
        try {
            String publicId = url.split("/upload/")[1].split("\\.")[0];
            cloudinary.uploader().destroy(publicId, ObjectUtils.asMap("invalidate", true));
        } catch (Exception e) {
            log.error("Delete failed: {}", e.getMessage());
        }
    }

    public void deleteFilesInFolder(CloudinaryEnum folderEnum, String subFolder) {
        String path = buildFolderPath(folderEnum, subFolder);
        try {
            cloudinary.api().deleteResourcesByPrefix(path, ObjectUtils.asMap("invalidate", true));
        } catch (Exception e) {
            log.error("Delete folder content failed: {}", e.getMessage());
        }
    }

    public void deleteFolder(CloudinaryEnum folderEnum, String subFolder) {
        String path = buildFolderPath(folderEnum, subFolder);
        try {
            cloudinary.api().deleteFolder(path, ObjectUtils.emptyMap());
        } catch (Exception e) {
            log.error("Delete folder failed: {}", e.getMessage());
        }
    }

    private String generateUniqueFileName(String fileName, String originalFileName) {
        String base = fileName != null && !fileName.trim().isEmpty()
                ? fileName.substring(0, fileName.lastIndexOf('.') > 0 ? fileName.lastIndexOf('.') : fileName.length())
                : originalFileName != null && originalFileName.contains(".")
                ? originalFileName.substring(0, originalFileName.lastIndexOf('.'))
                : UUID.randomUUID().toString();
        return base + "_" + UUID.randomUUID().toString().substring(0, 8);
    }

    private String buildFolderPath(CloudinaryEnum folderEnum, String subFolder) {
        String base = folderEnum != null ? folderEnum.getFolderPath() : "root";
        return subFolder != null && !subFolder.trim().isEmpty() ? base + "/" + subFolder.trim() : base;
    }

    private static class CacheEntry {
        final Map<String, Object> data;
        final long timestamp;
        CacheEntry(Map<String, Object> data, long timestamp) {
            this.data = data;
            this.timestamp = timestamp;
        }
        boolean isExpired() {
            return System.currentTimeMillis() - timestamp > CACHE_TTL_SECONDS * 1000;
        }
    }
}