package com.geekersjoel237.weline.queue.infrastructure.web.api;

import com.geekersjoel237.weline.queue.appication.command.callNextTicket.CallNextTicketResponse;
import com.geekersjoel237.weline.shared.infrastructure.web.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * Created on 08/10/2025
 *
 * @author Geekers_Joel237
 **/
public interface CallNextTicketApi {

    @Operation(
            summary = "Appeler le prochain ticket dans une file",
            description = "Permet à un agent de faire avancer la file. Le premier ticket en attente devient le ticket en cours.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Ticket suivant appelé avec succès"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "File d'attente non trouvée")
    })
    @PostMapping("/api/v1/queues/{queueId}/next")
    ResponseEntity<ApiResponse<CallNextTicketResponse>> callNextTicket(
            @Parameter(description = "ID de la file d'attente à faire avancer", required = true) @PathVariable String queueId
    );
}
