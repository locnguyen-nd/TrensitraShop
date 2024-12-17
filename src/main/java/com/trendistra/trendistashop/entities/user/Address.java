package com.trendistra.trendistashop.entities.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.userdetails.User;

import java.util.UUID;

@Table(name = "Address")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Address {
    @Id
    @GeneratedValue
    private UUID id;
    private String name;
    @Column(nullable = false)
    private String city;//TP
    @Column(nullable = false)
    private String district;//Quận
    @Column(nullable = false)
    private String ward;//Phường
    @Column(nullable = false)
    private String specAddress;//Chi tiết
    @Column(nullable = false)
    private String phoneNumber;//Chi tiết
    private Boolean isDefaultAddress;//Chi tiết
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore
    private UserEntity user;
}
