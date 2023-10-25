package com.petitapetit.miml.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class RestApiException extends RuntimeException{

    private final ErrorCode errorCode;
}
