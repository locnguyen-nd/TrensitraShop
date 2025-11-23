package com.trendistashop.dto.request;

/**
 *
 * @author Locnd
 */
public record ShipmentParams(
        double weightInKg,     // trọng lượng (kg)
        int lengthCm,           // chiều dài (cm)
        int widthCm,            // chiều rộng (cm)
        int heightCm            // chiều cao (cm)
) {
    public int weightInGram() {
        return (int) Math.round(weightInKg * 1000);
    }

    public double volumeWeightGram() {
        return (lengthCm * widthCm * heightCm / 6000.0) * 1000;
    }
}
