package com.trendistashop.services;

import java.security.Principal;
import java.util.UUID;

import com.trendistashop.dto.request.AddressRequest;
import com.trendistashop.dto.response.TypeResponse;
import com.trendistashop.entities.user.Address;

public interface IAddressService {
    TypeResponse<Address> createAddress(AddressRequest addressRequest, Principal principal);
    TypeResponse<Address> updateAddress(AddressRequest addressRequest, Principal principal);
    TypeResponse<Void> deleteAddress(UUID addressId, Principal principal);
}
