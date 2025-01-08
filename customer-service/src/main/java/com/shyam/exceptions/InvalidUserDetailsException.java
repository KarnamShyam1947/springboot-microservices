package com.shyam.exceptions;

public class InvalidUserDetailsException extends Exception {
    
    public InvalidUserDetailsException() {
        super("Invalid user details provided");
    }

    public InvalidUserDetailsException(String str) {
        super(str);
    }

}
