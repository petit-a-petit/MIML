package com.petitapetit.miml.domain.playlist.dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

public class PlayListDto {

    @AllArgsConstructor @NoArgsConstructor
    public static class Post {
        private String name;
        private boolean isPublic;
    }
}
