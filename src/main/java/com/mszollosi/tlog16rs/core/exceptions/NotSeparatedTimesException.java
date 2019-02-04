package com.mszollosi.tlog16rs.core.exceptions;

/**
 *
 * @author mszollosi
 */
public class NotSeparatedTimesException extends RuntimeException{

    private final String message;
    
    public NotSeparatedTimesException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
