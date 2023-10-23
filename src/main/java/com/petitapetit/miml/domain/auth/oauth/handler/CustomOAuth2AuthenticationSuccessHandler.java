package com.petitapetit.miml.domain.auth.oauth.handler;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import com.petitapetit.miml.domain.auth.oauth.CustomOAuth2User;
import com.petitapetit.miml.global.util.SessionUtil;

@Component
public class CustomOAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

	@Value("${callback-url-scheme}")
	private String callbackUrlScheme;

	@Override
	public void onAuthenticationSuccess(
		HttpServletRequest request,
		HttpServletResponse response,
		Authentication authentication
	) throws IOException, ServletException {

		OAuth2AuthenticationToken oauthToken = (OAuth2AuthenticationToken) authentication;
		CustomOAuth2User oauthUser = (CustomOAuth2User) oauthToken.getPrincipal();
		String accessToken = oauthUser.getAccessToken();

		SecurityContextHolder.getContext().setAuthentication(authentication);
		String originalRequestUri = SessionUtil.getOriginalRequestUri(request.getSession());

		String targetUrl = createTargetUrl(originalRequestUri);
		getRedirectStrategy().sendRedirect(request, response, targetUrl);
	}

	private String createTargetUrl(String uri)
		throws UnsupportedEncodingException {
		return UriComponentsBuilder
			.fromUriString(callbackUrlScheme + uri)
			.build().toUriString();
	}
}
