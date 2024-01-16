package com.tuyenngoc.bookstore.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

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

    private static final Map<String, String> BY_KEY = new HashMap<>();

    static {
        for (BillStatus status : values()) {
            BY_KEY.put(status.key, status.name);
        }
    }

    public static String getByKey(String key) {
        return BY_KEY.get(key);
    }
}
