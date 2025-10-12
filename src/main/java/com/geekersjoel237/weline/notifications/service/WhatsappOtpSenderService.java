package com.geekersjoel237.weline.notifications.service;

import com.geekersjoel237.weline.iam.domain.exceptions.ErrorOnSendOtpCodeException;
import com.geekersjoel237.weline.notifications.OtpSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Created on 04/10/2025
 *
 * @author Geekers_Joel237
 **/

@Service
public class WhatsappOtpSenderService implements OtpSender {
    private static final Logger log = LoggerFactory.getLogger(WhatsappOtpSenderService.class);

    @Override
    public void send(String phoneNumber, String otpCode) throws ErrorOnSendOtpCodeException {
        log.info("---- 🚀 WHATSAPP OTP SIMULATION 🚀 ----");
        log.info("Sending OTP [{}] to phone number [{}].", otpCode, phoneNumber);
        log.info("---------------------------------------");
    }
}
