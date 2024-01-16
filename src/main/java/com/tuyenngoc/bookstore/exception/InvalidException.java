package com.tuyenngoc.bookstore.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@NoArgsConstructor
public class InvalidException extends RuntimeException {

    private String message;

    private HttpStatus status;

    private String[] params;

    public InvalidException(String message) {
        super(message);
        this.status = HttpStatus.BAD_REQUEST;
        this.message = message;
    }

    public InvalidException(String message, String... args) {
        super(message);
        this.status = HttpStatus.BAD_REQUEST;
        this.message = message;
        this.params = args;
    }
}