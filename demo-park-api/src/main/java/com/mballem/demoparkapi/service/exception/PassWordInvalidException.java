package com.mballem.demoparkapi.service.exception;

public class PasswordInvalidException extends RuntimeException {
    public PasswordInvalidException(String messagem) {
        super(messagem);
    }
}
