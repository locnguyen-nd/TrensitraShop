package com.trendistra.trendistashop.entities.user;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.trendistra.trendistashop.entities.product.Product;
import jakarta.persistence.*;
import lombok.*;
import java.util.UUID;

@Table(name = "cart-item")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartItem {
    @Id
    @GeneratedValue
    private UUID id;
    @ManyToOne
    @JoinColumn(name = "cart_id")
    @JsonIgnore
    @EqualsAndHashCode.Exclude
    private Cart cart;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    @JsonIgnoreProperties(value = {
            "id",
            "user",
            "name"
    })
    private Product cartProduct;
    private UUID productVariantId;
    private Integer cartItemQuantity;
    @Override
    public String toString() {
        return "CartItem(id=" + id.toString() +
                ", productVariantId=" + productVariantId +
                ", quantity=" + cartItemQuantity + ")";
    }
}
