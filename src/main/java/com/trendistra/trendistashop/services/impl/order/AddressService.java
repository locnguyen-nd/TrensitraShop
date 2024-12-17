package com.trendistra.trendistashop.services.impl.order;

import com.trendistra.trendistashop.dto.request.AddressRequest;
import com.trendistra.trendistashop.entities.user.Address;
import com.trendistra.trendistashop.entities.user.UserEntity;
import com.trendistra.trendistashop.exceptions.ResourceNotFoundEx;
import com.trendistra.trendistashop.repositories.order.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.UUID;

@Service
public class AddressService {
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private AddressRepository addressRepository;
    public Address createAddress(AddressRequest addressRequest, Principal principal) {
        UserEntity user = (UserEntity) userDetailsService.loadUserByUsername(principal.getName());
        Address address = Address.builder()
                .name(addressRequest.getName())
                .city(addressRequest.getCity())
                .district(addressRequest.getDistrict())
                .ward(addressRequest.getWard())
                .specAddress(addressRequest.getSpecAddress())
                .phoneNumber(addressRequest.getPhoneNumber())
                .isDefaultAddress(addressRequest.getIsDefaultAddress())
                .user(user)
                .build();
        return addressRepository.save(address);
    }
    public Address updateAddress (AddressRequest addressRequest , Principal principal) {
        UserEntity user = (UserEntity) userDetailsService.loadUserByUsername(principal.getName());
        Address address = addressRepository.findById(addressRequest.getId()).orElseThrow( () ->
                new ResourceNotFoundEx("Address not fount")
        );
        address.setCity(addressRequest.getCity());
        address.setDistrict(addressRequest.getDistrict());
        address.setWard(addressRequest.getWard());
        address.setSpecAddress(addressRequest.getSpecAddress());
        address.setIsDefaultAddress(addressRequest.getIsDefaultAddress());
        address.setName(addressRequest.getName());
        address.setPhoneNumber(addressRequest.getPhoneNumber());
        address.setUser(user);
        return  addressRepository.save(address);

    }
    public void deleteAddress(UUID id, Principal principal) {
        UserEntity user = (UserEntity) userDetailsService.loadUserByUsername(principal.getName());
        if(user == null) {
            new ResourceNotFoundEx("User not fount for delete");
        }
        addressRepository.deleteById(id);
    }
}
