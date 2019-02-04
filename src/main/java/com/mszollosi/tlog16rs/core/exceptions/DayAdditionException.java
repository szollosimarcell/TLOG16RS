package com.mszollosi.tlog16rs.core.exceptions;

/**
 *
 * @author mszollosi
 */
public class DayAdditionException extends RuntimeException{
    
    private final String message;

    public DayAdditionException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
