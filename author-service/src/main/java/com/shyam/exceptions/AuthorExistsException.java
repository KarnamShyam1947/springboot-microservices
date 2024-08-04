package com.shyam.exceptions;

public class AuthorExistsException extends Exception {
    
    public AuthorExistsException() {
        super("Author Already Exists");
    }
    
    public AuthorExistsException(String msg) {
        super(msg);
    }

}
