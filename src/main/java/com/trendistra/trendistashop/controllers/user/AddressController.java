package com.trendistra.trendistashop.controllers.user;

import com.trendistra.trendistashop.docs.user.CreateAddressDocs;
import com.trendistra.trendistashop.docs.user.DeleteAddressDocs;
import com.trendistra.trendistashop.docs.user.UpdateAddressDocs;
import com.trendistra.trendistashop.docs.user.examples.AddressRequestExamples;
import com.trendistra.trendistashop.dto.request.AddressRequest;
import com.trendistra.trendistashop.dto.response.TypeResponse;
import com.trendistra.trendistashop.entities.user.Address;
import com.trendistra.trendistashop.services.IAddressService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.UUID;

@RestController
@RequestMapping("${api.prefix}/address")
@CrossOrigin
@Tag(name = "Address")
public class AddressController {
    @Autowired
    private IAddressService addressService;

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
