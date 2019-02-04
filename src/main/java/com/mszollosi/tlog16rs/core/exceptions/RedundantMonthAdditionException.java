package com.mszollosi.tlog16rs.core.exceptions;
/**
 *
 * @author mszollosi
 */
public class RedundantMonthAdditionException extends RuntimeException{
    
    private final String message = "This month already exists";

    public RedundantMonthAdditionException() {
    }

    @Override
    public String getMessage() {
        return message;
    }
}
