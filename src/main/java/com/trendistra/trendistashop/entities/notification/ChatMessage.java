package com.trendistra.trendistashop.entities.notification;

import com.trendistra.trendistashop.entities.user.UserEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "chat_message")
public class ChatMessage {
    @Id
    @GeneratedValue
    private UUID id;
    private String sender; // người gửi
    private String content;
    private LocalDateTime timestamp = LocalDateTime.now();
    private UUID userId; // Id nguoi nhan gui
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name ="user_info", insertable = false, updatable = false)
    private UserEntity user;
    private boolean isAdmin;
    private boolean isRead;
}
