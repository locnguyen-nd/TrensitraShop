package com.trendistra.trendistashop.entities;

import com.trendistra.trendistashop.enums.BannerTypeEnum;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.web.bind.annotation.GetMapping;

@Entity
@Table(name = "banners")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Banner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String imageUrl;
    private String linkUrl;

    @Enumerated(EnumType.STRING)
    private BannerTypeEnum type; // MAIN, PROMO, CATEGORY

    @Column
    private Integer displayOrder;

    @Column
    private Boolean isActive;
}