package com.tuyenngoc.bookstore.controller;

import com.tuyenngoc.bookstore.annotation.CurrentUser;
import com.tuyenngoc.bookstore.annotation.RestApiV1;
import com.tuyenngoc.bookstore.base.VsResponseUtil;
import com.tuyenngoc.bookstore.constant.UrlConstant;
import com.tuyenngoc.bookstore.domain.dto.request.CreateAddressRequestDto;
import com.tuyenngoc.bookstore.security.CustomUserDetails;
import com.tuyenngoc.bookstore.service.AddressService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestApiV1
@RequiredArgsConstructor
@Tag(name = "Address")
public class AddressController {

    private final AddressService addressService;

    @Operation(summary = "API get address")
    @GetMapping(UrlConstant.Address.GET_ADDRESS)
    public ResponseEntity<?> getAddress(
            @PathVariable int addressId,
            @CurrentUser CustomUserDetails userDetails
    ) {
        return VsResponseUtil.success(addressService.getAddressDetail(userDetails.getCustomerId(), addressId));
    }

    @Operation(summary = "API get addresses")
    @GetMapping(UrlConstant.Address.GET_ADDRESSES)
    public ResponseEntity<?> getAddresses(@CurrentUser CustomUserDetails userDetails) {
        return VsResponseUtil.success(addressService.getAddressDetails(userDetails.getCustomerId()));
    }

    @Operation(summary = "API create and update address")
    @PutMapping(UrlConstant.Address.SAVE_LOCATION_CUSTOMER)
    public ResponseEntity<?> createAddress(
            @Valid @RequestBody CreateAddressRequestDto requestDto,
            @CurrentUser CustomUserDetails userDetails
    ) {
        return VsResponseUtil.success(addressService.saveLocationCustomer(userDetails.getCustomerId(), requestDto));
    }

    @Operation(summary = "API set default address")
    @PatchMapping(UrlConstant.Address.SET_DEFAULT_ADDRESS)
    public ResponseEntity<?> setDefaultAddress(
            @PathVariable int addressId,
            @CurrentUser CustomUserDetails userDetails
    ) {
        return VsResponseUtil.success(addressService.setDefaultAddress(userDetails.getCustomerId(), addressId));
    }

    @Operation(summary = "API delete address")
    @DeleteMapping(UrlConstant.Address.DELETE_ADDRESS)
    public ResponseEntity<?> deleteAddress(
            @PathVariable int addressId,
            @CurrentUser CustomUserDetails userDetails
    ) {
        return VsResponseUtil.success(addressService.deleteAddressDetail(userDetails.getCustomerId(), addressId));
    }
}
