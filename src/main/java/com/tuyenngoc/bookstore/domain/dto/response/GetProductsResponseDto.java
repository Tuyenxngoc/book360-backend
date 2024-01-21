package com.tuyenngoc.bookstore.domain.dto.response;

import com.tuyenngoc.bookstore.domain.dto.CategoryDto;
import com.tuyenngoc.bookstore.domain.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetProductsResponseDto {

    private int productId;

    private String name;

    private String image;

    private int quantity;

    private double price;

    private int discount;

    private CategoryDto category;

    public GetProductsResponseDto(Product product) {
        this.productId = product.getId();
        this.name = product.getName();
        this.image = product.getFeaturedImage();
        this.quantity = product.getStockQuantity();
        this.price = product.getPrice();
        this.discount = product.getDiscount();
        this.category = new CategoryDto(product.getCategory());
    }
}
