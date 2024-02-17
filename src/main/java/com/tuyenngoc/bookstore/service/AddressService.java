package com.tuyenngoc.bookstore.service;

import com.tuyenngoc.bookstore.domain.dto.request.CreateAddressRequestDto;
import com.tuyenngoc.bookstore.domain.dto.response.CommonResponseDto;
import com.tuyenngoc.bookstore.domain.dto.response.address.GetAddressResponseDto;

import java.util.List;

public interface AddressService {

    CommonResponseDto saveLocationCustomer(int customerId, CreateAddressRequestDto requestDto);

    CommonResponseDto setDefaultAddress(int customerId, int addressDetailId);

    CommonResponseDto deleteAddressDetail(int customerId, int addressDetailId);

    GetAddressResponseDto getAddressDetail(int customerId, int addressDetailId);

    List<GetAddressResponseDto> getAddressDetails(int customerId);

}
