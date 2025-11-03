package com.trendistashop.docs.user.examples;

public class AddressRequestExamples {
    public static final String CREATE_ADDRESS_REQUEST = """
      {
                   "id": "3fa85f64-5717-4562-b3fc-2c963f66afa6",
                   "name": "Nhà riêng - Kim Mã",
                   "provinceId": "201",
                   "provinceName": "Hà Nội",
                   "districtId": "001",
                   "districtName": "Quận Ba Đình",
                   "wardCode": "00001",
                   "wardName": "Phường Kim Mã",
                   "specAddress": "Số 1, Ngõ 1, Phố Kim Mã",
                   "phoneNumber": "0909090909",
                   "isDefaultAddress": true,
                   "isShopAddress": false
                 }
    """;

    public static final String UPDATE_ADDRESS_REQUEST = """
         {
                   "id": "3fa85f64-5717-4562-b3fc-2c963f66afa6",
                   "name": "Nhà riêng - Kim Mã",
                   "provinceId": "201",
                   "provinceName": "Hà Nội",
                   "districtId": "001",
                   "districtName": "Quận Ba Đình",
                   "wardCode": "00001",
                   "wardName": "Phường Kim Mã",
                   "specAddress": "Số 1, Ngõ 1, Phố Kim Mã",
                   "phoneNumber": "0909090909",
                   "isDefaultAddress": true,
                   "isShopAddress": false
                 }
    """;

    public static final String DELETE_ADDRESS_REQUEST = """
        {
            "id": "3fa85f64-5717-4562-b3fc-2c963f66afa6"
        }
    """;
}
