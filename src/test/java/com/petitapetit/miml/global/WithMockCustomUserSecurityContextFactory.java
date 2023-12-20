package com.petitapetit.miml.global;

import java.util.List;
import java.util.Map;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

import com.petitapetit.miml.domain.auth.oauth.CustomOAuth2User;
import com.petitapetit.miml.domain.auth.oauth.OAuth2Provider;
import com.petitapetit.miml.domain.auth.oauth.userinfo.SpotifyUserInfo;
import com.petitapetit.miml.domain.member.model.Member;
import com.petitapetit.miml.domain.member.model.RoleType;

public class WithMockCustomUserSecurityContextFactory implements WithSecurityContextFactory<WithMockCustomUser> {

	@Override
	public SecurityContext createSecurityContext(WithMockCustomUser annotation) {

		final SecurityContext securityContext = SecurityContextHolder.createEmptyContext();

		Member member = createMember(annotation);
		final OAuth2AuthenticationToken oAuth2AuthenticationToken = new OAuth2AuthenticationToken(
			new CustomOAuth2User(createSpotifyUserInfo(annotation), member, annotation.accessToken()),
			List.of(new SimpleGrantedAuthority(member.getRole().name())),
			"id"
		);

		securityContext.setAuthentication(oAuth2AuthenticationToken);
		return securityContext;
	}

	private Member createMember(WithMockCustomUser annotation) {
		return Member.builder()
			.name(annotation.username())
			.role(RoleType.ROLE_USER)
			.provider(OAuth2Provider.SPOTIFY)
			.providerId("provider_id")
			.build();
	}

	private SpotifyUserInfo createSpotifyUserInfo(WithMockCustomUser annotation) {
		Map<String, Object> attribute = Map.of(
			"id", "spotify",
			"accessToken", annotation.accessToken()
		);
		return new SpotifyUserInfo(attribute);
	}

}
