package com.trendistashop.services.impl.order;

import com.trendistashop.constants.ResponseMessage;
import com.trendistashop.dto.request.AddressRequest;
import com.trendistashop.dto.response.TypeResponse;
import com.trendistashop.entities.user.Address;
import com.trendistashop.entities.user.UserEntity;
import com.trendistashop.repositories.order.AddressRepository;
import com.trendistashop.services.IAddressService;
import com.trendistashop.services.IShippingService;
import com.trendistashop.utils.ResponseHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.*;

@Service
@Slf4j
public class AddressService implements IAddressService {
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private AddressRepository addressRepository;

    private UserEntity getCurrentUser(Principal principal) {
        return (UserEntity) userDetailsService.loadUserByUsername(principal.getName());
    }
    @Override
    public TypeResponse<Address> createAddress(AddressRequest request, Principal principal) {
        UserEntity user = getCurrentUser(principal);
        if (user == null) return ResponseHelper.unauthorized(ResponseMessage.UNAUTHORIZED);
        if (!isValidGhnLocation(request.getProvinceId(), request.getDistrictId(), request.getWardCode())) {
            return ResponseHelper.validationError("location", "Mã tỉnh/huyện/phường không hợp lệ");
        }
        try {
            if (Boolean.TRUE.equals(request.getIsDefaultAddress())) {
                addressRepository.findByUserAndIsDefaultAddressTrue(user)
                        .ifPresent(defaultAddr -> {
                            defaultAddr.setIsDefaultAddress(false);
                            addressRepository.save(defaultAddr);
                        });
            }

            Address address = Address.builder()
                    .name(request.getName())
                    .provinceId(request.getProvinceId())
                    .provinceName(request.getProvinceName())
                    .districtId(request.getDistrictId())
                    .districtName(request.getDistrictName())
                    .wardCode(request.getWardCode())
                    .wardName(request.getWardName())
                    .specAddress(request.getSpecAddress())
                    .phoneNumber(request.getPhoneNumber())
                    .isDefaultAddress(request.getIsDefaultAddress())
                    .isShopAddress(request.getIsShopAddress())
                    .user(user)
                    .build();

            return ResponseHelper.ok(addressRepository.save(address), ResponseMessage.CREATE_SUCCESS);
        } catch (Exception e) {
            log.error("Lỗi tạo địa chỉ: {}", e.getMessage());
            return ResponseHelper.serverError(ResponseMessage.CREATE_FAILED);
        }
    }

    @Override
    public TypeResponse<Address> updateAddress(AddressRequest request, Principal principal) {
        UserEntity user = getCurrentUser(principal);
        if (user == null) return ResponseHelper.unauthorized(ResponseMessage.UNAUTHORIZED);

        Optional<Address> opt = addressRepository.findByIdAndUser(request.getId(), user);
        if (opt.isEmpty()) return ResponseHelper.notFound(ResponseMessage.NOT_FOUND);

        if (!isValidGhnLocation(request.getProvinceId(), request.getDistrictId(), request.getWardCode())) {
            return ResponseHelper.validationError("location", "Mã tỉnh/huyện/phường không hợp lệ");
        }

        try {
            if (Boolean.TRUE.equals(request.getIsDefaultAddress())) {
                addressRepository.findByUserAndIsDefaultAddressTrue(user)
                        .ifPresent(defaultAddr -> {
                            defaultAddr.setIsDefaultAddress(false);
                            addressRepository.save(defaultAddr);
                        });
            }

            Address address = opt.get();
            address.setName(request.getName());
            address.setProvinceId(request.getProvinceId());
            address.setProvinceName(request.getProvinceName());
            address.setDistrictId(request.getDistrictId());
            address.setDistrictName(request.getDistrictName());
            address.setWardCode(request.getWardCode());
            address.setWardName(request.getWardName());
            address.setSpecAddress(request.getSpecAddress());
            address.setPhoneNumber(request.getPhoneNumber());
            address.setIsDefaultAddress(request.getIsDefaultAddress());
            address.setIsShopAddress(request.getIsShopAddress());

            return ResponseHelper.ok(addressRepository.save(address), ResponseMessage.UPDATE_SUCCESS);
        } catch (Exception e) {
            return ResponseHelper.serverError(ResponseMessage.UPDATE_FAILED);
        }
    }

    @Override
    public TypeResponse<Void> deleteAddress(UUID id, Principal principal) {
        UserEntity user = getCurrentUser(principal);
        if (user == null) return ResponseHelper.unauthorized(ResponseMessage.UNAUTHORIZED);

        if (!addressRepository.findByIdAndUser(id, user).isPresent()) {
            return ResponseHelper.notFound(ResponseMessage.NOT_FOUND);
        }
        try {
            addressRepository.deleteById(id);
            return ResponseHelper.ok(null, ResponseMessage.DELETE_SUCCESS);
        } catch (Exception e) {
            return ResponseHelper.serverError(ResponseMessage.DELETE_FAILED);
        }
    }

    @Override
    public Optional<Address> getShopAddress() {
        return addressRepository.findByIsShopAddressTrue();
    }

    private boolean isValidGhnLocation(String provinceId, String districtId, String wardCode) {
        return provinceId != null && districtId != null && wardCode != null
                && !provinceId.isBlank() && !districtId.isBlank() && !wardCode.isBlank();
    }
}
