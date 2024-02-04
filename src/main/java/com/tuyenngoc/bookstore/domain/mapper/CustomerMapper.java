package com.tuyenngoc.bookstore.domain.mapper;

import com.tuyenngoc.bookstore.domain.dto.request.CreateCustomerRequestDto;
import com.tuyenngoc.bookstore.domain.entity.Customer;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CustomerMapper {

    Customer toCustomer(CreateCustomerRequestDto requestDto);

}
