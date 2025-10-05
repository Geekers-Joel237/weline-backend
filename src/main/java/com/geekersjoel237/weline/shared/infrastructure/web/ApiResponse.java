package com.geekersjoel237.weline.shared.infrastructure.web;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

/**
 * Created on 04/10/2025
 *
 * @author Geekers_Joel237
 **/


@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {

    private final String message;
    private final T data;

    private ApiResponse(String message, T data) {
        this.message = message;
        this.data = data;
    }

    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>("Operation successful", data);
    }

    public static <T> ApiResponse<T> success(String message, T data) {
        return new ApiResponse<>(message, data);
    }

    public static <T> ApiResponse<T> failure(String message) {
        return new ApiResponse<>(message, null);
    }

    public String message(){
        return message;
    }

    public T data(){
        return data;
    }
}
