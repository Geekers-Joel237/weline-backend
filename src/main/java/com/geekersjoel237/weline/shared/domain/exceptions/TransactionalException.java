package com.geekersjoel237.weline.shared.domain.exceptions;

import org.springframework.transaction.TransactionException;

/**
 * Created on 04/10/2025
 *
 * @author Geekers_Joel237
 **/
public class TransactionalException extends TransactionException {
    public TransactionalException(String message) {
        super(message);
    }

    public TransactionalException(String message, Throwable cause) {
        super(message, cause);
    }


}
