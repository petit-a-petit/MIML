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
	public static class CreateRequest {
		@NotNull
		private Long toMemberId; // 친구 요청을 보낼 회원의 아이디
	}

	@AllArgsConstructor
	@NoArgsConstructor
	@Getter
	@Builder
	public static class FetchResponse {
		private Long toMemberId;
		private String toMemberName;
		private LocalDateTime createdAt;
	}
}
