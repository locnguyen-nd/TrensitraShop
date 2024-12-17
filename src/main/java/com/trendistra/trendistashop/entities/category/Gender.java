package com.trendistra.trendistashop.entities.category;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Table(name = "Gender")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Gender {
    @Id
    @GeneratedValue
    private UUID id;
    @Column(nullable = false)
    private String name;
    @OneToMany(mappedBy = "gender", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonProperty("categories")
    private List<Category> categories; // Danh sách các danh mục liên quan
}
