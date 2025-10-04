package com.geekersjoel237.weline.iam.application.command.login;

import com.geekersjoel237.weline.iam.domain.repositories.CustomerRepository;
import com.geekersjoel237.weline.iam.domain.repositories.OtpRepository;
import com.geekersjoel237.weline.iam.domain.repositories.OtpSender;
import com.geekersjoel237.weline.iam.domain.vo.Otp;
import com.geekersjoel237.weline.iam.domain.vo.PhoneNumber;
import com.geekersjoel237.weline.shared.domain.exceptions.CustomIllegalArgumentException;
import com.geekersjoel237.weline.shared.domain.exceptions.NotFoundEntityException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created on 04/10/2025
 *
 * @author Geekers_Joel237
 **/

@Service
public class LoginCustomerHandler {
    private final CustomerRepository customerRepository;
    private final OtpRepository otpRepository;
    private final OtpSender otpSender;

    public LoginCustomerHandler(CustomerRepository customerRepository, OtpRepository otpRepository, OtpSender otpSender) {
        this.customerRepository = customerRepository;
        this.otpRepository = otpRepository;
        this.otpSender = otpSender;
    }

    @Transactional
    public void handle(LoginCommand command) {
        PhoneNumber phoneNumber;
        try {
            phoneNumber = new PhoneNumber(command.phoneNumber());
        } catch (CustomIllegalArgumentException e) {
            throw new RuntimeException(e);
        }

        var customer = customerRepository.ofPhoneNumber(phoneNumber.value())
                .orElseThrow(() -> new NotFoundEntityException("Customer with phone number " + phoneNumber.value() + " not found."));

        var newOtp = Otp.generateOtp();


        var customerOtp = otpRepository.ofCustomerId(customer.snapshot().id()).orElseThrow(
                () -> new NotFoundEntityException("OTP for customer with id " + customer.snapshot().id() + " not found.")
        );
        customerOtp.updateOtp(newOtp);

        otpRepository.update(customerOtp.snapshot());

        otpSender.send(phoneNumber.value(), newOtp.value());
    }
}
