package com.petitapetit.miml.domain.friendship.service;

import org.springframework.stereotype.Service;

import com.petitapetit.miml.domain.auth.oauth.CustomOAuth2User;
import com.petitapetit.miml.domain.friendship.dto.FriendshipDto;
import com.petitapetit.miml.domain.friendship.model.Friendship;
import com.petitapetit.miml.domain.friendship.repository.FriendshipRepository;
import com.petitapetit.miml.domain.member.exception.MemberNotFoundException;
import com.petitapetit.miml.domain.member.model.Member;
import com.petitapetit.miml.domain.member.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FriendshipService {
	private final MemberRepository memberRepository;
	private final FriendshipRepository friendshipRepository;

	public void createFriendship(
		FriendshipDto.CreateRequest createRequest,
		CustomOAuth2User oAuth2User
	) {
		// 친구 요청을 보낸 Member
		Member fromMember = oAuth2User.getUser();

		// 친구 요청을 받은 Member
		Member toMember = memberRepository.findById(createRequest.getToMemberId())
			.orElseThrow(() -> new MemberNotFoundException());

		// 정방향 레코드와 역방향 레코드 추가
		friendshipRepository.save(Friendship.builder()
			.toMember(toMember)
			.fromMember(fromMember)
			.isFriend(Boolean.FALSE)
			.build());
		friendshipRepository.save(Friendship.builder()
			.toMember(fromMember)
			.fromMember(toMember)
			.isFriend(Boolean.FALSE)
			.build());
	}
}
