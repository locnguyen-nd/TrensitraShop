package com.trendistashop.services.impl.order;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.trendistashop.config.ShippingConfig;
import com.trendistashop.constants.ResponseMessage;
import com.trendistashop.dto.request.CreateShipmentRequest;
import com.trendistashop.dto.response.TypeResponse;
import com.trendistashop.entities.user.Address;
import com.trendistashop.entities.user.Order;
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
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
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
    public TypeResponse<BigDecimal> calculateShippingCost( Address toAddress, double weight, Map<String, Integer> dimensions, String serviceType) {
        Address fromAddress = addressService.getShopAddress().get();
        try {
            String url = shippingConfig.getApiUrl() + "/shiip/public-api/v2/shipping-order/fee";

            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("shop_id", shippingConfig.getShopId());
            requestBody.put("from_district_id", fromAddress.getDistrictId());
            requestBody.put("to_district_id", toAddress.getDistrictId());
            requestBody.put("to_ward_code", toAddress.getWardCode());
            requestBody.put("weight", weight);
            requestBody.put("length", dimensions.getOrDefault("length", 0));
            requestBody.put("width", dimensions.getOrDefault("width", 0));
            requestBody.put("height", dimensions.getOrDefault("height", 0));
            requestBody.put("service_type_id", serviceType.equals("express") ? 2 : 5);

            HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, createHeaders());

            ResponseEntity<JsonNode> response = restTemplate.exchange(url, HttpMethod.POST, entity, JsonNode.class);

            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                JsonNode data = response.getBody().get("data");
                BigDecimal fee = new BigDecimal(data.get("total").asText());
                return ResponseHelper.ok(fee, ResponseMessage.FETCH_SUCCESS);
            } else {
                return ResponseHelper.badRequest("Lỗi tính phí GHN: " + response.getBody());
            }
        } catch (Exception e) {
            log.error("Lỗi tính phí GHN: {}", e.getMessage(), e);
            return ResponseHelper.serverError(ResponseMessage.FETCH_FAILED);
        }
    }

    @Override
    public TypeResponse<String> createShipment(CreateShipmentRequest request) {
        try {
            Order order = orderRepository.findById(request.getOrderId())
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy đơn hàng"));

            if (order.getShipmentTrackingNumber() != null) {
                return ResponseHelper.validationError("trackingNumber","Đơn hàng đã có mã vận đơn trước đó");
            }
            Address fromAddress = addressService.getShopAddress().get();
            Address toAddress = order.getAddress();
            // Lấy các thông tin từ order và address để tạo đơn vận chuyển
            Double weight = request.getWeight();
            Double length = request.getLength();
            Double width = request.getWidth();
            Double height = request.getHeight();
            String requiredNote = request.getRequiredNote();
            Integer serviceTypeId = request.getServiceId();
            if (weight == null || weight <= 0) weight = 200.0; // default
            if (length == null || length <= 0) length = 15.0;
            if (width == null || width <= 0) width = 15.0;
            if (height == null || height <= 0) height = 10.0;
            if (serviceTypeId == null) serviceTypeId = 53320;
            if (requiredNote == null || requiredNote.isEmpty()) requiredNote = "ĐƯỢC KIỂM TRA HÀNG KHI NHẬN";
            String url = shippingConfig.getApiUrl() + "/shiip/public-api/v2/shipping-order/create";
            // Tạo body theo yêu cầu của GHN API
            Map<String, Object> body = new HashMap<>();
            body.put("shop_id", shippingConfig.getShopId());
            body.put("payment_type_id", 2);
            body.put("note", Optional.ofNullable(order.getNote()).orElse(""));
            body.put("required_note", requiredNote);
            body.put("from_name", fromAddress.getName()); // "Kho Hàng"
            body.put("from_phone", fromAddress.getPhoneNumber());
            body.put("from_address", fromAddress.getSpecAddress());
            body.put("from_ward_code", fromAddress.getWardCode());
            body.put("from_district_id", Integer.parseInt(fromAddress.getDistrictId()));
            body.put("to_name", toAddress.getName());
            body.put("to_phone", toAddress.getPhoneNumber());
            body.put("to_address", toAddress.getSpecAddress());
            body.put("to_ward_code", toAddress.getWardCode());
            body.put("to_district_id", Integer.parseInt(toAddress.getDistrictId()));
            body.put("cod_amount", PaymentMethod.COD.name().equals(order.getPaymentMethod())
                    ? order.getTotalAmount().intValue() : 0);
            body.put("weight", weight);
            body.put("length", length);
            body.put("width", width);
            body.put("height", height);
            body.put("service_type_id", serviceTypeId);
            List<Map<String, Object>> items = order.getOrderItems().stream()
                    .map(oi -> {
                        Map<String, Object> item = new HashMap<>();
                        item.put("name", oi.getProduct().getName());
                        item.put("code", Optional.ofNullable(oi.getProduct().getCode()).orElse(""));
                        item.put("quantity", oi.getQuantity());
                        item.put("price", oi.getItemPrice().intValue());
                        return item;
                    })
                    .collect(Collectors.toList());
            body.put("items", items);

            HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, createHeaders());
            ResponseEntity<JsonNode> response = restTemplate.exchange(url, HttpMethod.POST, entity, JsonNode.class);

            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                JsonNode data = response.getBody().get("data");
                String orderCode = data.get("order_code").asText();

                order.setShipmentTrackingNumber(orderCode);
                order.setOrderStatus(OrderStatus.SHIPPED);
                TypeResponse<String> leadtimeResult = calculateLeadtime(order, fromAddress, toAddress, serviceTypeId);
                if (!leadtimeResult.isSuccess()) {
                    log.warn("Tạo đơn thành công nhưng không tính được leadtime: {}", leadtimeResult.getMessage());
                } else {
                    order.setExpectedDeliveryDate(LocalDateTime.parse(leadtimeResult.getData()));
                }
                orderRepository.save(order);

                return ResponseHelper.ok(orderCode, "Tạo đơn thành công. Dự kiến giao: " + leadtimeResult.getData());            }
            return ResponseHelper.badRequest("Lỗi tạo đơn GHN: " + response.getBody());

        } catch (Exception e) {
            log.error("Lỗi tạo shipment: {}", e.getMessage(), e);
            return ResponseHelper.serverError("Lỗi tạo shipment");
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
