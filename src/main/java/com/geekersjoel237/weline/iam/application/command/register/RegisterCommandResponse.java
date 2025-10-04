package com.geekersjoel237.weline.iam.application.command.register;

/**
 * Created on 04/10/2025
 *
 * @author Geekers_Joel237
 **/
public record RegisterCommandResponse(boolean isCreated, String message) {

    public static RegisterCommandResponse ofSuccess() {
        return new RegisterCommandResponse(true, "Customer Created Successfully !");
    }


    public static RegisterCommandResponse ofFailure(String message) {
        return new RegisterCommandResponse(false, message);
    }
}
