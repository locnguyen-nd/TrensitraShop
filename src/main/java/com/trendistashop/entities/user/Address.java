package com.trendistashop.entities.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.trendistashop.entities.BaseEntity;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Table(name = "Address")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Address extends BaseEntity {
    @Id
    @GeneratedValue
    private UUID id;
    private String name;
    @Column(nullable = false)
    private String provinceId;   // Mã tỉnh (GHN)
    @Column(nullable = false)
    private String provinceName; // Tên tỉnh
    @Column(nullable = false)
    private String districtId;   // Mã huyện (GHN)
    @Column(nullable = false)
    private String districtName; // Tên huyện
    @Column(nullable = false)
    private String wardCode;     // Mã phường (GHN)
    @Column(nullable = false)
    private String wardName;     // Tên phường
    @Column(nullable = false)
    private String specAddress;  // Số nhà, đường...
    @Column(nullable = false)
    private String phoneNumber;
    private Boolean isDefaultAddress;
    private Boolean isShopAddress = false;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore
    private UserEntity user;
}
