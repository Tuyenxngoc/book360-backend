package com.tuyenngoc.bookstore.util;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Slf4j
@Component
public class PdfUtil {

    public byte[] generatePdf(String content) {
        Document document = new Document();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        try {
            PdfWriter.getInstance(document, outputStream);
            document.open();

            BaseFont unicodeFont = BaseFont.createFont("src/main/resources/static/arial.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            Font font = new Font(unicodeFont, 12);
            Paragraph paragraph = new Paragraph("Xin chào, đây là văn bản tiếng Việt trong file PDF!", font);
            document.add(paragraph);

            document.close();
        } catch (DocumentException | IOException e) {
            log.error(e.getMessage(), e);
        }

        return outputStream.toByteArray();
    }

}
