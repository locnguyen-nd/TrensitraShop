package com.trendistashop.repositories.notification;

import com.trendistashop.entities.notification.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.UUID;
@Repository
public interface ChatMessageRepository extends JpaRepository<ChatMessage, UUID> {
    List<ChatMessage> findByConversationIdOrderBySentAtAsc(UUID conversationId);
    long countByConversationIdAndIsReadFalseAndReceiverId(UUID conversationId, UUID receiverId);

    @Query("SELECT m FROM ChatMessage m WHERE m.conversation.id = :convId AND m.isRead = false AND m.receiver.id = :receiverId")
    List<ChatMessage> findUnreadByConversationAndReceiver(@Param("convId") UUID convId, @Param("receiverId") UUID receiverId);
}
