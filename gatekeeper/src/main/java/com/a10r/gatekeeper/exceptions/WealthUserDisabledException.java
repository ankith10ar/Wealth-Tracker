package com.a10r.gatekeeper.exceptions;

public class WealthUserDisabledException extends RuntimeException{

    private static final long serialVersionUID = -448774046844L;
    private static final String ERROR_CODE = "USER_DISABLED";

    public WealthUserDisabledException() {
        super("User account is disabled");
    }

    public String getErrorCode() {
        return ERROR_CODE;
    }
}
