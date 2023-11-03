package com.petitapetit.miml.domain.member.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class MemberDto {
	@AllArgsConstructor
	@NoArgsConstructor
	@Getter
	@Builder
	public static class BriefInfoResponse {
		private Long memberId;
		private String memberName;
	}

}
