package com.trendistra.trendistashop.mapper;

import com.trendistra.trendistashop.dto.response.ChatMessageDTO;
import com.trendistra.trendistashop.entities.notification.ChatMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.ZoneOffset;

@Component
public class ChatMapper {
    @Autowired
    private UserDetailMapper userDetailMapper;
    public ChatMessageDTO chatMessageDTO(ChatMessage message) {
        return ChatMessageDTO.builder()
                .id(message.getId())
                .sender(message.getSender())
                .content(message.getContent())
                .timestamp(message.getTimestamp().toEpochSecond(ZoneOffset.UTC))
                .userId(message.getUserId())
                .user(userDetailMapper.convertToDto(message.getUser()))
                .isFromAdmin(message.isAdmin())
                .isRead(message.isRead())
                .build();
    }
}
