package com.petitapetit.miml.domain.notification.entity;

import com.petitapetit.miml.domain.notification.TempSong;
import com.petitapetit.miml.domain.notification.TempUser;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class SongAddedNotification extends Notification {
    private String songName;
    private String songArtist;

    public SongAddedNotification(TempUser user, TempSong song){
        super(user.getEmail());
        this.songArtist = song.getArtist().getName();
        this.songName = song.getName();
    }

    @Override
    public String makeSubject() {
        return "스트리밍 서비스 신곡 추가 알림";
    }

    @Override
    public String makeText() {
        StringBuilder sb = new StringBuilder();
        sb.append(getSongArtist());
        sb.append(getSongName());
        return sb.toString();
    }
}