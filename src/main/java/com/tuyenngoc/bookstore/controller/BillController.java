package com.tuyenngoc.bookstore.controller;

import com.tuyenngoc.bookstore.annotation.CurrentUser;
import com.tuyenngoc.bookstore.annotation.RestApiV1;
import com.tuyenngoc.bookstore.base.VsResponseUtil;
import com.tuyenngoc.bookstore.constant.UrlConstant;
import com.tuyenngoc.bookstore.domain.dto.request.OrderRequestDto;
import com.tuyenngoc.bookstore.security.CustomUserDetails;
import com.tuyenngoc.bookstore.service.BillService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestApiV1
@RequiredArgsConstructor
public class BillController {

    private final BillService billService;

    @Operation(summary = "API order from cart")
    @PostMapping(UrlConstant.Bill.SAVE_ORDER)
    public ResponseEntity<?> saveOrder(@Valid @RequestBody OrderRequestDto requestDto,
                                       @CurrentUser CustomUserDetails userDetails) {
        return VsResponseUtil.success(billService.saveOrder(userDetails.getCustomerId(), requestDto));
    }
}
