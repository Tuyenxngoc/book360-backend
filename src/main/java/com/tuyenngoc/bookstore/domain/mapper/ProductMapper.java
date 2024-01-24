package com.tuyenngoc.bookstore.domain.mapper;

import com.tuyenngoc.bookstore.domain.dto.request.CreateProductRequestDto;
import com.tuyenngoc.bookstore.domain.entity.Product;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    Product toProduct(CreateProductRequestDto requestDto);

}
