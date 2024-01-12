package com.tuyenngoc.bookstore.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@NoArgsConstructor
public class NotFoundException extends RuntimeException {

    private String message;

    private HttpStatus status;

    private String[] params;

    public NotFoundException(String message) {
        super(message);
        this.status = HttpStatus.NOT_FOUND;
        this.message = message;
    }

    public NotFoundException(String message, String... args) {
        super(message);
        this.status = HttpStatus.NOT_FOUND;
        this.message = message;
        this.params = args;
    }

}
