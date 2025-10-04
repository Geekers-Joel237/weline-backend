package com.geekersjoel237.weline.iam.application.command.validateOtp;

import com.geekersjoel237.weline.iam.domain.entities.Customer;
import com.geekersjoel237.weline.iam.domain.entities.CustomerOtp;
import com.geekersjoel237.weline.iam.domain.repositories.CustomerRepository;
import com.geekersjoel237.weline.iam.domain.repositories.OtpRepository;
import com.geekersjoel237.weline.iam.domain.service.TokenService;
import com.geekersjoel237.weline.iam.domain.vo.PhoneNumber;
import com.geekersjoel237.weline.shared.domain.exceptions.CustomIllegalArgumentException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Created on 04/10/2025
 *
 * @author Geekers_Joel237
 **/

@Service
public class ValidateOtpHandler {
    private final CustomerRepository customerRepository;
    private final OtpRepository otpRepository;
    private final TokenService tokenService;

    public ValidateOtpHandler(
            CustomerRepository customerRepository,
            OtpRepository otpRepository,
            TokenService tokenService
    ) {
        this.customerRepository = customerRepository;
        this.otpRepository = otpRepository;
        this.tokenService = tokenService;
    }

    @Transactional
    public ValidateOtpResponse handle(ValidateOtpCommand command) {
        PhoneNumber phoneNumber;
        try {
            phoneNumber = new PhoneNumber(command.phoneNumber());

            Optional<Customer> optionalCustomer = customerRepository.ofPhoneNumber(phoneNumber.value());
            if (optionalCustomer.isEmpty()) {
                return ValidateOtpResponse.ofFailure("Customer with phone number " + phoneNumber.value() + " not found");
            }
            Customer existingCustomer = optionalCustomer.get();

            Optional<CustomerOtp> optionalOtp = otpRepository.ofCustomerId(existingCustomer.snapshot().id());

            if (optionalOtp.isEmpty()) {
                return ValidateOtpResponse.ofFailure("Invalid OTP code");
            }
            CustomerOtp customerOtp = optionalOtp.get();


            customerOtp.verify(command.otpCode());

            existingCustomer.verified();

            customerRepository.update(existingCustomer.snapshot());
            otpRepository.delete(customerOtp.snapshot().id());

            return ValidateOtpResponse.ofSuccess(tokenService.generateToken(existingCustomer));

        } catch (CustomIllegalArgumentException e) {
            return ValidateOtpResponse.ofFailure(e.getMessage());
        }

    }
}
