package com.geekersjoel237.weline.iam.domain.exceptions;

/**
 * Created on 04/10/2025
 *
 * @author Geekers_Joel237
 **/
public class ErrorOnSendOtpCodeException extends RuntimeException {
    public ErrorOnSendOtpCodeException(String message) {
        super(message);
    }

    public ErrorOnSendOtpCodeException(String message, Throwable cause) {
        super(message, cause);
    }

}
