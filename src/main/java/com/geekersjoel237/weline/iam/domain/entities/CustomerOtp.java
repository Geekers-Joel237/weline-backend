package com.geekersjoel237.weline.iam.domain.entities;

import com.geekersjoel237.weline.iam.domain.vo.Otp;
import com.geekersjoel237.weline.shared.domain.exceptions.CustomIllegalArgumentException;
import com.geekersjoel237.weline.shared.domain.vo.Id;

/**
 * Created on 04/10/2025
 *
 * @author Geekers_Joel237
 **/
public class CustomerOtp {
    private final Id id;
    private final Id customerId;
    private Otp otp;

    public CustomerOtp(Id id, Id customerId, Otp otp) {
        this.id = id;
        this.customerId = customerId;
        this.otp = otp;
    }

    public static CustomerOtp create(String customerId, Otp otp) throws CustomIllegalArgumentException {
        return new CustomerOtp(
                Id.generate(),
                Id.of(customerId),
                otp
        );
    }

    public static CustomerOtp createFromAdapter(Id id, Id customerId, Otp otp) {
        return new CustomerOtp(id, customerId, otp);
    }

    public void verify(String otpCodeToVerify) throws CustomIllegalArgumentException {
        if (this.otp.isExpired()) {
            throw new CustomIllegalArgumentException("OTP code has expired");
        }
        if (!this.otp.value().equals(otpCodeToVerify)) {
            throw new CustomIllegalArgumentException("Invalid OTP code");
        }
    }

    public Snapshot snapshot() {
        return new Snapshot(id.value(), customerId.value(), otp.value(), otp.expiredIn().toString());
    }

    public void updateOtp(Otp newOtp) {
        otp = newOtp;
    }


    public record Snapshot(String id, String customerId, String otpCode, String expiredIn) {
    }

}
