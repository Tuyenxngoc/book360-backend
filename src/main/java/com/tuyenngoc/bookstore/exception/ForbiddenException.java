package com.tuyenngoc.bookstore.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@NoArgsConstructor
public class ForbiddenException extends RuntimeException {

    private String message;

    private HttpStatus status;

    private String[] params;

    public ForbiddenException(String message) {
        super(message);
        this.status = HttpStatus.FORBIDDEN;
        this.message = message;
    }
}