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
	public static class toMemberResponse {
		private Long toMemberId; // 내가 친구 요청을 보낸 회원의 PK
		private String toMemberName; // 내가 친구 요청을 보낸 회원의 이름
		private LocalDateTime createdAt; // 친구 요청을 보낸 시각
	}
}
