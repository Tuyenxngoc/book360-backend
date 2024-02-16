package com.tuyenngoc.bookstore.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@NoArgsConstructor
public class DataIntegrityViolationException extends RuntimeException {
    private String message;

    private HttpStatus status;

    private String[] params;

    public DataIntegrityViolationException(String message) {
        super(message);
        this.status = HttpStatus.CONFLICT;
        this.message = message;
    }

    public DataIntegrityViolationException(String message, String... params) {
        super(message);
        this.status = HttpStatus.CONFLICT;
        this.message = message;
        this.params = params;
    }

}
