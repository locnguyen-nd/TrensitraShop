package com.trendistra.trendistashop.repositories.order;

import com.trendistra.trendistashop.entities.user.Order;
import com.trendistra.trendistashop.entities.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface OrderRepository extends JpaRepository<Order , UUID> {
    List<Order> findByUser(UserEntity user);
}
