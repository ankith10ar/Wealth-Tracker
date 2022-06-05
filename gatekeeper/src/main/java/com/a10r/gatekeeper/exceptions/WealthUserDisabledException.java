package com.a10r.gatekeeper.exceptions;

public class WealthUserDisabledException extends RuntimeException{

    private static final long serialVersionUID = -448774046844L;

    public WealthUserDisabledException() {
        super("User account is disabled");
    }
}
