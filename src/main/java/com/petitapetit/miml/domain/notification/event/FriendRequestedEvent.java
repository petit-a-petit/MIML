package com.petitapetit.miml.domain.notification.event;

import com.petitapetit.miml.domain.member.model.Member;
import lombok.Getter;

@Getter
// 친구 요청을 했을 때 발행(publish) 될 event
public class FriendRequestedEvent {
    private final String currentUserName;  // 친구요청한 유저 이름
    private final String requestedUserName;  // 친구 요청 받은 유저 이름

    public FriendRequestedEvent(Member currentUser, Member requestedUser) {
        this.currentUserName = currentUser.getEmail();
        this.requestedUserName = requestedUser.getEmail();
    }
}

