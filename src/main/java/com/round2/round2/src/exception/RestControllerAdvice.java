package com.round2.round2.src.exception;

import org.springframework.web.bind.annotation.ExceptionHandler;

@org.springframework.web.bind.annotation.RestControllerAdvice
public class RestControllerAdvice {

    @ExceptionHandler(CustomException.class)
    public String custom() {
        return "hello custom";
    }

}
