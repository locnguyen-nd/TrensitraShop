package com.trendistashop.repositories.order;

import com.trendistashop.entities.user.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PaymentRepository extends JpaRepository<Payment, UUID> {

    Payment findByTransactionId(Long transactionId);
}
