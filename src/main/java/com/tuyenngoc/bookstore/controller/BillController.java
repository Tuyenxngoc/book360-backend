package com.tuyenngoc.bookstore.controller;

import com.tuyenngoc.bookstore.annotation.CurrentUser;
import com.tuyenngoc.bookstore.annotation.RestApiV1;
import com.tuyenngoc.bookstore.base.VsResponseUtil;
import com.tuyenngoc.bookstore.constant.UrlConstant;
import com.tuyenngoc.bookstore.domain.dto.pagination.PaginationFullRequestDto;
import com.tuyenngoc.bookstore.domain.dto.request.OrderRequestDto;
import com.tuyenngoc.bookstore.security.CustomUserDetails;
import com.tuyenngoc.bookstore.service.BillService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @Operation(summary = "API get bills")
    @GetMapping(UrlConstant.Bill.GET_BILLS)
    public ResponseEntity<?> getBills(@ParameterObject PaginationFullRequestDto requestDto,
                                      @RequestParam(name = "billStatus", required = false, defaultValue = "") String billStatus,
                                      @CurrentUser CustomUserDetails userDetails) {
        return VsResponseUtil.success(billService.getBills(userDetails.getCustomerId(), requestDto, billStatus));
    }

    @Operation(summary = "API cancel order")
    @PatchMapping(UrlConstant.Bill.CANCEL_ORDER)
    public ResponseEntity<?> cancelOrder(@PathVariable int billId,
                                         @CurrentUser CustomUserDetails userDetails) {
        return VsResponseUtil.success(billService.cancelOrder(userDetails.getCustomerId(), billId));
    }
}
