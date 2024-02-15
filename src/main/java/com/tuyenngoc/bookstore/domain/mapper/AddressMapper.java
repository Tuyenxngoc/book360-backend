package com.tuyenngoc.bookstore.domain.mapper;

import com.tuyenngoc.bookstore.domain.dto.AddressDto;
import com.tuyenngoc.bookstore.domain.dto.request.CreateAddressRequestDto;
import com.tuyenngoc.bookstore.domain.entity.Address;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AddressMapper {

    Address toAddress(AddressDto requestDto);

    Address toAddress(CreateAddressRequestDto requestDto);

}
