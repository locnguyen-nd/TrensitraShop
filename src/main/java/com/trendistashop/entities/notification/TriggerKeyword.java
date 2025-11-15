package com.trendistashop.entities.notification;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.UUID;

/**
 *
 * @author Locnd
 */
@Entity
@Table(name = "trigger_keywords", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"keyword", "rule_id"})
})
@Data
@EqualsAndHashCode(exclude = "rule")
public class TriggerKeyword {
    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false)
    private String keyword;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rule_id", nullable = false)
    private AutoReplyConfig rule;
}
