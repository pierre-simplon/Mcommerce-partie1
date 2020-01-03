package com.example.first.API.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.EXPECTATION_FAILED)
public class ProduitSansMargeException extends RuntimeException {
    public ProduitSansMargeException(String s) {
        super(s);
    }
}
