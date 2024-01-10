package com.tuyenngoc.bookstore.constant;

public enum SortByDataConstant implements SortByInterface {


    PRODUCT {
        @Override
        public String getSortBy(String sortBy) {
            return null;
        }
    }
}
