package com.trendistashop.services.impl.notification;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.*;

/**
 * Service để tương tác với Gemini API
 * @author Locnd
 */
@Service
@Slf4j
public class AIChatService {
    @Value("${app.gemini.api.key}")
    private String apiKey;
    @Value("${app.gemini.api.url}")
    private String geminiApi;
    @Value("${app.gemini.api.model}")
    private String model;
    @Value("${app.gemini.api.email}")
    private String botEmail;

    private final WebClient webClient;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public AIChatService(@Qualifier("geminiWebClient") WebClient webClient) {
        this.webClient = webClient;
    }

    public String getBotEmail() {
        if (botEmail == null) {
            return "trolytrendista@gmail.com";
        }
        return botEmail;
    }

    public String generateReply(String userMessage, List<Map<String, String>> history) {
        try {
            // Build contents
            List<Map<String, Object>> contents = buildContents(userMessage, history);

            // Tạo request body
            Map<String, Object> requestBody = new LinkedHashMap<>();
            requestBody.put("contents", contents);

            log.debug("Calling Gemini API with {} messages", contents.size());

            // Gọi API với retry mechanism
            Map responseMap = webClient.post()
                    .uri(geminiApi)
                    .header("x-goog-api-key", apiKey)
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(requestBody)
                    .retrieve()
                    .onStatus(
                            HttpStatusCode::is4xxClientError,
                            response -> response.bodyToMono(String.class)
                                    .flatMap(errorBody -> {
                                        log.error("Gemini 4xx Error: {}", errorBody);
                                        return Mono.error(new RuntimeException("Client error: " + errorBody));
                                    })
                    )
                    .onStatus(
                            HttpStatusCode::is5xxServerError,
                            response -> response.bodyToMono(String.class)
                                    .flatMap(errorBody -> {
                                        log.warn("Gemini 5xx Error (will retry): {}", errorBody);
                                        return Mono.error(new RuntimeException("Server error: " + errorBody));
                                    })
                    )
                    .bodyToMono(Map.class)
                    .retryWhen(Retry.backoff(3, Duration.ofSeconds(2))
                            .maxBackoff(Duration.ofSeconds(10))
                            .filter(throwable -> {
                                String msg = throwable.getMessage();
                                return msg != null && (
                                        msg.contains("503") ||
                                                msg.contains("429") ||
                                                msg.contains("overloaded") ||
                                                msg.contains("timeout")
                                );
                            })
                            .doBeforeRetry(retrySignal ->
                                    log.warn("Retrying Gemini API call, attempt: {}", retrySignal.totalRetries() + 1)
                            )
                    )
                    .block(); // Block để đồng bộ với code hiện tại

            // Parse response
            return parseGeminiResponse(responseMap);

        } catch (Exception e) {
            log.error("Gemini error after retries: {}", e.getMessage(), e);

            // Xử lý các loại lỗi khác nhau
            String errorMsg = e.getMessage();
            if (errorMsg != null) {
                if (errorMsg.contains("503") || errorMsg.contains("overloaded")) {
                    return "Hệ thống đang bận, bạn vui lòng thử lại sau vài giây nhé! 😊";
                } else if (errorMsg.contains("429")) {
                    return "Hệ thống đang xử lý nhiều yêu cầu, bạn chờ chút xíu nhé! ⏳";
                }
            }
            return "Xin lỗi, tôi đang bận. Vui lòng đợi admin nhé! 🙏";
        }
    }
    /**
     * Xây dựng contents cho Gemini API
     */
    private List<Map<String, Object>> buildContents(String userMessage, List<Map<String, String>> history) {
        List<Map<String, Object>> contents = new ArrayList<>();

        // Thêm system prompt ở lần đầu tiên
        boolean isFirstMessage = (history == null || history.isEmpty());
        if (isFirstMessage) {
            Map<String, Object> userGreeting = new LinkedHashMap<>();
            userGreeting.put("role", "user");
            userGreeting.put("parts", Collections.singletonList(
                    Collections.singletonMap("text", "Hello")
            ));
            contents.add(userGreeting);

            Map<String, Object> systemPrompt = new LinkedHashMap<>();
            systemPrompt.put("role", "model");
            systemPrompt.put("parts", Collections.singletonList(
                    Collections.singletonMap("text", getSystemPrompt())
            ));
            contents.add(systemPrompt);
        }

        // Thêm history
        if (history != null) {
            for (Map<String, String> msg : history) {
                String role = "user".equals(msg.get("role")) ? "user" : "model";
                Map<String, Object> historyMsg = new LinkedHashMap<>();
                historyMsg.put("role", role);
                historyMsg.put("parts", Collections.singletonList(
                        Collections.singletonMap("text", msg.get("content"))
                ));
                contents.add(historyMsg);
            }
        }

        // Thêm user message hiện tại
        Map<String, Object> currentMsg = new LinkedHashMap<>();
        currentMsg.put("role", "user");
        currentMsg.put("parts", Collections.singletonList(
                Collections.singletonMap("text", userMessage)
        ));
        contents.add(currentMsg);

        return contents;
    }

    /**
     * Parse response từ Gemini API
     */
    private String parseGeminiResponse(Map<String, Object> data) {
        if (data == null || !data.containsKey("candidates")) {
            log.error("Gemini response invalid: {}", data);
            return "Xin lỗi, tôi đang gặp lỗi kỹ thuật. Vui lòng thử lại sau nhé!";
        }

        List<Map<String, Object>> candidates = (List<Map<String, Object>>) data.get("candidates");
        if (candidates == null || candidates.isEmpty()) {
            return "Tôi chưa hiểu rõ, bạn có thể nói lại được không ạ?";
        }

        Map<String, Object> candidate = candidates.get(0);
        if (!candidate.containsKey("content")) {
            log.warn("Gemini candidate missing content: {}", candidate);
            return "Tôi chưa hiểu rõ, bạn có thể nói lại được không ạ?";
        }

        Map<String, Object> content = (Map<String, Object>) candidate.get("content");
        List<Map<String, Object>> parts = (List<Map<String, Object>>) content.get("parts");

        if (parts == null || parts.isEmpty()) {
            return "Tôi chưa hiểu rõ, bạn có thể nói lại được không ạ?";
        }

        String reply = (String) parts.get(0).get("text");
        log.info("Gemini replied successfully");
        return reply;
    }
    /**
     * System Prompt
     */
    private String getSystemPrompt() {
        return "Mày là cô bán hàng siêu lầy lội của Trendista Shop nha " +
                "Nói chuyện như bạn thân, ngắn gọn, nghịch nghịch, thỉnh thoảng có thả thêm emoji. " +
                "Khách hỏi gì trả lời đúng cái đó, không vòng vo tam quốc. " +
                "Tư vấn size phải thật chuẩn, phối đồ phải chất, giá phải rõ. " +
                "Không biết thì bảo thẳng: 'Đợi tí chị ơi, để em check kho liền! ️' " +
                "Khách chốt đơn thì bảo: 'Để em lên đơn đi liền nhé !' " +
                "Luôn luôn kết thúc bằng câu hỏi hoặc lời rủ rê mua thêm, kiểu bạn bè dụ nhau shopping ấy!";
    }
}