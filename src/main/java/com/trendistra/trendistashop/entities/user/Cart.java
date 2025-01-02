package com.trendistra.trendistashop.entities.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Table(name = "cart")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Cart {
    @Id
    @GeneratedValue
    private UUID id;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cart", fetch = FetchType.EAGER, orphanRemoval = true)
    @EqualsAndHashCode.Exclude
    private List<CartItem> cartItems ;
    private BigDecimal cartTotal = BigDecimal.ZERO;
    @OneToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    @EqualsAndHashCode.Exclude
    private UserEntity user;

    @Override
    public String toString() {
        return "Cart(id=" + id.toString() +
                ", cartTotal=" + cartTotal +
                ", cartItemsCount=" + (cartItems != null ? cartItems.size() : 0) + ")";
    }
}
