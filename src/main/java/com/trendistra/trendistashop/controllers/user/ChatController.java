package com.trendistra.trendistashop.controllers.user;

import com.trendistra.trendistashop.dto.response.ChatMessageDTO;
import com.trendistra.trendistashop.entities.notification.ChatMessage;
import com.trendistra.trendistashop.services.CloudinaryService;
import com.trendistra.trendistashop.services.impl.notification.ChatService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("${api.prefix}/chat")
@CrossOrigin(origins = "http://127.0.0.1:5500")
public class ChatController {
     @Autowired
     private ChatService chatService;
     @Autowired
     private CloudinaryService cloudinaryService;

    @PostMapping("/send-message")
    public ResponseEntity<ChatMessageDTO> sendMessage(
            Principal principal,
            @RequestBody ChatMessageDTO request) {
        ChatMessageDTO dto = ChatMessageDTO.builder()
                .receiverId(request.getReceiverId())
                .message(request.getMessage())
                .build();
        ChatMessageDTO response = chatService.sendMessage(dto, principal);
        return ResponseEntity.ok(response);
    }
    @PostMapping(value = "/send-image", consumes = "multipart/form-data")
    public ResponseEntity<ChatMessageDTO> sendImage(
            Principal principal,
            @RequestParam("receiverId") UUID receiverId,
            @RequestParam(value = "caption", required = false) String caption,
            @RequestParam("image") MultipartFile image) {
        try {
            // Tạo tên file với receiverId, không cần phần mở rộng vì CloudinaryService sẽ xử lý
            String fileName = "chat_" + receiverId.toString();
            String imageUrl = cloudinaryService.uploadFile(image, fileName, "CHAT");
            ChatMessageDTO response = chatService.sendImage(principal, receiverId, imageUrl, caption);
            return ResponseEntity.ok(response);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }
    }
    @PostMapping("/send-product-link")
    public ResponseEntity<ChatMessageDTO> sendProductLink(
            Principal principal,
            @RequestBody ProductLinkRequest request) {
        ChatMessageDTO response = chatService.sendProductLink(
                principal,
                request.getReceiverId(),
                request.getProductId(),
                request.getProductName(),
                request.getProductImageUrl(),
                request.getProductPrice()
        );
        return ResponseEntity.ok(response);
    }
    @GetMapping("/{receiverId}")
    public ResponseEntity<List<?>> getChatHistory(
            Principal principal,
            @PathVariable UUID receiverId) {
        return ResponseEntity.ok(chatService.getChatHistory(principal, receiverId));
    }
    static class ProductLinkRequest {
        private UUID receiverId;
        private String productId;
        private String productName;
        private String productImageUrl;
        private String productPrice;

        public UUID getReceiverId() { return receiverId; }
        public String getProductId() { return productId; }
        public String getProductName() { return productName; }
        public String getProductImageUrl() { return productImageUrl; }
        public String getProductPrice() { return productPrice; }

        public void setReceiverId(UUID receiverId) { this.receiverId = receiverId; }
        public void setProductId(String productId) { this.productId = productId; }
        public void setProductName(String productName) { this.productName = productName; }
        public void setProductImageUrl(String productImageUrl) { this.productImageUrl = productImageUrl; }
        public void setProductPrice(String productPrice) { this.productPrice = productPrice; }
    }


}
