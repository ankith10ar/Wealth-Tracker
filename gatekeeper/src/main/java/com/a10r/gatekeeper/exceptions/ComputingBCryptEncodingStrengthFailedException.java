package com.a10r.gatekeeper.exceptions;

public class ComputingBCryptEncodingStrengthFailedException extends RuntimeException{
    private static final long serialVersionUID = -448774046844L;
    private static final String ERROR_CODE = "BCRYPT_COMPUTE_ERROR";

    public ComputingBCryptEncodingStrengthFailedException(String message) {
        super(message);
    }

    public String getErrorCode() {
        return ERROR_CODE;
    }
}
