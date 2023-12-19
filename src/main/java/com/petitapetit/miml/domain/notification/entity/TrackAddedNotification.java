package com.petitapetit.miml.domain.notification.entity;

import com.petitapetit.miml.domain.artist.domain.Artist;
import com.petitapetit.miml.domain.member.model.Member;
import com.petitapetit.miml.domain.track.entity.Track;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class TrackAddedNotification extends Notification {
    private String songName;
    @ElementCollection
    private List<String> songArtist;

    public static TrackAddedNotification of(Track song, Member user){
        return new TrackAddedNotification(user, song);
    }

    private TrackAddedNotification(Member user, Track track){
        super(user.getEmail());
        this.songArtist = track.getArtists().stream().map(Artist::getName).collect(Collectors.toList());
        this.songName = track.getName();
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