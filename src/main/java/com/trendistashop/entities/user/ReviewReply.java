package com.trendistashop.entities.user;

import com.trendistashop.entities.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 *
 * @author Locnd
 */
@Entity
@Table(name = "review_reply")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewReply extends BaseEntity {
    @Id
    @GeneratedValue
    private UUID id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_id", nullable = false)
    private Review review;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;
    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_reply_id")
    private ReviewReply parentReply;
    @OneToMany(mappedBy = "parentReply", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<ReviewReply> children = new ArrayList<>();
    private Boolean isAdminReply = false;

    public void addChild(ReviewReply child) {
        children.add(child);
        child.parentReply = this;
    }
}
