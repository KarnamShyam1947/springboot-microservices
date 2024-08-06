package com.shyam.exceptions;

public class BookNotFoundException extends Exception {
    
    public BookNotFoundException() {
        super("Book Not Found");
    }

    public BookNotFoundException(String msg) {
        super(msg);
    }

}
