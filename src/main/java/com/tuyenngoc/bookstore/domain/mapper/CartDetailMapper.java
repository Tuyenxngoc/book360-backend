package com.tuyenngoc.bookstore.domain.mapper;

import com.tuyenngoc.bookstore.domain.dto.request.AddProductToCartResponseDto;
import com.tuyenngoc.bookstore.domain.entity.CartDetail;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CartDetailMapper {

    CartDetail toCartDetail(AddProductToCartResponseDto responseDto);
}
