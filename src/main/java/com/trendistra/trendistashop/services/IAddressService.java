package com.trendistra.trendistashop.services;

import java.security.Principal;
import java.util.UUID;

import com.trendistra.trendistashop.dto.request.AddressRequest;
import com.trendistra.trendistashop.dto.response.TypeResponse;
import com.trendistra.trendistashop.entities.user.Address;

public interface IAddressService {
    TypeResponse<Address> createAddress(AddressRequest addressRequest, Principal principal);
    TypeResponse<Address> updateAddress(AddressRequest addressRequest, Principal principal);
    TypeResponse<Void> deleteAddress(UUID addressId, Principal principal);
}
