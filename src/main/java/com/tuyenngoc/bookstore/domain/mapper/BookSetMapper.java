package com.tuyenngoc.bookstore.domain.mapper;

import com.tuyenngoc.bookstore.domain.dto.BookSetDto;
import com.tuyenngoc.bookstore.domain.dto.response.bookSet.GetBookSetDetailResponseDto;
import com.tuyenngoc.bookstore.domain.dto.response.product.GetProductBasicInfoResponseDto;
import com.tuyenngoc.bookstore.domain.entity.BookSet;
import com.tuyenngoc.bookstore.domain.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Mapper(componentModel = "spring")
public interface BookSetMapper {

    BookSet toBookSet(BookSetDto bookSetDto);

    @Named("mapProducts")
    default List<GetProductBasicInfoResponseDto> getProductDtos(Set<Product> products) {
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
    GetBookSetDetailResponseDto toGetBookSetDetailResponseDto(BookSet bookSet);

}
