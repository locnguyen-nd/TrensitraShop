package com.trendistashop.entities.notification;

import com.trendistashop.entities.BaseEntity;
import com.trendistashop.entities.user.UserEntity;
import com.trendistashop.enums.ConversationStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 *
 * @author Locnd
 */
@Entity
@Table(name = "chat_conversation")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = "messages")
public class ChatConversation extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String title; // Tên hiển thị: "Khách hàng: Nguyễn Văn A" hoặc "Guest #123"

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @Column(name = "guest_session_id")
    private UUID guestSessionId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "admin_id")
    private UserEntity admin;

    @Column(name = "started_at")
    private LocalDateTime startedAt;

    @Column(name = "last_message_at")
    private LocalDateTime lastMessageAt;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private ConversationStatus status = ConversationStatus.ACTIVE;

    @OneToMany(mappedBy = "conversation", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ChatMessage> messages = new ArrayList<>();

    @PrePersist
    protected void onCreate() {
        this.startedAt = LocalDateTime.now();
        this.lastMessageAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.lastMessageAt = LocalDateTime.now();
    }
}
