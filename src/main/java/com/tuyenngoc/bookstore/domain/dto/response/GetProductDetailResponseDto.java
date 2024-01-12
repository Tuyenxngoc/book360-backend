package com.tuyenngoc.bookstore.domain.dto.response;

import com.tuyenngoc.bookstore.domain.entity.Category;
import com.tuyenngoc.bookstore.domain.entity.Product;
import com.tuyenngoc.bookstore.domain.entity.ProductImage;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class GetProductDetailResponseDto {

    private int productId;

    private String name;

    private Category category;

    private String featuredImage;

    private List<ProductImage> images;

    public GetProductDetailResponseDto(Product product) {
        this.productId = product.getId();
        this.name = product.getName();
        this.images = product.getImages();
        this.category = product.getCategory();
    }
}
