package com.bookstore.bookstore.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RoleConstant {

    ADMINISTRATOR("ROLE_ADMIN", "Administrator"),
    SALES_STAFF("ROLE_SALES_STAFF", "Sales Staff"),
    CUSTOMER("ROLE_USER", "Customer"),
    INVENTORY_MANAGER("ROLE_INVENTORY_MANAGER", "Inventory Manager"),
    ACCOUNTING("ROLE_ACCOUNTING", "Accounting"),
    CUSTOMER_SUPPORT("ROLE_CUSTOMER_SUPPORT", "Customer Support");

    private final String roleName;
    private final String description;
}
