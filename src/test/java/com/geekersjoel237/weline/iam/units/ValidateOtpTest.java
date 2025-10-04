package com.geekersjoel237.weline.iam.units;

import com.geekersjoel237.weline.iam.application.command.register.RegisterCustomerCommand;
import com.geekersjoel237.weline.iam.application.command.register.RegisterCustomerHandler;
import com.geekersjoel237.weline.iam.application.command.validateOtp.ValidateOtpCommand;
import com.geekersjoel237.weline.iam.application.command.validateOtp.ValidateOtpHandler;
import com.geekersjoel237.weline.iam.domain.repositories.OtpRepository;
import com.geekersjoel237.weline.iam.domain.service.TokenService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Created on 04/10/2025
 *
 * @author Geekers_Joel237
 **/

@SpringBootTest()
public class ValidateOtpTest {

    private OtpRepository otpRepository;
    private TokenService tokenService;

    @BeforeEach
    void setup() {
        otpRepository = new InMemoryOtpRepository();
        tokenService = new InMemoryTokenService();
    }

    @Test
    public void shouldValidateOtp() {
        // GIVEN
        var customerRepository = new InMemoryCustomerRepository();
        var otpSender = new InMemoryOtpSender();
        var registerHandler = new RegisterCustomerHandler(customerRepository, otpSender, otpRepository);

        var registerCommand = new RegisterCustomerCommand(
                "customer-id-123",
                "677889900",
                true
        );
        registerHandler.handle(registerCommand);


        var sentOtp = otpSender.getSentOtps().getFirst();
        var phoneNumber = sentOtp.phoneNumber();
        var otp = sentOtp.otp();
        assert otpRepository.ofCustomerId("customer-id-123").isPresent();

        // WHEN
        var validateOtpHandler = new ValidateOtpHandler(customerRepository, otpRepository, tokenService);
        var validateCommand = new ValidateOtpCommand(
                phoneNumber,
                otp
        );

        var response = validateOtpHandler.handle(validateCommand);

        // THEN
        Assertions.assertTrue(response.isValidated());
        Assertions.assertEquals("OTP validated successfully!", response.message());

        var customer = customerRepository.ofId("customer-id-123").orElseThrow();
        Assertions.assertEquals("VERIFIED", customer.snapshot().status());

        assert otpRepository.ofCustomerId("customer-id-123").isEmpty();

    }
}
