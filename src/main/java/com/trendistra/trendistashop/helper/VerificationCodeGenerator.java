package com.trendistra.trendistashop.helper;
import java.util.Random;
/**
 * Generates a random 6-digit verification code.
 * This method uses Java's Random class to generate a 6-digit number
 * between 100000 and 999999.
 *
 * @return A string representation of the randomly generated 6-digit code.
 */
public class VerificationCodeGenerator {
    public static  String generateCode() {
        Random random = new Random();
        int code = 100000 + random.nextInt(900000);
        return String.valueOf(code);
    }
}
