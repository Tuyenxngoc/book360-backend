package com.tuyenngoc.bookstore.constant;

public class SuccessMessage {

    public static final String UPDATE = "success.update";
    public static final String DELETE = "success.delete";

    public static class User {
        public static final String CHANGE_PASSWORD = "success.change-password";
        public static final String FORGET_PASSWORD = "success.send.password";
    }

    public static class Auth {
        public static final String LOGOUT = "success.logout";
    }

    public static class Cart {
        public static final String ADD_PRODUCT_TO_CAR = "success.cart.add-product-to-car";
    }
}
