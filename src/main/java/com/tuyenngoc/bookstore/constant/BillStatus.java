package com.tuyenngoc.bookstore.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BillStatus {

    WAIT_FOR_CONFIRMATION("Chờ xác nhận", "unpaid"),
    WAIT_FOR_DELIVERY("Chờ lấy hàng", "to_ship"),
    DELIVERING("Đang giao", "shipping"),
    DELIVERED("Đã giao", "completed"),
    CANCELLED("Đã hủy", "cancelled"),
    REFUND("Trả hàng/Hoàn tiền", "refund"),
    DELIVERY_FAILED("Giao không thành công", "delivery_failed");

    private final String name;
    private final String key;
}
