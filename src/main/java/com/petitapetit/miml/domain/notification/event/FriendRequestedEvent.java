package com.petitapetit.miml.domain.notification.event;

import lombok.Getter;

@Getter
// 친구 요청을 했을 때 발행(publish) 될 event
public class FriendRequestedEvent {
    private final String currentUserName;  // 친구요청한 유저명
    private final String requestedUserEmail;  // 친구 요청 받은 유저 이메일

    public FriendRequestedEvent(String currentUserName, String requestedUserEmail) {
        this.currentUserName = currentUserName;
        this.requestedUserEmail = requestedUserEmail;
    }
}

