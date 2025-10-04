package com.geekersjoel237.weline.iam.application.command.register;

/**
 * Created on 04/10/2025
 *
 * @author Geekers_Joel237
 **/
public record RegisterCommandResponse(boolean isCreated) {

    public static RegisterCommandResponse ofSuccess() {
        return new RegisterCommandResponse(true);
    }

    public static RegisterCommandResponse ofFailure() {
        return new RegisterCommandResponse(false);
    }
}
