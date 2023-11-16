package com.petitapetit.miml.domain.friendship.api;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.petitapetit.miml.domain.auth.oauth.CustomOAuth2User;
import com.petitapetit.miml.domain.friendship.dto.FriendshipDto;
import com.petitapetit.miml.domain.friendship.service.FriendshipService;
import com.petitapetit.miml.domain.member.dto.MemberDto;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/friendship")
@RequiredArgsConstructor
public class FriendshipController {

	private final FriendshipService friendshipService;

	// 친구 신청
	@PostMapping()
	public ResponseEntity<Void> createFriendship(
		@RequestBody @Validated FriendshipDto.CreateRequest createRequest,
		@AuthenticationPrincipal CustomOAuth2User oAuth2User
	) {
		friendshipService.createFriendship(createRequest, oAuth2User);
		return ResponseEntity.status(HttpStatus.CREATED).body(null);
	}

	// 친구 신청 수락
	@PostMapping("/manage")
	public ResponseEntity<Void> acceptFriendship(
		@RequestBody @Validated FriendshipDto.AcceptRequest acceptRequest,
		@AuthenticationPrincipal CustomOAuth2User oAuth2User
	) {
		friendshipService.acceptFriendship(acceptRequest, oAuth2User);
		return ResponseEntity.status(HttpStatus.OK).body(null);
	}

	// 내가 친구 요청 보낸 회원 조회 기능
	@GetMapping("/to-members")
	public ResponseEntity<List<FriendshipDto.NotFriendResponse>> getToMemberList(
		@AuthenticationPrincipal CustomOAuth2User oAuth2User
	) {
		List<FriendshipDto.NotFriendResponse> fetchResponseList = friendshipService.getToMemberList(oAuth2User);
		return ResponseEntity.status(HttpStatus.OK).body(fetchResponseList);
	}

	// 나에게 친구 요청 보낸 회원 조회 기능
	@GetMapping("/from-members")
	public ResponseEntity<List<FriendshipDto.NotFriendResponse>> getFromMemberList(
		@AuthenticationPrincipal CustomOAuth2User oAuth2User
	) {
		List<FriendshipDto.NotFriendResponse> fetchResponseList = friendshipService.getFromMemberList(oAuth2User);
		return ResponseEntity.status(HttpStatus.OK).body(fetchResponseList);
	}

	// 나와 서로 친구인 회원 조회 기능
	@GetMapping("/friends")
	public ResponseEntity<List<MemberDto.BriefInfoResponse>> getFriendList(
		@AuthenticationPrincipal CustomOAuth2User oAuth2User
	) {
		List<MemberDto.BriefInfoResponse> friendList = friendshipService.getFriendList(oAuth2User);
		return ResponseEntity.status(HttpStatus.OK).body(friendList);
	}
}