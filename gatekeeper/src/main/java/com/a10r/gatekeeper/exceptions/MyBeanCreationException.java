package com.a10r.gatekeeper.exceptions;

public class MyBeanCreationException extends RuntimeException{

    private static final long serialVersionUID = -448774046844L;

    public MyBeanCreationException() {
        super("Error while creating the bean");
    }
}
