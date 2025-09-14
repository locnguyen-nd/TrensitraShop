package com.trendistashop.entities.product;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.trendistashop.entities.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Table(name = "Size")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Size extends BaseEntity {
    @Id
    @GeneratedValue
    private UUID id;
    private String value;
    @OneToMany(mappedBy = "size", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    @JsonIgnore
    private List<ProductImage> productImages;
}
