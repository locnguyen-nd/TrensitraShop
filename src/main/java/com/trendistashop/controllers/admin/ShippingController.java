package com.trendistashop.controllers.admin;

import com.trendistashop.dto.request.CreateShipmentRequest;
import com.trendistashop.dto.response.TypeResponse;
import com.trendistashop.entities.user.Order;
import com.trendistashop.exceptions.ResourceNotFoundEx;
import com.trendistashop.repositories.order.OrderRepository;
import com.trendistashop.services.IShippingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

/**
 *
 * @author Locnd
 */
@RestController
@RequestMapping("${api.prefix}/shipping")
@RequiredArgsConstructor
@Tag(name = "Shipping Location")
public class ShippingController {
    private final IShippingService shippingService;

    @PostMapping("/create")
    @Operation(summary = "Tạo đơn vận chuyển GHN")
    public TypeResponse<String> createShipment(@RequestBody CreateShipmentRequest request) {
        return shippingService.createShipment(request);
    }

    @GetMapping("/label/{shipmentCode}")
    @Operation(summary = "In tem vận chuyển")
    public ResponseEntity<byte[]> printLabel(@PathVariable String shipmentCode,
                                             @RequestParam(defaultValue = "inline") String mode) {
        TypeResponse<byte[]> response = shippingService.printShipmentLabel(shipmentCode);
        if (response.getData() == null) {
            return ResponseEntity.status(response.getStatusCode()).build();
        }

        String filename = "Tem_GHN_" + shipmentCode + ".pdf";
        String encoded = "filename*=UTF-8''" + URLEncoder.encode(filename, StandardCharsets.UTF_8);
        String disposition = "download".equalsIgnoreCase(mode) ? "attachment" : "inline";

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .header(HttpHeaders.CONTENT_DISPOSITION, disposition + "; " + encoded)
                .body(response.getData());
    }

    @GetMapping("/track/{shipmentCode}")
    @Operation(summary = "Theo dõi vận đơn")
    public ResponseEntity<TypeResponse<String>> track(@PathVariable String shipmentCode) {
        TypeResponse<String> response = shippingService.trackShipmentStatus(shipmentCode);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }
}
