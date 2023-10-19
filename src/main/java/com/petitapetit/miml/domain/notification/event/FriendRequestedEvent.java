package com.petitapetit.miml.domain.notification.event;

import com.petitapetit.miml.domain.notification.TempUser;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
// 친구 요청을 했을 때 발행(publish) 될 event
public class FriendRequestedEvent extends ApplicationEvent {
    private String currentUserName;  // 친구요청한 유저 이름
    private String requestedUserName;  // 친구 요청 받은 유저 이름

    public FriendRequestedEvent(Object source, TempUser currentUser, TempUser requestedUser) {
        super(source);
        this.currentUserName = currentUser.getEmail();
        this.requestedUserName = requestedUser.getEmail();
    }
}

