package com.petitapetit.miml.domain.notification.entity;

import com.petitapetit.miml.domain.notification.event.FriendRequestedEvent;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

@Getter
@NoArgsConstructor
@Entity
public class FriendRequestedNotification extends Notification {
    private String currentUserName;  // 친구 요청한 유저명

    public static FriendRequestedNotification from(FriendRequestedEvent event) {
        return new FriendRequestedNotification(event.getCurrentUserName(), event.getRequestedUserEmail());
    }

    private FriendRequestedNotification(String currentUserName, String requestedUserEmail){
        super(requestedUserEmail);
        this.currentUserName = currentUserName;
    }

    @Override
    public String makeSubject() {
        return "스트리밍 서비스 친구 추가 요청 알림";
    }

    @Override
    public String makeText() {
        StringBuilder sb = new StringBuilder();
        sb.append(getCurrentUserName());
        return sb.toString();
    }
}