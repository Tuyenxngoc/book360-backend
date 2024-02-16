package com.tuyenngoc.bookstore.domain.dto.response.product;

import com.tuyenngoc.bookstore.domain.entity.CartDetail;
import com.tuyenngoc.bookstore.domain.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetProductFromCartResponseDto {

    private int productId;

    private String name;

    private String image;

    private double price;

    private int discount;

    private int quantity;

    public GetProductFromCartResponseDto(CartDetail cartDetail) {
        Product product = cartDetail.getProduct();

        this.productId = product.getId();
        this.name = product.getName();
        this.image = product.getFeaturedImage();
        this.price = product.getPrice();
        this.discount = product.getDiscount();
        this.quantity = cartDetail.getQuantity();
    }
}
