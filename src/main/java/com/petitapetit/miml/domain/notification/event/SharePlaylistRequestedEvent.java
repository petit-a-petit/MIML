package com.petitapetit.miml.domain.notification.event;

import com.petitapetit.miml.domain.member.model.Member;
import lombok.Getter;

@Getter
// 플레이리스트 공유 요청 했을 때 발행(publish) 될 event
public class SharePlaylistRequestedEvent {
    private final String currentUserName;  // 플레이리스트 공유 요청한 유저명
    private final String requestedUserEmail;  // 플레이리스트 공유 요청 받은 유저 이름

    public SharePlaylistRequestedEvent(Member currentUser, Member requestedUser) {
        this.currentUserName = currentUser.getName();
        this.requestedUserEmail = requestedUser.getEmail();
    }
}

