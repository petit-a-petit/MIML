package com.petitapetit.miml.domain.notification.service;

import com.petitapetit.miml.domain.notification.TempUser;
import org.springframework.context.ApplicationEvent;

// 플레이리스트 공유 요청 했을 때 발행(publish) 될 event
public class SharePlaylistRequestedEvent extends ApplicationEvent {
    private TempUser tempUser;

    public SharePlaylistRequestedEvent(Object source, TempUser song) {
        super(source);
        this.tempUser = song;
    }

    public TempUser getTempUser() {
        return this.tempUser;
    }
}

