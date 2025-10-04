package com.geekersjoel237.weline.iam.infrastructure.web.dto;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * Created on 04/10/2025
 *
 * @author Geekers_Joel237
 **/
public record RegistrationRequestDto(
        @NotBlank(message = "Le numéro de téléphone ne peut pas être vide")
        @Size(min = 9, max = 13, message = "Le numéro de téléphone doit avoir entre 9 et 13 caractères")
        String phoneNumber,

        @AssertTrue(message = "Vous devez accepter les termes et conditions")
        boolean acceptedRights,

        @NotBlank(message = "L'ID du client ne peut pas être vide")
        String customerId
) {

}
