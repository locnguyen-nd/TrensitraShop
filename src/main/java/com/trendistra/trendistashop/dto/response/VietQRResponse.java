package com.trendistra.trendistashop.dto.response;

import lombok.Data;

@Data
public class VietQRResponse {
    private String code;
    private String desc;
    private VietQRData data;

    @Data
    public static class VietQRData {
        private String qrCode;
        private String qrDataURL;

        @Override
        public String toString() {
            return "VietQRData(qrCode=" + (qrCode != null ? qrCode.substring(0, 20) + "..." : "null") +
                    ", qrDataURL=" + (qrDataURL != null ? qrDataURL.substring(0, 20) + "..." : "null") + ")";
        }
    }

    @Override
    public String toString() {
        return "VietQRResponse(code=" + code + ", desc=" + desc + ", data=" + data + ")";
    }
}
