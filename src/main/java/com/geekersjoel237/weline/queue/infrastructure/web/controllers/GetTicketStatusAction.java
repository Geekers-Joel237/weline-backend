package com.geekersjoel237.weline.queue.infrastructure.web.controllers;

import com.geekersjoel237.weline.queue.appication.command.getStatus.GetTicketStatusQueryHandler;
import com.geekersjoel237.weline.queue.appication.command.getStatus.TicketStatusResponse;
import com.geekersjoel237.weline.shared.domain.exceptions.CustomIllegalArgumentException;
import com.geekersjoel237.weline.shared.infrastructure.web.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created on 06/10/2025
 *
 * @author Geekers_Joel237
 **/
@RestController
@RequestMapping("/api/v1/tickets")
@Tag(name = "Tickets", description = "Opérations relatives aux tickets individuels")
public class GetTicketStatusAction {

    private final GetTicketStatusQueryHandler getTicketStatusQueryHandler;

    public GetTicketStatusAction(GetTicketStatusQueryHandler getTicketStatusQueryHandler) {
        this.getTicketStatusQueryHandler = getTicketStatusQueryHandler;
    }

    @GetMapping("/{ticketId}/status")
    @Operation(summary = "Obtenir le statut en temps réel d'un ticket", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<ApiResponse<TicketStatusResponse>> getTicketStatus(@PathVariable String ticketId) throws CustomIllegalArgumentException {
        var status = getTicketStatusQueryHandler.handle(ticketId);
        return ResponseEntity.ok(ApiResponse.success(status));
    }
}
