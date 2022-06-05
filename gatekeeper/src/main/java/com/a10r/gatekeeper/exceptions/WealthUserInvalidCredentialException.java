package com.a10r.gatekeeper.exceptions;

public class WealthUserInvalidCredentialException extends RuntimeException{

    private static final long serialVersionUID = -448774046844L;

    public WealthUserInvalidCredentialException() {
        super("Invalid credentials");
    }
}
