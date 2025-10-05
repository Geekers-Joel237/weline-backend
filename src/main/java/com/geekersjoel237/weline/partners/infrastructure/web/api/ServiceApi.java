package com.geekersjoel237.weline.partners.infrastructure.web.api;

import com.geekersjoel237.weline.partners.application.query.ListServicesResponse;
import com.geekersjoel237.weline.shared.infrastructure.web.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Tag(name = "Services", description = "API for managing services offered by partners")
@RequestMapping("/api/v1/services")
public interface ServiceApi {

    @Operation(
            summary = "List all available services",
            description = "Retrieves a list of all services that users can join queues for."
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "Successfully retrieved the list of services"
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "500",
                    description = "Internal Server Error"
            )
    })
    @GetMapping
    ResponseEntity<ApiResponse<List<ListServicesResponse>>> getAllServices();
}