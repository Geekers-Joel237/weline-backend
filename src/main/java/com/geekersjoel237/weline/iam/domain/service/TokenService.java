package com.geekersjoel237.weline.iam.domain.service;

import com.geekersjoel237.weline.iam.domain.entities.Customer;

/**
 * Created on 04/10/2025
 *
 * @author Geekers_Joel237
 **/
public interface TokenService {

    String generateToken(Customer customer);

}
