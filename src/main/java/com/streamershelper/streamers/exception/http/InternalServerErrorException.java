package com.streamershelper.streamers.exception.http;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
public class InternalServerErrorException extends CustomHttpException
{
    public InternalServerErrorException(String message)
    {
        super(message);
    }

    public InternalServerErrorException(String message, Throwable cause)
    {
        super(message, cause);
    }
}
