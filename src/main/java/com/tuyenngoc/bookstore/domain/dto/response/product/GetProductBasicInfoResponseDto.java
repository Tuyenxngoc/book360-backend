package com.tuyenngoc.bookstore.domain.dto.response.product;

import com.tuyenngoc.bookstore.domain.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetProductBasicInfoResponseDto {

    private int productId;

    private String name;

    private String image;

    public GetProductBasicInfoResponseDto(Product product) {
        this.productId = product.getId();
        this.name = product.getName();
        this.image = product.getFeaturedImage();
    }

}
