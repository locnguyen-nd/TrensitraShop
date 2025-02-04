package com.trendistra.trendistashop.services.impl.order;

import com.trendistra.trendistashop.config.VietQRConfig;
import com.trendistra.trendistashop.dto.response.VietQRResponse;
import com.trendistra.trendistashop.entities.user.Order;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class VietQRService {
    @Autowired
    private VietQRConfig vietQRConfig;
    @Autowired
    private RestTemplate restTemplate;

    public String generateQRCode(Order order) {
        // Generate QR code for order
        HttpHeaders headers = new HttpHeaders();
        headers.set("x-client-id", vietQRConfig.getClientId());
        headers.set("x-api-key", vietQRConfig.getApiKey());
        headers.setContentType(MediaType.APPLICATION_JSON);
        Long amount = order.getTotalAmount().longValue();

        Map<String, Object> payload = new HashMap<>();
        payload.put("accountNo", vietQRConfig.getAccountNo());
        payload.put("accountName", vietQRConfig.getAccountName());
        payload.put("acqId", vietQRConfig.getBankId());
        payload.put("amount", amount);
        payload.put("addInfo", "Order-" + order.getOrderCoder());
        payload.put("format", "text");
        payload.put("template", "compact2");
        HttpEntity<Map<String, Object>> request = new HttpEntity<>(payload, headers);
        try {
            log.info("Calling VietQR API with payload: {}", payload);
            ResponseEntity<VietQRResponse> responseEntity = restTemplate.exchange(
                    VietQRConfig.VIETQR_API_URL,
                    HttpMethod.POST,
                    request,
                    VietQRResponse.class
            );
            VietQRResponse response = responseEntity.getBody();
            log.info("VietQR API Response: {}", response);
            log.info("VietQR API Response status: {}", responseEntity.getStatusCode());
            if (response != null && "00".equals(response.getCode()) && response.getData() != null) {
                return response.getData().getQrDataURL();
            } else {
                String errorMessage = response != null ? response.getDesc() : "Unknown error";
                log.error("Error generating QR code for order {}: {}", order.getOrderCoder(), errorMessage);
                //throw new PaymentException("Failed to generate QR code: " + errorMessage);
            }
        }  catch (RestClientException e) {
            log.error("Error calling VietQR API for order {}: {}", order.getOrderCoder(), e.getMessage());
            //throw new PaymentException("Failed to call VietQR API", e);
        } catch (Exception e) {
            log.error("Unexpected error generating QR code for order {}: {}", order.getOrderCoder(), e.getMessage());
           // throw new PaymentException("Unexpected error generating QR code", e);
        }
        return null;
    }
}
