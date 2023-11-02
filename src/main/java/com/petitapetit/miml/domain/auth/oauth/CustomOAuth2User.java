package com.petitapetit.miml.domain.auth.oauth;

import java.util.List;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;

import com.petitapetit.miml.domain.auth.oauth.userinfo.OAuth2UserInfo;
import com.petitapetit.miml.domain.member.model.Member;

import lombok.Getter;

/*
 *     OAuth2 로그인 직후 SecurityContext 의 Authentication Token 내에 저장될 객체
 *     로그인 이외의 요청시에는 사용되지 않으나 로그인 후 JWT 발행 등에 사용된다.
 */
@Getter
public class CustomOAuth2User extends DefaultOAuth2User {

	private Member user;
	private String providerName; // spotify, kakao, naver
	private String accessToken; // spotify에서 내려준 access token

	/**
	 * OAuth2 사용자 정보, 사용자 계정 기반으로 CustomOAuth2User 객체를 생성할때 사용하는 생성자
	 * @param oAuth2UserInfo OAuth2 사용자 정보 객체
	 * @param user OAuth2 계정 정보를 기반으로 생성/조회한 사용자 계정 엔티티
	 */
	public CustomOAuth2User(OAuth2UserInfo oAuth2UserInfo, Member user, String accessToken) {
		super(List.of(new SimpleGrantedAuthority(user.getRole().name())), oAuth2UserInfo.getAttributes(),
			user.getProvider().getAttributeKey());
		this.user = user;
		this.providerName = user.getProvider().getProviderName();
		this.accessToken = accessToken;
	}

	// 시큐리티 컨텍스트 내의 인증 정보를 가져와 하는 작업을 수행할 경우 계정 식별자가 사용되도록 조치
	@Override
	public String getName() {
		return String.valueOf(user.getProviderId());
	}

	public OAuth2Provider getProvider() {
		return OAuth2Provider.getEnum(providerName);
	}

	public String getAccessToken() {
		return accessToken;
	}
}
