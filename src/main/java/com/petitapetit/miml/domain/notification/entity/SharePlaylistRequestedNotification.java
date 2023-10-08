package com.petitapetit.miml.domain.notification.entity;

import com.petitapetit.miml.domain.notification.TempUser;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

@Getter
@NoArgsConstructor
@Entity
public class SharePlaylistRequestedNotification extends Notification {
    private String userName;

    public SharePlaylistRequestedNotification(TempUser user){
        super(user);
        this.userName = user.getEmail();
    }

    @Override
    public String makeSubject() {
        return "스트리밍 서비스 플레이리스트 공유 신청 알림";
    }

    @Override
    public String makeText() {
        StringBuilder sb = new StringBuilder();
        sb.append(getUserName());
        return sb.toString();
    }
}