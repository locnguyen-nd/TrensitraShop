package com.trendistashop.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "banner_images")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class BannerImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String imageUrl;
    private String linkUrl;
    private String content;
    private Integer displayOrder;
    // 1-n
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "banner_id")
    private Banner banner;
}
