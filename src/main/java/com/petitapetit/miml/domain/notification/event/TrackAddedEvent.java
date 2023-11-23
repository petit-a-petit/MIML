package com.petitapetit.miml.domain.notification.event;

import com.petitapetit.miml.domain.track.entity.Track;

// 신곡이 추가될 때 발행(publish) 될 event
public class TrackAddedEvent {
    // 임시 곡
    private final Track track;

    public TrackAddedEvent(Track track) {
        this.track = track;
    }

    public Track getTrack() {
        return this.track;
    }
}

