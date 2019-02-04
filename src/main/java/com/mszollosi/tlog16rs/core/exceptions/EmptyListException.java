package com.mszollosi.tlog16rs.core.exceptions;

/**
 *
 * @author mszollosi
 */
public class EmptyListException extends RuntimeException{

    private final String message;
    
    public EmptyListException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
