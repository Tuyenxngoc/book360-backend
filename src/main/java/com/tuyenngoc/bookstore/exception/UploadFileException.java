package com.tuyenngoc.bookstore.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@NoArgsConstructor
public class UploadFileException extends RuntimeException {

    private String message;

    private HttpStatus status;

    private String[] params;

    public UploadFileException(String message) {
        super(message);
        this.status = HttpStatus.BAD_GATEWAY;
        this.message = message;
    }
}
