package com.tuyenngoc.bookstore.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PaymentMethod {

    CASH("Thanh toán bằng tiền mặt"),
    CREDIT_CARD("Thanh toán bằng thẻ tín dụng"),
    DEBIT_CARD("Thanh toán bằng thẻ ghi nợ"),
    PAYPAL("Thanh toán qua PayPal"),
    ZALO_PAY("Thanh toán qua Zalo pay"),
    MOMO("Thanh toán qua Momo"),
    OTHER("Phương thức thanh toán khác");

    private final String description;
}