package com.geekersjoel237.weline.iam.units;

import com.geekersjoel237.weline.iam.application.command.register.RegisterCommandResponse;
import com.geekersjoel237.weline.iam.application.command.register.RegisterCustomerCommand;
import com.geekersjoel237.weline.iam.application.command.register.RegisterCustomerHandler;
import com.geekersjoel237.weline.iam.domain.repositories.CustomerRepository;
import com.geekersjoel237.weline.iam.domain.repositories.OtpRepository;
import com.geekersjoel237.weline.notifications.OtpSender;
import com.geekersjoel237.weline.iam.domain.vo.Otp;
import com.geekersjoel237.weline.iam.domain.vo.PhoneNumber;
import com.geekersjoel237.weline.shared.domain.exceptions.CustomIllegalArgumentException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneOffset;

/**
 * Created on 04/10/2025
 *
 * @author Geekers_Joel237
 **/

@SpringBootTest
public class CustomerRegistrationTest {
    private CustomerRepository customerRepository;
    private OtpSender otpSender;
    private OtpRepository otpRepository;

    @BeforeEach
    void setup() {
        customerRepository = new InMemoryCustomerRepository();
        otpSender = new InMemoryOtpSender();
        otpRepository = new InMemoryOtpRepository();
    }

    @Test
    @DisplayName("Phone Number VO should correctly normalize and validate Cameroonian numbers")
    public void shouldValidateCameroonianPhoneNumber() throws CustomIllegalArgumentException {
        var case1 = "237698745671";
        var case2 = "695 897 320";
        var case3 = "695897320";
        var case4 = "6 987 456 123 000";

        var phoneNumber1 = new PhoneNumber(case1);
        var phoneNumber2 = new PhoneNumber(case2);
        var phoneNumber3 = new PhoneNumber(case3);

        assert phoneNumber1.value().equals("+237698745671");
        assert phoneNumber2.value().equals("+237695897320");
        assert phoneNumber3.value().equals("+237695897320");
        Assertions.assertThrows(CustomIllegalArgumentException.class, () -> new PhoneNumber(case4));
        Assertions.assertThrows(CustomIllegalArgumentException.class, () -> new PhoneNumber(null));
        Assertions.assertThrows(CustomIllegalArgumentException.class, () -> new PhoneNumber(""));
    }

    @Test
    @DisplayName("Handler should successfully register a new customer with valid data")
    public void shouldRegisterCustomer() {
        var command = new RegisterCustomerCommand(
                "001",
                "689 456 123",
                true
        );
        var res = registerCustomer(command);

        assert ((InMemoryCustomerRepository) customerRepository).customers.size() == 1;
        assert res.isCreated();
        assert res.message().equals("Customer Created Successfully !");

        var foundCustomer = customerRepository.ofId(command.customerId());
        assert foundCustomer.isPresent();
        assert command.customerId().equals(foundCustomer.get().snapshot().id());
        assert foundCustomer.get().snapshot().status().equals("PENDING_VERIFICATION");
    }

    private RegisterCommandResponse registerCustomer(RegisterCustomerCommand command) {
        var handler = new RegisterCustomerHandler(customerRepository, otpSender, otpRepository);
        return handler.handle(command);
    }

    @Test
    @DisplayName("Handler should send an OTP after successfully registering a customer")
    public void shouldSendOtpCodeAfterRegisterCustomer() {
        // GIVEN a valid registration command
        var command = new RegisterCustomerCommand(
                "001",
                "689 456 123",
                true
        );

        // WHEN the handler is called
        registerCustomer(command);

        // THEN the OtpSender should have been called correctly
        var inMemoryOtpSender = (InMemoryOtpSender) otpSender;
        Assertions.assertEquals(1, inMemoryOtpSender.timesCalled());

        var sentOtp = inMemoryOtpSender.getSentOtps().getFirst();
        Assertions.assertEquals("+237689456123", sentOtp.phoneNumber());
        Assertions.assertNotNull(sentOtp.otp());
    }

    @Test
    public void shouldFailedRegistrationIfCustomerDontAcceptRights() {
        var command = new RegisterCustomerCommand(
                "001",
                "689 456 123",
                false
        );

        var res = registerCustomer(command);

        Assertions.assertFalse(res.isCreated());
        Assertions.assertEquals("Customer must accept the terms and conditions", res.message());
        Assertions.assertEquals(0, ((InMemoryCustomerRepository) customerRepository).customers.size());
        Assertions.assertEquals(0, ((InMemoryOtpSender) otpSender).timesCalled());
    }

    @Test
    @DisplayName("OTP should be valid and expire in 5 minutes")
    public void otpCodeShouldExpireIn5Min() {
        // GIVEN a fixed point in time
        var now = Instant.now();
        var clock = Clock.fixed(now, ZoneOffset.UTC);

        // WHEN an OTP is generated using the fixed clock
        var otp = Otp.generateOtp(clock);

        // THEN its properties should be correct
        Assertions.assertNotNull(otp);
        Assertions.assertNotNull(otp.value());
        Assertions.assertEquals(6, otp.value().length());

        // AND its expiration should be exactly 5 minutes in the future
        Assertions.assertEquals(now.plusSeconds(300), otp.expiredIn());
    }
}
