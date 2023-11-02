package com.petitapetit.miml.domain.friendship.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.petitapetit.miml.domain.auth.oauth.CustomOAuth2User;
import com.petitapetit.miml.domain.friendship.dto.FriendshipDto;
import com.petitapetit.miml.domain.friendship.service.FriendshipService;

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
}
