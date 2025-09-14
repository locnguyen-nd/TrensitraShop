package com.trendistashop.services.impl.order;

import com.trendistashop.constants.ResponseMessage;
import com.trendistashop.dto.request.AddressRequest;
import com.trendistashop.dto.response.TypeResponse;
import com.trendistashop.entities.user.Address;
import com.trendistashop.entities.user.UserEntity;
import com.trendistashop.repositories.order.AddressRepository;
import com.trendistashop.services.IAddressService;
import com.trendistashop.utils.ResponseHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.*;

@Service
public class AddressService implements IAddressService {
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private AddressRepository addressRepository;

    public TypeResponse<Address> createAddress(AddressRequest addressRequest, Principal principal) {
        UserEntity user = (UserEntity) userDetailsService.loadUserByUsername(principal.getName());
        if (user == null) {
            return ResponseHelper.unauthorized(ResponseMessage.UNAUTHORIZED);
        }
        try {
            Optional<Address> addressDefaultOpt = addressRepository.findByIsDefaultAddressTrue();
            if (addressDefaultOpt.isPresent()) {
                Address addressDefault = addressDefaultOpt.get();
                addressDefault.setIsDefaultAddress(false);
                addressRepository.save(addressDefault);
            }
            Address address = Address.builder()
                    .name(addressRequest.getName())
                    .city(addressRequest.getCity())
                    .ward(addressRequest.getWard())
                    .specAddress(addressRequest.getSpecAddress())
                    .phoneNumber(addressRequest.getPhoneNumber())
                    .isDefaultAddress(addressRequest.getIsDefaultAddress())
                    .user(user)
                    .build();

            return ResponseHelper.ok(addressRepository.save(address), ResponseMessage.CREATE_SUCCESS);
        } catch (Exception e) {
            return ResponseHelper.serverError(ResponseMessage.CREATE_FAILED);
        }
    }

    public TypeResponse<Address> updateAddress(AddressRequest addressRequest, Principal principal) {
        UserEntity user = (UserEntity) userDetailsService.loadUserByUsername(principal.getName());
        if (user == null) {
            return ResponseHelper.unauthorized(ResponseMessage.UNAUTHORIZED);
        }
        Optional<Address> addressOpt = addressRepository.findById(addressRequest.getId());
        if (addressOpt.isEmpty()) {
            return ResponseHelper.notFound(ResponseMessage.NOT_FOUND);
        }

        try {
            Optional<Address> addressDefaultOpt = addressRepository.findByIsDefaultAddressTrue();
            if (addressDefaultOpt.isPresent()) {
                Address addressDefault = addressDefaultOpt.get();
                addressDefault.setIsDefaultAddress(false);
                addressRepository.save(addressDefault);
            }

            Address address = addressOpt.get();
            address.setCity(addressRequest.getCity());
            address.setWard(addressRequest.getWard());
            address.setSpecAddress(addressRequest.getSpecAddress());
            address.setIsDefaultAddress(addressRequest.getIsDefaultAddress());
            address.setName(addressRequest.getName());
            address.setPhoneNumber(addressRequest.getPhoneNumber());
            address.setUser(user);

            return ResponseHelper.ok(addressRepository.save(address), ResponseMessage.UPDATE_SUCCESS);
        } catch (Exception e) {
            return ResponseHelper.serverError(ResponseMessage.UPDATE_FAILED);
        }
    }

    public TypeResponse<Void> deleteAddress(UUID id, Principal principal) {
        UserEntity user = (UserEntity) userDetailsService.loadUserByUsername(principal.getName());
        if (user == null) {
            return ResponseHelper.notFound(ResponseMessage.UNAUTHORIZED);
        }
        try {
            addressRepository.deleteById(id);
            return ResponseHelper.ok(null, ResponseMessage.DELETE_SUCCESS);
        } catch (Exception e) {
            return ResponseHelper.serverError(ResponseMessage.DELETE_FAILED);
        }
    }
}
