package com.tuyenngoc.bookstore.controller;

import com.tuyenngoc.bookstore.annotation.CurrentUser;
import com.tuyenngoc.bookstore.annotation.RestApiV1;
import com.tuyenngoc.bookstore.base.VsResponseUtil;
import com.tuyenngoc.bookstore.constant.BillStatus;
import com.tuyenngoc.bookstore.constant.UrlConstant;
import com.tuyenngoc.bookstore.domain.dto.pagination.PaginationFullRequestDto;
import com.tuyenngoc.bookstore.domain.dto.request.BillRequestDto;
import com.tuyenngoc.bookstore.security.CustomUserDetails;
import com.tuyenngoc.bookstore.service.BillService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestApiV1
@RequiredArgsConstructor
@Tag(name = "Bill")
public class BillController {

    private final BillService billService;

    @Operation(summary = "API get bills")
    @GetMapping(UrlConstant.Bill.GET_BILLS)
    public ResponseEntity<?> getBills(
            @RequestParam(name = "billStatus", required = false) BillStatus billStatus,
            @CurrentUser CustomUserDetails userDetails
    ) {
        return VsResponseUtil.success(billService.getBills(userDetails.getCustomerId(), billStatus));
    }

    @Operation(summary = "API save order from cart")
    @PostMapping(UrlConstant.Bill.SAVE_ORDER)
    public ResponseEntity<?> saveOrder(
            @Valid @RequestBody BillRequestDto requestDto,
            @CurrentUser CustomUserDetails userDetails
    ) {
        return VsResponseUtil.success(billService.saveOrder(userDetails.getCustomerId(), requestDto));
    }

    @Operation(summary = "API cancel order")
    @PatchMapping(UrlConstant.Bill.CANCEL_ORDER)
    public ResponseEntity<?> cancelOrder(
            @PathVariable int billId,
            @RequestParam(name = "cancellationReason") String cancellationReason,
            @CurrentUser CustomUserDetails userDetails
    ) {
        return VsResponseUtil.success(billService.cancelOrder(userDetails.getCustomerId(), billId, cancellationReason));
    }

    @Operation(summary = "API get count bills by status")
    @GetMapping(UrlConstant.Bill.GET_COUNT_BILLS_BY_STATUS)
    public ResponseEntity<?> getCountBillsByStatus(
            @RequestParam(name = "billStatus") BillStatus billStatus,
            @CurrentUser CustomUserDetails userDetails
    ) {
        return VsResponseUtil.success(billService.getCountBillByStatus(userDetails.getCustomerId(), billStatus));
    }

    @Operation(summary = "API get bill detail")
    @GetMapping(UrlConstant.Bill.GET_BILL_DETAIL)
    public ResponseEntity<?> getBillDetails(
            @PathVariable int billId,
            @CurrentUser CustomUserDetails userDetails
    ) {
        return VsResponseUtil.success(billService.getBillDetail(userDetails.getCustomerId(), billId));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "API get bill")
    @GetMapping(UrlConstant.Bill.GET_BILL)
    public ResponseEntity<?> getBill(@PathVariable int billId) {
        return VsResponseUtil.success(billService.getBill(billId));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "API get count bills")
    @GetMapping(UrlConstant.Bill.GET_COUNT_BILLS)
    public ResponseEntity<?> getCountBills() {
        return VsResponseUtil.success(billService.getCountBills());
    }

    @PreAuthorize(value = "hasRole('ADMIN')")
    @Operation(summary = "API get bills")
    @GetMapping(UrlConstant.Bill.GET_BILLS_FOR_ADMIN)
    public ResponseEntity<?> getBillsForAdmin(
            @RequestParam(name = "billStatus", required = false) BillStatus billStatus,
            @ParameterObject PaginationFullRequestDto requestDto
    ) {
        return VsResponseUtil.success(billService.getBillsForAdmin(requestDto, billStatus));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "API update bill status")
    @PatchMapping(UrlConstant.Bill.UPDATE_BILL_STATUS)
    public ResponseEntity<?> updateBillStatus(
            @PathVariable int billId,
            @RequestParam(name = "billStatus") BillStatus newStatus
    ) {
        return VsResponseUtil.success(billService.updateBillStatus(billId, newStatus));
    }
}
