package com.trendistashop.docs.user.examples;

public class AddressRequestExamples {
    public static final String CREATE_ADDRESS_REQUEST = """
        {
            "id": "3fa85f64-5717-4562-b3fc-2c963f66afa6",
            "name": "địa chỉ 1",
            "city": "Hà Nội",
            "district": "Quận Ba Đình",
            "ward": "Phường Nghĩa Đô",
            "specAddress": "Số 1, Ngõ 1, Phố 1",
            "phoneNumber": "0909090909",
            "isDefaultAddress": true
        }
    """;

    public static final String UPDATE_ADDRESS_REQUEST = """
        {
            "id": "3fa85f64-5717-4562-b3fc-2c963f66afa6",
            "name": "địa chỉ 1",
            "city": "Hà Nội",
            "district": "Quận Ba Đình",
            "ward": "Phường Nghĩa Đô",
            "specAddress": "Số 1, Ngõ 1, Phố 1",
            "phoneNumber": "0909090909",
            "isDefaultAddress": true
        }
    """;

    public static final String DELETE_ADDRESS_REQUEST = """
        {
            "id": "3fa85f64-5717-4562-b3fc-2c963f66afa6"
        }
    """;
}
