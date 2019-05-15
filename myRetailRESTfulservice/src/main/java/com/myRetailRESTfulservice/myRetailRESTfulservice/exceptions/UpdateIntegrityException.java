package com.myRetailRESTfulservice.myRetailRESTfulservice.exceptions;

public class UpdateIntegrityException extends Exception {
    public UpdateIntegrityException() {

    }

    public UpdateIntegrityException(String message) {
        super(message);
    }

    public UpdateIntegrityException(String message, Throwable cause) {
        super(message, cause);
    }
}
