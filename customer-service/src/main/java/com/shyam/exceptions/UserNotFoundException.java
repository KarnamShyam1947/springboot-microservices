package com.shyam.exceptions;

public class UserNotFoundException extends Exception {
    
    public UserNotFoundException() {
        super("User not found");
    }
    
    public UserNotFoundException(String str) {
        super(str);
    }
}
