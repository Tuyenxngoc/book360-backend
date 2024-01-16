package com.tuyenngoc.bookstore.constant;

public enum SortByDataConstant implements SortByInterface {


    PRODUCT {
        @Override
        public String getSortBy(String sortBy) {
            return "name";
        }
    },

    BANNER {
        @Override
        public String getSortBy(String sortBy) {
            return switch (sortBy) {
                case "id" -> "id";
                case "viewOrder" -> "viewOrder";
                default -> "createdDate";
            };
        }
    },

    BILL {
        @Override
        public String getSortBy(String sortBy) {
            return "createdDate";
        }
    }

}
