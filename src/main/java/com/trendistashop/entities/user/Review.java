package com.trendistashop.entities.user;

import com.trendistashop.entities.BaseEntity;
import com.trendistashop.entities.product.Product;
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
@Table(name = "review")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Review extends BaseEntity {
    @Id
    @GeneratedValue
    private UUID id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @Column(nullable = false)
    private Integer rating;

    @Column(columnDefinition = "TEXT")
    private String content;

    private Boolean isRecommended = false;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "review_media", joinColumns = @JoinColumn(name = "review_id"))
    @Column(name = "media_url")
    @Builder.Default
    private List<String> mediaUrls = new ArrayList<>();

    @Column(name = "is_approved")
    private Boolean isApproved = false;
    public void addMedia(String url) {
        if (url != null && !url.isBlank()) {
            this.mediaUrls.add(url.trim());
        }
    }
}
