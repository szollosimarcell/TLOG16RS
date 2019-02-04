package com.mszollosi.tlog16rs.core.exceptions;

public class ViolationOfRequiredWorkingMinutesException extends RuntimeException{

    private final String message = "You have to at least work 4 hours (240 minutes) a day! ";

    public String getMessage() {
        return message;
    }
}
