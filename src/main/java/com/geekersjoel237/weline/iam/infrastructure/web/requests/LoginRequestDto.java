package com.geekersjoel237.weline.iam.infrastructure.web.requests;

import jakarta.validation.constraints.NotBlank;

/**
 * Created on 04/10/2025
 *
 * @author Geekers_Joel237
 **/

public record LoginRequestDto(
        @NotBlank(message = "Le numéro de téléphone ne peut pas être vide")
        String phoneNumber
) {
}
