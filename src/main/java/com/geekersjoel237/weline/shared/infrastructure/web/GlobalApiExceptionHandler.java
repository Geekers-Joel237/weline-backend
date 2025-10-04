package com.geekersjoel237.weline.shared.infrastructure.web;

import com.geekersjoel237.weline.shared.domain.exceptions.TransactionalException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * Created on 04/10/2025
 *
 * @author Geekers_Joel237
 **/

@ControllerAdvice
public class GlobalApiExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({
            TransactionalException.class
    })
    public ResponseEntity<ApiResponse<Object>> handleTransactionalException(TransactionalException ex) {
        logger.error("An unexpected internal server error occurred", ex);
        return new ResponseEntity<>(
                ApiResponse.failure(ex.getMessage()),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }
}
