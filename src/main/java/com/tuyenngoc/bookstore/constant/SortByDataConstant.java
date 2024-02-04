package com.tuyenngoc.bookstore.constant;

public enum SortByDataConstant implements SortByInterface {

    AUTHOR {
        @Override
        public String getSortBy(String sortBy) {
            return switch (sortBy) {
                case "id" -> "id";
                case "fullName" -> "fullName";
                case "lastModifiedDate" -> "lastModifiedDate";
                default -> "createdDate";
            };
        }
    },

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
                case "lastModifiedDate" -> "lastModifiedDate";
                default -> "createdDate";
            };
        }
    },

    BANNER {
        @Override
        public String getSortBy(String sortBy) {
            return switch (sortBy) {
                case "id" -> "id";
                case "createdDate" -> "createdDate";
                case "viewOrder" -> "viewOrder";
                default -> "lastModifiedDate";
            };
        }
    },

    BOOK_SET {
        @Override
        public String getSortBy(String sortBy) {
            return switch (sortBy) {
                case "id" -> "id";
                case "name" -> "name";
                case "lastModifiedDate" -> "lastModifiedDate";
                default -> "createdDate";
            };
        }
    },

    BILL {
        @Override
        public String getSortBy(String sortBy) {
            return switch (sortBy) {
                case "id" -> "id";
                case "consigneeName" -> "consigneeName";
                case "shippingAddress" -> "shippingAddress";
                case "shippingFee" -> "shippingFee";
                case "totalAmount" -> "totalAmount";
                case "paymentMethod" -> "paymentMethod";
                case "lastModifiedDate" -> "lastModifiedDate";
                default -> "createdDate";
            };
        }
    },

    CUSTOMER {
        @Override
        public String getSortBy(String sortBy) {
            return switch (sortBy) {
                case "id" -> "id";
                case "fullName" -> "fullName";
                case "lastModifiedDate" -> "lastModifiedDate";
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
                case "lastModifiedDate" -> "lastModifiedDate";
                default -> "createdDate";
            };
        }
    },

}
