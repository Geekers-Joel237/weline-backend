package com.geekersjoel237.weline.iam.domain.exceptions;

import com.geekersjoel237.weline.shared.domain.exceptions.TransactionalException;

/**
 * Created on 04/10/2025
 *
 * @author Geekers_Joel237
 **/
public class ErrorOnSendOtpCodeException extends TransactionalException {
    public ErrorOnSendOtpCodeException(String message) {
        super(message);
    }

    public ErrorOnSendOtpCodeException(String message, Throwable cause) {
        super(message, cause);
    }

}
