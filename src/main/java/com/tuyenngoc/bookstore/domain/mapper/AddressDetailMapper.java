package com.tuyenngoc.bookstore.domain.mapper;

import com.tuyenngoc.bookstore.domain.dto.request.CreateAddressRequestDto;
import com.tuyenngoc.bookstore.domain.entity.AddressDetail;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AddressDetailMapper {

    AddressDetail toAddressDetail(CreateAddressRequestDto requestDto);

}
