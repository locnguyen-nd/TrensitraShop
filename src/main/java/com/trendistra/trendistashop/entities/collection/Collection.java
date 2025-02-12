package com.trendistra.trendistashop.entities.collection;

import com.trendistra.trendistashop.entities.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Where;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity(name = "collection")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Collection extends BaseEntity {
    @Id
    @GeneratedValue
    private UUID id;
    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String slug;
    @Lob
    @Column(columnDefinition = "TEXT")
    private String description;
    private String thumbnail;
    @OneToMany(mappedBy = "collection", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SubCollection> subCollections = new ArrayList<>();
    @OneToMany(mappedBy = "collection", cascade = CascadeType.ALL, orphanRemoval = true)
    @Where(clause = "owner_type = 'COLLECTION'")
    private List<Media> media = new ArrayList<>();
}
