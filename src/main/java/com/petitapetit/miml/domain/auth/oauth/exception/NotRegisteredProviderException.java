package com.petitapetit.miml.domain.auth.oauth.exception;

public class NotRegisteredProviderException extends RuntimeException {
	public NotRegisteredProviderException(){
		super("지원하지 않는 provider 입니다.");
	}
}
