package com.petitapetit.miml.domain.auth.oauth.filter;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.petitapetit.miml.domain.auth.oauth.CustomOAuth2User;
import com.petitapetit.miml.domain.auth.oauth.OAuth2Provider;
import com.petitapetit.miml.domain.auth.oauth.userinfo.SpotifyUserInfo;
import com.petitapetit.miml.domain.member.model.Member;
import com.petitapetit.miml.domain.member.model.RoleType;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class SimpleAuthFilter extends OncePerRequestFilter {
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
		FilterChain filterChain) throws ServletException, IOException {

		if (request.getHeader(HttpHeaders.AUTHORIZATION) != null) {
			String accessToken = request.getHeader(HttpHeaders.AUTHORIZATION);
			Map<String, Object> attribute = Map.of(
				"id", "spotify",
				"accessToken", accessToken
			);

			SecurityContextHolder.getContext().setAuthentication(
				new OAuth2AuthenticationToken(
					new CustomOAuth2User(new SpotifyUserInfo(attribute), createMember(), accessToken),
					Collections.emptySet(),
					"id"
				)
			);
		}
		filterChain.doFilter(request, response);
	}

	private Member createMember() {
		return Member.builder()
			.memberId(1000L)
			.name("name")
			.email("email")
			.image("image")
			.role(RoleType.ROLE_USER)
			.provider(OAuth2Provider.SPOTIFY)
			.providerId("provider_id")
			.build();
	}
}
