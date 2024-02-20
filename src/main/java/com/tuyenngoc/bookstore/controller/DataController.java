package com.tuyenngoc.bookstore.controller;

import com.tuyenngoc.bookstore.annotation.RestApiV1;
import com.tuyenngoc.bookstore.base.VsResponseUtil;
import com.tuyenngoc.bookstore.constant.UrlConstant;
import com.tuyenngoc.bookstore.service.BillService;
import com.tuyenngoc.bookstore.util.PdfUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;

@RestApiV1
@RequiredArgsConstructor
@Tag(name = "My data")
public class DataController {

    private final PdfUtil pdfService;

    private final BillService billService;

    @GetMapping("/generate-pdf")
    public ResponseEntity<byte[]> generatePdf2(HttpServletResponse response) throws IOException {
        String content = "Nội dung của file PDF được tạo trong Spring Boot.";

        // Tạo PDF trong bộ nhớ
        byte[] pdfBytes = pdfService.generatePdf(content);

        // Set headers để trình duyệt hiểu rằng đây là một file PDF
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("filename", "sample.pdf");

        return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "API get key metrics bill")
    @GetMapping(UrlConstant.Bill.GET_KEY_METRICS)
    public ResponseEntity<?> getKeyMetrics(
            @RequestParam(value = "orderType", required = false, defaultValue = "confirmed") String orderType
    ) {
        return VsResponseUtil.success(billService.getKeyMetrics(orderType));
    }

}