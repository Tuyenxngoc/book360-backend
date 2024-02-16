package com.tuyenngoc.bookstore.domain.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetTodoResponseDto {

    private ProductInfo productInfo;

    private OrderInfo orderInfo;

    public GetTodoResponseDto(int productSoldOut, int waitForConfirmationCount, int waitForDeliveryCount, int deliveringCount, int cancelledCount) {
        this.productInfo = new ProductInfo(productSoldOut);
        this.orderInfo = new OrderInfo(waitForConfirmationCount, waitForDeliveryCount, deliveringCount, cancelledCount);
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ProductInfo {
        private int productSoldOut;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class OrderInfo {
        private int waitForConfirmationCount;

        private int waitForDeliveryCount;

        private int deliveringCount;

        private int cancelledCount;
    }

}
