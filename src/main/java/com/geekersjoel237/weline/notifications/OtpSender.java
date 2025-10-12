package com.geekersjoel237.weline.notifications;

import com.geekersjoel237.weline.iam.domain.exceptions.ErrorOnSendOtpCodeException;

/**
 * Created on 04/10/2025
 *
 * @author Geekers_Joel237
 **/
public interface OtpSender {
    void send(String phoneNumber, String otpCode) throws ErrorOnSendOtpCodeException;
}
