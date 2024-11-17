package com.kayas.brokerageFirm.exception;

public class InvalidUsernameOrPasswordException extends RuntimeException {

    public InvalidUsernameOrPasswordException() {
        super("Invalid username or password");
    }

}

