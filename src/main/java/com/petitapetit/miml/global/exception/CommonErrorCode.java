package com.petitapetit.miml.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public enum CommonErrorCode implements ErrorCode{

    RESOURCE_NOT_FOUND(HttpStatus.NOT_FOUND, "Resource not exists");

    private final HttpStatus httpStatus;
    private final String message;
}
