package com.petitapetit.miml.domain.friendship.service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.petitapetit.miml.domain.auth.oauth.CustomOAuth2User;
import com.petitapetit.miml.domain.friendship.dto.FriendshipDto;
import com.petitapetit.miml.domain.friendship.exception.FriendshipConflictException;
import com.petitapetit.miml.domain.friendship.exception.FriendshipNotFoundException;
import com.petitapetit.miml.domain.friendship.model.Friendship;
import com.petitapetit.miml.domain.friendship.repository.FriendshipRepository;
import com.petitapetit.miml.domain.member.dto.MemberDto;
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

		// 친구 신청 내역이 존재하거나 이미 친구라면 예외 처리
		if (friendshipRepository.existsByFromMemberAndToMember(fromMember, toMember)) {
			throw new FriendshipConflictException();
		}

		// 정방향 레코드
		Friendship friendship = Friendship.builder()
			.toMember(toMember)
			.fromMember(fromMember)
			.isFriend(Boolean.TRUE)
			.build();
		// 역방향 레코드
		Friendship reversedFriendship = Friendship.builder()
			.toMember(fromMember)
			.fromMember(toMember)
			.isFriend(Boolean.FALSE)
			.build();
		friendshipRepository.saveAll(Arrays.asList(friendship, reversedFriendship));
	}

	// 친구 요청 수락
	public void acceptFriendship(
		FriendshipDto.AcceptRequest acceptRequest,
		CustomOAuth2User oAuth2User
	) {
		// 나에게 친구를 요청한 사용자
		Member fromMember = memberRepository.findById(acceptRequest.getFromMemberId())
			.orElseThrow(() -> new MemberNotFoundException());
		// 역방향 레코드로 해당 Friendship 조회 (fromMember 가 로그인한 사용자)
		Friendship friendship = friendshipRepository.findByFromMemberAndToMemberAndIsFriend(
				oAuth2User.getUser(), fromMember, Boolean.FALSE)
			.orElseThrow(() -> new FriendshipNotFoundException());
		// 친구 관계 갱신
		friendship.acceptRequest();
	}

	// 내가 친구 요청 보낸 회원 조회 기능 (fromMember: 나 -> toMember: 상대방)
	public List<FriendshipDto.NotFriendResponse> getToMemberList(CustomOAuth2User oAuth2User) {
		// toMember 가 로그인한 사용자이면서 아직 친구가 아닌 Friendship 조회 (역방향 레코드로 조회)
		List<Friendship> toMembers = friendshipRepository.findFriendshipsByToMemberAndIsFriend(
			oAuth2User.getUser(), Boolean.FALSE);

		return toMembers.stream()
			.map(friendship -> FriendshipDto.NotFriendResponse.builder()
				.memberId(friendship.getFromMember().getMemberId())
				.memberName(friendship.getFromMember().getName())
				.createdAt(friendship.getCreatedAt())
				.build()
			)
			.collect(Collectors.toList());
	}

	// 나에게 친구 요청 보낸 회원 조회 기능 (fromMember: 상대방 -> toMember: 나)
	public List<FriendshipDto.NotFriendResponse> getFromMemberList(CustomOAuth2User oAuth2User) {
		// fromMember 가 로그인한 사용자이면서 아직 친구가 아닌 Friendship 조회 (역방향 레코드로 조회)
		List<Friendship> fromMembers = friendshipRepository.findFriendshipsByFromMemberAndIsFriend(
			oAuth2User.getUser(), Boolean.FALSE);

		return fromMembers.stream()
			.map(friendship -> FriendshipDto.NotFriendResponse.builder()
				.memberId(friendship.getToMember().getMemberId())
				.memberName(friendship.getToMember().getName())
				.createdAt(friendship.getCreatedAt())
				.build()
			)
			.collect(Collectors.toList());
	}

	// 나와 서로 친구인 회원 조회 기능
	public List<MemberDto.BriefInfoResponse> getFriendList(CustomOAuth2User oAuth2User) {
		List<Member> friends = friendshipRepository.findFriends(oAuth2User.getUser());

		return friends.stream()
			.map(friend -> MemberDto.BriefInfoResponse.builder()
				.memberId(friend.getMemberId())
				.memberName(friend.getName())
				.build())
			.collect(Collectors.toList());
	}
}
