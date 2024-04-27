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

    public static class Address {
        private static final String PRE_FIX = "/address";

        public static final String GET_ADDRESS = PRE_FIX + "/{addressId}";
        public static final String GET_ADDRESSES = PRE_FIX + "/get";
        public static final String SAVE_LOCATION_CUSTOMER = PRE_FIX + "/create";
        public static final String SET_DEFAULT_ADDRESS = PRE_FIX + "/set-default/{addressId}";
        public static final String DELETE_ADDRESS = PRE_FIX + "/delete/{addressId}";
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
        public static final String UPLOAD_AVATAR = PRE_FIX + "/upload-avatar";
        public static final String UPDATE_CUSTOMER = PRE_FIX + "/update";

        //Admin
        public static final String UPLOAD_IMAGES = ADMIN_URL + PRE_FIX + "/upload-images";
        public static final String GET_TODO = ADMIN_URL + PRE_FIX + "/get-todo";
        public static final String GET_COUNT_CUSTOMER = ADMIN_URL + PRE_FIX + "/count";
        public static final String GET_CUSTOMERS = ADMIN_URL + PRE_FIX + "/get";
        public static final String GET_CUSTOMER = ADMIN_URL + PRE_FIX + "/{customerId}";
        public static final String CREATE_CUSTOMER = ADMIN_URL + PRE_FIX + "/create";
    }

    public static class Cart {
        private static final String PRE_FIX = "/cart";

        public static final String GET_PRODUCTS_FROM_CART = PRE_FIX + "/product/get";
        public static final String GET_TOTAL_PRODUCTS = PRE_FIX + "/product/total";
        public static final String ADD_PRODUCT_TO_CART = PRE_FIX + "/product/add";
        public static final String UPDATE_CART_DETAIL = PRE_FIX + "/product/update";
        public static final String DELETE_PRODUCT = PRE_FIX + "/product/delete/{productId}";
    }

    public static class Bill {
        private static final String PRE_FIX = "/bill";

        public static final String GET_BILLS = PRE_FIX + "/get";
        public static final String SAVE_ORDER = PRE_FIX + "/save-order";
        public static final String CANCEL_ORDER = PRE_FIX + "/cancel-order/{billId}";
        public static final String GET_COUNT_BILLS_BY_STATUS = PRE_FIX + "/count";
        public static final String GET_BILL_DETAIL = PRE_FIX + "/detail/{billId}";

        public static final String GET_BILL = ADMIN_URL + PRE_FIX + "/{billId}";
        public static final String GET_COUNT_BILLS = ADMIN_URL + PRE_FIX + "/count";
        public static final String GET_BILLS_FOR_ADMIN = ADMIN_URL + PRE_FIX + "/get";
        public static final String UPDATE_BILL_STATUS = ADMIN_URL + PRE_FIX + "/update-status/{billId}";
        public static final String GET_KEY_METRICS = ADMIN_URL + PRE_FIX + "/key-metrics";
    }

    public static class Author {

        private static final String PRE_FIX = "/author";

        public static final String GET_ALL_AUTHORS = ADMIN_URL + PRE_FIX + "/all";
        public static final String GET_AUTHOR = ADMIN_URL + PRE_FIX + "/{authorId}";
        public static final String GET_AUTHOR_DETAIL = ADMIN_URL + PRE_FIX + "/detail/{authorId}";
        public static final String GET_AUTHORS = ADMIN_URL + PRE_FIX + "/get";
        public static final String CREATE_AUTHOR = ADMIN_URL + PRE_FIX + "/create";
        public static final String DELETE_AUTHOR = ADMIN_URL + PRE_FIX + "/delete/{authorId}";
    }

    public static class Banner {
        private static final String PRE_FIX = "/banner";

        public static final String GET_ALL_BANNERS = PRE_FIX + "/all";

        public static final String GET_BANNERS = ADMIN_URL + PRE_FIX + "/get";
        public static final String GET_BANNER = ADMIN_URL + PRE_FIX + "/{bannerId}";
        public static final String CREATE_BANNER = ADMIN_URL + PRE_FIX + "/create";
        public static final String DELETE_BANNER = ADMIN_URL + PRE_FIX + "/delete/{bannerId}";
    }

    public static class BookSet {
        private static final String PRE_FIX = "/book-set";

        public static final String GET_ALL_BOOK_SETS = ADMIN_URL + PRE_FIX + "/all";
        public static final String GET_BOOK_SETS = ADMIN_URL + PRE_FIX + "/get";
        public static final String GET_BOOK_SET = ADMIN_URL + PRE_FIX + "/{bookSetId}";
        public static final String GET_BOOK_SET_DETAIL = ADMIN_URL + PRE_FIX + "/detail/{bookSetId}";
        public static final String CREATE_BOOK_SET = ADMIN_URL + PRE_FIX + "/create";
        public static final String DELETE_BOOK_SET = ADMIN_URL + PRE_FIX + "/delete/{bookSetId}";
    }

    public static class Category {
        private static final String PRE_FIX = "/category";

        public static final String GET_CATEGORIES = PRE_FIX + "/get";

        public static final String GET_CATEGORY = ADMIN_URL + PRE_FIX + "/{categoryId}";
        public static final String GET_ALL_CATEGORIES = ADMIN_URL + PRE_FIX + "/all";
        public static final String GET_CATEGORIES_FOR_ADMIN = ADMIN_URL + PRE_FIX + "/get";
        public static final String CREATE_CATEGORY = ADMIN_URL + PRE_FIX + "/create";
        public static final String DELETE_CATEGORY = ADMIN_URL + PRE_FIX + "/delete/{categoryId}";
    }

    public static class Product {
        private static final String PRE_FIX = "/product";

        public static final String GET_PRODUCTS = PRE_FIX + "/get";
        public static final String FIND_PRODUCT = PRE_FIX + "/find";
        public static final String GET_PRODUCTS_BY_CATEGORY_ID = PRE_FIX + "/category/{categoryId}";
        public static final String GET_PRODUCTS_BY_AUTHOR_ID = PRE_FIX + "/author/{authorId}";
        public static final String GET_PRODUCTS_SAME_AUTHOR = PRE_FIX + "/same-author/{productId}";
        public static final String GET_PRODUCT_DETAIL = PRE_FIX + "/detail/{productId}";

        public static final String GET_PRODUCTS_FOR_ADMIN = ADMIN_URL + PRE_FIX + "/get";
        public static final String GET_STOCK_QUANTITY_PRODUCTS = ADMIN_URL + PRE_FIX + "/stock-quantity";
        public static final String CREATE_PRODUCTS = ADMIN_URL + PRE_FIX + "/create";
        public static final String GET_PRODUCT = ADMIN_URL + PRE_FIX + "/{productId}";
        public static final String DELETE_PRODUCT = ADMIN_URL + PRE_FIX + "/delete/{productId}";
    }

    public static class Chat {
        private static final String PRE_FIX = "/message";
        private static final String PRE_CHAT_ROOM = "/chatrooms";
        private static final String PRE_CHAT = "/chat";


        public static final String GET_CHAT_ROOMS = PRE_CHAT_ROOM;
        public static final String GET_MESSAGES = PRE_CHAT_ROOM + "/{chatRoomId}/messages";
        public static final String GET_SUPPORT_USER = PRE_CHAT + "/support-user";


        public static final String GET_USERS = ADMIN_URL + PRE_FIX + "/get-users";
    }
}
