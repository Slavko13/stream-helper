package com.streamershelper.streamers.exception.http;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR) // по умолчанию
public class CustomHttpException extends RuntimeException {

    public CustomHttpException(String message) {
        super(message);
    }

    public CustomHttpException(String message, Throwable cause) {
        super(message, cause);
    }
}


