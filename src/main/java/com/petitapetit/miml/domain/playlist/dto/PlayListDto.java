package com.petitapetit.miml.domain.playlist.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
public class PlayListDto {

    @AllArgsConstructor @NoArgsConstructor
    @Getter @Builder
    public static class SaveRequest {
        private String name;
        private Boolean isPublic;
    }

    @AllArgsConstructor @NoArgsConstructor
    @Builder @Getter
    public static class SaveResponse {
        private Long playListId;
    }
}
