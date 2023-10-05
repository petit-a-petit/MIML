package com.petitapetit.miml.domain.notification.model;

import com.petitapetit.miml.domain.notification.TempUser;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

@Getter
@NoArgsConstructor
@Entity
public class FriendRequestedNotification extends Notification {
    private String userName;

    public FriendRequestedNotification(TempUser user){
        super(user);
        this.userName = user.getEmail();
    }

    @Override
    public String makeSubject() {
        return "스트리밍 서비스 친구 추가 요청 알림";
    }

    @Override
    public String makeText() {
        StringBuilder sb = new StringBuilder();
        sb.append(getUserName());
        return sb.toString();
    }
}