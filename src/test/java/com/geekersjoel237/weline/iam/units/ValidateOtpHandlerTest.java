package com.geekersjoel237.weline.iam.units;

import com.geekersjoel237.weline.iam.application.command.validateOtp.ValidateOtpCommand;
import com.geekersjoel237.weline.iam.application.command.validateOtp.ValidateOtpHandler;
import com.geekersjoel237.weline.iam.domain.entities.Customer;
import com.geekersjoel237.weline.iam.domain.entities.CustomerOtp;
import com.geekersjoel237.weline.iam.domain.enums.CustomerStatusEnum;
import com.geekersjoel237.weline.iam.domain.service.TokenService;
import com.geekersjoel237.weline.iam.domain.vo.Otp;
import com.geekersjoel237.weline.iam.domain.vo.PhoneNumber;
import com.geekersjoel237.weline.shared.domain.exceptions.CustomIllegalArgumentException;
import com.geekersjoel237.weline.shared.domain.exceptions.NotFoundEntityException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneOffset;

public class ValidateOtpHandlerTest {

    private final String customerId = "customer-123";
    private final String validPhoneNumber = "691234567";
    private final String validOtpCode = "123456";
    private InMemoryCustomerRepository customerRepository;
    private InMemoryOtpRepository otpRepository;
    private ValidateOtpHandler handler;

    @BeforeEach
    void setUp() {
        customerRepository = new InMemoryCustomerRepository();
        otpRepository = new InMemoryOtpRepository();
        TokenService tokenService = new InMemoryTokenService();
        handler = new ValidateOtpHandler(customerRepository, otpRepository, tokenService);
    }

    private void seedPendingCustomer() throws CustomIllegalArgumentException {
        var customer = Customer.create(
                customerId,
                new PhoneNumber(validPhoneNumber)
        );
        customerRepository.add(customer.snapshot());
    }

    private void seedValidOtp() throws CustomIllegalArgumentException {
        var otp = new Otp(validOtpCode, Instant.now().plusSeconds(300));
        var customerOtp = CustomerOtp.create(customerId, otp);
        otpRepository.add(customerOtp.snapshot());
    }

    @Test
    @DisplayName("Should successfully validate OTP and verify customer")
    void shouldSuccessfullyValidateOtpAndVerifyCustomer() throws CustomIllegalArgumentException {
        // GIVEN a pending customer and a valid OTP exist
        seedPendingCustomer();
        seedValidOtp();

        var command = new ValidateOtpCommand(validPhoneNumber, validOtpCode);

        // WHEN the handler is called
        var response = handler.handle(command);

        // THEN the response should be successful
        Assertions.assertTrue(response.isValidated());

        // AND the customer should be verified
        var verifiedCustomer = customerRepository.ofId(customerId).get();
        Assertions.assertEquals(CustomerStatusEnum.VERIFIED.name(), verifiedCustomer.snapshot().status());

        // AND the OTP should be deleted
        Assertions.assertTrue(otpRepository.ofCustomerId(customerId).isEmpty());
    }

    @Test
    @DisplayName("Should fail validation when customer is not found")
    void shouldFailValidationWhenCustomerNotFound() {
        // GIVEN no customer exists
        var command = new ValidateOtpCommand(validPhoneNumber, validOtpCode);

        // WHEN the handler is called
        // THEN a NotFoundEntityException should be thrown
        Assertions.assertThrows(NotFoundEntityException.class, () -> handler.handle(command));
    }

    @Test
    @DisplayName("Should fail validation when OTP is not found for the customer")
    void shouldFailValidationWhenOtpIsNotFound() throws CustomIllegalArgumentException {
        // GIVEN a customer exists, but no OTP
        seedPendingCustomer();
        var command = new ValidateOtpCommand(validPhoneNumber, validOtpCode);

        // WHEN the handler is called
        var response = handler.handle(command);

        // THEN the response should be a failure
        Assertions.assertFalse(response.isValidated());
        Assertions.assertEquals("Invalid OTP code", response.message());
    }

    @Test
    @DisplayName("Should fail validation for an incorrect OTP code")
    void shouldFailValidationForIncorrectOtpCode() throws CustomIllegalArgumentException {
        // GIVEN a customer and a valid OTP exist
        seedPendingCustomer();
        seedValidOtp();

        var command = new ValidateOtpCommand(validPhoneNumber, "654321"); // Incorrect OTP

        // WHEN the handler is called
        var response = handler.handle(command);

        // THEN the response should be a failure
        Assertions.assertFalse(response.isValidated());
        Assertions.assertEquals("Invalid OTP code", response.message());

        // AND the OTP should NOT be deleted
        Assertions.assertTrue(otpRepository.ofCustomerId(customerId).isPresent());
    }

    @Test
    @DisplayName("Should fail validation for an expired OTP code")
    void shouldFailValidationForExpiredOtpCode() throws CustomIllegalArgumentException {
        // GIVEN a customer exists
        seedPendingCustomer();

        // AND an OTP was created more than 5 minutes ago
        var pastInstant = Instant.now().minusSeconds(301);
        var clock = Clock.fixed(pastInstant, ZoneOffset.UTC);
        var expiredOtp = Otp.generateOtp(clock); // This OTP is now expired

        var customerOtp = CustomerOtp.create(customerId, new Otp(validOtpCode, expiredOtp.expiredIn()));
        otpRepository.add(customerOtp.snapshot());

        var command = new ValidateOtpCommand(validPhoneNumber, validOtpCode);

        // WHEN the handler is called
        var response = handler.handle(command);

        // THEN the response should be a failure
        Assertions.assertFalse(response.isValidated());
        Assertions.assertEquals("OTP code has expired", response.message());

        // AND the OTP should NOT be deleted
        Assertions.assertTrue(otpRepository.ofCustomerId(customerId).isPresent());
    }

    @Test
    @DisplayName("Should throw an exception for an invalid phone number format")
    void shouldThrowExceptionForInvalidPhoneNumberFormat() {
        // GIVEN a command with an invalid phone number
        var command = new ValidateOtpCommand("invalid-number", validOtpCode);

        // WHEN the handler is called
        // THEN a RuntimeException should be thrown
        Assertions.assertThrows(RuntimeException.class, () -> handler.handle(command));
    }
}