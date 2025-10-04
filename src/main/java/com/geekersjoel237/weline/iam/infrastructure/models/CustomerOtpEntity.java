package com.geekersjoel237.weline.iam.infrastructure.models;

import com.geekersjoel237.weline.iam.domain.entities.CustomerOtp;
import com.geekersjoel237.weline.iam.domain.vo.Otp;
import com.geekersjoel237.weline.shared.domain.exceptions.CustomIllegalArgumentException;
import com.geekersjoel237.weline.shared.domain.vo.Id;
import com.geekersjoel237.weline.shared.infrastructure.persistence.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

/**
 * Created on 04/10/2025
 *
 * @author Geekers_Joel237
 **/

@Entity
@Table(name = "customer_otps")
@Getter
@Setter
@NoArgsConstructor
public class CustomerOtpEntity extends BaseEntity {
    @Column(nullable = false)
    private String customerId;

    @Column(nullable = false)
    private String otpCode;

    @Column(nullable = false)
    private Instant expiredIn;

    public static CustomerOtpEntity fromDomain(CustomerOtp.Snapshot customerOtp) {
        CustomerOtpEntity entity = new CustomerOtpEntity();
        entity.setId(customerOtp.id());
        entity.setCustomerId(customerOtp.customerId());
        entity.setOtpCode(customerOtp.otpCode());
        entity.setExpiredIn(Instant.parse(customerOtp.expiredIn()));
        return entity;

    }

    public CustomerOtp toDomain() {
        try {
            return CustomerOtp.createFromAdapter(
                    Id.of(this.getId()),
                    Id.of(this.getCustomerId()),
                    new Otp(this.getOtpCode(), this.getExpiredIn())
            );
        } catch (CustomIllegalArgumentException e) {
            throw new RuntimeException(e);
        }

    }

    public void update(String otpCode, String expiredId) {
        this.otpCode = otpCode;
        this.expiredIn = Instant.parse(expiredId);
    }
}
