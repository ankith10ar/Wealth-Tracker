package com.a10r.gatekeeper.exceptions;

public class WealthUserInvalidCredentialException extends RuntimeException{

    private static final long serialVersionUID = -448774046844L;
    private static final String ERROR_CODE = "WRONG_CREDENTIALS";

    public WealthUserInvalidCredentialException() {
        super("Invalid credentials");
    }

    public String getErrorCode() {
        return ERROR_CODE;
    }
}
