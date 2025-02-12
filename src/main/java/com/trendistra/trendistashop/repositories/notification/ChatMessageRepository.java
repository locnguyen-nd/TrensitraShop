package com.trendistra.trendistashop.repositories.notification;

import com.trendistra.trendistashop.entities.notification.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;
@Repository
public interface ChatMessageRepository extends JpaRepository<ChatMessage, UUID> {
    List<ChatMessage> findChatMessageByUserId(UUID userId);
}
