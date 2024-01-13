package com.tuyenngoc.bookstore.domain.dto.response;

import com.tuyenngoc.bookstore.domain.dto.AuthorDto;
import com.tuyenngoc.bookstore.domain.dto.CategoryDto;
import com.tuyenngoc.bookstore.domain.entity.Author;
import com.tuyenngoc.bookstore.domain.entity.Product;
import com.tuyenngoc.bookstore.domain.entity.ProductImage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetProductDetailResponseDto {

    private int productId;

    private String isbn;

    private String name;

    private double price;

    private int discount;

    private String size;

    private int pageCount;

    private int soldQuantity;

    private int stockQuantity;

    private String description;

    private String format;

    private double weight;

    private CategoryDto category;

    private List<ProductImage> images;

    private List<AuthorDto> authors;

    public GetProductDetailResponseDto(Product product) {
        this.productId = product.getId();
        this.isbn = product.getIsbn();
        this.name = product.getName();
        this.images = product.getImages();
        this.price = product.getPrice();
        this.discount = product.getDiscount();
        this.size = product.getSize();
        this.soldQuantity = product.getSoldQuantity();
        this.stockQuantity = product.getStockQuantity();
        this.description = product.getDescription();
        this.pageCount = product.getPageCount();
        this.format = product.getFormat();
        this.weight = product.getWeight();

        this.category = new CategoryDto(product.getCategory());
        this.authors = getAuthorDto(product.getAuthors());
    }

    private List<AuthorDto> getAuthorDto(List<Author> authors) {
        List<AuthorDto> result = new ArrayList<>();
        for (Author author : authors) {
            AuthorDto authorDto = new AuthorDto(author);
            result.add(authorDto);
        }
        return result;
    }
}
