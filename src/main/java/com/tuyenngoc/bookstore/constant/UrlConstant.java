package com.tuyenngoc.bookstore.constant;

public class UrlConstant {

    public static class Auth {
        private static final String PRE_FIX = "/auth";

        public static final String LOGIN = PRE_FIX + "/login";
        public static final String LOGOUT = PRE_FIX + "/logout";
        public static final String REGISTER = PRE_FIX + "/register";
        public static final String FORGET_PASSWORD = PRE_FIX + "/forget-password";
        public static final String CHANGE_PASSWORD = PRE_FIX + "/change-password/{username}";
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

        public static final String GET_FAVORITE_PRODUCTS = PRE_FIX + "/{customerId}/favorite-products";
        public static final String CHECK_FAVORITE_PRODUCT = PRE_FIX + "/{customerId}/favorite-products/{productId}";
        public static final String ADD_FAVORITE_PRODUCT = PRE_FIX + "/{customerId}/favorite-products/{productId}";
        public static final String REMOVE_FAVORITE_PRODUCT = PRE_FIX + "/{customerId}/favorite-products/{productId}";
    }

    public static class Product {
        private static final String PRE_FIX = "/product";

        public static final String GET_PRODUCTS = PRE_FIX + "/get-products";
        public static final String FIND_PRODUCT = PRE_FIX + "/find-products";

        public static final String GET_PRODUCTS_BY_CATEGORY_ID = PRE_FIX + "/get-products-by-categoryId/{categoryId}";

        public static final String GET_PRODUCT_DETAIL = PRE_FIX + "/get-product-detail/{productId}";
        public static final String GET_PRODUCTS_SAME_AUTHOR = PRE_FIX + "/get-products-same-author/{productId}";

    }

    public static class Cart {
        private static final String PRE_FIX = "/cart";

        public static final String GET_TOTAL_PRODUCTS = PRE_FIX + "/get-total-products";
        public static final String ADD_PRODUCT_TO_CART = PRE_FIX + "/add-product";
        public static final String GET_PRODUCTS_FROM_CART = PRE_FIX + "/get-products";
        public static final String UPDATE_CART_DETAIL = PRE_FIX + "/update-cart";
        public static final String DELETE_PRODUCT = PRE_FIX + "/delete-product/{productId}";

    }

    public static class Bill {
        private static final String PRE_FIX = "/bill";

        public static final String SAVE_ORDER = PRE_FIX + "/save-order";

    }


    public static class Banner {
        private static final String PRE_FIX = "/banner";

        public static final String GET_BANNER = PRE_FIX + "/get-banner/{bannerId}";
        public static final String GET_BANNERS = PRE_FIX + "/get-banners";
        public static final String GET_ALL_BANNERS = PRE_FIX + "/get-all-banners";

        public static final String UPDATE_BANNER = PRE_FIX + "/update-banner/{bannerId}";
        public static final String DELETE_BANNER = PRE_FIX + "/delete-banner/{bannerId}";
        public static final String CREATE_BANNER = PRE_FIX + "/create-banner";

    }

    public static class Category {
        private static final String PRE_FIX = "/category";

        public static final String CREATE_CATEGORY = PRE_FIX + "/create-category";
        public static final String UPDATE_CATEGORY = PRE_FIX + "/update-category/{categoryId}";
        public static final String DELETE_CATEGORY = PRE_FIX + "/delete-category/{categoryId}";
        public static final String GET_CATEGORY = PRE_FIX + "/get-category/{categoryId}";
        public static final String GET_CATEGORIES = PRE_FIX + "/get-categories";

    }
}
