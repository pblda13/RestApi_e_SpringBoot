package com.mballem.demoparkapi.service.exception;

public class PassWordInvalidException extends RuntimeException {
    public PassWordInvalidException(String messagem) {
        super(messagem);
    }
}
