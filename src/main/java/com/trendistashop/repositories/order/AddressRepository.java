package com.trendistashop.repositories.order;

import com.trendistashop.entities.user.Address;

import com.trendistashop.entities.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AddressRepository extends JpaRepository<Address, UUID> {
    Optional<Address> findByIsShopAddressTrue();
    Optional<Address> findByUserAndIsDefaultAddressTrue(UserEntity user);
    Optional<Address> findByIdAndUser(UUID id, UserEntity user);
}
