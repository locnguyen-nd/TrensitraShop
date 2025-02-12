package com.trendistra.trendistashop.controllers.user;

import com.trendistra.trendistashop.dto.response.ChatMessageDTO;
import com.trendistra.trendistashop.entities.notification.ChatMessage;
import com.trendistra.trendistashop.mapper.ChatMapper;
import com.trendistra.trendistashop.repositories.notification.ChatMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

import java.time.ZoneId;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("${api.prefix}/chat")
@CrossOrigin(origins = "http://127.0.0.1:5500")
public class ChatController {
    @Autowired
    private ChatMessageRepository chatMessageRepository;
    @Autowired
    private ChatMapper chatMapper;
    // Lấy lịch sử chat của một user cụ thể theo userId
    @GetMapping("/history/{userId}")
    public List<ChatMessageDTO> getChatHistory(@PathVariable UUID userId) {
        List<ChatMessage> chatMessages = chatMessageRepository.findChatMessageByUserId(userId);
        return chatMessages.stream()
                .map(chatMapper::chatMessageDTO)
                .collect(Collectors.toList());
    }
    @GetMapping("/admin/messages")
    public Map<UUID, List<ChatMessageDTO>> getAllMessages() {
        List<ChatMessage> allMessages = chatMessageRepository.findAll();
        return allMessages.stream()
                .collect(Collectors.groupingBy(
                        ChatMessage::getUserId,
                        Collectors.mapping(chatMapper::chatMessageDTO, Collectors.toList())
                ));
    }
}
