package com.petitapetit.miml.domain.auth.oauth;

import java.util.Optional;

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.petitapetit.miml.domain.auth.oauth.exception.NotRegisteredProviderException;
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
		OAuth2UserInfo oAuth2UserInfo = getOAuth2UserInfo(userRequest, oAuth2User);
		return new CustomOAuth2User(
			oAuth2UserInfo,
			getOrCreateMember(oAuth2UserInfo),
			userRequest.getAccessToken().getTokenValue()
		);
	}

	private OAuth2UserInfo getOAuth2UserInfo(OAuth2UserRequest userRequest, OAuth2User oAuth2User) {
		String registrationId = userRequest.getClientRegistration().getRegistrationId();
		if (registrationId.equals(OAuth2Provider.SPOTIFY.getProviderName())) {
			return new SpotifyUserInfo(oAuth2User.getAttributes());
		}
		throw new NotRegisteredProviderException();
	}

	private Member getOrCreateMember(OAuth2UserInfo oAuth2UserInfo) {
		Member existingMember = getExistingMember(oAuth2UserInfo);
		return existingMember != null ? existingMember : createMember(oAuth2UserInfo);
	}

	private Member getExistingMember(OAuth2UserInfo oAuth2UserInfo) {
		Optional<Member> oMember = memberRepository.findByProviderAndProviderId(
			oAuth2UserInfo.getProvider(),
			oAuth2UserInfo.getProviderId()
		);
		return oMember.orElse(null); // 회원이 없으면 null 반환
	}

	private Member createMember(OAuth2UserInfo oAuth2UserInfo) {
		Member member = Member.builder()
			.name(oAuth2UserInfo.getName())
			.email(oAuth2UserInfo.getEmail())
			.role(RoleType.ROLE_USER)
			.image(oAuth2UserInfo.getImage())
			.provider(oAuth2UserInfo.getProvider())
			.providerId(oAuth2UserInfo.getProviderId())
			.build();
		return memberRepository.save(member);
	}
}
