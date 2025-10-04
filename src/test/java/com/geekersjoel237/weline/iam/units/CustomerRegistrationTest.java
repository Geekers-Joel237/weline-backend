package com.geekersjoel237.weline.iam.units;

import com.geekersjoel237.weline.iam.application.command.register.RegisterCustomerCommand;
import com.geekersjoel237.weline.iam.application.command.register.RegisterCustomerHandler;
import com.geekersjoel237.weline.iam.domain.repositories.CustomerRepository;
import com.geekersjoel237.weline.iam.domain.vo.PhoneNumber;
import com.geekersjoel237.weline.shared.domain.exceptions.CustomIllegalArgumentException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Created on 04/10/2025
 *
 * @author Geekers_Joel237
 **/

@SpringBootTest
public class CustomerRegistrationTest {
    private CustomerRepository customerRepository;

    @BeforeEach
    void setup() {
        customerRepository = new InMemoryCustomerRepository();
    }

    @Test
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
    public void shouldRegisterCustomer() throws CustomIllegalArgumentException {
        var command = new RegisterCustomerCommand(
                "001",
                "689 456 123",
                true
        );
        var handler = new RegisterCustomerHandler(customerRepository);
        var res = handler.handle(command);

        assert ((InMemoryCustomerRepository) customerRepository).customers.size() == 1;
        assert res.isCreated();
    }
}
