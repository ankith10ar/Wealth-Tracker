package com.a10r.gatekeeper.controllers;

import com.a10r.gatekeeper.exceptions.ComputingBCryptEncodingStrengthFailedException;
import com.a10r.gatekeeper.exceptions.MyBeanCreationException;
import com.a10r.gatekeeper.exceptions.WealthUserDisabledException;
import com.a10r.gatekeeper.exceptions.WealthUserInvalidCredentialException;
import com.a10r.gatekeeper.models.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Date;

@RestControllerAdvice
public class GatekeeperControllerAdvice {

    @ExceptionHandler(ComputingBCryptEncodingStrengthFailedException.class)
    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse computingBCryptError(ComputingBCryptEncodingStrengthFailedException exception) {
        return new ErrorResponse(exception.getErrorCode(), exception.getMessage(), new Date().toString());
    }

    @ExceptionHandler(MyBeanCreationException.class)
    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse beanCreationError(MyBeanCreationException exception) {
        return new ErrorResponse(exception.getErrorCode(), exception.getMessage(), new Date().toString());
    }

    @ExceptionHandler(WealthUserDisabledException.class)
    @ResponseStatus(code = HttpStatus.UNAUTHORIZED)
    public ErrorResponse userDisabledError(WealthUserDisabledException exception) {
        return new ErrorResponse(exception.getErrorCode(), exception.getMessage(), new Date().toString());
    }

    @ExceptionHandler(WealthUserInvalidCredentialException.class)
    @ResponseStatus(code = HttpStatus.UNAUTHORIZED)
    public ErrorResponse wrongCredentialsError(WealthUserInvalidCredentialException exception) {
        return new ErrorResponse(exception.getErrorCode(), exception.getMessage(), new Date().toString());
    }

}
