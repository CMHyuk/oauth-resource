package com.oauth.resource.domain.user.exception;

import com.oauth.resource.global.exception.ErrorCode;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public enum UserErrorCode implements ErrorCode {

    NOT_FOUND(3001, 404, "존재하지 않는 사용자입니다."),
    FORBIDDEN(3001, 403, "권한이 없습니다.")
    ;

    private final int code;
    private final int httpStatusCode;
    private final String message;
}
