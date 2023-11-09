package com.petitapetit.miml.domain.notification.entity;

import com.petitapetit.miml.domain.notification.event.FriendRequestedEvent;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

@Getter
@NoArgsConstructor
@Entity
public class FriendRequestedNotification extends Notification {
    private String requestedUserName;  // 친구 요청 받은 유저 이름

    public static FriendRequestedNotification of(FriendRequestedEvent event) {
        return new FriendRequestedNotification(event.getCurrentUserName(), event.getRequestedUserName());
    }

    private FriendRequestedNotification(String currentUserEmail, String requestedUserEmail){
        super(currentUserEmail);
        this.requestedUserName = requestedUserEmail;
    }

    @Override
    public String makeSubject() {
        return "스트리밍 서비스 친구 추가 요청 알림";
    }

    @Override
    public String makeText() {
        StringBuilder sb = new StringBuilder();
        sb.append(getRequestedUserName());
        return sb.toString();
    }
}