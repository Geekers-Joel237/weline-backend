package com.geekersjoel237.weline.shared.domain.exceptions;

/**
 * Created on 04/10/2025
 *
 * @author Geekers_Joel237
 **/
public class ErrorOnPersistEntityException extends TransactionalException {
    public ErrorOnPersistEntityException(String message) {
        super(message);
    }

    public ErrorOnPersistEntityException(String message, Throwable cause) {
        super(message, cause);
    }

}
