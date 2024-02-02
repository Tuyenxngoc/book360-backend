package com.tuyenngoc.bookstore.domain.dto;

import com.tuyenngoc.bookstore.domain.dto.response.GetProductResponseDto;
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

    private int id;

    private int quantity;

    private double price;

    private GetProductResponseDto product;

    public BillDetailDto(BillDetail billDetail) {
        this.id = billDetail.getId();
        this.price = billDetail.getPrice();
        this.quantity = billDetail.getQuantity();
        this.product = new GetProductResponseDto(billDetail.getProduct());
    }
}
