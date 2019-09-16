package com.freenow.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "The search String Passed was Invalid.")
public class InvalidQueryStringException extends Exception
{
    private static final long serialVersionUID = 3324141180275166358L;


    public InvalidQueryStringException(String message)
    {
        super(message);
    }

}
