package com.trendistashop.controllers.user;

import com.trendistashop.docs.user.CreateAddressDocs;
import com.trendistashop.docs.user.DeleteAddressDocs;
import com.trendistashop.docs.user.UpdateAddressDocs;
import com.trendistashop.docs.user.examples.AddressRequestExamples;
import com.trendistashop.dto.request.AddressRequest;
import com.trendistashop.dto.response.TypeResponse;
import com.trendistashop.entities.user.Address;
import com.trendistashop.services.IAddressService;

import com.trendistashop.services.IShippingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("${api.prefix}/address")
@CrossOrigin
@Tag(name = "Address API", description = "Tạo địa chỉ nhận hàng, chuẩn hóa theo đơn vị ship")
public class AddressController {
    @Autowired
    private IAddressService addressService;
    @Autowired
    private IShippingService shippingService;
    @GetMapping("/provinces")
    @Operation(summary = "Lấy danh sách tỉnh thành", description = "Lấy danh sách tỉnh thành từ GHN")
    public ResponseEntity<TypeResponse<List<Map<String, Object>>>> getProvinces() {
        TypeResponse<List<Map<String, Object>>> response = shippingService.getProvinces();
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/districts")
    @Operation(summary = "Lấy danh sách quận huyện", description = "Lấy danh sách quận huyện theo tỉnh thành từ GHN")
    public ResponseEntity<TypeResponse<List<Map<String, Object>>>> getDistricts(@RequestParam String provinceId) {
        TypeResponse<List<Map<String, Object>>> response = shippingService.getDistricts(provinceId);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/wards")
    @Operation(summary = "Lấy danh sách phường xã", description = "Lấy danh sách phường xã theo quận huyện từ GHN")
    public ResponseEntity<TypeResponse<List<Map<String, Object>>>> getWards(@RequestParam String districtId) {
        TypeResponse<List<Map<String, Object>>> response = shippingService.getWards(districtId);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }
    @Operation(summary = "Tạo địa chỉ mới", description = "Tạo địa chỉ mới cho người dùng")
    @CreateAddressDocs
    @PostMapping
    public ResponseEntity<TypeResponse<Address>> createAddress(
        @io.swagger.v3.oas.annotations.parameters.RequestBody(
            required = true,
            content = @Content(
                schema = @Schema(implementation = AddressRequest.class),
                examples = @ExampleObject(value = AddressRequestExamples.CREATE_ADDRESS_REQUEST)
            )
        )
        @RequestBody AddressRequest addressRequest,
        Principal principal
    ) {
        TypeResponse<Address> response = addressService.createAddress(addressRequest,principal);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @Operation(summary = "Cập nhật địa chỉ", description = "Cập nhật địa chỉ cho người dùng")
    @UpdateAddressDocs
    @PutMapping
    public ResponseEntity<TypeResponse<Address>> updateAddress(
        @io.swagger.v3.oas.annotations.parameters.RequestBody(
            required = true,
            content = @Content(
                schema = @Schema(implementation = AddressRequest.class),
                examples = @ExampleObject(value = AddressRequestExamples.UPDATE_ADDRESS_REQUEST)
            )
        )
        @RequestBody AddressRequest addressRequest,
        Principal principal
    ) {
        TypeResponse<Address> response = addressService.updateAddress(addressRequest,principal);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @Operation(summary = "Xóa địa chỉ", description = "Xóa địa chỉ cho người dùng")
    @DeleteAddressDocs
    @DeleteMapping("/{addressId}")
    public ResponseEntity<TypeResponse<Void>> deleteAddress(
        Principal principal,
        @PathVariable UUID addressId
    ) {
        TypeResponse<Void> response = addressService.deleteAddress(addressId,principal);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }
}
