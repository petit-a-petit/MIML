package com.petitapetit.miml.domain.notification.model;

import com.petitapetit.miml.domain.notification.TempUser;
import org.springframework.context.ApplicationEvent;

// 친구 요청을 했을 때 발행(publish) 될 event
public class FriendRequestedEvent extends ApplicationEvent {
    private TempUser user;

    public FriendRequestedEvent(Object source, TempUser user) {
        super(source);
        this.user = user;
    }

    public TempUser getUser() {
        return this.user;
    }
}

