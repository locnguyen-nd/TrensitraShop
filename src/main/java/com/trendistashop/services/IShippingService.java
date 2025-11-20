package com.trendistashop.services;

import com.trendistashop.dto.request.CreateShipmentRequest;
import com.trendistashop.dto.response.TypeResponse;
import com.trendistashop.entities.user.Address;
import com.trendistashop.entities.user.Order;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Locnd
 */
public interface IShippingService {
    TypeResponse<List<Map<String, Object>>> getProvinces();
    TypeResponse<List<Map<String, Object>>> getDistricts(String provinceId);
    TypeResponse<List<Map<String, Object>>> getWards(String districtId);
    /**
     * Tính phí vận chuyển dựa trên địa chỉ, trọng lượng, kích thước, v.v.
     * @param toAddress Địa chỉ người nhận
     * @param weight Trọng lượng đơn hàng (gram)
     * @param dimensions Kích thước (length, width, height - cm)
     * @param serviceType Loại dịch vụ (standard, express,...)
     * @return Phí vận chuyển
     */
    TypeResponse<BigDecimal> calculateShippingCost( Address toAddress, double weight, Map<String, Integer> dimensions, String serviceType);

    /**
     * Tạo đơn vận chuyển trên GHN dựa trên đơn hàng
     * @param request Đơn hàng cần tạo shipment
     * @return Shipment code từ GHN
     */
    TypeResponse<String> createShipment(CreateShipmentRequest request);
    /**
     * In tem vận chuyển (label) từ GHN
     * @param shipmentCode Mã vận đơn GHN
     * @return byte[] của PDF label
     */
    TypeResponse<byte[]> printShipmentLabel(String shipmentCode);
    /**
     * Theo dõi trạng thái đơn hàng từ GHN và cập nhật vào order nếu cần
     * @param shipmentCode Mã vận đơn GHN
     * @return Trạng thái hiện tại
     */
    TypeResponse<String> trackShipmentStatus(String shipmentCode);
}
