package com.tuyenngoc.bookstore.constant;

public enum SortByDataConstant implements SortByInterface {


    PRODUCT {
        @Override
        public String getSortBy(String sortBy) {
            return switch (sortBy) {
                case "id" -> "id";
                case "name" -> "name";
                case "price" -> "price";
                case "discount" -> "discount";
                case "weight" -> "weight";
                case "pageCount" -> "pageCount";
                case "soldQuantity" -> "soldQuantity";
                case "stockQuantity" -> "stockQuantity";
                default -> "createdDate";
            };
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
    },

    CUSTOMER {
        @Override
        public String getSortBy(String sortBy) {
            return switch (sortBy) {
                case "id" -> "id";
                case "fullName" -> "fullName";
                default -> "createdDate";
            };
        }
    },
    CATEGORY {
        @Override
        public String getSortBy(String sortBy) {
            return switch (sortBy) {
                case "id" -> "id";
                case "name" -> "name";
                default -> "createdDate";
            };
        }
    },

}
