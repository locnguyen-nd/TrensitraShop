package com.trendistashop.dto.request;

import lombok.Data;

import java.util.UUID;

/**
 *
 * @author Locnd
 */
@Data
public class CreateShipmentRequest {
    private UUID orderId;
    private Double weight; // gram
    private Double length;
    private Double width;
    private Double height;
    private Integer serviceId;
    private String requiredNote;
}
