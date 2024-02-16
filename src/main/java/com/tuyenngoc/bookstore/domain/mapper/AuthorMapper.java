package com.tuyenngoc.bookstore.domain.mapper;

import com.tuyenngoc.bookstore.domain.dto.AuthorDto;
import com.tuyenngoc.bookstore.domain.dto.response.author.GetAuthorDetailResponseDto;
import com.tuyenngoc.bookstore.domain.dto.response.product.GetProductBasicInfoResponseDto;
import com.tuyenngoc.bookstore.domain.entity.Author;
import com.tuyenngoc.bookstore.domain.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring")
public interface AuthorMapper {

    Author toAuthor(AuthorDto authorDto);

    @Named("mapProducts")
    default List<GetProductBasicInfoResponseDto> getProductDtos(List<Product> products) {
        List<GetProductBasicInfoResponseDto> productResponseDtos = new ArrayList<>();
        for (Product product : products) {
            if (!product.getDeleteFlag()) {
                productResponseDtos.add(new GetProductBasicInfoResponseDto(product));
            }
        }
        return productResponseDtos;
    }

    @Mappings({
            @Mapping(target = "products", source = "products", qualifiedByName = "mapProducts"),
    })
    GetAuthorDetailResponseDto toGetAuthorDetailResponseDto(Author author);
}
