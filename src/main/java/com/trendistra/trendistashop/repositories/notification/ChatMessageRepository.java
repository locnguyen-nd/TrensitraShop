package com.trendistra.trendistashop.repositories.notification;

import com.trendistra.trendistashop.entities.notification.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;
@Repository
public interface ChatMessageRepository extends JpaRepository<ChatMessage, UUID> {
    @Query("SELECT cm FROM chat_message cm WHERE " +
            "(cm.sender.id = :senderId AND cm.receiver.id = :receiverId) OR " +
            "(cm.sender.id = :receiverId AND cm.receiver.id = :senderId) " +
            "ORDER BY cm.createdAt ASC")
    List<ChatMessage> findChatHistoryBetweenUsers(@Param("senderId") UUID senderId, @Param("receiverId") UUID receiverId);

    List<ChatMessage> findByReceiverId(UUID senderId);
}
