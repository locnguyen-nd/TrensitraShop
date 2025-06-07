package com.trendistra.trendistashop.entities.user;

import java.util.UUID;

import com.trendistra.trendistashop.entities.BaseEntity;

import jakarta.persistence.*;
import lombok.*;

@Table(name = "verification_attempts")
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VerificationAttempt extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "user_id", nullable = false)
    private UUID userId;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String token;
}
