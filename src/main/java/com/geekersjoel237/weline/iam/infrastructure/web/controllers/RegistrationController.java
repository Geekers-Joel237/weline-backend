package com.geekersjoel237.weline.iam.infrastructure.web.controllers;

import com.geekersjoel237.weline.iam.application.command.register.RegisterCommandResponse;
import com.geekersjoel237.weline.iam.application.command.register.RegisterCustomerCommand;
import com.geekersjoel237.weline.iam.application.command.register.RegisterCustomerHandler;
import com.geekersjoel237.weline.iam.application.command.validateOtp.ValidateOtpCommand;
import com.geekersjoel237.weline.iam.application.command.validateOtp.ValidateOtpHandler;
import com.geekersjoel237.weline.iam.application.command.validateOtp.ValidateOtpResponse;
import com.geekersjoel237.weline.iam.infrastructure.web.api.RegistrationApi;
import com.geekersjoel237.weline.iam.infrastructure.web.dto.RegistrationRequestDto;
import com.geekersjoel237.weline.iam.infrastructure.web.dto.VerifyOtpRequestDto;
import com.geekersjoel237.weline.shared.infrastructure.web.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * Created on 04/10/2025
 *
 * @author Geekers_Joel237
 **/
@RestController
public class RegistrationController implements RegistrationApi {
    private final RegisterCustomerHandler registerCustomerHandler;
    private final ValidateOtpHandler validateOtpHandler;

    public RegistrationController(
            RegisterCustomerHandler registerCustomerHandler,
            ValidateOtpHandler validateOtpHandler
    ) {
        this.registerCustomerHandler = registerCustomerHandler;
        this.validateOtpHandler = validateOtpHandler;
    }

    @Override
    public ResponseEntity<ApiResponse<RegisterCommandResponse>> register(RegistrationRequestDto request) {
        var command = new RegisterCustomerCommand(
                request.customerId(),
                request.phoneNumber(),
                request.acceptedRights()
        );

        var response = registerCustomerHandler.handle(command);

        return ResponseEntity.ok(
                ApiResponse.success("Registration process attempted.", response)
        );

    }

    @Override
    public ResponseEntity<ApiResponse<ValidateOtpResponse>> verifyOtp(VerifyOtpRequestDto request) {
        var command = new ValidateOtpCommand(
                request.phoneNumber(),
                request.otpCode()
        );

        ValidateOtpResponse response = validateOtpHandler.handle(command);

        return ResponseEntity.ok(
                ApiResponse.success("Verification process attempted.", response)
        );

    }
}
