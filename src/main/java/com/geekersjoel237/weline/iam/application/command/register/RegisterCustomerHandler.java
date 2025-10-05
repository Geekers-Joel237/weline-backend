package com.geekersjoel237.weline.iam.application.command.register;

import com.geekersjoel237.weline.iam.domain.entities.Customer;
import com.geekersjoel237.weline.iam.domain.entities.CustomerOtp;
import com.geekersjoel237.weline.iam.domain.repositories.CustomerRepository;
import com.geekersjoel237.weline.iam.domain.repositories.OtpRepository;
import com.geekersjoel237.weline.iam.domain.repositories.OtpSender;
import com.geekersjoel237.weline.iam.domain.vo.Otp;
import com.geekersjoel237.weline.iam.domain.vo.PhoneNumber;
import com.geekersjoel237.weline.shared.domain.exceptions.CustomIllegalArgumentException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created on 04/10/2025
 *
 * @author Geekers_Joel237
 **/

@Service
public class RegisterCustomerHandler {
    private final CustomerRepository customerRepository;
    private final OtpSender otpSender;
    private final OtpRepository otpRepository;

    public RegisterCustomerHandler(CustomerRepository customerRepository, OtpSender otpSender, OtpRepository otpRepository) {
        this.customerRepository = customerRepository;
        this.otpSender = otpSender;
        this.otpRepository = otpRepository;
    }

    @Transactional
    public RegisterCommandResponse handle(RegisterCustomerCommand command) {
        if (!command.hasAcceptedRights()) {
            return RegisterCommandResponse.ofFailure("Customer must accept the terms and conditions");
        }
        PhoneNumber phoneNumber;
        Customer customer;
        CustomerOtp customerOtp;

        try {
            phoneNumber = new PhoneNumber(command.phoneNumber());

            if (customerRepository.ofPhoneNumber(phoneNumber.value()).isPresent()) {
                return RegisterCommandResponse.ofFailure("Phone number already exists");
            }

            customer = Customer.create(
                    command.customerId(),
                    phoneNumber
            );

            customerOtp = CustomerOtp.create(
                    customer.snapshot().id(),
                    Otp.generateOtp()
            );

        } catch (CustomIllegalArgumentException e) {
            return RegisterCommandResponse.ofFailure(e.getMessage());
        }


        customerRepository.add(customer.snapshot());
        otpSender.send(phoneNumber.value(), customerOtp.snapshot().otpCode());
        otpRepository.add(customerOtp.snapshot());


        return RegisterCommandResponse.ofSuccess();
    }
}
