// package com.petitapetit.miml.domain.auth.oauth.handler;
//
// import java.io.IOException;
// import java.io.UnsupportedEncodingException;
// import java.util.Optional;
//
// import javax.servlet.http.HttpServletRequest;
// import javax.servlet.http.HttpServletResponse;
//
// import org.springframework.beans.factory.annotation.Value;
// import org.springframework.security.core.Authentication;
// import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
// import org.springframework.stereotype.Component;
// import org.springframework.web.util.UriComponentsBuilder;
//
// import com.petitapetit.miml.domain.member.model.Member;
// import com.petitapetit.miml.domain.member.model.RoleType;
// import com.petitapetit.miml.domain.member.repository.MemberRepository;
// import com.petitapetit.miml.domain.auth.jwt.AuthToken;
// import com.petitapetit.miml.domain.auth.jwt.TokenProvider;
// import com.petitapetit.miml.domain.auth.oauth.CustomOAuth2User;
// import com.petitapetit.miml.domain.auth.oauth.provider.OAuth2Provider;
// import com.petitapetit.miml.domain.auth.oauth.provider.OAuth2UserInfo;
// import com.petitapetit.miml.domain.auth.oauth.provider.SpotifyUserInfo;
//
// import lombok.RequiredArgsConstructor;
//
// /*
//  * 요청으로 준 유저정보와 DB 에 담긴 유저정보가 일치할 때 JWT 토큰을 생성하는 필터
//  */
// @Component
// @RequiredArgsConstructor
// public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
//
// 	private final TokenProvider tokenProvider;
// 	// private final RefreshTokenService refreshTokenService;
// 	private final MemberRepository memberRepository;
//
// 	@Value("${callback-url-scheme}")
// 	private String callbackUrlScheme;
//
// 	@Override
// 	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
// 		Authentication authentication) throws IOException {
// 		System.out.println("------------- @@@ OAuth2AuthSucHandler !!!");
// 		// writeTokenResponse(request, response, authentication);
// 	}
//
// 	private void writeTokenResponse(HttpServletRequest request, HttpServletResponse response,
// 		Authentication authentication) throws IOException {
//
// 		CustomOAuth2User oauth2user = (CustomOAuth2User)authentication.getPrincipal();
// 		String userName = oauth2user.getName(); //authentication 의 name
//
// 		Optional<Member> oUser = memberRepository.findById(Long.parseLong(userName));
//
// 		String providerName = oauth2user.getProviderName(); //authentication 의 name
// 		OAuth2UserInfo oAuth2UserInfo = null;
// 		if (providerName.equals(OAuth2Provider.SPOTIFY.getProviderName())) {
// 			oAuth2UserInfo = new SpotifyUserInfo(oauth2user.getAttributes());
// 		}
// 		// else if (providerName.equals(OAuth2Provider.NAVER.getProviderName())) {
// 		// 	oAuth2UserInfo = new NaverUserInfo(oauth2user.getAttributes());
// 		// }
//
// 		// JWT 생성
// 		AuthToken accessToken = tokenProvider.generateToken(oAuth2UserInfo.getProviderId(), RoleType.ROLE_USER.name(),
// 			true);
// 		AuthToken refreshToken = tokenProvider.generateToken(oAuth2UserInfo.getProviderId(), RoleType.ROLE_USER.name(),
// 			false);
//
// 		// refreshTokenService.save(oAuth2UserInfo.getProviderId(), refreshToken.getToken());
//
// 		String targetUrl;
// 		if (oUser.isEmpty()) {
// 			targetUrl = createTargetUrl(accessToken.getToken(), Boolean.TRUE);
// 		} else {
// 			targetUrl = createTargetUrl(accessToken.getToken(), Boolean.FALSE);
// 		}
//
// 		getRedirectStrategy().sendRedirect(request, response, targetUrl);
// 	}
//
// 	private String createTargetUrl(String accessToken, Boolean isNewMember)
// 		throws UnsupportedEncodingException {
// 		return UriComponentsBuilder
// 			.fromUriString(callbackUrlScheme + "auth/success-login") // /auth 아니고 auth
// 			.queryParam("token", accessToken)
// 			.queryParam("isNewMember", isNewMember)
// 			.build().toUriString();
// 	}
//
// }
