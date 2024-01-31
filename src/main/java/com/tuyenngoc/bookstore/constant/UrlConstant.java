package com.tuyenngoc.bookstore.constant;

public class UrlConstant {

    public static final String ADMIN_URL = "/admin";

    public static class Auth {
        private static final String PRE_FIX = "/auth";

        public static final String LOGIN = PRE_FIX + "/login";
        public static final String LOGOUT = PRE_FIX + "/logout";
        public static final String REGISTER = PRE_FIX + "/register";
        public static final String FORGET_PASSWORD = PRE_FIX + "/forget-password";
        public static final String CHANGE_PASSWORD = PRE_FIX + "/change-password";
        public static final String REFRESH_TOKEN = PRE_FIX + "/refresh-token";
    }

    public static class User {
        private static final String PRE_FIX = "/user";

        public static final String GET_CURRENT_USER = PRE_FIX + "/current";
    }

    public static class Customer {
        private static final String PRE_FIX = "/customer";

        public static final String GET_FAVORITE_PRODUCTS = PRE_FIX + "/favorite-products";
        public static final String CHECK_FAVORITE_PRODUCT = PRE_FIX + "/favorite-products/{productId}";
        public static final String ADD_FAVORITE_PRODUCT = PRE_FIX + "/favorite-products/{productId}";
        public static final String REMOVE_FAVORITE_PRODUCT = PRE_FIX + "/favorite-products/{productId}";

        public static final String UPLOAD_IMAGE = PRE_FIX + "/upload-image";
        public static final String UPLOAD_IMAGES = PRE_FIX + "/upload-images";
        public static final String UPDATE_CUSTOMER = PRE_FIX;

        //Admin
        public static final String GET_TODO = PRE_FIX + "/get-todo";
        public static final String GET_COUNT_CUSTOMER = PRE_FIX + "/get-count-customer";
        public static final String GET_CUSTOMERS = PRE_FIX + "/get-customers";
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
        public static final String GET_BILLS = PRE_FIX + "/get-bills";
        public static final String CANCEL_ORDER = PRE_FIX + "/cancel-order/{billId}";
        public static final String GET_COUNT_BILLS_BY_STATUS = PRE_FIX + "/get-count-bills-by-status";

        public static final String GET_COUNT_BILLS = PRE_FIX + "/get-count-bills";
        public static final String GET_ALL_BILLS = PRE_FIX + "/get-all-bills";
    }

    public static class Author {
        private static final String PRE_FIX = "/author";

        //Private
        public static final String GET_ALL_AUTHORS = ADMIN_URL + PRE_FIX + "/all";
    }

    public static class Banner {
        private static final String PRE_FIX = "/banner";

        //Public
        public static final String GET_ALL_BANNERS = PRE_FIX + "/all";

        //Private
        public static final String GET_BANNERS_FOR_ADMIN = ADMIN_URL + PRE_FIX + "/get";
        public static final String GET_BANNER = ADMIN_URL + PRE_FIX + "/{bannerId}";
        public static final String CREATE_BANNER = ADMIN_URL + PRE_FIX + "/create";
        public static final String DELETE_BANNER = ADMIN_URL + PRE_FIX + "/delete/{bannerId}";
    }

    public static class BookSet {
        private static final String PRE_FIX = "/book-set";

        //Public

        //Private
        public static final String GET_ALL_BOOK_SETS = ADMIN_URL + PRE_FIX + "/all";
    }

    public static class Category {
        private static final String PRE_FIX = "/category";

        //Public
        public static final String GET_CATEGORIES = PRE_FIX + "/get";

        //Private
        public static final String GET_CATEGORY = ADMIN_URL + PRE_FIX + "/{categoryId}";
        public static final String GET_ALL_CATEGORIES = ADMIN_URL + PRE_FIX + "/all";
        public static final String GET_CATEGORIES_FOR_ADMIN = ADMIN_URL + PRE_FIX + "/get";
        public static final String CREATE_CATEGORY = ADMIN_URL + PRE_FIX + "/create";
        public static final String DELETE_CATEGORY = ADMIN_URL + PRE_FIX + "/delete/{categoryId}";
    }

    public static class Product {
        private static final String PRE_FIX = "/product";

        //Public
        public static final String GET_PRODUCTS = PRE_FIX + "/get";
        public static final String FIND_PRODUCT = PRE_FIX + "/find";
        public static final String GET_PRODUCTS_BY_CATEGORY_ID = PRE_FIX + "/category/{categoryId}";
        public static final String GET_PRODUCTS_BY_AUTHOR_ID = PRE_FIX + "/author/{authorId}";
        public static final String GET_PRODUCTS_SAME_AUTHOR = PRE_FIX + "/same-author/{productId}";
        public static final String GET_PRODUCT_DETAIL = PRE_FIX + "/detail/{productId}";

        //Private
        public static final String GET_PRODUCTS_FOR_ADMIN = ADMIN_URL + PRE_FIX + "/get";
        public static final String GET_STOCK_QUANTITY_PRODUCTS = ADMIN_URL + PRE_FIX + "/stock-quantity";
        public static final String CREATE_PRODUCTS = ADMIN_URL + PRE_FIX + "/create";
        public static final String GET_PRODUCT = ADMIN_URL + PRE_FIX + "/{productId}";
        public static final String DELETE_PRODUCT = ADMIN_URL + PRE_FIX + "/delete/{productId}";

    }

}
