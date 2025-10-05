package com.geekersjoel237.weline.partners.infrastructure.web.controllers;

import com.geekersjoel237.weline.partners.application.query.ListServicesQueryHandler;
import com.geekersjoel237.weline.partners.application.query.ListServicesResponse;
import com.geekersjoel237.weline.partners.infrastructure.web.api.ServiceApi;
import com.geekersjoel237.weline.shared.infrastructure.web.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created on 05/10/2025
 *
 * @author Geekers_Joel237
 **/
@RestController
public class ServiceController implements ServiceApi {

    private final ListServicesQueryHandler listServicesQueryHandler;

    public ServiceController(ListServicesQueryHandler listServicesQueryHandler) {
        this.listServicesQueryHandler = listServicesQueryHandler;
    }

    @Override
    public ResponseEntity<ApiResponse<List<ListServicesResponse>>> getAllServices() {
        var services = listServicesQueryHandler.handle();
        return ResponseEntity.ok(ApiResponse.success(services));
    }
}
