package com.petitapetit.miml.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public enum CommonErrorCode implements ErrorCode{

    FORBIDDEN(HttpStatus.FORBIDDEN, "플레이리스트에 대한 권한이 없습니다."),
    PLAYLIST_NOT_FOUND(HttpStatus.NOT_FOUND, "플레이리스트가 존재하지 않습니다.");

    private final HttpStatus httpStatus;
    private final String message;

}
