package com.a10r.gatekeeper.models;


import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ErrorResponse {

    private String errorCode;
    private String errorMessage;
    private String dateTime;

}
