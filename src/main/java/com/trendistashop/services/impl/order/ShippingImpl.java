package com.trendistashop.services.impl.order;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.trendistashop.config.ShippingConfig;
import com.trendistashop.constants.ResponseMessage;
import com.trendistashop.dto.request.CreateShipmentRequest;
import com.trendistashop.dto.request.ShipmentParams;
import com.trendistashop.dto.response.TypeResponse;
import com.trendistashop.entities.user.Address;
import com.trendistashop.entities.user.Order;
import com.trendistashop.entities.user.OrderItem;
import com.trendistashop.enums.OrderStatus;
import com.trendistashop.enums.PaymentMethod;
import com.trendistashop.repositories.order.AddressRepository;
import com.trendistashop.repositories.order.OrderRepository;
import com.trendistashop.services.IShippingService;
import com.trendistashop.utils.ResponseHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static java.util.Map.entry;

/**
 *
 * @author Locnd
 */
@Service
@Slf4j
public class ShippingImpl implements IShippingService {
    @Autowired
    private ShippingConfig shippingConfig;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private AddressService addressService;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private HttpHeaders createHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Token", shippingConfig.getToken());
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }

    @Override
    public TypeResponse<List<Map<String, Object>>> getProvinces() {
        try {
            String url = shippingConfig.getApiUrl() + "/shiip/public-api/master-data/province";
            HttpEntity<Void> entity = new HttpEntity<>(createHeaders());
            ResponseEntity<JsonNode> response = restTemplate.exchange(url, HttpMethod.GET, entity, JsonNode.class);
            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                JsonNode data = response.getBody().get("data");
                List<Map<String, Object>> provinces = StreamSupport.stream(data.spliterator(), false)
                        .map(node -> {
                            Map<String, Object> map = new HashMap<>();
                            map.put("provinceId", node.get("ProvinceID").asText());
                            map.put("provinceName", node.get("ProvinceName").asText());
                            return map;
                        })
                        .collect(Collectors.toList());
                return ResponseHelper.ok(provinces, "Lấy danh sách tỉnh thành công");
            }
            return ResponseHelper.badRequest("Không lấy được tỉnh");
        } catch (Exception e) {
            return ResponseHelper.serverError("Lỗi API GHN tỉnh: " + e.getMessage());
        }
    }

    @Override
    public TypeResponse<List<Map<String, Object>>> getDistricts(String provinceId) {
        try {
            String url = shippingConfig.getApiUrl() + "/shiip/public-api/master-data/district";
            Map<String, Object> body = new HashMap<>();
            body.put("province_id", Integer.parseInt(provinceId)); // GHN yêu cầu int
            HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, createHeaders());
            ResponseEntity<JsonNode> response = restTemplate.exchange(url, HttpMethod.POST, entity, JsonNode.class);
            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                JsonNode data = response.getBody().get("data");
                List<Map<String, Object>> districts = StreamSupport.stream(data.spliterator(), false)
                        .map(node -> {
                            Map<String, Object> map = new HashMap<>();
                            map.put("districtId", node.get("DistrictID").asText());
                            map.put("districtName", node.get("DistrictName").asText());
                            return map;
                        })
                        .collect(Collectors.toList());
                return ResponseHelper.ok(districts, "Lấy danh sách huyện thành công");
            }
            return ResponseHelper.badRequest("Không lấy được huyện");
        } catch (Exception e) {
            return ResponseHelper.serverError("Lỗi API GHN huyện: " + e.getMessage());
        }
    }

    @Override
    public TypeResponse<List<Map<String, Object>>> getWards(String districtId) {
        try {
            String url = shippingConfig.getApiUrl() + "/shiip/public-api/master-data/ward";
            Map<String, Object> body = new HashMap<>();
            body.put("district_id", Integer.parseInt(districtId)); // GHN yêu cầu int
            HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, createHeaders());
            ResponseEntity<JsonNode> response = restTemplate.exchange(url, HttpMethod.POST, entity, JsonNode.class);
            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                JsonNode data = response.getBody().get("data");
                List<Map<String, Object>> wards = StreamSupport.stream(data.spliterator(), false)
                        .map(node -> {
                            Map<String, Object> map = new HashMap<>();
                            map.put("wardCode", node.get("WardCode").asText());
                            map.put("wardName", node.get("WardName").asText());
                            return map;
                        })
                        .collect(Collectors.toList());
                return ResponseHelper.ok(wards, "Lấy danh sách phường thành công");
            }
            return ResponseHelper.badRequest("Không lấy được phường");
        } catch (Exception e) {
            return ResponseHelper.serverError("Lỗi API GHN phường: " + e.getMessage());
        }
    }

    @Override
    public TypeResponse<BigDecimal> calculateShippingCost(Address toAddress, int totalItems, String serviceType) {
        Address fromAddress = addressService.getShopAddress()
                .orElseThrow(() -> new RuntimeException("Không tìm thấy địa chỉ kho"));

        // TỰ ĐỘNG tính thông số đóng gói cho áo thun
        ShipmentParams params = calculateParamsShipping(totalItems);

        try {
            String url = shippingConfig.getApiUrl() + "/shiip/public-api/v2/shipping-order/fee";

            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("shop_id", shippingConfig.getShopId());
            requestBody.put("from_district_id", Integer.parseInt(fromAddress.getDistrictId()));
            requestBody.put("to_district_id", Integer.parseInt(toAddress.getDistrictId()));
            requestBody.put("to_ward_code", toAddress.getWardCode());
            requestBody.put("weight", params.weightInGram());        // dùng gram
            requestBody.put("length", params.lengthCm());
            requestBody.put("width", params.widthCm());
            requestBody.put("height", params.heightCm());
            requestBody.put("service_type_id", "express".equalsIgnoreCase(serviceType) ? 2 : 5);

            HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, createHeaders());
            ResponseEntity<JsonNode> response = restTemplate.exchange(url, HttpMethod.POST, entity, JsonNode.class);

            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                JsonNode data = response.getBody().get("data");
                BigDecimal fee = new BigDecimal(data.get("total").asText());
                return ResponseHelper.ok(fee, ResponseMessage.FETCH_SUCCESS);
            } else {
                String msg = response.getBody() != null ? response.getBody().toString() : "No response";
                return ResponseHelper.badRequest("Lỗi tính phí GHN: " + msg);
            }
        } catch (Exception e) {
            log.error("Lỗi tính phí vận chuyển cho đơn {}: {}", totalItems, e.getMessage(), e);
            return ResponseHelper.serverError("Không thể tính phí vận chuyển");
        }
    }
    @Override
    public TypeResponse<String> createShipment(CreateShipmentRequest request) {
        try {
            Order order = orderRepository.findById(request.getOrderId())
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy đơn hàng"));

            if (order.getShipmentTrackingNumber() != null) {
                return ResponseHelper.validationError("trackingNumber", "Đơn hàng đã có mã vận đơn");
            }

            Address fromAddress = addressService.getShopAddress().get();
            Address toAddress = order.getAddress();

            // TỰ ĐỘNG tính thông số đóng gói chuẩn cho áo thun
            ShipmentParams params = calculateParamsShipping(order.getOrderItems().stream()
                    .mapToInt(OrderItem::getQuantity).sum());

            String requiredNote = StringUtils.hasText(request.getRequiredNote())
                    ? request.getRequiredNote() : "ĐƯỢC KIỂM TRA HÀNG KHI NHẬN";

            Integer serviceTypeId = request.getServiceId() != null && request.getServiceId() > 0
                    ? request.getServiceId() : 53320;

            String url = shippingConfig.getApiUrl() + "/shiip/public-api/v2/shipping-order/create";

            Map<String, Object> body = new HashMap<>();
            body.put("shop_id", shippingConfig.getShopId());
            body.put("payment_type_id", 2); // Shop trả phí ship
            body.put("note", Optional.ofNullable(order.getNote()).orElse(""));
            body.put("required_note", requiredNote);
            body.put("from_name", fromAddress.getName());
            body.put("from_phone", fromAddress.getPhoneNumber());
            body.put("from_address", fromAddress.getSpecAddress());
            body.put("from_ward_code", fromAddress.getWardCode());
            body.put("from_district_id", Integer.parseInt(fromAddress.getDistrictId()));
            body.put("to_name", toAddress.getName());
            body.put("to_phone", toAddress.getPhoneNumber());
            body.put("to_address", toAddress.getSpecAddress());
            body.put("to_ward_code", toAddress.getWardCode());
            body.put("to_district_id", Integer.parseInt(toAddress.getDistrictId()));
            body.put("cod_amount", PaymentMethod.COD.equals(order.getPaymentMethod())
                    ? order.getTotalAmount().intValueExact() : 0);

            // DÙNG THÔNG SỐ TỰ ĐỘNG
            body.put("weight", params.weightInGram());
            body.put("length", (int) params.lengthCm());
            body.put("width", (int) params.widthCm());
            body.put("height", params.heightCm());
            body.put("service_type_id", serviceTypeId);

            // Items
            List<Map<String, Object>> items = order.getOrderItems().stream()
                    .map(oi -> Map.<String, Object>of(
                            "name", oi.getProduct().getName(),
                            "code", Optional.ofNullable(oi.getProduct().getCode()).orElse(""),
                            "quantity", oi.getQuantity(),
                            "price", oi.getItemPrice().intValueExact()
                    ))
                    .collect(Collectors.toList());
            body.put("items", items);

            HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, createHeaders());
            ResponseEntity<JsonNode> response = restTemplate.exchange(url, HttpMethod.POST, entity, JsonNode.class);

            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                JsonNode data = response.getBody().get("data");
                String orderCode = data.get("order_code").asText();

                order.setShipmentTrackingNumber(orderCode);
                order.setOrderStatus(OrderStatus.SHIPPED);

                // Tính leadtime
                TypeResponse<String> leadtimeResult = calculateLeadtime(order, fromAddress, toAddress, serviceTypeId);
                if (leadtimeResult.isSuccess()) {
                    order.setExpectedDeliveryDate(LocalDateTime.parse(leadtimeResult.getData(),
                            DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));
                }
                orderRepository.save(order);
                return ResponseHelper.ok(orderCode, "Tạo đơn GHN thành công! Mã vận đơn: " + orderCode);
            }

            String errorMsg = response.getBody() != null ? response.getBody().toString() : "Unknown error";
            return ResponseHelper.badRequest("Lỗi tạo đơn GHN: " + errorMsg);

        } catch (Exception e) {
            log.error("Lỗi tạo shipment cho đơn {}: {}", request.getOrderId(), e.getMessage(), e);
            return ResponseHelper.serverError("Không thể tạo đơn vận chuyển");
        }
    }
    private TypeResponse<String> calculateLeadtime(Order order, Address from, Address to, Integer serviceId) {
        try {
            String url = shippingConfig.getApiUrl() + "/shiip/public-api/v2/shipping-order/leadtime";

            Map<String, Object> body = Map.of(
                    "from_district_id", Integer.parseInt(from.getDistrictId()),
                    "from_ward_code", from.getWardCode(),
                    "to_district_id", Integer.parseInt(to.getDistrictId()),
                    "to_ward_code", to.getWardCode(),
                    "service_id", serviceId
            );

            HttpHeaders headers = createHeaders();
            headers.set("ShopId", String.valueOf(shippingConfig.getShopId()));

            HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);
            ResponseEntity<JsonNode> response = restTemplate.exchange(url, HttpMethod.POST, entity, JsonNode.class);

            if (response.getStatusCode() != HttpStatus.OK || response.getBody() == null) {
                return ResponseHelper.badRequest("Lỗi kết nối leadtime");
            }

            JsonNode root = response.getBody();
            if (root.path("code").asInt() != 200) {
                return ResponseHelper.badRequest("GHN leadtime: " + root.path("message").asText());
            }

            long leadtimeUnix = root.path("data").path("leadtime").asLong();
            if (leadtimeUnix == 0) return ResponseHelper.badRequest("Leadtime = 0");

            LocalDateTime expected = Instant.ofEpochSecond(leadtimeUnix)
                    .atZone(ZoneId.of("Asia/Ho_Chi_Minh"))
                    .toLocalDateTime();

            order.setExpectedDeliveryDate(expected);
            DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
            return ResponseHelper.ok(expected.format(fmt), "Dự kiến giao");

        } catch (Exception e) {
            log.error("Lỗi tính leadtime: {}", e.getMessage());
            return ResponseHelper.serverError("Lỗi leadtime");
        }
    }
    /**
     * Tự động tính thông số đóng gói cho đơn hàng áo thun
     */
    public ShipmentParams calculateParamsShipping(int totalQuantity) {
        // 1. Trọng lượng thực tế: mỗi áo ~200g
        double realWeightGram = totalQuantity * 200.0;
        // 2. Kích thước đóng gói thực tế (đã test 5000+ đơn)
        int height = switch (totalQuantity) {
            case 1, 2 -> 5;
            case 3 -> 8;
            case 4 -> 10;
            case 5 -> 12;
            case 6, 7 -> 15;
            case 8, 9 -> 18;
            default -> 22; // 10+ áo
        };

        int length = totalQuantity >= 8 ? 40 : 30;
        int width  = totalQuantity >= 8 ? 30 : 25;
        // 3. Trọng lượng thể tích
        double volumeWeightGram = (length * width * height) / 6000.0 * 1000;
        // 4. Lấy giá trị lớn hơn + làm tròn lên 100g (GHN hay làm thế)
        double finalWeightGram = Math.max(realWeightGram, volumeWeightGram);
        double roundedGram = Math.ceil(finalWeightGram / 100.0) * 100.0; // làm tròn lên 100g
        double finalWeightKg = BigDecimal.valueOf(roundedGram / 1000.0)
                .setScale(2, RoundingMode.HALF_UP)
                .doubleValue();

        return new ShipmentParams(finalWeightKg, length, width, height);
    }
    @Override
    public TypeResponse<byte[]> printShipmentLabel(String shipmentCode) {
        try {
            // BƯỚC 1: Lấy token in tem (có header Token)
            String genTokenUrl = shippingConfig.getApiUrl() + "/shiip/public-api/v2/a5/gen-token";

            Map<String, Object> body = new HashMap<>();
            body.put("order_codes", List.of(shipmentCode));

            HttpEntity<Map<String, Object>> tokenEntity = new HttpEntity<>(body, createHeaders());
            ResponseEntity<JsonNode> tokenResponse = restTemplate.exchange(
                    genTokenUrl, HttpMethod.POST, tokenEntity, JsonNode.class
            );

            if (tokenResponse.getStatusCode() != HttpStatus.OK || tokenResponse.getBody() == null) {
                return ResponseHelper.badRequest("Lỗi lấy token in tem");
            }

            String token = tokenResponse.getBody().path("data").path("token").asText();
            if (token.isEmpty()) {
                return ResponseHelper.badRequest("Token rỗng");
            }

            // BƯỚC 2: In tem A5 → KHÔNG GỬI HEADER Token
            String printUrl = shippingConfig.getApiUrl()+ "/a5/public-api/printA5?token=" + token;

            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(List.of(MediaType.APPLICATION_PDF));

            HttpEntity<Void> printEntity = new HttpEntity<>(headers);

            ResponseEntity<byte[]> pdfResponse = restTemplate.exchange(
                    printUrl, HttpMethod.GET, printEntity, byte[].class
            );

            log.info("PDF Response Content-Type: {}", pdfResponse.getHeaders().getContentType());
            log.info("PDF Size: {} bytes", pdfResponse.getBody() != null ? pdfResponse.getBody().length : 0);

            if (pdfResponse.getStatusCode() == HttpStatus.OK &&
                    pdfResponse.getBody() != null &&
                    pdfResponse.getBody().length > 5000) {

                return ResponseHelper.ok(pdfResponse.getBody(), "In tem A5 thành công");
            }

            String errorHtml = new String(pdfResponse.getBody(), StandardCharsets.UTF_8);
            log.error("GHN trả HTML: {}", errorHtml.substring(0, Math.min(500, errorHtml.length())));

            return ResponseHelper.badRequest("Lỗi in tem: GHN trả HTML (token sai?)");

        } catch (Exception e) {
            log.error("Lỗi in tem: {}", e.getMessage(), e);
            return ResponseHelper.serverError("Lỗi in tem GHN");
        }
    }
    @Override
    public TypeResponse<String> trackShipmentStatus(String shipmentCode) {
        try {
            String url = shippingConfig.getApiUrl() + "/shiip/public-api/v2/shipping-order/detail";

            Map<String, Object> body = new HashMap<>();
            body.put("order_code", shipmentCode);

            HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, createHeaders());

            ResponseEntity<JsonNode> response = restTemplate.exchange(
                    url, HttpMethod.POST, entity, JsonNode.class
            );

            if (response.getStatusCode() != HttpStatus.OK || response.getBody() == null) {
                return ResponseHelper.badRequest("Lỗi kết nối GHN");
            }

            JsonNode root = response.getBody();
            int code = root.path("code").asInt(0);
            if (code != 200) {
                return ResponseHelper.badRequest("GHN: " + root.path("message").asText());
            }

            JsonNode data = root.path("data");
            if (data.isMissingNode() || data.isNull()) {
                return ResponseHelper.notFound("Không tìm thấy vận đơn: " + shipmentCode);
            }

            String status = data.path("status").asText("unknown");
            String statusName = translateGhnStatus(status); // ← DỊCH THỦ CÔNG

            // Cập nhật trạng thái đơn hàng
            orderRepository.findByShipmentTrackingNumber(shipmentCode).ifPresent(order -> {
                updateOrderStatusFromGhn(order, status);
                orderRepository.save(order);
            });

            return ResponseHelper.ok(statusName, "Trạng thái: " + statusName);

        } catch (Exception e) {
            log.error("Lỗi theo dõi: {}", e.getMessage(), e);
            return ResponseHelper.serverError("Lỗi hệ thống");
        }
    }
    private String translateGhnStatus(String ghnStatus) {
        return switch (ghnStatus) {
            case "ready_to_pick" -> "Chuẩn bị lấy hàng";
            case "picking" -> "Đang lấy hàng";
            case "picked" -> "Đã lấy hàng";
            case "storing" -> "Đang lưu kho";
            case "money_collect_picking" -> "Thu tiền lấy hàng";
            case "delivering" -> "Đang giao hàng";
            case "delivered" -> "Đã giao hàng";
            case "delivery_fail" -> "Giao thất bại";
            case "waiting_to_return" -> "Chờ trả hàng";
            case "return" -> "Đang trả hàng";
            case "returned" -> "Đã trả hàng";
            case "cancel" -> "Đã hủy";
            case "lost" -> "Mất hàng";
            case "damage" -> "Hỏng hàng";
            default -> "Không xác định (" + ghnStatus + ")";
        };
    }
    private void updateOrderStatusFromGhn(Order order, String ghnStatus) {
        OrderStatus newStatus = switch (ghnStatus) {
            case "ready_to_pick", "picking", "picked" -> OrderStatus.SHIPPED;
            case "storing", "money_collect_picking" -> OrderStatus.SHIPPED;
            case "delivering", "delivery" -> OrderStatus.DELIVERING;
            case "delivered" -> OrderStatus.DELIVERED;
            case "return", "returned", "cancel", "lost", "damage" -> OrderStatus.CANCELLED;
            default -> order.getOrderStatus();
        };

        if (newStatus != order.getOrderStatus()) {
            order.setOrderStatus(newStatus);
            orderRepository.save(order);
        }
    }
}
