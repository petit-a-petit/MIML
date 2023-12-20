package com.petitapetit.miml.domain.notification.entity;

import com.petitapetit.miml.domain.notification.event.SharePlaylistRequestedEvent;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

@Getter
@NoArgsConstructor
@Entity
public class SharePlaylistRequestedNotification extends Notification {
    private String currentUserName;  // 친구 요청한 유저명

    public static SharePlaylistRequestedNotification from(SharePlaylistRequestedEvent event) {
        return new SharePlaylistRequestedNotification(event.getCurrentUserName(), event.getRequestedUserEmail());
    }

    private SharePlaylistRequestedNotification(String currentUserName, String requestedUserEmail){
        super(requestedUserEmail);
        this.currentUserName = currentUserName;
    }

    @Override
    public String makeSubject() {
        return "스트리밍 서비스 플레이리스트 공유 신청 알림";
    }

    @Override
    public String makeText() {
        StringBuilder sb = new StringBuilder();
        sb.append(getCurrentUserName());
        return sb.toString();
    }
}