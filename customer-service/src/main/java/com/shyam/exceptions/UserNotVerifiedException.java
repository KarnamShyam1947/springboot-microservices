package com.shyam.exceptions;

public class UserNotVerifiedException extends Exception {
    
    public UserNotVerifiedException() {
        super("User email not verified");
    }
    
    public UserNotVerifiedException(String str) {
        super(str);
    }

}
