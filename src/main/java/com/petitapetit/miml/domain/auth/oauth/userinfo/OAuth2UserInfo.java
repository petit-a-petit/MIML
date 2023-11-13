package com.petitapetit.miml.domain.auth.oauth.userinfo;

// OAuth2.0 제공자들 마다 응답해주는 속성값이 달라서 공통으로 만들어준다.

import java.util.Map;

import com.petitapetit.miml.domain.auth.oauth.OAuth2Provider;

public abstract class OAuth2UserInfo {

	protected Map<String, Object> attributes;
	public OAuth2UserInfo(Map<String, Object> attributes) {
		this.attributes = attributes;
	}
	public abstract String getProviderId(); //소셜 식별 값 : 구글 - "sub", 카카오 - "id", 네이버 - "id"
	public abstract OAuth2Provider getProvider();
	public abstract String getEmail();
	public abstract String getName();
	public abstract String getImage();
	public Map<String, Object> getAttributes() {
		return this.attributes;
	}
}
