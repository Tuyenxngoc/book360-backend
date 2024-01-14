package com.tuyenngoc.bookstore.domain.mapper;

import com.tuyenngoc.bookstore.domain.dto.request.OrderRequestDto;
import com.tuyenngoc.bookstore.domain.entity.Bill;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BillMapper {

    Bill toBill(OrderRequestDto requestDto);

}
