package com.freenow.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class CarSelectDeselectException extends Exception
{

    private static final long serialVersionUID = 3797263480950217670L;


    public CarSelectDeselectException(String message)
    {
        super(message);
    }

}
