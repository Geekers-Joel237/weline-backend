package com.geekersjoel237.weline.queue.infrastructure.web.controllers;

import com.geekersjoel237.weline.iam.domain.entities.Customer;
import com.geekersjoel237.weline.queue.appication.command.takeTicket.TakeTicketCommand;
import com.geekersjoel237.weline.queue.appication.command.takeTicket.TakeTicketHandler;
import com.geekersjoel237.weline.queue.appication.command.takeTicket.TakeTicketResponse;
import com.geekersjoel237.weline.queue.infrastructure.web.api.TakeTicketApi;
import com.geekersjoel237.weline.shared.infrastructure.web.ApiResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created on 05/10/2025
 *
 * @author Geekers_Joel237
 **/

@RestController
@AllArgsConstructor
public class TakeTicketAction implements TakeTicketApi {
    private final TakeTicketHandler takeTicketHandler;

    @Override
    public ResponseEntity<ApiResponse<TakeTicketResponse>> takeTicket(String queueId, Authentication authentication) {
        Customer customer = (Customer) authentication.getPrincipal();
        String customerId = customer.snapshot().id();

        var command = new TakeTicketCommand(customerId, queueId);
        var response = takeTicketHandler.handle(command);

        return new ResponseEntity<>(
                ApiResponse.success("Ticket created successfully.", response),
                HttpStatus.CREATED
        );
    }
}
