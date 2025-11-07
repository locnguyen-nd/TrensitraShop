package com.trendistashop.controllers.admin;

import com.trendistashop.dto.response.TypeResponse;
import com.trendistashop.dto.response.record.RevenueReportDTO;
import com.trendistashop.dto.response.record.StatusSummaryDTO;
import com.trendistashop.dto.response.record.TopProductDTO;
import com.trendistashop.enums.OrderStatus;
import com.trendistashop.services.IOrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

/**
 *
 * @author Locnd
 */
@RestController
@RequestMapping("${api.prefix}/order/report")
@RequiredArgsConstructor
@CrossOrigin
@Slf4j
@Tag(name = "Order Report API", description = "Thống kê & báo cáo đơn hàng (Admin)")
public class OrderReportController {
    private final IOrderService orderService;

    @GetMapping("/revenue")
    @Operation(summary = "Doanh thu theo khoảng thời gian", description = "Thống kê tổng tiền, số đơn, hoàn thành...")
    public ResponseEntity<TypeResponse<RevenueReportDTO>> getRevenueReport(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to) {
        TypeResponse<RevenueReportDTO> response = orderService.getRevenueReport(from, to);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/status-summary")
    @Operation(summary = "Tóm tắt số lượng đơn theo trạng thái")
    public ResponseEntity<TypeResponse<List<StatusSummaryDTO>>> getStatusSummary(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to) {
        TypeResponse<List<StatusSummaryDTO>> response = orderService.getOrderStatusSummary(from, to);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/top-products")
    @Operation(summary = "Sản phẩm bán chạy nhất")
    public ResponseEntity<TypeResponse<List<TopProductDTO>>> getTopProducts(
            @RequestParam(defaultValue = "10") int limit,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to) {
        TypeResponse<List<TopProductDTO>> response = orderService.getTopSellingProducts(limit, from, to);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }
    @GetMapping(value = "/pdf/{orderId}", produces = MediaType.APPLICATION_PDF_VALUE)
    @Operation(summary = "Xuất file pdf thông tin đơn hàng 2 mode: inline / download")
    public ResponseEntity<byte[]> exportOrderInvoicePdf(
            @PathVariable UUID orderId,
            @RequestParam(defaultValue = "inline") String mode) { // ?mode=inline hoặc ?mode=download
        TypeResponse<byte[]> response = orderService.exportInvoicePdf(orderId);
        if (response.getData() == null) {
            return ResponseEntity.status(response.getStatusCode()).build();
        }
        String filename = "Hóa_đơn_" + orderId + ".pdf";
        String encodedFilename = "filename*=UTF-8''" + URLEncoder.encode(filename, StandardCharsets.UTF_8);
        String disposition = "download".equalsIgnoreCase(mode) ? "attachment" : "inline";
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .header(HttpHeaders.CONTENT_DISPOSITION, disposition + "; " + encodedFilename)
                .body(response.getData());
    }

    @GetMapping(value = "/excel", produces = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
    @Operation(summary = "Tải danh sách đơn hàng ra Excel ")
    public ResponseEntity<byte[]> exportOrdersToExcel(
            @RequestParam(required = false) OrderStatus status,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to) {
        TypeResponse<byte[]> response = orderService.exportOrdersToExcel(status, from, to);
        if (response.getData() == null) {
            return ResponseEntity.status(response.getStatusCode()).build();
        }

        String filename = "Danh_sách_đơn_hàng_" + LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")) + ".xlsx";
        String encodedFilename = "filename*=UTF-8''" + URLEncoder.encode(filename, StandardCharsets.UTF_8);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; " + encodedFilename)
                .body(response.getData());
    }
}
