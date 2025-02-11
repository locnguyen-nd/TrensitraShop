package com.trendistra.trendistashop.repositories.order;

import com.trendistra.trendistashop.entities.user.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CartRepository  extends JpaRepository<Cart, UUID> {
}
