package com.trendistashop.entities;

import com.trendistashop.enums.BannerTypeEnum;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "banners")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Banner extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String event;
    @Enumerated(EnumType.STRING)
    private BannerTypeEnum type;
    @Column
    private Boolean isActive;
    @OneToMany(mappedBy = "banner", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BannerImage> bannerImages;
}