package com.geekersjoel237.weline.iam.domain.vo;

import java.security.SecureRandom;
import java.time.Clock;
import java.time.Instant;

/**
 * Created on 04/10/2025
 *
 * @author Geekers_Joel237
 **/
public record Otp(String value, Instant expiredIn) {

    public static final int FIVE_MINUTES = 300;
    private static final SecureRandom random = new SecureRandom();

    public static Otp generateOtp() {
        return generateOtp(Clock.systemUTC());
    }

    public static Otp generateOtp(Clock clock) {
        // Use SecureRandom for cryptographically strong random numbers
        int otpValue = 100000 + random.nextInt(900000);
        return new Otp(String.valueOf(otpValue), Instant.now(clock).plusSeconds(FIVE_MINUTES));
    }

    public boolean isExpired() {
        return Instant.now().isAfter(expiredIn);
    }

}
