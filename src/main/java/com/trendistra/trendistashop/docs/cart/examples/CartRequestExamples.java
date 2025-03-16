package com.trendistra.trendistashop.docs.cart.examples;

public class CartRequestExamples {
    public static final String ADD_PRODUCT_TO_CART_REQUEST = """
        {
          "productId": "1234567890",
          "variantDTO": {
            "id": "1234567890"
          },
          "quantity": 1
        }
    """;

    public static final String REGISTER_REQUEST = """
        {
          "firstName": "Nguyen",
          "lastName": "Van A",
          "email": "trendista@example.com",
          "phoneNumber": "0987654321",
          "password": "password",
          "confirmPassword": "password"
        }
    """;

    public static final String VERIFY_EMAIL_REQUEST = """
        {
          "email": "trendista@example.com",
          "token": "eyJhbGciOiJIUzI1NiJ9..."
        }
    """;

    public static final String RESEND_VERIFY_EMAIL_REQUEST = """
        {
          "email": "trendista@example.com"
        }
    """;

    public static final String FORGOT_PASSWORD_REQUEST = """
        {
          "email": "trendista@example.com"
        }
    """;
    
    public static final String LOGIN_REQUEST = """
        {
          "email": "trendista@example.com",
          "password": "password"
        }
    """;

    public static final String RESET_PASSWORD_REQUEST = """
        {
          "email": "trendista@example.com",
          "code": "123456",
          "password": "newpassword",
          "confirmPassword": "confirmnewpassword"
        }
    """;
}
