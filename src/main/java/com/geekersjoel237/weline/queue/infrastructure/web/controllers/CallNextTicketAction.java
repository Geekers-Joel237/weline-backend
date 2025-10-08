package com.geekersjoel237.weline.queue.infrastructure.web.controllers;

import com.geekersjoel237.weline.queue.appication.command.callNextTicket.CallNextTicketCommand;
import com.geekersjoel237.weline.queue.appication.command.callNextTicket.CallNextTicketHandler;
import com.geekersjoel237.weline.queue.appication.command.callNextTicket.CallNextTicketResponse;
import com.geekersjoel237.weline.queue.infrastructure.web.api.CallNextTicketApi;
import com.geekersjoel237.weline.shared.infrastructure.web.ApiResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created on 08/10/2025
 *
 * @author Geekers_Joel237
 **/

@RestController
@AllArgsConstructor
public class CallNextTicketAction implements CallNextTicketApi {

    private final CallNextTicketHandler callNextTicketHandler;

    @Override
    public ResponseEntity<ApiResponse<CallNextTicketResponse>> callNextTicket(@PathVariable String queueId) {
        var command = new CallNextTicketCommand(queueId);
        var res = callNextTicketHandler.handle(command);

        return ResponseEntity.ok(ApiResponse.success("Next ticket called successfully.", res));
    }
}
