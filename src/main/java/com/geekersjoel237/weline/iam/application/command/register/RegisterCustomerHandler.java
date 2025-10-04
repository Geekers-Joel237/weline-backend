package com.geekersjoel237.weline.iam.application.command.register;

import com.geekersjoel237.weline.iam.domain.entities.Customer;
import com.geekersjoel237.weline.iam.domain.repositories.CustomerRepository;
import com.geekersjoel237.weline.iam.domain.vo.PhoneNumber;
import com.geekersjoel237.weline.shared.domain.exceptions.CustomIllegalArgumentException;

/**
 * Created on 04/10/2025
 *
 * @author Geekers_Joel237
 **/
public class RegisterCustomerHandler {
    private final CustomerRepository customerRepository;

    public RegisterCustomerHandler(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public RegisterCommandResponse handle(RegisterCustomerCommand command) throws CustomIllegalArgumentException {
        if (!command.hasAcceptedRights()) {
            RegisterCommandResponse.ofFailure();
        }
        var customer = Customer.create(
                command.customerId(),
                new PhoneNumber(command.phoneNumber())
        );

        customerRepository.add(customer.snapshot());

        return RegisterCommandResponse.ofSuccess();
    }
}
