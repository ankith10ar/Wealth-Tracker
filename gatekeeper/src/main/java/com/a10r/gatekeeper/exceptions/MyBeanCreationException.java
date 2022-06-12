package com.a10r.gatekeeper.exceptions;

public class MyBeanCreationException extends RuntimeException{

    private static final long serialVersionUID = -448774046844L;
    private static final String ERROR_CODE = "BEAN_CREATION_FAILED";

    public MyBeanCreationException() {
        super("Error while creating the bean");
    }

    public String getErrorCode() {
        return ERROR_CODE;
    }
}
