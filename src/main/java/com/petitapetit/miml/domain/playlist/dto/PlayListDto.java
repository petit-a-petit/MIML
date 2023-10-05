package com.petitapetit.miml.domain.playlist.dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class PlayListDto {

    @AllArgsConstructor @NoArgsConstructor
    public static class Post {
        private String name;
        private boolean publicYN;
    }
}
