package com.shyam.exceptions;

public class UserEmailNotVerifiedException extends Exception {

    public UserEmailNotVerifiedException() {
        super("User Email not verified");
    }

    public UserEmailNotVerifiedException(String msg) {
        super(msg);
    }

}
