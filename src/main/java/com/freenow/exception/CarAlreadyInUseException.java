package com.freenow.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Requested Car is already in use by some other driver")
public class CarAlreadyInUseException extends Exception
{

    private static final long serialVersionUID = -2212996081777324712L;


    public CarAlreadyInUseException(String message)
    {
        super(message);
    }

}
