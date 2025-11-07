package com.trendistashop.entities.notification;

import com.trendistashop.entities.BaseEntity;
import com.trendistashop.entities.user.UserEntity;
import com.trendistashop.enums.NotificationStatus;
import com.trendistashop.enums.NotificationType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Comment;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "notification")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = "recipients")
public class Notification extends BaseEntity {
    @Id
    @GeneratedValue
    private UUID id;
    @Column(nullable = false, length = 500)
    private String title;

    @Column(nullable = false, length = 1000)
    private String content;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private NotificationType type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by", nullable = false)
    private UserEntity createdBy;

    @Comment("Lịch gửi thông báo")
    private LocalDateTime scheduledAt; // Lịch gửi
    @Comment("Trạng thái gửi")
    private boolean isSent = false;
    @Comment("Trạng thái xuất bản draft/publish")
    private boolean isDraft = true; // true = nháp, false = đã publish

    @OneToMany(mappedBy = "notification", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private Set<NotificationRecipient> recipients = new HashSet<>();
}
