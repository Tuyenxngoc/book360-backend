package com.tuyenngoc.bookstore.domain.dto.response;

import com.tuyenngoc.bookstore.constant.AgeGroup;
import com.tuyenngoc.bookstore.domain.dto.AgeGroupDto;
import com.tuyenngoc.bookstore.domain.dto.AuthorDto;
import com.tuyenngoc.bookstore.domain.dto.BookSetDto;
import com.tuyenngoc.bookstore.domain.dto.CategoryDto;
import com.tuyenngoc.bookstore.domain.entity.Author;
import com.tuyenngoc.bookstore.domain.entity.Product;
import com.tuyenngoc.bookstore.domain.entity.ProductImage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    private String coverType;

    private double weight;

    private CategoryDto category;

    private BookSetDto bookSet;

    private List<ProductImage> images;

    private Set<AuthorDto> authors;

    private Set<AgeGroupDto> ageClassifications;

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
        this.weight = product.getWeight();
        this.coverType = product.getCoverType();

        this.category = new CategoryDto(product.getCategory());
        this.bookSet = new BookSetDto(product.getBookSet());

        this.authors = getAuthorDto(product.getAuthors());
        this.ageClassifications = getAgeGroupDto(product.getAgeClassifications());
    }

    private Set<AgeGroupDto> getAgeGroupDto(Set<AgeGroup> ageClassifications) {
        Set<AgeGroupDto> result = new HashSet<>();
        for (AgeGroup ageGroup : ageClassifications) {
            AgeGroupDto ageGroupDto = new AgeGroupDto(ageGroup);
            result.add(ageGroupDto);
        }
        return result;
    }

    private Set<AuthorDto> getAuthorDto(Set<Author> authors) {
        Set<AuthorDto> result = new HashSet<>();
        for (Author author : authors) {
            AuthorDto authorDto = new AuthorDto(author);
            result.add(authorDto);
        }
        return result;
    }
}
