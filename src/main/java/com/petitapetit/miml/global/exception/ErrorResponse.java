package com.petitapetit.miml.global.exception;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Builder @Getter
@RequiredArgsConstructor
public class ErrorResponse {

    private final String code;
    private final String message;
}
