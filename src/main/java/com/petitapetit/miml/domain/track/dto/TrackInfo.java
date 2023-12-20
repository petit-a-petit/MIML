package com.petitapetit.miml.domain.track.dto;

import com.petitapetit.miml.domain.track.entity.Track;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TrackInfo {
    private Long id;
    private int rank;
    private String uri;
    private String name;
    private String source;

    public TrackInfo(Track track) {
        this.id = track.getId();
        this.rank = track.getRank();
        this.uri = track.getUri();
        this.name = track.getName();
        this.source = track.getSource();
    }
}
