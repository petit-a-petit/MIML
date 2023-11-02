package com.petitapetit.miml.domain.auth.oauth.provider;

import java.util.Arrays;

import com.petitapetit.miml.domain.auth.oauth.exception.NotRegisteredProviderException;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * providerName : OAuth2 인증 요청시 URL 로 들어오는 OAuth2 서비스 제공자명
 * attributeKey : OAuth2 인증 후 계정 정보를 가져올 때 사용하는 키 값
 */
@Getter
@RequiredArgsConstructor
public enum OAuth2Provider {
	SPOTIFY("spotify", "id");

	private final String providerName;
	private final String attributeKey;

	public static OAuth2Provider getEnum(String providerName) {
		return Arrays.stream(OAuth2Provider.values())
			.filter(value -> value.providerName.equals(providerName))
			.findFirst()
			.orElseThrow(() -> new NotRegisteredProviderException());
	}
}
