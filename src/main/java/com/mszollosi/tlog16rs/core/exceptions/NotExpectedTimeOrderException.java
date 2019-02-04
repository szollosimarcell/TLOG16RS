package com.mszollosi.tlog16rs.core.exceptions;

/**
 *
 * @author precognox
 */
public class NotExpectedTimeOrderException extends RuntimeException{

    private final String message;

    public NotExpectedTimeOrderException(String message) {
        this.message = message;
    }
    
    @Override
    public String getMessage() {
        return message;
    }
}
