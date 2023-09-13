package com.streamershelper.streamers.exception.http;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.FORBIDDEN)
public class ForbiddenException extends CustomHttpException
{
    public ForbiddenException(String message)
    {
        super(message);
    }

    public ForbiddenException(String message, Throwable cause)
    {
        super(message, cause);
    }
}
