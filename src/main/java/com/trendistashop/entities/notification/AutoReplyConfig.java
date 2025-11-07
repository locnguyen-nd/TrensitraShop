package com.trendistashop.entities.notification;

import com.trendistashop.entities.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 *
 * @author Locnd
 */
@Entity
@Table(name = "auto_reply_config")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AutoReplyConfig extends BaseEntity {
    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false)
    private String triggerKeyword;

    @Column(columnDefinition = "TEXT")
    private String replyMessage;

    private boolean enabled = true;

    @Column(nullable = false)
    private String scope;
    @Column(nullable = false)
    private boolean autoChatEnabled = true;
}
