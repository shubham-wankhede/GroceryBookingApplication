package com.gb.store.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class GroceryNotFoundException extends  RuntimeException{

    public GroceryNotFoundException() {
    }

    public GroceryNotFoundException(String message) {
        super(message);
    }
}
