package com.petitapetit.miml.domain.notification.event;

import com.petitapetit.miml.domain.artist.domain.Artist;
import com.petitapetit.miml.domain.track.entity.Track;
import java.util.List;
import java.util.stream.Collectors;

// 신곡이 추가될 때 발행(publish) 될 event
public class TrackAddedEvent {
    private final Track track;  // 생성된 노래 제목
    private List<String> artistsNames;  // 좋아요한 사용자들을 찾기 위한 아티스트 이름들

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

