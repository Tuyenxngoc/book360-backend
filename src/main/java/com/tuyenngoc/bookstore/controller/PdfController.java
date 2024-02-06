package com.tuyenngoc.bookstore.controller;

import com.tuyenngoc.bookstore.util.PdfUtil;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileInputStream;
import java.io.IOException;

@RestController
public class PdfController {

    @Autowired
    private PdfUtil pdfService;

    @GetMapping("/generate-pdf")
    public void generatePdf(HttpServletResponse response) throws IOException {
        String content = "Nội dung của file PDF được tạo trong Spring Boot.";
        String filePath = "sample2.pdf";

        // Tạo PDF và lưu vào đường dẫn filePath
        pdfService.generatePdf(filePath, content);

        // Set headers để trình duyệt có thể tải file PDF
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=sample.pdf");

        // Đọc file PDF và ghi vào HttpServletResponse
        FileCopyUtils.copy(new FileInputStream(filePath), response.getOutputStream());
    }

    @GetMapping("/generate-pdf2")
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
}