package com.trinity.ms.invoicing.sale_session.exception;

public class SessionAlreadyExistsException extends RuntimeException{

    public SessionAlreadyExistsException(String message) {
        super(message);
    }
}
