package com.geekersjoel237.weline.iam.application.command.register;

/**
 * Created on 04/10/2025
 *
 * @author Geekers_Joel237
 **/
public record RegisterCustomerCommand(String customerId, String phoneNumber, boolean hasAcceptedRights) {
}
