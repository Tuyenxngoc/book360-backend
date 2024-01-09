package com.tuyenngoc.bookstore.constant;

public class ErrorMessage {

    public static final String ERR_EXCEPTION_GENERAL = "exception.general";
    public static final String UNAUTHORIZED = "exception.unauthorized";
    public static final String FORBIDDEN = "exception.forbidden";
    public static final String FORBIDDEN_UPDATE_DELETE = "exception.forbidden.update-delete";

    //error validation dto
    public static final String INVALID_SOME_THING_FIELD = "invalid.general";
    public static final String INVALID_FORMAT_SOME_THING_FIELD = "invalid.general.format";
    public static final String INVALID_SOME_THING_FIELD_IS_REQUIRED = "invalid.general.required";
    public static final String NOT_BLANK_FIELD = "invalid.general.not-blank";
    public static final String INVALID_FORMAT_USERNAME = "invalid.username-format";
    public static final String INVALID_FORMAT_PASSWORD = "invalid.password-format";
    public static final String INVALID_FORMAT_EMAIL = "invalid.email-format";
    public static final String INVALID_FORMAT_PHONE = "invalid.phone-format";
    public static final String INVALID_DATE = "invalid.date-format";
    public static final String INVALID_DATE_FEATURE = "invalid.date-future";
    public static final String INVALID_DATETIME = "invalid.datetime-format";
    public static final String INVALID_TIME = "invalid.time-format";
    public static final String INVALID_REPEAT_PASSWORD = "invalid.password-repeat";
    public static final String INVALID_COORDINATES = "invalid.coordinates";

    public static class Auth {
        public static final String ERR_INCORRECT_USERNAME_PASSWORD = "exception.auth.incorrect.username-password";
        public static final String ERR_INCORRECT_EMAIL = "exception.auth.incorrect.email";
        public static final String ERR_DUPLICATE_USERNAME = "exception.auth.duplicate.username";
        public static final String ERR_DUPLICATE_EMAIL = "exception.auth.duplicate.email";
        public static final String ERR_DUPLICATE_USERNAME_EMAIL = "exception.auth.duplicate.username.email";
        public static final String ERR_ACCOUNT_NOT_ENABLED = "exception.auth.account.not.enabled";
        public static final String ERR_ACCOUNT_LOCKED = "exception.auth.account.locked";
        public static final String INVALID_REFRESH_TOKEN = "exception.auth.invalid.refresh.token";
        public static final String EXPIRED_REFRESH_TOKEN = "exception.auth.expired.refresh.token";
    }

    public static class User {
        public static final String ERR_NOT_FOUND_USERNAME = "exception.user.not.found.username";
        public static final String ERR_NOT_FOUND_EMAIL = "exception.user.not.found.email";
        public static final String ERR_NOT_FOUND_ID = "exception.user.not.found.id";
    }

    public static class Customer {
        public static final String ERR_NOT_FOUND_ID = "exception.customer.not.found.id";
    }

    public static class Product {
        public static final String ERR_NOT_FOUND_ID = "exception.product.not.found.id";
    }

    public static class Category {
        public static final String ERR_NOT_FOUND_ID = "exception.category.not.found.id";
    }

    public static class Shop {
        public static final String ERR_NOT_FOUND_ID = "exception.shop.not.found.id";
    }

    public static class Cart {
        public static final String ERR_NOT_FOUND_ID = "exception.cart.not.found.id";
    }

    public static class ShopProduct {
        public static final String ERR_NOT_FOUND = "exception.shop.not.found.product";
    }

    public static class Bill {
        public static final String ERR_NOT_ALLOW_CANCEL = "exception.order.not.allow.cancel";
    }

    public static class CartProduct {
        public static final String ERR_NOT_IN_STOCK = "exception.quantity.not.in.stock";
    }
}
