package com.trendistashop.controllers.user;

import com.trendistashop.dto.chat.*;
import com.trendistashop.dto.response.PageDTO;
import com.trendistashop.dto.response.TypeResponse;
import com.trendistashop.entities.user.UserEntity;
import com.trendistashop.enums.ConversationStatus;
import com.trendistashop.services.impl.notification.ChatService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("${api.prefix}/chat")
@CrossOrigin
@Tag(name = "Chat API", description = "API quản lý chat, sử dụng Websocket")
@Slf4j
public class ChatController {
     @Autowired
     private ChatService chatService;

     @PostMapping("/conversation")
     @Operation(summary = "Tạo đoạn chat mới hoặc load lịch sử khi người dùng mở thanh chat")
     public ResponseEntity<TypeResponse<ConversationResponseDTO>> createOrGetConversation(
            @RequestBody(required = false) Map<String, Object> requestBody,
            Principal principal,
            HttpServletRequest request) {
            UUID guestSessionId = null;
            UserEntity currentUser = null;
            if (principal != null) {
                Authentication auth = (Authentication) principal;
                currentUser = (UserEntity) auth.getPrincipal();
            }
            // Case 2: Guest user (không có JWT hoặc JWT không hợp lệ)
            else {
                Object guestIdObj = requestBody.get("guestSessionId");
                String guestIdStr = guestIdObj.toString();
                guestSessionId = UUID.fromString(guestIdStr);
            }

            TypeResponse<ConversationResponseDTO> response = chatService.getOrCreateConversation(guestSessionId, currentUser);
            return ResponseEntity.status(response.getStatusCode()).body(response);
        }
    /**
     * Gửi tin nhắn
     */
    @PostMapping("/send")
    @Operation(summary = "Gửi tin nhắn")
    public ResponseEntity<TypeResponse<MessageResponseDTO>> sendMessage(@RequestBody SendMessageRequestDTO request) {
            TypeResponse<MessageResponseDTO> response = chatService.sendMessage(request);
            return ResponseEntity.status(response.getStatusCode()).body(response);
    }
    /**
     * Lấy chi tiết conversation
     */
    @GetMapping("/conversation/{id}")
    @Operation(summary = "Truy cập 1 đoạn chat")
    public ResponseEntity<TypeResponse<ConversationResponseDTO>> getConversation(@PathVariable UUID id) {
        TypeResponse<ConversationResponseDTO> response = chatService.getConversation(id);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    /**
     *  Admin: Lấy danh sách tất cả sessions đang active
     */
    @GetMapping("/admin/sessions")
    @Operation(summary = "Lấy danh sách session chat cho Admin",
            description = "Hỗ trợ tìm kiếm, lọc, phân trang, sắp xếp")
    public ResponseEntity<TypeResponse<PageDTO<ChatSessionDTO>>> getActiveSessions(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) ConversationStatus status,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fromDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime toDate,
            @RequestParam(defaultValue = "0") @Min(0) Integer page,
            @RequestParam(defaultValue = "10") @Min(1) @Max(100) Integer size,
            @RequestParam(defaultValue = "lastMessageAt") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDir
    ) {
        FilterDTO filter = FilterDTO.builder()
                .search(search)
                .status(status)
                .fromDate(fromDate)
                .toDate(toDate)
                .page(page != null ? page : 0)
                .size(size != null ? size : 10)
                .sortBy(StringUtils.hasText(sortBy) ? sortBy : "lastMessageAt")
                .sortDir(StringUtils.hasText(sortDir) ? sortDir.toLowerCase() : "desc")
                .build();

        TypeResponse<PageDTO<ChatSessionDTO>> response = chatService.getActiveSessions(filter);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }
    /**
     * Admin: Lấy thống kê chat
     */
    @GetMapping("/admin/statistics")
    @Operation(summary = "Thống kê các thông tin kênh chat cho admin")
    public ResponseEntity<TypeResponse<ChatStatisticsDTO>> getStatistics() {
        TypeResponse<ChatStatisticsDTO> response = chatService.getStatistics();
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }
}
