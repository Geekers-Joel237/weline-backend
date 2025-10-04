package com.geekersjoel237.weline.iam.units;

import com.geekersjoel237.weline.iam.domain.entities.Customer;
import com.geekersjoel237.weline.iam.domain.service.TokenService;

import java.util.UUID;

/**
 * Created on 04/10/2025
 *
 * @author Geekers_Joel237
 **/
public class InMemoryTokenService implements TokenService {
    @Override
    public String generateToken(Customer customer) {
        return UUID.randomUUID().toString();
    }
}
