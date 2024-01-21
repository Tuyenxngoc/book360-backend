package com.tuyenngoc.bookstore.constant;

public class ErrorMessage {

    public static final String ERR_EXCEPTION_GENERAL = "exception.general";
    public static final String ERR_UNAUTHORIZED = "exception.unauthorized";
    public static final String ERR_FORBIDDEN = "exception.forbidden";
    public static final String ERR_FORBIDDEN_UPDATE_DELETE = "exception.forbidden.update-delete";
    public static final String ERR_METHOD_NOT_SUPPORTED = "exception.method-not-supported";
    public static final String ERR_REQUEST_BODY = "exception.required-request-body-missing";
    public static final String ERR_REQUIRED_MISSING_PARAMETER = "exception.missing-servlet-request-parameter";
    public static final String ERR_METHOD_ARGUMENT_TYPE_MISMATCH = "exception.method-argument-type-mismatch";
    public static final String ERR_RESOURCE_NOT_FOUND = "exception.resource-not-found";

    public static final String INVALID_SOME_THING_FIELD = "invalid.general";
    public static final String INVALID_FORMAT_SOME_THING_FIELD = "invalid.general.format";
    public static final String INVALID_SOME_THING_FIELD_IS_REQUIRED = "invalid.general.required";
    public static final String INVALID_NOT_BLANK_FIELD = "invalid.general.not-blank";
    public static final String INVALID_FORMAT_USERNAME = "invalid.username-format";
    public static final String INVALID_FORMAT_PASSWORD = "invalid.password-format";
    public static final String INVALID_REPEAT_PASSWORD = "invalid.password-repeat";
    public static final String INVALID_FORMAT_EMAIL = "invalid.email-format";
    public static final String INVALID_FORMAT_PHONE = "invalid.phone-format";
    public static final String INVALID_COORDINATES = "invalid.coordinates";
    public static final String INVALID_VIEW_ORDER = "invalid.view-order";
    public static final String INVALID_MINIMUM_ONE = "invalid.minimum-one";
    public static final String INVALID_MINIMUM_ZERO = "invalid.minimum-zero";
    public static final String INVALID_MAXIMUM_ONE_HUNDRED = "invalid.maximum-one-hundred";
    //Date
    public static final String INVALID_DATE = "invalid.date-format";
    public static final String INVALID_DATE_FEATURE = "invalid.date-future";
    public static final String INVALID_DATE_PAST = "invalid.date-past";
    public static final String INVALID_TIME = "invalid.time-format";
    public static final String INVALID_LOCAL_DATE_FORMAT = "invalid.local-date-format";
    public static final String INVALID_LOCAL_DATE_TIME_FORMAT = "invalid.local-date-time-format";
    //File
    public static final String INVALID_MAX_UPLOAD_SIZE_EXCEEDED = "invalid.max-upload-size-exceeded";
    public static final String INVALID_FILE_REQUIRED = "invalid.file.required";
    public static final String INVALID_FILE_SIZE = "invalid.file.size";
    public static final String INVALID_FILE_TYPE = "invalid.file.type";

    public static class Auth {
        public static final String ERR_INCORRECT_USERNAME_PASSWORD = "exception.auth.incorrect.username-password";
        public static final String ERR_INCORRECT_PASSWORD = "exception.auth.incorrect.password";
        public static final String ERR_INCORRECT_EMAIL = "exception.auth.incorrect.email";
        public static final String ERR_DUPLICATE_USERNAME = "exception.auth.duplicate.username";
        public static final String ERR_DUPLICATE_EMAIL = "exception.auth.duplicate.email";
        public static final String ERR_DUPLICATE_USERNAME_EMAIL = "exception.auth.duplicate.username.email";
        public static final String ERR_ACCOUNT_NOT_ENABLED = "exception.auth.account.not.enabled";
        public static final String ERR_ACCOUNT_LOCKED = "exception.auth.account.locked";
        public static final String ERR_INVALID_REFRESH_TOKEN = "exception.auth.invalid.refresh.token";
        public static final String EXPIRED_REFRESH_TOKEN = "exception.auth.expired.refresh.token";
    }

    public static class User {
        public static final String ERR_NOT_FOUND_USERNAME_OR_EMAIL = "exception.user.not.found.username-email";
        public static final String ERR_NOT_FOUND_USERNAME = "exception.user.not.found.username";
        public static final String ERR_NOT_FOUND_EMAIL = "exception.user.not.found.email";
        public static final String ERR_NOT_FOUND_ID = "exception.user.not.found.id";
        public static final String ERR_NOT_FOUND_ACCOUNT = "exception.user.not.found.account";
    }

    public static class Customer {
        public static final String ERR_NOT_FOUND_ID = "exception.customer.not.found.id";
    }

    public static class Product {
        public static final String ERR_NOT_FOUND_ID = "exception.product.not.found.id";
        public static final String ERR_INSUFFICIENT_STOCK = "exception.product.insufficient.stock";
    }

    public static class Category {
        public static final String ERR_NOT_FOUND_ID = "exception.category.not.found.id";
    }

    public static class Cart {
        public static final String ERR_NOT_FOUND_ID = "exception.cart.not.found.id";
        public static final String ERR_NOT_FOUND_CUSTOMER_ID = "exception.cart.not.found.customer-id";
        public static final String ERR_NOT_FOUND_PRODUCT_ID = "exception.cart.not.found.product-id";
    }

    public static class Bill {
        public static final String ERR_NOT_FOUND_ID = "exception.bill.not.found.id";
        public static final String ERR_NOT_ALLOW_CANCEL = "exception.order.not.allow.cancel";
    }

    public static class Banner {
        public static final String ERR_NOT_FOUND_ID = "exception.banner.not.found.id";
    }
}
