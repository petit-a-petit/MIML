package com.petitapetit.miml.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class BusinessException extends RuntimeException{

    private final ErrorCode errorCode;
}
