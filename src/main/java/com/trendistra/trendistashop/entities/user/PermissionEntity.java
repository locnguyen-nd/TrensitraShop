package com.trendistra.trendistashop.entities.user;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.UUID;

@Table(name = "Permission")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PermissionEntity {
    @Id
    @GeneratedValue
    private UUID id ;
    @Column(nullable = false, unique = true)
    private String name;
    @Column(nullable = false)
    private String method;
    @Column(nullable = false)
    private String endPoint;
}
