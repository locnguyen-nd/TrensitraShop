package com.trendistra.trendistashop.entities.collection;

import com.trendistra.trendistashop.enums.MediaOwnerType;
import com.trendistra.trendistashop.enums.MediaType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "sub_collection_media")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Media {
    @Id
    @GeneratedValue
    private UUID id;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MediaType type;
    @Enumerated(EnumType.STRING)
    @Column(name = "owner_type", nullable = false)
    private MediaOwnerType ownerType;  // COLLECTION/SUB_COLLECTION
    @Column(nullable = false)
    private String url;
    @Column(name = "sort_order")
    private Integer sortOrder;
    private String title;

    private String alt;
    @ManyToOne
    @JoinColumn(name = "collection_id")
    private Collection collection;

    @ManyToOne
    @JoinColumn(name = "sub_collection_id")
    private SubCollection subCollection;
    @PrePersist
    protected void onCreate() {
        if (collection != null) {
            ownerType = MediaOwnerType.COLLECTION;
        } else if (subCollection != null) {
            ownerType = MediaOwnerType.SUB_COLLECTION;
        }
    }

}
