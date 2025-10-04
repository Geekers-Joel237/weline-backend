package com.geekersjoel237.weline.iam.infrastructure.web.api;

import com.geekersjoel237.weline.iam.application.command.register.RegisterCommandResponse;
import com.geekersjoel237.weline.iam.application.command.validateOtp.ValidateOtpResponse;
import com.geekersjoel237.weline.iam.infrastructure.web.dto.LoginRequestDto;
import com.geekersjoel237.weline.iam.infrastructure.web.dto.RegistrationRequestDto;
import com.geekersjoel237.weline.iam.infrastructure.web.dto.VerifyOtpRequestDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created on 04/10/2025
 *
 * @author Geekers_Joel237
 **/
@RequestMapping("/api/v1/auth")
@Tag(name = "1. Authentification", description = "Endpoints pour l'inscription et la connexion des clients")
public interface RegistrationApi {

    @Operation(summary = "Inscrire un nouveau client",
            description = "Lance le processus d'inscription en envoyant un OTP via WhatsApp au numéro de téléphone fourni.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OTP envoyé avec succès"),
            @ApiResponse(responseCode = "400", description = "Données invalides (ex: format du numéro)", content = @Content),
            @ApiResponse(responseCode = "409", description = "Le numéro de téléphone existe déjà", content = @Content)
    })
    @PostMapping("/register")
    ResponseEntity<com.geekersjoel237.weline.shared.infrastructure.web.ApiResponse<RegisterCommandResponse>> register(@Valid @RequestBody RegistrationRequestDto request);


    @Operation(summary = "Valider un OTP",
            description = "Vérifie l'OTP fourni par l'utilisateur pour activer le compte. Retourne un token d'authentification si la validation réussit.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Validation réussie, token retourné"),
            @ApiResponse(responseCode = "400", description = "Données invalides ou OTP incorrect", content = @Content),
            @ApiResponse(responseCode = "404", description = "Client non trouvé", content = @Content)
    })
    @PostMapping("/verify-otp")
    ResponseEntity<com.geekersjoel237.weline.shared.infrastructure.web.ApiResponse<ValidateOtpResponse>> verifyOtp(@Valid @RequestBody VerifyOtpRequestDto request);


    @Operation(summary = "Demander un OTP de connexion pour un client existant",
            description = "Lance le processus de connexion en envoyant un nouvel OTP via WhatsApp si le numéro de téléphone correspond à un compte existant.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OTP de connexion envoyé avec succès"),
            @ApiResponse(responseCode = "400", description = "Données invalides (ex: format du numéro)", content = @Content),
            @ApiResponse(responseCode = "404", description = "Aucun client trouvé pour ce numéro de téléphone", content = @Content)
    })
    @PostMapping("/login")
    ResponseEntity<com.geekersjoel237.weline.shared.infrastructure.web.ApiResponse<Void>> login(@Valid @RequestBody LoginRequestDto request);

}
