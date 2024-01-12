package com.tuyenngoc.bookstore.constant;

public class UrlConstant {

    public static class Auth {
        private static final String PRE_FIX = "/auth";

        public static final String LOGIN = PRE_FIX + "/login";
        public static final String LOGOUT = PRE_FIX + "/logout";
        public static final String REGISTER = PRE_FIX + "/register";
        public static final String FORGET_PASSWORD = PRE_FIX + "/forget-password";
        public static final String REFRESH_TOKEN = PRE_FIX + "/refresh-token";

    }

    public static class User {
        private static final String PRE_FIX = "/user";

        public static final String GET_USERS = PRE_FIX;
        public static final String GET_USER = PRE_FIX + "/{userId}";
        public static final String GET_CURRENT_USER = PRE_FIX + "/current";
    }

    public static class Customer {
        private static final String PRE_FIX = "/customer";
        public static final String GET_PRODUCTS = PRE_FIX + "/get-products";

    }

    public static class Product {
        private static final String PRE_FIX = "/product";
        public static final String GET_PRODUCTS = PRE_FIX + "/get-products";

    }

    public static class Cart {
        private static final String PRE_FIX = "/cart";
        public static final String GET_PRODUCTS = PRE_FIX + "/get-total-products";

    }

    public static class Banner {
        private static final String PRE_FIX = "/banner";
        public static final String GET_ALL_BANNERS = PRE_FIX + "/get-all-banners";
        public static final String GET_BANNERS = PRE_FIX + "/get-banners";
        public static final String GET_BANNER = PRE_FIX + "/get-banner/{bannerId}";
        public static final String UPDATE_BANNER = PRE_FIX + "/update-banner/{bannerId}";
        public static final String DELETE_BANNER = PRE_FIX + "/delete-banner/{bannerId}";
        public static final String CREATE_BANNER = PRE_FIX + "/create-banner";

    }
}
