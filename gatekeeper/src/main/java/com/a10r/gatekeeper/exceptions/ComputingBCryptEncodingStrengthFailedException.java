package com.a10r.gatekeeper.exceptions;

public class ComputingBCryptEncodingStrengthFailedException extends RuntimeException{
    private static final long serialVersionUID = -448774046844L;

    public ComputingBCryptEncodingStrengthFailedException(String message) {
        super(message);
    }
}
