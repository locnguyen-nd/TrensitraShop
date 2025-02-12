package com.trendistra.trendistashop.entities.notification;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "notification")
public class Notification {
    @Id
    @GeneratedValue
    private UUID id;
    private String type;
    private String message;
    private UUID recipient; // dành riêng cho người nhận thông báo
    private LocalDateTime timestamp = LocalDateTime.now();
}
