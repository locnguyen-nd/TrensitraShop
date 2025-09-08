package com.trendistashop.docs.cart.examples;

public class CartRequestExamples {
    public static final String ADD_TO_CART_REQUEST = """
        {
          "productId": "3fa85f64-5717-4562-b3fc-2c963f66afa6",
          "variantDTO": {
            "id": "3fa85f64-5717-4562-b3fc-2c963f66afa6"
          },
          "quantity": 1
        }
    """;

    public static final String DELETE_CART_ITEM_REQUEST = """
        {
          "productId": "3fa85f64-5717-4562-b3fc-2c963f66afa6",
          "variantDTO": {
            "id": "3fa85f64-5717-4562-b3fc-2c963f66afa6"
          },
          "quantity": 1
        }
    """;
}
