package com.shyam.exceptions;

public class InvalidUserDetailsException extends Exception {
    
    public InvalidUserDetailsException() {
        super("Invalid user details provider");
    }
    
    public InvalidUserDetailsException(String msg) {
        super(msg);
    }

}
