package com.geekersjoel237.weline.iam.application.command.validateOtp;

/**
 * Created on 04/10/2025
 *
 * @author Geekers_Joel237
 **/
public record ValidateOtpCommand(String phoneNumber, String otpCode) {
}
