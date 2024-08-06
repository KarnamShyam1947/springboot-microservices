package com.shyam.exceptions;

public class AuthorNotFoundException extends Exception {

    public AuthorNotFoundException(String msg) {
        super(msg);
    }

    public AuthorNotFoundException() {
        super("Author Not Found.");
    }
}
