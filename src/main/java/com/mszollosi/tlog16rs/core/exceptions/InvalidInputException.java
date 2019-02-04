package com.mszollosi.tlog16rs.core.exceptions;

/**
 *
 * @author mszollosi
 */
public class InvalidInputException extends RuntimeException{
    
    private final String message;

    public InvalidInputException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
