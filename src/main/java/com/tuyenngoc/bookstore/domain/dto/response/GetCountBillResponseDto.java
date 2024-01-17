package com.tuyenngoc.bookstore.domain.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetCountBillResponseDto {

    private int unpaid;

    private int toShip;

    private int shipping;

    private int completed;

    private int cancelled;

    private int refund;

}
