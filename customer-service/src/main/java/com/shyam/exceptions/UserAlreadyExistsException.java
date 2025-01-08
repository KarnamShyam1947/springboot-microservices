package com.shyam.exceptions;

public class UserAlreadyExistsException extends Exception {

    public UserAlreadyExistsException() {
        super("User already exists");
    }
    
    public UserAlreadyExistsException(String str) {
        super(str);
    }

}
