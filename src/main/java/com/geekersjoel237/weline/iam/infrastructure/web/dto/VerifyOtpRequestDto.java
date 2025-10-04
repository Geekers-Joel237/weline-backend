package com.geekersjoel237.weline.iam.infrastructure.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * Created on 04/10/2025
 *
 * @author Geekers_Joel237
 **/
public record VerifyOtpRequestDto(
        @NotBlank(message = "Le numéro de téléphone ne peut pas être vide")
        String phoneNumber,

        @NotBlank(message = "Le code OTP ne peut pas être vide")
        @Size(min = 6, max = 6, message = "L'OTP doit contenir 6 chiffres")
        String otpCode
) {}
