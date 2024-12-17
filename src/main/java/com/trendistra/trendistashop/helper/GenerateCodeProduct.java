package com.trendistra.trendistashop.helper;

import java.util.Random;

public class GenerateCodeProduct {
    public static String generateCodeProduct() {
        Random random = new Random();
        // Tạo 3 chữ cái ngẫu nhiên
        StringBuilder letters = new StringBuilder();
        for (int i = 0; i < 3; i++) {
            char letter = (char) ('A' + random.nextInt(26)); // Chọn ngẫu nhiên từ A-Z
            letters.append(letter);
        }
        // Tạo 3 chữ số ngẫu nhiên
        int digits  = 100 + random.nextInt(900);
        return letters + String.valueOf(digits);
    }

}
