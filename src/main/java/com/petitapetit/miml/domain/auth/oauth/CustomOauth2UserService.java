package com.petitapetit.miml.domain.auth.oauth;

import java.util.Optional;

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.petitapetit.miml.domain.auth.oauth.provider.OAuth2Provider;
import com.petitapetit.miml.domain.auth.oauth.provider.OAuth2UserInfo;
import com.petitapetit.miml.domain.auth.oauth.provider.SpotifyUserInfo;
import com.petitapetit.miml.domain.member.model.Member;
import com.petitapetit.miml.domain.member.model.RoleType;
import com.petitapetit.miml.domain.member.repository.MemberRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CustomOauth2UserService extends DefaultOAuth2UserService {

	private final MemberRepository memberRepository;

	// userRequest 는 code를 받아서 accessToken을 응답 받은 객체
	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		OAuth2User oAuth2User = super.loadUser(userRequest);
		return processOAuth2User(userRequest, oAuth2User);
	}

	private OAuth2User processOAuth2User(OAuth2UserRequest userRequest, OAuth2User oAuth2User) {
		OAuth2UserInfo oAuth2UserInfo = null;

		Member member;

		// OAuth2 서비스 제공자 구분
		String registrationId = userRequest.getClientRegistration().getRegistrationId();
		if (registrationId.equals(OAuth2Provider.SPOTIFY.getProviderName())) {
			oAuth2UserInfo = new SpotifyUserInfo(oAuth2User.getAttributes());
		}

		Optional<Member> userOptional =
			memberRepository.findByProviderAndProviderId(oAuth2UserInfo.getProvider(),
				oAuth2UserInfo.getProviderId());

		boolean isFirst; // 최초 로그인 여부
		if (userOptional.isPresent()) { // 이미 가입한 회원이라면
			isFirst = false;
			member = userOptional.get();
			// member.updateEmail(oAuth2UserInfo.getEmail());
			memberRepository.save(member);
		} else {
			isFirst = true;

			// user의 패스워드가 null이기 때문에 OAuth 유저는 일반적인 로그인을 할 수 없음.
			member = Member.builder()
				.name(oAuth2UserInfo.getName())
				.email(oAuth2UserInfo.getEmail())
				.role(RoleType.ROLE_USER)
				.provider(oAuth2UserInfo.getProvider())
				.providerId(oAuth2UserInfo.getProviderId())
				.image(oAuth2UserInfo.getImage())
				.build();
			memberRepository.save(member);
		}

		return new CustomOAuth2User(oAuth2UserInfo, member, isFirst, userRequest.getAccessToken().getTokenValue());
	}
}
