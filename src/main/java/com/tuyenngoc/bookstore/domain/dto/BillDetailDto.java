package com.tuyenngoc.bookstore.domain.dto;

import com.tuyenngoc.bookstore.domain.dto.response.GetProductsResponseDto;
import com.tuyenngoc.bookstore.domain.entity.BillDetail;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BillDetailDto {

    private int quantity;

    private double price;

    private GetProductsResponseDto product;

    public BillDetailDto(BillDetail billDetail) {
        this.price = billDetail.getPrice();
        this.quantity = billDetail.getQuantity();
        this.product = new GetProductsResponseDto(billDetail.getProduct());
    }
}
