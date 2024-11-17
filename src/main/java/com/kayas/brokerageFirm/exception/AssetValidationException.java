package com.kayas.brokerageFirm.exception;

public class AssetValidationException extends RuntimeException {
    public AssetValidationException(String message) {
        super(message);
    }
    
    public AssetValidationException(String message, Throwable cause) {
        super(message, cause);
    }
} 