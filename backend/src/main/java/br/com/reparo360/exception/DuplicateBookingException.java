package br.com.reparo360.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class DuplicateBookingException extends RuntimeException {
    public DuplicateBookingException(String msg) {
        super(msg);
    }
}
