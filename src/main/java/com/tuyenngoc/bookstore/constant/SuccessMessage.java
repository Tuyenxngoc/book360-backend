package com.tuyenngoc.bookstore.constant;

public class SuccessMessage {

    public static final String CREATE = "success.create";
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
        public static final String ADD_PRODUCT = "success.cart.add-product";
    }

    public static class Bill {
        public static final String SAVE_ORDER = "success.bill.save-order";
        public static final String CANCEL_ORDER = "success.bill.cancel-order";

    }
}
