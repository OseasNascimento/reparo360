package br.com.reparo360.exception;

public class TecnicoNotFoundException extends RuntimeException {
    public TecnicoNotFoundException(String message) {
        super(message);
    }
}
