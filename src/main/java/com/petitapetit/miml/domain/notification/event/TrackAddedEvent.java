package com.petitapetit.miml.domain.notification.event;

import com.petitapetit.miml.domain.artist.domain.Artist;
import com.petitapetit.miml.domain.track.entity.Track;
import java.util.List;
import java.util.stream.Collectors;

// 신곡이 추가될 때 발행(publish) 될 event
public class TrackAddedEvent {
    // 임시 곡
    private final Track track;
    private List<String> artistsNames;

    public TrackAddedEvent(Track track,List<Artist> artists) {
        this.track = track;
        this.artistsNames = artists.stream().map(Artist::getName).collect(Collectors.toList());
    }

    public Track getTrack() {
        return this.track;
    }

    public List<String> getArtistsNames() {
        return artistsNames;
    }
}

