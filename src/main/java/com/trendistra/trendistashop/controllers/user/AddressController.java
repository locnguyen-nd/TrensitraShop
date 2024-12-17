package com.trendistra.trendistashop.controllers.user;

import com.trendistra.trendistashop.dto.request.AddressRequest;
import com.trendistra.trendistashop.entities.user.Address;
import com.trendistra.trendistashop.services.impl.order.AddressService;
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
    private AddressService addressService;
    @PostMapping
    public ResponseEntity<Address> createAddress(@RequestBody AddressRequest addressRequest, Principal principal) {
        return ResponseEntity.ok(addressService.createAddress(addressRequest,principal));
    }
    @PutMapping
    public ResponseEntity<Address> updateAddress(@RequestBody AddressRequest addressRequest, Principal principal) {
        return ResponseEntity.ok(addressService.updateAddress(addressRequest,principal));
    }
    @DeleteMapping("/{addressId}")
    public ResponseEntity<String> deleteAddress(Principal principal, @PathVariable UUID addressId) {
       addressService.deleteAddress(addressId,principal);
       return ResponseEntity.ok("Your address has been deleted successfully.");
    }
}
