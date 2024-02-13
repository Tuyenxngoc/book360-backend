package com.tuyenngoc.bookstore.controller;

import com.tuyenngoc.bookstore.annotation.RestApiV1;
import com.tuyenngoc.bookstore.service.AddressService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestApiV1
@RequiredArgsConstructor
@Tag(name = "Address")
public class AddressController {

    private final AddressService addressService;

}
