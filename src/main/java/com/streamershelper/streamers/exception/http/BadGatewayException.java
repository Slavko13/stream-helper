package com.streamershelper.streamers.exception.http;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_GATEWAY)
public class BadGatewayException extends CustomHttpException
{
    public BadGatewayException(String message)
    {
        super(message);
    }

    public BadGatewayException(String message, Throwable cause)
    {
        super(message, cause);
    }
}
