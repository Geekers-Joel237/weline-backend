package com.geekersjoel237.weline.queue.infrastructure.web.api;

import com.geekersjoel237.weline.queue.appication.command.takeTicket.TakeTicketResponse;
import com.geekersjoel237.weline.shared.infrastructure.web.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created on 05/10/2025
 *
 * @author Geekers_Joel237
 **/


@Tag(name = "Queues", description = "Opérations relatives aux files d'attente")
@RequestMapping("/api/v1/queues")
public interface TakeTicketApi {

    @Operation(
            summary = "Prendre un ticket pour une file d'attente",
            description = "Permet à un utilisateur authentifié de prendre un ticket pour une file d'attente spécifique. L'ID de l'utilisateur est extrait du token JWT.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "Ticket créé avec succès"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "403", description = "Accès refusé (Token invalide ou manquant)"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "File d'attente non trouvée"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "409", description = "Conflit (ex: l'utilisateur est déjà dans la file)")
    })

    @PostMapping("/{queueId}/tickets")
    ResponseEntity<ApiResponse<TakeTicketResponse>> takeTicket(
            @Parameter(description = "ID de la file d'attente pour laquelle prendre un ticket", required = true) String queueId,
            Authentication authentication
    );
}
