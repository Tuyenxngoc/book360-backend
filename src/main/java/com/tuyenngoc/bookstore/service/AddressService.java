package com.tuyenngoc.bookstore.service;

import com.tuyenngoc.bookstore.domain.dto.request.CoordinatesRequestDto;
import com.tuyenngoc.bookstore.domain.dto.request.CreateAddressRequestDto;
import com.tuyenngoc.bookstore.domain.dto.response.CommonResponseDto;
import com.tuyenngoc.bookstore.domain.entity.Address;
import com.tuyenngoc.bookstore.domain.entity.AddressDetail;

import java.util.List;

public interface AddressService {

    CommonResponseDto saveLocationCustomer(int customerId, CreateAddressRequestDto requestDto);

    CommonResponseDto setDefaultAddress(int customerId, int addressDetailId);

    CommonResponseDto deleteAddressDetail(int customerId, int addressDetailId);

    AddressDetail getAddressDetail(int customerId, int addressDetailId);

    List<AddressDetail> getAddressDetails(int customerId);

    Address getAddress(CoordinatesRequestDto addressDto);
}
