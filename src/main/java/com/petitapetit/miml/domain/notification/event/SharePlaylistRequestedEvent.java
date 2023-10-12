package com.petitapetit.miml.domain.notification.event;

import com.petitapetit.miml.domain.notification.TempUser;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
// 플레이리스트 공유 요청 했을 때 발행(publish) 될 event
public class SharePlaylistRequestedEvent extends ApplicationEvent {
    private String currentUserEmail;  // 플레이리스트 공유 요청한 유저 이름
    private String requestedUserEmail;  // 플레이리스트 공유 요청 받은 유저 이름

    public SharePlaylistRequestedEvent(Object source, TempUser currentUser, TempUser requestedUser) {
        super(source);
        this.currentUserEmail = currentUser.getEmail();
        this.requestedUserEmail = requestedUser.getEmail();
    }
}

