package com.trendistra.trendistashop.docs.auth.examples;

public class AuthRequestExamples {
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

    public static final String VERIFY_RESET_PASSWORD_REQUEST = """
        {
          "email": "trendista@example.com",
          "code": "123456",
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
          "password": "newpassword"
        }
    """;
}
