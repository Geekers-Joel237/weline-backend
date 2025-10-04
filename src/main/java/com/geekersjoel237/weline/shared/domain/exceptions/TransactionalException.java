package com.geekersjoel237.weline.shared.domain.exceptions;

/**
 * Created on 04/10/2025
 *
 * @author Geekers_Joel237
 **/
public class TransactionalException extends RuntimeException{
    public TransactionalException(String message) {
        super(message);
    }

    public TransactionalException(String message, Throwable cause) {
        super(message, cause);
    }

    public TransactionalException(Throwable cause) {
        super(cause);
    }
}
