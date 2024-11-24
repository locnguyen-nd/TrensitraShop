package com.trendistra.trendistashop.entities.auth;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "Permission")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PermissionEntity {
    @Id
    @GeneratedValue
    private  Long id ;
    @Column(nullable = false, unique = true)
    private String name;
    private String method;
    private String endPoint;
}
