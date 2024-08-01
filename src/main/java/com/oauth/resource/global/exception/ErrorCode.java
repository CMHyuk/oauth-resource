package com.oauth.resource.global.exception;

public interface ErrorCode {

    int getCode();

    int getHttpStatusCode();

    String getMessage();
}
