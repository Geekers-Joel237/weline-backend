package com.geekersjoel237.weline.iam.application.command.validateOtp;

/**
 * Created on 04/10/2025
 *
 * @author Geekers_Joel237
 **/
public record ValidateOtpResponse(boolean isValidated, String message, String token) {
    public static ValidateOtpResponse ofSuccess(String jwrToken) {
        return new ValidateOtpResponse(true, "OTP validated successfully!", jwrToken);
    }


    public static ValidateOtpResponse ofFailure(String message) {
        return new ValidateOtpResponse(false, message, null);
    }

}
