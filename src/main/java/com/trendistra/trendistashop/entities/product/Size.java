package com.trendistra.trendistashop.entities.product;

import com.trendistra.trendistashop.entities.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
}
