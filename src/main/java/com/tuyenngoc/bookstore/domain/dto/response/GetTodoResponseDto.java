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

    private int productSoldOut;

    private int waitForConfirmationCount;

    private int waitForDeliveryCount;

    private int deliveringCount;

    private int cancelledCount;

}
