package com.geekersjoel237.weline.shared.domain.exceptions;

/**
 * Created on 04/10/2025
 *
 * @author Geekers_Joel237
 **/
public class NotFoundEntityException extends TransactionalException {
    public NotFoundEntityException(String message) {
        super(message);
    }
}
