package com.geekersjoel237.weline.iam.units;

import com.geekersjoel237.weline.iam.domain.repositories.OtpSender;

import java.util.ArrayList;
import java.util.List;

public class InMemoryOtpSender implements OtpSender {

    private final List<SentOtp> sentOtps = new ArrayList<>();

    @Override
    public void send(String phoneNumber, String otpCode) {
        this.sentOtps.add(new SentOtp(phoneNumber, otpCode));
    }

    public List<SentOtp> getSentOtps() {
        return List.copyOf(sentOtps);
    }

    public int timesCalled() {
        return sentOtps.size();
    }

    public record SentOtp(String phoneNumber, String otp) {
    }
}