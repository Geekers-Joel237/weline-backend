package com.geekersjoel237.weline.iam.application.command.login;

/**
 * Created on 04/10/2025
 *
 * @author Geekers_Joel237
 **/
public record LoginResponse(boolean isSent, String message) {
    public static LoginResponse ofSuccess(String message) {
        return new LoginResponse(true, message);
    }

    public static LoginResponse ofFailure(String message) {
        return new LoginResponse(false, message);
    }

}
