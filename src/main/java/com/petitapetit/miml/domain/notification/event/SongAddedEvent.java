package com.petitapetit.miml.domain.notification.event;

import com.petitapetit.miml.domain.notification.TempSong;

// 신곡이 추가될 때 발행(publish) 될 event
public class SongAddedEvent {
    // 임시 곡
    private final TempSong song;

    public SongAddedEvent(TempSong song) {
        this.song = song;
    }

    public TempSong getSong() {
        return this.song;
    }
}

