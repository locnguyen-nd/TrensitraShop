package com.trendistra.trendistashop.helper;

import com.trendistra.trendistashop.repositories.product.DiscountRepository;

import java.security.SecureRandom;
import java.util.Random;

public class GenerateCodeDiscount {
    // Các ký tự có thể dùng trong mã giảm giá: chữ in hoa và số
    private static final String CODE_CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final int CODE_LENGTH = 6;
    private static final Random random = new SecureRandom();

    // Giả sử discountRepository được inject vào để kiểm tra mã đã tồn tại hay chưa
    private DiscountRepository discountRepository;

    public GenerateCodeDiscount(DiscountRepository discountRepository) {
        this.discountRepository = discountRepository;
    }

    /**
     * Sinh mã giảm giá 6 ký tự và đảm bảo tính duy nhất.
     * Nếu mã đã tồn tại, sẽ tiếp tục sinh mã mới cho đến khi duy nhất.
     *
     * @return Mã giảm giá duy nhất
     */
    public String generateUniqueDiscountCode() {
        String code;
        do {
            code = generateRandomCode();
        } while (discountRepository.existsByCode(code));
        return code;
    }

    /**
     * Sinh một mã ngẫu nhiên với độ dài CODE_LENGTH.
     *
     * @return Mã giảm giá ngẫu nhiên
     */
    private String generateRandomCode() {
        StringBuilder sb = new StringBuilder(CODE_LENGTH);
        for (int i = 0; i < CODE_LENGTH; i++) {
            int index = random.nextInt(CODE_CHARACTERS.length());
            sb.append(CODE_CHARACTERS.charAt(index));
        }
        return sb.toString();
    }
}
