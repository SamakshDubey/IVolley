package com.studiojpp.ivolley.params.exception;

public class ParameterMissingException extends Exception {

    public ParameterMissingException(String param) {
        super("Missing parameter: " + param);
    }
}
