package com.petitapetit.miml.domain.notification.service;

import com.petitapetit.miml.domain.notification.TempSong;
import org.springframework.context.ApplicationEvent;

// 신곡이 추가될 때 발행(publish) 될 event
public class SongAddedEvent extends ApplicationEvent {
    // 임시 곡
    private TempSong song;

    public SongAddedEvent(Object source, TempSong song) {
        super(source);
        this.song = song;
    }

    public TempSong getSong() {
        return this.song;
    }
}

