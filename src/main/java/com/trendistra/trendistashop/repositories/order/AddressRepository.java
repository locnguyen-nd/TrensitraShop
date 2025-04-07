package com.trendistra.trendistashop.repositories.order;

import com.trendistra.trendistashop.entities.user.Address;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AddressRepository extends JpaRepository<Address, UUID> {
    Optional<Address> findByIsDefaultAddressTrue();
}
