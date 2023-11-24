package com.petitapetit.miml.domain.friendship.dto;

import java.time.LocalDateTime;

import com.sun.istack.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class FriendshipDto {
	@AllArgsConstructor
	@NoArgsConstructor
	@Getter
	@Builder
	public static class CreateRequest {
		@NotNull
		private Long toMemberId; // 내가 친구 요청을 보낼 회원의 아이디
	}

	@AllArgsConstructor
	@NoArgsConstructor
	@Getter
	public static class AcceptRequest {
		@NotNull
		private Long fromMemberId; // 나에게 친구 요청을 보낸 회원의 아이디
	}

	@AllArgsConstructor
	@NoArgsConstructor
	@Getter
	@Builder
	public static class NotFriendResponse {
		private Long memberId; // 친구 요청을 했거나 받은 회원의 아이디
		private String memberName;
		private LocalDateTime createdAt;
	}

	@AllArgsConstructor
	@NoArgsConstructor
	@Getter
	@Builder
	public static class AlreadyFriendResponse {
		private Long memberId; // 친구인 회원의 아이디
		private String memberName;
		private LocalDateTime createdAt;
	}
}
