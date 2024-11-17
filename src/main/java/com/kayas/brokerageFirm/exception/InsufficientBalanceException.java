package com.kayas.brokerageFirm.exception;

public class InsufficientBalanceException extends RuntimeException{

    public InsufficientBalanceException(String message) {
        super(message);
    }
}
