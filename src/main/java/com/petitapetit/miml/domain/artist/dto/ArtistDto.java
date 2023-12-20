package com.petitapetit.miml.domain.artist.dto;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class ArtistDto {
	@AllArgsConstructor
	@NoArgsConstructor
	@Getter
	public static class LikeRequest {
		@NotNull
		private Long artistId; // 좋아요할 아티스트의 아이디
	}

	@AllArgsConstructor
	@NoArgsConstructor
	@Getter
	@Builder
	public static class LikeResponse {
		private Long artistId; // 좋아요한 아티스트의 아이디
		private String artistName;
	}
}

