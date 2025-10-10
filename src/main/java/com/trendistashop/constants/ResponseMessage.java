package com.trendistashop.constants;

public class ResponseMessage {
    public static final String CREATE_SUCCESS = "create_success";
    public static final String UPDATE_SUCCESS = "update_success";
    public static final String DELETE_SUCCESS = "delete_success";
    public static final String FETCH_SUCCESS = "fetch_success";
    public static final String CREATE_FAILED = "create_failed";
    public static final String UPDATE_FAILED = "update_failed";
    public static final String DELETE_FAILED = "delete_failed";
    public static final String FETCH_FAILED = "fetch_failed";

    public static final String AUTHENTICATED = "authenticated";
    public static final String UNAUTHENTICATED = "unauthenticated";
    public static final String UNAUTHORIZED = "unauthorized";
    public static final String PASSWORD_CREATE_SUCCESS = "password_create_success";
    public static final String PASSWORD_UPDATE_SUCCESS = "password_update_success";
    public static final String LOGOUT_SUCCESS = "logout_success";
    public static final String LOGOUT_FAILED = "logout_failed";
    public static final String PERMISSION_DENIED = "permission_denied";
    public static final String TOKEN_REFRESH_INVALID = "token_refresh_invalid";
    public static final String TOKEN_REFRESH_SUCCESS = "token_refresh_success";
    public static final String TOKEN_REFRESH_FAILURE = "token_refresh_failure";
    public static final String INCORRECT_CURRENT_PASSWORD = "incorrect_current_password";
    public static final String ACCOUNT_LOCKED = "account_locked";
    public static final String VALIDATION_ERROR = "validation_error";
    public static final String BAD_REQUEST = "bad_request";
    public static final String NOT_FOUND = "not_found";
    public static final String FORBIDDEN = "forbidden";
    public static final String SERVER_ERROR = "server_error";

    // response message
    public static final String EMAIL_EXISTS = "email_exists";
    public static final String GENDER_EXISTS = "gender_exists";
    public static final String COLOR_EXISTS = "color_exists";
    public static final String CREDENTIALS_DO_NOT_MATCH_RECORD = "credentials_do_not_match_record";
    public static final String PASSWORD_NOT_MATCH = "password_not_match";
    public static final String TOKEN_INVALID = "token_invalid";
    public static final String SEND_CODE_SUCCESS = "send_code_success";
    public static final String SEND_CODE_FAILED = "send_code_failed";
    public static final String INVALID_OR_EXPIRED_TOKEN = "invalid_or_expired_token";
    public static final String ACCOUNT_ALREADY_ACTIVATED = "account_already_activated";
    public static final String ACCOUNT_NOT_ACTIVATED = "account_not_activated";
    public static final String VERIFICATION_SUCCESS = "verification_success";
    public static final String VERIFICATION_FAILED = "verification_failed";
    public static final String TOO_MANY_ATTEMPTS = "too_many_attempts";
    public static final String VERIFICATION_REQUEST_SUCCESS = "verification_request_success";
    public static final String INVALID_OTP_CODE = "invalid_otp_code";
    public static final String EXPIRED_OTP_CODE = "expired_otp_code";
    public static final String PASSWORD_SAME_AS_OLD = "password_same_as_old";
    public static final String CART_EMPTY = "cart_empty";
    public static final String DISCOUNT_EXIST = "discount_code_existed";


    public static final String USER_NOT_FOUND = "user_not_found";
    public static final String ROLE_NOT_FOUND = "role_not_found";
    public static final String PRODUCT_NOT_FOUND = "product_not_found";
    public static final String GENDER_NOT_FOUND = "gender_not_found";
    public static final String CATEGORY_NOT_FOUND = "category_not_found";
    public static final String ADDRESS_NOT_FOUND = "address_not_found";
    public static final String CART_NOT_FOUND = "cart_not_found";
    public static final String ORDER_NOT_FOUND = "order_not_found";
    public static final String SIZE_NOT_FOUND = "size_not_found";
    public static final String COLOR_NOT_FOUND = "color_not_found";
    public static final String COLLECTION_NOT_FOUND = "collection_not_found";
    public static final String COLLECTION_NAME_EXIST = "collection_name_exist";
    public static final String BANNER_EVENT_EXISTS = "banner_event_exists";
    public static final String REQUIRED = "required";

    private ResponseMessage() {
    }
}
